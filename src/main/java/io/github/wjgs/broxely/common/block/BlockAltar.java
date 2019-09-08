package io.github.wjgs.broxely.common.block;

import io.github.wjgs.broxely.Broxely;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

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
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }


}
