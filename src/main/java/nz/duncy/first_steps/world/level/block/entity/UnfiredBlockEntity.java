package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public abstract class UnfiredBlockEntity extends BlockEntity {
    private static final int FIRING_TIME = 1024;

    public UnfiredBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    private int getFiringProgress() {
        return this.getBlockState().getValue(ModBlockStateProperties.FIRING_PROGRESS);
    }

    private void setFiringProgress(int value) {
        BlockState updatedBlockState = this.getBlockState().setValue(ModBlockStateProperties.FIRING_PROGRESS, value);
        BlockPos blockPos = getBlockPos();
        if (value % 16 == 0) {
            level.addParticle(ParticleTypes.SMOKE, blockPos.getX() + 0.5, blockPos.getY() + 1.35, blockPos.getZ() + 0.5, 0.0, 0.05, 0.0);
        }
        level.setBlock(this.getBlockPos(), updatedBlockState, Block.UPDATE_ALL);
    }

    private void incrementFiringProgress() {
        setFiringProgress(getFiringProgress() + 1);
    }

    public void recheckFiringProgress(Level level, BlockPos blockPos) {
        BlockState blockState = this.getBlockState();
        if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
            if (blockState.getValue(ModBlockStateProperties.FIRING_PROGRESS) > 0) {
                setFiringProgress(0);
            }
        } else {
            if (level.dimension() == Level.NETHER) {
                incrementFiringProgress();
            } else {
                for (Direction direction : Direction.values()) {
                    BlockPos relativeBlockPos = blockPos.relative(direction);
                    BlockState relativeBlockState = level.getBlockState(relativeBlockPos);
                    if (
                        relativeBlockState.is(Blocks.FIRE) || 
                        relativeBlockState.is(Blocks.SOUL_FIRE) ||
                        relativeBlockState.is(Blocks.MAGMA_BLOCK) ||
                        relativeBlockState.getFluidState().is(FluidTags.LAVA) 
                    ) {
                        incrementFiringProgress();
                        break;
                    } else if (relativeBlockState.is(Blocks.CAMPFIRE) || relativeBlockState.is(Blocks.SOUL_CAMPFIRE)) {
                        if (relativeBlockState.getValue(CampfireBlock.LIT)) {
                            incrementFiringProgress();
                            break;
                        }
                    }
                }
            }

            if (getFiringProgress() >= FIRING_TIME) {
                this.fire(level, blockPos, blockState);
            }
        }
    }

    public abstract void fire(Level level, BlockPos blockPos, BlockState blockState);
    
}
