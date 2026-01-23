package nz.duncy.first_steps.world.level.block.entity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public abstract class UnfiredDecoratedBlockEntity extends BlockEntity {
    public static final String TAG_SHERDS = "sherds";
    private PotDecorations decorations;
    private static final int FIRING_TIME = 1024;

    public UnfiredDecoratedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.decorations = PotDecorations.EMPTY;
    }

    public PotDecorations getDecorations() {
        return this.decorations;
    }

    public void setDecoration(ItemStack itemStack, int i, Player player) {
        switch (i) {
            case 0: // North
                this.decorations = new PotDecorations(this.decorations.back(), this.decorations.left(), this.decorations.right(), Optional.of(itemStack.getItem()));
                break;
      
            case 1: // East
                this.decorations = new PotDecorations(this.decorations.back(), Optional.of(itemStack.getItem()), this.decorations.right(), this.decorations.front());
                break;
        
            case 2: // South
                this.decorations = new PotDecorations(Optional.of(itemStack.getItem()), this.decorations.left(), this.decorations.right(), this.decorations.front());
                break;
        
            case 3: // West
                this.decorations = new PotDecorations(this.decorations.back(), this.decorations.left(), Optional.of(itemStack.getItem()), this.decorations.front());
                break;
        
            default:
                break;
        }

        this.level.playSound(
            null,
            this.getBlockPos(),
            SoundEvents.GRAVEL_STEP,
            SoundSource.BLOCKS,
            1.0F,
            1.0F
        );

        if (!player.getAbilities().instabuild) itemStack.shrink(1);
    }

    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!this.decorations.equals(PotDecorations.EMPTY)) {
           valueOutput.store("sherds", PotDecorations.CODEC, this.decorations);
        }
    }

    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.decorations = (PotDecorations)valueInput.read("sherds", PotDecorations.CODEC).orElse(PotDecorations.EMPTY);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POT_DECORATIONS, this.decorations);
    }

    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        this.decorations = (PotDecorations)dataComponentGetter.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
    }

    public Direction getDirection() {
        return (Direction)this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
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
            setFiringProgress(0);
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
                this.firePot(level, blockPos, blockState);
            }
        }
    }

    public abstract ItemStack createUnfiredDecoratedItem(PotDecorations potDecorations);

    public abstract void firePot(Level level, BlockPos blockPos, BlockState blockState);
}
