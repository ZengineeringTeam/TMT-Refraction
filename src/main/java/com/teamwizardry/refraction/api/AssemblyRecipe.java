package com.teamwizardry.refraction.api;

import com.teamwizardry.librarianlib.LibrarianLog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LordSaad44
 */
public class AssemblyRecipe {

	private final ArrayList<ItemStack> items;
	private final int minRed, minGreen, minBlue, minStrength;
	private final int maxRed, maxGreen, maxBlue, maxStrength;
	private final Color minColor, maxColor;
	private final ItemStack result;

	public AssemblyRecipe(ItemStack result, int minRed, int minGreen, int minBlue, int minStrength, int maxRed, int maxGreen, int maxBlue, int maxStrength, Object... items) {
		this.result = result;
		this.minRed = minRed < maxRed ? minRed : maxRed;
		this.maxRed = maxRed < minRed ? minRed : maxRed;
		this.minGreen = minGreen < maxGreen ? minGreen : maxGreen;
		this.maxGreen = maxGreen < minGreen ? minGreen : maxGreen;
		this.minBlue = minBlue < maxBlue ? minBlue : maxBlue;
		this.maxBlue = maxBlue < minBlue ? minBlue : maxBlue;
		this.minStrength = minStrength < maxStrength ? minStrength : maxStrength;
		this.maxStrength = maxStrength < minStrength ? minStrength : maxStrength;
		this.minColor = new Color(this.minRed, this.minGreen, this.minBlue, this.minStrength > 255 ? 255 : this.minStrength);
		this.maxColor = new Color(this.maxRed, this.maxGreen, this.maxBlue, this.maxStrength > 255 ? 255 : this.maxStrength);

		this.items = new ArrayList<>();
		for (Object obj : items) {
			if (obj instanceof ItemStack) {
				ItemStack stack = (ItemStack) obj;
				int stackSize = stack.getCount();
				stack.setCount(1);
				for (int i = 0; i < stackSize; i++) {
					this.items.add(stack);
				}
			} else if (obj instanceof Item) {
				this.items.add(new ItemStack((Item) obj));
			} else if (obj instanceof Block) {
				this.items.add(new ItemStack((Block) obj));
			}else if (obj instanceof String) {
				List<ItemStack> oreDicts = OreDictionary.getOres((String) obj);
				if (oreDicts == null || oreDicts.size() <= 0)
				{
					LibrarianLog.INSTANCE.warn("Invalid OreDict entry " + obj + " in recipe for " + result.getDisplayName());
					continue;
				}
				this.items.add(OreDictionary.getOres((String) obj).get(0));
			}
		}
	}

	public AssemblyRecipe(ItemStack result, Color one, Color two, Object... items) {
		int redOne = one.getRed();
		int greenOne = one.getGreen();
		int blueOne = one.getBlue();
		int alphaOne = one.getAlpha();
		int redTwo = two.getRed();
		int greenTwo = two.getGreen();
		int blueTwo = two.getBlue();
		int alphaTwo = two.getAlpha();
		this.result = result;
		this.minRed = redOne < redTwo ? redOne : redTwo;
		this.minGreen = greenOne < greenTwo ? greenOne : greenTwo;
		this.minBlue = blueOne < blueTwo ? blueOne : blueTwo;
		this.minStrength = alphaOne < alphaTwo ? alphaOne : alphaTwo;
		this.maxRed = redOne > redTwo ? redOne : redTwo;
		this.maxGreen = greenOne > greenTwo ? greenOne : greenTwo;
		this.maxBlue = blueOne > blueTwo ? blueOne : blueTwo;
		this.maxStrength = alphaOne > alphaTwo ? alphaOne : alphaTwo;
		this.minColor = new Color(minRed, minGreen, minBlue, minStrength);
		this.maxColor = new Color(maxRed, maxGreen, maxBlue, maxStrength);

		this.items = new ArrayList<>();
		for (Object obj : items) {
			if (obj instanceof ItemStack) {
				ItemStack stack = (ItemStack) obj;
				int stackSize = stack.getCount();
				stack.setCount(1);
				for (int i = 0; i < stackSize; i++) {
					this.items.add(stack);
				}
			} else if (obj instanceof Item) {
				this.items.add(new ItemStack((Item) obj));
			} else if (obj instanceof Block) {
				this.items.add(new ItemStack((Block) obj));
			} else if (obj instanceof String) {
				List<ItemStack> oreDicts = OreDictionary.getOres((String) obj);
				if (oreDicts == null || oreDicts.size() <= 0)
				{
					LibrarianLog.INSTANCE.warn("Invalid OreDict entry " + obj + " in recipe for " + result.getDisplayName());
					continue;
				}
				this.items.add(OreDictionary.getOres((String) obj).get(0));
			}
		}
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}

	public int getMaxRed() {
		return maxRed;
	}

	public int getMinRed() {
		return minRed;
	}

	public int getMaxGreen() {
		return maxGreen;
	}

	public int getMinGreen() {
		return minGreen;
	}

	public int getMaxBlue() {
		return maxBlue;
	}

	public int getMinBlue() {
		return minBlue;
	}

	public int getMaxStrength() {
		return maxStrength;
	}

	public int getMinStrength() {
		return minStrength;
	}

	public ItemStack getResult() {
		return result;
	}

	public Color getMinColor() {
		return minColor;
	}

	public Color getMaxColor() {
		return maxColor;
	}
}
