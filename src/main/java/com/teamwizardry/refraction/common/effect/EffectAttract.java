package com.teamwizardry.refraction.common.effect;

import com.teamwizardry.refraction.api.CapsUtils;
import com.teamwizardry.refraction.api.Utils;
import com.teamwizardry.refraction.api.beam.Effect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.teamwizardry.refraction.api.beam.EffectTracker.gravityReset;

/**
 * Created by Demoniaque
 * Will attract any entities that intersect with the beam. < 128 only attracts item entities.
 */
public class EffectAttract extends Effect {

	@Nonnull
	protected Color getEffectColor() {
		return Color.CYAN;
	}

	@Override
	public EffectType getType() {
		return EffectType.BEAM;
	}

	private void setEntityMotion(Entity entity, double potency) {
		Vec3d pullDir;
		if (beam.finalLoc != null) {
			pullDir = beam.initLoc.subtract(beam.finalLoc).normalize();
			entity.setNoGravity(true);
			entity.motionY = pullDir.y * potency / 255.0;
			entity.motionZ = pullDir.z * potency / 255.0;
			entity.motionX = pullDir.x * potency / 255.0;
			entity.fallDistance = 0;
		}
	}

	@Override
	public void runEntity(World world, Entity entity, int potency) {
		if(entity instanceof EntityLivingBase && Utils.entityWearsFullReflective((EntityLivingBase)entity)) return;
		if(entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative()) return;
		setEntityMotion(entity, potency);
		gravityReset.put(entity, 10);
		if (entity instanceof EntityPlayer)
			((EntityPlayer) entity).velocityChanged = true;
	}

	@Override
	public void runBlock(World world, BlockPos pos, int potency) {
		if (beam.trace == null || beam.trace.getBlockPos() == null) return;
		TileEntity tile = world.getTileEntity(beam.trace.getBlockPos());
		if (tile == null) return;
		if (!EffectBurn.burnedTileTracker.contains(beam.trace.getBlockPos())) return;
		EffectBurn.burnedTileTracker.remove(beam.trace.getBlockPos());
		if (ThreadLocalRandom.current().nextInt(potency > 0 ? 10 * potency : 1) == 0) return;

		if (tile instanceof IInventory) {
			IInventory inv = (IInventory) tile;

			int lastSlot = 0;
			for (int i = inv.getSizeInventory() - 1; i > 0; i--) {
				if (!inv.getStackInSlot(i).isEmpty()) {
					lastSlot = i;
					break;
				}
			}
			ItemStack slotStack = inv.getStackInSlot(lastSlot);
			if (slotStack.isEmpty()) return;

			ItemStack stack = inv.decrStackSize(lastSlot, slotStack.getCount() < potency / 50 ? slotStack.getCount() : potency / 50);
			if (!stack.isEmpty()) extractItem(world, beam.trace, stack);
		} else if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, beam.trace.sideHit)) {
			IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, beam.trace.sideHit);
			int lastSlot = CapsUtils.getLastOccupiedSlot(cap);
			if (CapsUtils.getOccupiedSlotCount(cap) > 0) {
				ItemStack stack = cap.extractItem(lastSlot, cap.getStackInSlot(lastSlot).getCount() < potency / 50 ? cap.getStackInSlot(lastSlot).getCount() : potency / 50, false);
				if (!stack.isEmpty()) extractItem(world, beam.trace, stack);
			}
		}
	}

	private static void extractItem(World world, RayTraceResult trace, ItemStack stack)
	{
		Vec3d posVec = new Vec3d(trace.getBlockPos().getX() + 0.5, trace.getBlockPos().getY() + 0.5, trace.getBlockPos().getZ() + 0.5);
		Vec3d popVec = posVec.add(trace.hitVec.subtract(posVec).scale(1.5));
		EntityItem item = new EntityItem(world, popVec.x, popVec.y, popVec.z, stack);
		item.motionX = 0;
		item.motionY = 0;
		item.motionZ = 0;
		world.spawnEntity(item);
	}
}
