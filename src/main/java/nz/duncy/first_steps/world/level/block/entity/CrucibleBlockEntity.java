package nz.duncy.first_steps.world.level.block.entity;

import java.util.EnumMap;
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
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.inventory.CrucibleMenu;
import nz.duncy.first_steps.world.item.Metal;
import nz.duncy.first_steps.world.item.component.CrucibleContainerContents;
import nz.duncy.first_steps.world.item.component.ModDataComponents;

public class CrucibleBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private static final int[] SLOTS = IntStream.range(0, 9).toArray();
    private static final Component DEFAULT_NAME = Component.translatable("container." + FirstSteps.MOD_ID + ".crucible");
    private NonNullList<ItemStack> itemStacks;
    private final EnumMap<Metal, Integer> capacity = new EnumMap<>(Metal.class);
    protected final ContainerData data;
    private int temperature = 20;

    public CrucibleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.CRUCIBLE, blockPos, blockState);
        this.itemStacks = NonNullList.withSize(9, ItemStack.EMPTY);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index < Metal.values().length) {
                    return capacity.getOrDefault(Metal.byIndex(index), 0);
                }
                return temperature;
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

    public int getTemperature() {
        return this.temperature;
    }

    public int[] getMetalAmounts() {
        return this.capacity.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setMetalAmounts(int[] metalAmounts) {
        for (int index = 0; index < metalAmounts.length; index++) {
            int amount = metalAmounts[index];
            Metal metal = Metal.byIndex(index);

            if (amount > 0) {
                this.capacity.put(metal, metalAmounts[index]);
            } else {
                this.capacity.remove(metal);
            } 
        }
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

        this.buildCapacity();
    }

    public void preRemoveSideEffects(BlockPos blockPos, BlockState blockState) {
    }

    public boolean wouldThisOverfill(ItemStack newItemStack) {
        int total = this.capacity.values().stream().mapToInt(Integer::intValue).sum();
        return total + getItemAmount(newItemStack) > 81;
    }
                                                      
    @Override
    public boolean canPlaceItem(int i, ItemStack itemStack) {
        return !wouldThisOverfill(itemStack);
    }

    public void addItemAmount(ItemStack itemStack) {
        updateCapacity(itemStack, getItemAmount(itemStack));
    }

    public int getItemAmount(ItemStack itemStack) {
        int amount = 0;
        if (itemStack.is(ModItemTags.ONE_NINTH_INGOT_EQUIVALENT)) amount = 1;
        else if (itemStack.is(ModItemTags.ONE_INGOT_EQUIVALENT)) amount = 9;
        else if (itemStack.is(ModItemTags.NINE_INGOTS_EQUIVALENT)) amount = 81;

        return amount;
    }

    public void updateCapacity(ItemStack itemStack, int itemAmount) {
        if (itemAmount != 0){
            for (Metal metal : Metal.values()) {
                if (itemStack.is(metal.getItemTag())) {
                    this.capacity.merge(metal, itemAmount, Integer::sum);
                    break;
                }
            }
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        
        if (this.capacity != null) {
            this.buildCapacity();
        }
    }

    private void buildCapacity() {
        this.capacity.clear();

        for (ItemStack itemStack : this.itemStacks) {
            if (!itemStack.isEmpty()) {
                this.addItemAmount(itemStack);
            }
        }
        
        this.buildAlloys();
    }

    private void buildAlloys() {
        if (this.capacity.size() > 1) { 
            int copperAmount = this.capacity.getOrDefault(Metal.COPPER, 0);
            int tinAmount = this.capacity.getOrDefault(Metal.TIN, 0);

            int bronzeAmount = Metal.buildBronzeAmount(copperAmount, tinAmount);

            if (bronzeAmount > 0) {
                this.capacity.remove(Metal.COPPER);
                this.capacity.remove(Metal.TIN);
                this.capacity.merge(Metal.BRONZE, bronzeAmount, Integer::sum);
                return;
            }
            
            this.capacity.clear();
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        super.applyImplicitComponents(dataComponentGetter);
        
        CrucibleContainerContents crucibleContainerContents = dataComponentGetter.get(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS);
        if (crucibleContainerContents != null) {
            this.temperature = crucibleContainerContents.temperature();
            ItemContainerContents inventory = crucibleContainerContents.inventory();
            if (inventory != null) {
                inventory.copyInto(this.itemStacks);
                this.buildCapacity();
            }
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        builder.set(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS, new CrucibleContainerContents(
            this.temperature,
            ItemContainerContents.fromItems(this.itemStacks)
        ));
    }
}
