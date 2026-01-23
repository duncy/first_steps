package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class PottersWheelBlockEntity extends BlockEntity {
    private float progress;
    private float progressOld;
    private boolean spinning;

    public PottersWheelBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.POTTERS_WHEEL, blockPos, blockState);
        this.spinning = false;
        this.progress = 0.0F;
        this.progressOld = 0.0F;
    }

    public float getProgress(float progress) {
        return Mth.lerp(progress, this.progressOld, this.progress);
    }
    
    public void updateAnimation(Level level, BlockPos blockPos, BlockState blockState) {
        if (isSpinning()) {
            this.progressOld = this.progress;
            if (this.progressOld >= 1.0F) {
                this.progress = 1.0F;
                this.spinning = false;
                setChanged();
                level.updateNeighborsAt(blockPos, blockState.getBlock());
            } 
            else {
                this.progress += 0.1F;         
            }
        } else {
            if (this.progress == 1.0F) {
                this.progressOld = 0.0F;
                this.progress = 0.0F;
                setChanged();
            }
        }
    }

    public boolean isSpinning() {
        return this.spinning;
    }

    public void startSpinning() {
        this.spinning = true;
        setChanged();
        if (!level.isClientSide()) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_CLIENTS);
        }
    }

    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putBoolean("spinning", this.spinning);
        valueOutput.putFloat("progress", this.progress);
        valueOutput.putFloat("progressOld", this.progressOld);
    }

    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.spinning = valueInput.getBooleanOr("spinning", false);
        this.progress = valueInput.getFloatOr("progress", 0.0F);
        this.progressOld = valueInput.getFloatOr("progressOld", 0.0F);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
    }
}
