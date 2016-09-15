package com.teamwizardry.refraction.common.block;

import com.teamwizardry.librarianlib.common.base.ModCreativeTab;
import com.teamwizardry.librarianlib.common.base.block.BlockModContainer;
import com.teamwizardry.refraction.Refraction;
import com.teamwizardry.refraction.client.render.RenderOpticFiber;
import com.teamwizardry.refraction.common.tile.TileOpticFiber;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Saad on 9/15/2016.
 */
public class BlockOpticFiber extends BlockModContainer {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	public BlockOpticFiber() {
		super("optic_fiber", Material.GLASS);
		setHardness(1F);
		setSoundType(SoundType.GLASS);
		GameRegistry.registerTileEntity(TileOpticFiber.class, "optic_fiber");
	}

	private TileOpticFiber getTE(World world, BlockPos pos) {
		return (TileOpticFiber) world.getTileEntity(pos);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileOpticFiber.class, new RenderOpticFiber());
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(meta).withProperty(FACING, facing);
	}


	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState iBlockState) {
		return new TileOpticFiber();
	}

	@Nullable
	@Override
	public ModCreativeTab getCreativeTab() {
		return Refraction.tab;
	}
}