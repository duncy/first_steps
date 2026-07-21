package nz.duncy.first_steps.world.level.block.entity;

import java.util.stream.IntStream;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.metallurgy.Metal;
import nz.duncy.first_steps.metallurgy.MetalStorage;
import nz.duncy.first_steps.metallurgy.TemperatureStorage;
import nz.duncy.first_steps.world.inventory.CrucibleMenu;
import nz.duncy.first_steps.world.item.component.CrucibleContainerContents;
import nz.duncy.first_steps.world.item.component.ModDataComponents;

public class CrucibleBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final int[] SLOTS = IntStream.range(0, 9).toArray();
    private static final Component DEFAULT_NAME = Component.translatable("container." + FirstSteps.MOD_ID + ".crucible");
    private NonNullList<ItemStack> itemStacks;
    protected final ContainerData data;
    public MetalStorage metalStorage;
    public TemperatureStorage temperatureStorage;

    public CrucibleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.CRUCIBLE, blockPos, blockState);
        this.itemStacks = NonNullList.withSize(9, ItemStack.EMPTY);
        this.metalStorage = new MetalStorage();
        this.temperatureStorage = new TemperatureStorage();

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index < Metal.values().length) {
                    return metalStorage.getMetalByIndex(index);
                }
                return temperatureStorage.getTemperature();
            }

            @Override
            public void set(int index, int value) {
                
            }

            @Override
            public int getCount() {
                return Metal.values().length + 1;
            }
        };
    }

    public ContainerData getContainerData() {
        return this.data;
    }

    @Override
    public int getContainerSize() {
        return this.itemStacks.size();
    }

    @Override
    public boolean canPlaceItemThroughFace(int arg0, ItemStack arg1, @Nullable Direction arg2) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new CrucibleMenu(i, inventory, this, this.data);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.itemStacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.itemStacks = nonNullList;
    }

    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.loadFromTag(valueInput);
    }

    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!this.trySaveLootTable(valueOutput)) {
            ContainerHelper.saveAllItems(valueOutput, this.itemStacks, false);
        }
    }

    public void loadFromTag(ValueInput valueInput) {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(valueInput)) {
            ContainerHelper.loadAllItems(valueInput, this.itemStacks);
        }

        this.metalStorage.buildCapacity(this.itemStacks);
    }

    public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
    }
                                                      
    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        return !metalStorage.wouldThisOverfill(itemStack);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        
        metalStorage.buildCapacity(this.itemStacks);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        
        CrucibleContainerContents crucibleContainerContents = dataComponentGetter.get(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS);
        if (crucibleContainerContents != null) {
            temperatureStorage.setTemperature(crucibleContainerContents.temperature());
            ItemContainerContents inventory = crucibleContainerContents.inventory();
            if (inventory != null) {
                inventory.copyInto(this.itemStacks);
                metalStorage.buildCapacity(this.itemStacks);
            }
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        builder.set(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS, new CrucibleContainerContents(
            temperatureStorage.getTemperature(),
            ItemContainerContents.fromItems(this.itemStacks)
        ));
    }
}
