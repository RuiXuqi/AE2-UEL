package appeng.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class GuiTextButton extends GuiButton {

    public GuiTextButton(int id, int x, int y, String text) {
        super(id, x, y, text);
    }

    public GuiTextButton(int id, int x, int y, int widthIn, int heightIn, String text) {
        super(id, x, y, widthIn, heightIn, text);
    }

    @Override
    public void drawButton(@NotNull Minecraft mc, int x, int y, float partial) {
        if (this.visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.renderEngine.bindTexture(new ResourceLocation("appliedenergistics2", "textures/guis/sprites.png"));
            this.hovered = x >= this.x && y >= this.y && x < this.x + this.width && y < this.y + this.height;
            this.mouseDragged(mc, x, y);

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            FontRenderer pFont = mc.fontRenderer;

            if (!this.enabled) {
                this.drawTexturedModalRect(this.x, this.y + 2, 0, 40 + 2, this.width / 2, this.height - 2);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y + 2, 200 - this.width / 2, 40 + 2, this.width / 2, this.height - 1);
                renderButtonText(mc, pFont, 2, 0x413f54, -1);
            } else if (this.hovered) {
                this.drawTexturedModalRect(this.x, this.y + 1, 0, 20 + 1, this.width / 2, this.height - 1);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y + 1, 200 - this.width / 2, 20 + 1, this.width / 2, this.height - 1);
                renderButtonText(mc, pFont, 2, 0x517497, 0);
            } else {
                this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width / 2, this.height);
                this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 0, this.width / 2, this.height);
                renderButtonText(mc, pFont, 2, 0xf2f2f2, 1);
            }
        }
    }

    public static void renderButtonText(Minecraft minecraft, FontRenderer pFont, String pText, int pMinX, int pMinY, int pMaxX, int pMaxY, int yOffset, int pColor) {
        renderButtonText(minecraft, pFont, pText, (pMinX + pMaxX) / 2, pMinX, pMinY, pMaxX, pMaxY, yOffset, pColor);
    }

    public static void renderButtonText(Minecraft minecraft, FontRenderer pFont, String pText, int pCenterX, int pMinX, int pMinY, int pMaxX, int pMaxY, int yOffset, int pColor) {
        int i = pFont.getStringWidth(pText);
        int j = (pMinY + pMaxY - 9) / 2 + 1;
        int k = pMaxX - pMinX;
        if (i > k) {
            int l = i - k;
            double d0 = (double) System.currentTimeMillis() / 1000.0;
            double d1 = Math.max((double) l * 0.5, 3.0);
            double d2 = Math.sin((Math.PI / 2) * Math.cos((Math.PI * 2) * d0 / d1)) / 2.0 + 0.5;
            double d3 = d2 * l;

            ScaledResolution scaledResolution = new ScaledResolution(minecraft);
            int scaleFactor = scaledResolution.getScaleFactor();

            GlStateManager.pushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(pMinX * scaleFactor, (scaledResolution.getScaledHeight() - pMaxY) * scaleFactor,
                    (pMaxX - pMinX) * scaleFactor, (pMaxY - pMinY) * scaleFactor);

            pFont.drawString(pText, pMinX - (int) d3, j - yOffset, pColor, false);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();
        } else {
            int i1 = MathHelper.clamp(pCenterX, pMinX + i / 2, pMaxX - i / 2);
            pFont.drawString(pText, i1 - (float) pFont.getStringWidth(pText) / 2,
                    j - yOffset, pColor, false);
        }
    }

    protected void renderButtonText(Minecraft minecraft, FontRenderer pFont, int pWidth, int pColor, int yOffset) {
        int i = this.x + pWidth;
        int j = this.x + this.width - pWidth;
        renderButtonText(minecraft, pFont, this.displayString, i, this.y, j, this.y + this.height,
                yOffset, pColor);
    }

}