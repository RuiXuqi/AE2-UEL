/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.client.gui.implementations;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.widgets.GuiNumberBox;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.container.implementations.ContainerPriority;
import appeng.core.AEConfig;
import appeng.core.AELog;
import appeng.core.localization.GuiText;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketSwitchGuis;
import appeng.core.sync.packets.PacketValueConfig;
import appeng.helpers.IPriorityHost;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

import java.io.IOException;


public class GuiPriority extends AEBaseGui {

    private GuiNumberBox priority;
    private GuiTabButton originalGuiBtn;

    private GuiButton plus1;
    private GuiButton plus10;
    private GuiButton plus100;
    private GuiButton plus1000;
    private GuiButton minus1;
    private GuiButton minus10;
    private GuiButton minus100;
    private GuiButton minus1000;

    private GuiBridge OriginalGui;

    public GuiPriority(final InventoryPlayer inventoryPlayer, final IPriorityHost te) {
        super(new ContainerPriority(inventoryPlayer, te));
    }

    @Override
    public void initGui() {
        super.initGui();

        final int a = AEConfig.instance().priorityByStacksAmounts(0);
        final int b = AEConfig.instance().priorityByStacksAmounts(1);
        final int c = AEConfig.instance().priorityByStacksAmounts(2);
        final int d = AEConfig.instance().priorityByStacksAmounts(3);

        this.plus1 = newTextButtonToList(0, this.guiLeft + 20, this.guiTop + 32, 22, 20, "+" + a);
        this.plus10 = newTextButtonToList(0, this.guiLeft + 48, this.guiTop + 32, 28, 20, "+" + b);
        this.plus100 = newTextButtonToList(0, this.guiLeft + 82, this.guiTop + 32, 32, 20, "+" + c);
        this.plus1000 = newTextButtonToList(0, this.guiLeft + 120, this.guiTop + 32, 38, 20, "+" + d);

        this.minus1 = newTextButtonToList(0, this.guiLeft + 20, this.guiTop + 69, 22, 20, "-" + a);
        this.minus10 = newTextButtonToList(0, this.guiLeft + 48, this.guiTop + 69, 28, 20, "-" + b);
        this.minus100 = newTextButtonToList(0, this.guiLeft + 82, this.guiTop + 69, 32, 20, "-" + c);
        this.minus1000 = newTextButtonToList(0, this.guiLeft + 120, this.guiTop + 69, 38, 20, "-" + d);

        final ContainerPriority con = ((ContainerPriority) this.inventorySlots);
        final ItemStack myIcon = con.getPriorityHost().getItemStackRepresentation();
        this.OriginalGui = con.getPriorityHost().getGuiBridge();

        if (this.OriginalGui != null && !myIcon.isEmpty()) {
            this.originalGuiBtn = newTabButtonToList(myIcon, myIcon.getDisplayName(), this.itemRender);
        }

        this.priority = new GuiNumberBox(this.fontRenderer, this.guiLeft + 60, this.guiTop + 55, 61, 12, Long.class);
        this.priority.setMaxStringLength(16);
        this.priority.setVisible(true);
        this.priority.setFocused(true);
        ((ContainerPriority) this.inventorySlots).setTextField(this.priority);
    }

    @Override
    public void drawFG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.fontRenderer.drawString(GuiText.Priority.getLocal(), 8, 6, 4210752);
    }

    @Override
    public void drawBG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.bindTexture("guis/priority.png");
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        this.priority.drawTextBox();
    }

    @Override
    protected void actionPerformed(final GuiButton btn) throws IOException {
        super.actionPerformed(btn);

        if (btn == this.originalGuiBtn) {
            NetworkHandler.instance().sendToServer(new PacketSwitchGuis(this.OriginalGui));
        }

        final boolean isPlus = btn == this.plus1 || btn == this.plus10 || btn == this.plus100 || btn == this.plus1000;
        final boolean isMinus = btn == this.minus1 || btn == this.minus10 || btn == this.minus100
                || btn == this.minus1000;

        if (isPlus || isMinus) {
            this.addQty(this.getQty(btn));
        }
    }

    private void addQty(final int i) {
        try {
            String out = this.priority.getText();

            boolean fixed = false;
            while (out.startsWith("0") && out.length() > 1) {
                out = out.substring(1);
                fixed = true;
            }

            if (fixed) {
                this.priority.setText(out);
            }

            if (out.isEmpty()) {
                out = "0";
            }

            long result = Long.parseLong(out);
            result += i;

            this.priority.setText(out = Long.toString(result));

            NetworkHandler.instance().sendToServer(new PacketValueConfig("PriorityHost.Priority", out));
        } catch (final NumberFormatException e) {
            // nope..
            this.priority.setText("0");
        } catch (final IOException e) {
            AELog.debug(e);
        }
    }

    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        if (!this.checkHotbarKeys(key)) {
            if ((key == 211
                    || key == 205 || key == 203 || key == 14 || character == '-' || Character.isDigit(character))
                    && this.priority
                            .textboxKeyTyped(character, key)) {
                try {
                    String out = this.priority.getText();

                    boolean fixed = false;
                    while (out.startsWith("0") && out.length() > 1) {
                        out = out.substring(1);
                        fixed = true;
                    }

                    if (fixed) {
                        this.priority.setText(out);
                    }

                    if (out.isEmpty()) {
                        out = "0";
                    }

                    NetworkHandler.instance().sendToServer(new PacketValueConfig("PriorityHost.Priority", out));
                } catch (final IOException e) {
                    AELog.debug(e);
                }
            } else {
                super.keyTyped(character, key);
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        final int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        final int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        final int i = Mouse.getEventDWheel();
        if (i != 0 && priority.isMouseIn(x, y)) {
            addQty(i > 0 ? 1 : -1);
        }
    }

    protected String getBackground() {
        return "guis/priority.png";
    }
}
