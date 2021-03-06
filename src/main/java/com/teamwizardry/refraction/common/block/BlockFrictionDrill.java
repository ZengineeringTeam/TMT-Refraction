package com.teamwizardry.refraction.common.block;

import com.teamwizardry.librarianlib.features.base.block.tile.BlockModContainer;
import com.teamwizardry.refraction.api.beam.Beam;
import com.teamwizardry.refraction.api.beam.ILightSink;
import com.teamwizardry.refraction.common.tile.TileFrictionDrill;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Demoniaque.
 */
public class BlockFrictionDrill extends BlockModContainer implements ILightSink {

	public BlockFrictionDrill() {
		super("friction_drill", Material.IRON);
	}

	@Override
	public boolean handleBeam(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull Beam beam) {
		if(beam.isAesthetic()) return true;
		TileFrictionDrill drill = (TileFrictionDrill) world.getTileEntity(pos);
		if (drill == null) return true;

		drill.handleBeam(beam);
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState iBlockState) {
		return new TileFrictionDrill();
	}
}
