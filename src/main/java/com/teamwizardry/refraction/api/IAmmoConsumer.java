package com.teamwizardry.refraction.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Demoniaque.
 */
public interface IAmmoConsumer {

	@Nonnull
	static List<ItemStack> findAllAmmo(EntityPlayer player) {
		List<ItemStack> stacks = new ArrayList<>();
		if (isAmmo(player.getHeldItem(EnumHand.OFF_HAND))) {
			stacks.add(player.getHeldItem(EnumHand.OFF_HAND));
		}
		if (isAmmo(player.getHeldItem(EnumHand.MAIN_HAND))) {
			stacks.add(player.getHeldItem(EnumHand.MAIN_HAND));
		}
		for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = player.inventory.getStackInSlot(i);

			if (isAmmo(itemstack)) stacks.add(itemstack);
		}

		return stacks;
	}

	@Nonnull
	static List<ItemStack> findAllAmmo(EntityPlayer player, Color color) {
		List<ItemStack> stacks = new ArrayList<>();
		if (isAmmo(player.getHeldItem(EnumHand.OFF_HAND), color)) {
			stacks.add(player.getHeldItem(EnumHand.OFF_HAND));
		}
		if (isAmmo(player.getHeldItem(EnumHand.MAIN_HAND), color)) {
			stacks.add(player.getHeldItem(EnumHand.MAIN_HAND));
		}
		for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = player.inventory.getStackInSlot(i);

			if (isAmmo(itemstack, color)) stacks.add(itemstack);
		}

		return stacks;
	}

	@Nullable
	static ItemStack findAmmo(EntityPlayer player, Color color) {
		if (isAmmo(player.getHeldItem(EnumHand.OFF_HAND), color)) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (isAmmo(player.getHeldItem(EnumHand.MAIN_HAND), color)) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (isAmmo(itemstack, color)) return itemstack;
			}
			return null;
		}
	}

	static boolean isAmmo(@Nullable ItemStack stack) {
		return stack != null
				&& stack.getItem() instanceof IAmmo
				&& ((IAmmo) stack.getItem()).hasColor(stack)
				&& ((IAmmo) stack.getItem()).drain(stack, 1, true);
	}

	static boolean isAmmo(@Nullable ItemStack stack, Color color) {
		return isAmmo(stack)
				&& Utils.doColorsMatchNoAlpha(color, new Color(((IAmmo) stack.getItem()).getInternalColor(stack)));
	}
}
