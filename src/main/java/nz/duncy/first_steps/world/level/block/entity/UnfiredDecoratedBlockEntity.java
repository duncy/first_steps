package nz.duncy.first_steps.world.level.block.entity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class UnfiredDecoratedBlockEntity extends UnfiredBlockEntity {
    public static final String TAG_SHERDS = "sherds";
    private PotDecorations decorations;

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

    public abstract ItemStack createUnfiredDecoratedItem(PotDecorations potDecorations);
}
