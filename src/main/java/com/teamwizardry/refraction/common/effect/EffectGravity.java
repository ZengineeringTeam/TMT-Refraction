package com.teamwizardry.refraction.common.effect;

import com.teamwizardry.refraction.api.beam.BeamHitEvent;
import com.teamwizardry.refraction.api.beam.Effect;
import com.teamwizardry.refraction.api.beam.EffectTracker;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

import static com.teamwizardry.refraction.api.beam.EffectTracker.gravityReset;

/**
 * Created by LordSaad.
 */
public class EffectGravity extends Effect {

	@Override
	public EffectType getType() {
		return EffectType.BEAM;
	}

	@Override
	public Color getColor() {
		return new Color(0x0096FF);
	}

	@Override
	public void runEntity(World world, Entity entity, int potency) {
		if (entity instanceof EntityFallingBlock) return;
		entity.setNoGravity(true);
		gravityReset.put(entity, 30);
		entity.fallDistance = 0;
		if (entity instanceof EntityPlayer)
			((EntityPlayer) entity).velocityChanged = true;
	}


	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void beamHit(BeamHitEvent event) {
		if (event.getBeam().effect instanceof EffectGravity) {
			World world = event.getWorld();
			BlockPos pos = event.getPos();

			if (world.isAirBlock(pos)) return;

			EffectTracker.gravityProtection.put(pos, 50);
			IBlockState state = world.getBlockState(pos);

			if (world.isAirBlock(pos.down())) {
				int potency = event.getBeam().color.getAlpha();
				double hardness = state.getBlock().getBlockHardness(state, world, event.getPos());
				if (hardness >= 0 && hardness * 64 < potency && world.getTileEntity(pos) == null) {
					EntityFallingBlock falling = new EntityFallingBlock(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state);
					falling.fallTime = 1;
					world.setBlockToAir(pos);
					world.spawnEntity(falling);
				}
			}
		}
	}


	@SubscribeEvent
	public void interact1(PlayerInteractEvent.RightClickBlock event) {
		if (EffectTracker.gravityProtection.containsKey(event.getPos()))
			event.setCanceled(true);
	}

	@SubscribeEvent
	public void interact2(PlayerInteractEvent.LeftClickBlock event) {
		if (EffectTracker.gravityProtection.containsKey(event.getPos()))
			event.setCanceled(true);
	}
}
