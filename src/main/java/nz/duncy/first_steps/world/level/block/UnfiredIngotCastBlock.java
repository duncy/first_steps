package nz.duncy.first_steps.world.level.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnfiredIngotCastBlock extends HoriztonalFacingUnfiredBlock {
    public static final MapCodec<UnfiredIngotCastBlock> CODEC;
    private static final VoxelShape SHAPE_EAST_WEST;
    private static final VoxelShape SHAPE_NORTH_SOUTH;
    private static final Map<Direction, VoxelShape> SHAPES;

    protected UnfiredIngotCastBlock(Properties properties) {
        super(properties);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPES.get(blockState.getValue(HORIZONTAL_FACING));
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    static {
        CODEC = simpleCodec(UnfiredIngotCastBlock::new);
        SHAPE_EAST_WEST = Shapes.or(
            Block.box(2.0, 0.0, 4.0, 4.0, 3.0, 12.0),
            Block.box(12.0, 0.0, 4.0, 14.0, 3.0, 12.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 3.0, 6.0),
            Block.box(4.0, 0.0, 10.0, 12.0, 3.0, 12.0),
            Block.box(4.0, 0.0, 6.0, 12.0, 1.0, 10.0)
        );
        SHAPE_NORTH_SOUTH = Shapes.or(
            Block.box(4.0, 0.0, 2.0, 12.0, 3.0, 4.0),
            Block.box(4.0, 0.0, 12.0, 12.0, 3.0, 14.0),
            Block.box(4.0, 0.0, 4.0, 6.0, 3.0, 12.0),
            Block.box(10.0, 0.0, 4.0, 12.0, 3.0, 12.0),
            Block.box(6.0, 0.0, 4.0, 10.0, 1.0, 12.0)
        );
        SHAPES = ImmutableMap.of(
            Direction.NORTH, SHAPE_NORTH_SOUTH,
            Direction.SOUTH, SHAPE_NORTH_SOUTH,
            Direction.EAST, SHAPE_EAST_WEST,
            Direction.WEST, SHAPE_EAST_WEST
        );
    }
}
