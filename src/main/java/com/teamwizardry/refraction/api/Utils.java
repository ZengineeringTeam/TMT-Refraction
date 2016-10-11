package com.teamwizardry.refraction.api;

import net.minecraft.item.EnumDyeColor;

import java.awt.*;

/**
 * Created by Saad on 10/9/2016.
 */
public class Utils {

	public static Color getColorFromDyeEnum(EnumDyeColor dye) {
		switch (dye) {
			case WHITE:
				return Color.WHITE;
			case ORANGE:
				return Color.ORANGE;
			case MAGENTA:
				return Color.MAGENTA;
			case LIGHT_BLUE:
				return new Color(0xadd8e6);
			case YELLOW:
				return Color.YELLOW;
			case LIME:
				return new Color(0x32cd32);
			case PINK:
				return Color.PINK;
			case GRAY:
				return Color.GRAY;
			case SILVER:
				return new Color(0xd3d3d3);
			case CYAN:
				return Color.CYAN;
			case PURPLE:
				return new Color(0xa020f0);
			case BLUE:
				return Color.BLUE;
			case BROWN:
				return new Color(0x8b4513);
			case GREEN:
				return Color.GREEN;
			case RED:
				return Color.RED;
			case BLACK:
				return Color.BLACK;
			default:
				return Color.WHITE;
		}
	}
}