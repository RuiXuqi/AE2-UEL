/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AlgorithmX2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package appeng.api.util;


import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.translation.I18n;


/**
 * List of all colors supported by AE, their names, and various colors for display.
 *
 * Should be the same order as Dyes, excluding Transparent.
 */
public enum AEColor
{

	WHITE( "gui.appliedenergistics2.White", EnumDyeColor.WHITE, 0xB4B4B4, 0xE0E0E0, 0xF9F9F9 ),

	ORANGE( "gui.appliedenergistics2.Orange", EnumDyeColor.ORANGE, 0xD9782F, 0xECA23C, 0xF2BA49 ),

	MAGENTA( "gui.appliedenergistics2.Magenta", EnumDyeColor.MAGENTA, 0xC15189, 0xD5719C, 0xE69EBF ),

	LIGHT_BLUE( "gui.appliedenergistics2.LightBlue", EnumDyeColor.LIGHT_BLUE, 0x69B9FF, 0x70D2FF, 0x80F7FF ),

	YELLOW( "gui.appliedenergistics2.Yellow", EnumDyeColor.YELLOW, 0xFFCF40, 0xFFE359, 0xF4FF80 ),

	LIME( "gui.appliedenergistics2.Lime", EnumDyeColor.LIME, 0x4EC04E, 0x70E259, 0xB3F86D ),

	PINK( "gui.appliedenergistics2.Pink", EnumDyeColor.PINK, 0xD86EAA, 0xFF99BB, 0xFBCAD5 ),

	GRAY( "gui.appliedenergistics2.Gray", EnumDyeColor.GRAY, 0x4F4F4F, 0x6C6B6C, 0x949294 ),

	LIGHT_GRAY( "gui.appliedenergistics2.LightGray", EnumDyeColor.SILVER, 0x7E7E7E, 0xA09FA0, 0xC4C4C4 ),

	CYAN( "gui.appliedenergistics2.Cyan", EnumDyeColor.CYAN, 0x22B0AE, 0x2FCCB7, 0x65E8C9 ),

	PURPLE( "gui.appliedenergistics2.Purple", EnumDyeColor.PURPLE, 0x6E5CB8, 0x915DCD, 0xB06FDD ),

	BLUE( "gui.appliedenergistics2.Blue", EnumDyeColor.BLUE, 0x337FF0, 0x3894FF, 0x40C1FF ),

	BROWN( "gui.appliedenergistics2.Brown", EnumDyeColor.BROWN, 0x6E4A12, 0x7E5C16, 0x8E6E1A ),

	GREEN( "gui.appliedenergistics2.Green", EnumDyeColor.GREEN, 0x079B6B, 0x17B86D, 0x32D850 ),

	RED( "gui.appliedenergistics2.Red", EnumDyeColor.RED, 0xAA212B, 0xD73E42, 0xF07665 ),

	BLACK( "gui.appliedenergistics2.Black", EnumDyeColor.BLACK, 0x131313, 0x272727, 0x3B3B3B ),

	TRANSPARENT( "gui.appliedenergistics2.Fluix", null, 0x5A479E, 0x915DCD, 0xE2A3E3 );

	public static final List<AEColor> VALID_COLORS = Arrays.asList( WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, LIGHT_GRAY, CYAN, PURPLE,
			BLUE, BROWN, GREEN, RED, BLACK );

	/**
	 * The {@link BakedQuad#getTintIndex() tint index} that can normally be used to get the {@link #blackVariant dark
	 * variant} of the apprioriate AE color.
	 */
	public static final int TINTINDEX_DARK = 1;

	/**
	 * The {@link BakedQuad#getTintIndex() tint index} that can normally be used to get the {@link #mediumVariant medium
	 * variant} of the apprioriate AE color.
	 */
	public static final int TINTINDEX_MEDIUM = 2;

	/**
	 * The {@link BakedQuad#getTintIndex() tint index} that can normally be used to get the {@link #whiteVariant bright
	 * variant} of the apprioriate AE color.
	 */
	public static final int TINTINDEX_BRIGHT = 3;

	/**
	 * The {@link BakedQuad#getTintIndex() tint index} that can normally be used to get a color between the
	 * {@link #mediumVariant medium}
	 * and {@link #whiteVariant bright variant} of the apprioriate AE color.
	 */
	public static final int TINTINDEX_MEDIUM_BRIGHT = 4;

	/**
	 * Unlocalized name for color.
	 */
	public final String unlocalizedName;

	/**
	 * Darkest Variant of the color, nearly black; as a RGB HEX Integer
	 */
	public final int blackVariant;

	/**
	 * The Variant of the color that is used to represent the color normally; as a RGB HEX Integer
	 */
	public final int mediumVariant;

	/**
	 * Lightest Variant of the color, nearly white; as a RGB HEX Integer
	 */
	public final int whiteVariant;

	/**
	 * Vanilla Dye Equivilient
	 */
	public final EnumDyeColor dye;

	AEColor( final String unlocalizedName, final EnumDyeColor dye, final int blackHex, final int medHex, final int whiteHex )
	{
		this.unlocalizedName = unlocalizedName;
		this.blackVariant = blackHex;
		this.mediumVariant = medHex;
		this.whiteVariant = whiteHex;
		this.dye = dye;
	}

	/**
	 * Will return a variant of this color based on the given tint index.
	 *
	 * @param tintIndex A tint index as it can be used for {@link BakedQuad#getTintIndex()}.
	 * @return The appropriate color variant, or -1.
	 */
	public int getVariantByTintIndex( int tintIndex )
	{
		switch( tintIndex )
		{
			// Please note that tintindex 0 is hardcoded for the block breaking particles. Returning anything other than
			// -1 for tintindex=0 here
			// will cause issues with those particles
			case 0:
				return -1;
			case TINTINDEX_DARK:
				return this.blackVariant;
			case TINTINDEX_MEDIUM:
				return this.mediumVariant;
			case TINTINDEX_BRIGHT:
				return this.whiteVariant;
			case TINTINDEX_MEDIUM_BRIGHT:
				final int light = this.whiteVariant;
				final int dark = this.mediumVariant;
				return ( ( ( ( ( light >> 16 ) & 0xff ) + ( ( dark >> 16 ) & 0xff ) ) / 2 ) << 16 ) | ( ( ( ( ( light >> 8 ) & 0xff ) + ( ( dark >> 8 ) & 0xff ) ) / 2 ) << 8 ) | ( ( ( ( light ) & 0xff ) + ( ( dark ) & 0xff ) ) / 2 );
			default:
				return -1;
		}
	}

	/**
	 * Logic to see which colors match each other.. special handle for Transparent
	 */
	public boolean matches( final AEColor color )
	{
		return this == TRANSPARENT || color == TRANSPARENT || this == color;
	}

	@Override
	public String toString()
	{
		return I18n.translateToLocal( this.unlocalizedName );
	}

}
