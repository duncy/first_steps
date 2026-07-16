package nz.duncy.first_steps.world.level.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnfiredCrucibleBlock extends UnfiredBlock {
    public static final MapCodec<UnfiredCrucibleBlock> CODEC;
    private static final VoxelShape SHAPE;

    protected UnfiredCrucibleBlock(Properties properties) {
        super(properties);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    static {
        CODEC = simpleCodec(UnfiredCrucibleBlock::new);
        SHAPE = Block.box(3, 0, 3, 13, 10, 13);
    }
}
