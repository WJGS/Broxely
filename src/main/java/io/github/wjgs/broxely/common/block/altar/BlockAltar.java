package io.github.wjgs.broxely.common.block.altar;

import io.github.wjgs.broxely.Broxely;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockAltar extends Block {

    private static DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = VoxelShapes
            .or(Block.makeCuboidShape(3, 1, 3, 13, 11, 13),
                    Block.makeCuboidShape(3, 0, 2, 13, 1, 3),
                    Block.makeCuboidShape(3, 11, 2, 13, 12, 3),
                    Block.makeCuboidShape(3, 0, 13, 13, 1, 14),
                    Block.makeCuboidShape(3, 11, 13, 13, 12, 14),
                    Block.makeCuboidShape(2, 0, 3, 3, 1, 13),
                    Block.makeCuboidShape(2, 11, 3, 3, 12, 13),
                    Block.makeCuboidShape(13, 0, 3, 14, 1, 13),
                    Block.makeCuboidShape(2, 12, 2, 3, 13, 14),
                    Block.makeCuboidShape(13, 11, 3, 14, 12, 13),
                    Block.makeCuboidShape(13, 12, 2, 14, 13, 14),
                    Block.makeCuboidShape(3, 12, 2, 13, 13, 3),
                    Block.makeCuboidShape(3, 12, 13, 13, 13, 14),
                    Block.makeCuboidShape(2, 13, 6, 3, 14, 10),
                    Block.makeCuboidShape(6, 13, 13, 10, 14, 14),
                    Block.makeCuboidShape(6, 13, 2, 10, 14, 3),
                    Block.makeCuboidShape(13, 13, 6, 14, 14, 10),
                    Block.makeCuboidShape(7, 1, 13, 9, 11, 14),
                    Block.makeCuboidShape(13, 1, 7, 14, 11, 9),
                    Block.makeCuboidShape(2, 1, 7, 3, 11, 9),
                    Block.makeCuboidShape(7, 1, 2, 9, 11, 3)
                    );

    public BlockAltar() {
        super(Properties.create(Material.ROCK));
        setRegistryName(Broxely.MODID, "altar");
        setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AltarTE();
    }

    @Override
    // onBlockActivated
    public ActionResultType func_225533_a_(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            }
        }
        return super.func_225533_a_(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }
}
