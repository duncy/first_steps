package nz.duncy.first_steps.world.level.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nz.duncy.first_steps.world.level.block.entity.IngotCastBlockEntity;

public class IngotCastBlock extends HorizontalFacingWaterloggedCrackedEntityBlock {
    public static final MapCodec<IngotCastBlock> CODEC;
    private static final VoxelShape SHAPE_EAST_WEST;
    private static final VoxelShape SHAPE_NORTH_SOUTH;
    private static final Map<Direction, VoxelShape> SHAPES;

    public IngotCastBlock(Properties properties) {
        super(properties);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPES.get(blockState.getValue(HORIZONTAL_FACING));
    }

    protected SoundType getSoundType(BlockState blockState) {
        return (Boolean)blockState.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IngotCastBlockEntity(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    static {
        CODEC = simpleCodec(IngotCastBlock::new);
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
