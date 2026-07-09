package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UnfiredFlowerPotBlockEntity extends UnfiredBlockEntity {

    public UnfiredFlowerPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.UNFIRED_FLOWER_POT, blockPos, blockState);
    }

    @Override
    public void fire(Level level, BlockPos blockPos, BlockState blockState) {
        BlockState firedBlockState = Blocks.FLOWER_POT.defaultBlockState();
        level.setBlock(blockPos, firedBlockState, Block.UPDATE_ALL);
    }
    
}
