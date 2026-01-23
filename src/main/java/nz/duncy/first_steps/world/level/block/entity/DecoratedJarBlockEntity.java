package nz.duncy.first_steps.world.level.block.entity;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.DecoratedJarBlock;

public class DecoratedJarBlockEntity extends RandomizableContainerBlockEntity {
   private static final Component DEFAULT_NAME = Component.translatable("container." + FirstSteps.MOD_ID + ".decorated_jar");
   public static final String TAG_SHERDS = "sherds";
   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
   private PotDecorations decorations;
   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(final Level level, final BlockPos pos, final BlockState state) {
            DecoratedJarBlockEntity.this.playSound(state, SoundEvents.DECORATED_POT_INSERT);
            DecoratedJarBlockEntity.this.updateBlockState(state, true);
        }

        @Override
        protected void onClose(final Level level, final BlockPos pos, final BlockState state) {
            DecoratedJarBlockEntity.this.playSound(state, SoundEvents.DECORATED_POT_INSERT_FAIL);
            DecoratedJarBlockEntity.this.updateBlockState(state, false);
        }

        @Override
        protected void openerCountChanged(final Level level, final BlockPos pos, final BlockState blockState, final int previous, final int current) {
        }

        @Override
        public boolean isOwnContainer(final Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu)player.containerMenu).getContainer();
                return container == DecoratedJarBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public DecoratedJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.DECORATED_JAR, blockPos, blockState);
        this.decorations = PotDecorations.EMPTY;
    }

    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!this.trySaveLootTable(valueOutput)) {
            ContainerHelper.saveAllItems(valueOutput, this.items);
        }

        if (!this.decorations.equals(PotDecorations.EMPTY)) {
            valueOutput.store("sherds", PotDecorations.CODEC, this.decorations);
        }
    }

    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.decorations = (PotDecorations)valueInput.read("sherds", PotDecorations.CODEC).orElse(PotDecorations.EMPTY);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(valueInput)) {
            ContainerHelper.loadAllItems(valueInput, this.items);
        }
    }

    public int getContainerSize() {
        return 27;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.threeRows(i, inventory, this);
    }

    public void startOpen(ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.openersCounter.incrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), containerUser.getContainerInteractionRange());
        }

    }

    public void stopOpen(ContainerUser containerUser) {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator()) {
            this.openersCounter.decrementOpeners(containerUser.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public List<ContainerUser> getEntitiesWithContainerOpen() {
        return this.openersCounter.getEntitiesWithContainerOpen(this.getLevel(), this.getBlockPos());
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    void updateBlockState(BlockState blockState, boolean bl) {
        this.level.setBlock(this.getBlockPos(), (BlockState)blockState.setValue(DecoratedJarBlock.OPEN, bl), 3);
    }

    void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vec3i vec3i = ((Direction)blockState.getValue(DecoratedJarBlock.HORIZONTAL_FACING)).getUnitVec3i();
        double d = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.level.playSound((Entity)null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return this.saveCustomOnly(provider);
    }

    public Direction getDirection() {
        return (Direction)this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public PotDecorations getDecorations() {
        return this.decorations;
    }

    public static ItemStack createDecoratedJarItem(PotDecorations potDecorations) {
        ItemStack itemStack = ModItems.DECORATED_JAR.getDefaultInstance();
        itemStack.set(DataComponents.POT_DECORATIONS, potDecorations);
        return itemStack;
    }

    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(DataComponents.POT_DECORATIONS, this.decorations);
    }

    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        this.decorations = (PotDecorations)dataComponentGetter.getOrDefault(DataComponents.POT_DECORATIONS, PotDecorations.EMPTY);
    }

    public void removeComponentsFromTag(ValueOutput valueOutput) {
        super.removeComponentsFromTag(valueOutput);
        valueOutput.discard("sherds");
    }

    public BlockEntity getContainerBlockEntity() {
        return this;
    }
}
