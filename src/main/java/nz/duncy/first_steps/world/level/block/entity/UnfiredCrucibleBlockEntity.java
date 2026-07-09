package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class UnfiredCrucibleBlockEntity extends UnfiredBlockEntity {

    public UnfiredCrucibleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.UNFIRED_CRUCIBLE, blockPos, blockState);
    }

    @Override
    public void fire(Level level, BlockPos blockPos, BlockState blockState) {
        BlockState firedBlockState = ModBlocks.CRUCIBLE.defaultBlockState();
        level.setBlock(blockPos, firedBlockState, Block.UPDATE_ALL);

    }
    
}
