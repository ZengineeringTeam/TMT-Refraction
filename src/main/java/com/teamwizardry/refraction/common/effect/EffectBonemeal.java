package com.teamwizardry.refraction.common.effect;

import com.teamwizardry.refraction.api.Effect;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Set;

/**
 * Created by LordSaad44
 */
public class EffectBonemeal extends Effect {

	@Override
	public int getCooldown() {
		return potency == 0 ? 0 : 25500 / potency;
	}

	@Override
	public EffectType getType() {
		return EffectType.BEAM;
	}

	@Override
	public void run(World world, Set<BlockPos> locations) {
		for (BlockPos pos : locations) {
			if (world.getBlockState(pos).getBlock() instanceof IGrowable) {
				for (int i = 0; i < (3 * potency / 32); i++)
					ItemDye.applyBonemeal(new ItemStack(Items.DYE), world, pos);
			}
		}
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}
}
