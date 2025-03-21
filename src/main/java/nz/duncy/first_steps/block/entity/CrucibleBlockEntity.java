package nz.duncy.first_steps.block.entity;

import java.util.HashMap;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.screen.CrucibleScreenHandler;

public class CrucibleBlockEntity extends LootableContainerBlockEntity implements SidedInventory {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private DefaultedList<ItemStack> inventory;
    private HashMap<String, Integer> volumeContents = new HashMap<>(); 

    public final PropertyDelegate propertyDelegate;

    private int temperature;

    public CrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRUCIBLE_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

        this.temperature = 20;

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrucibleBlockEntity.this.temperature;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrucibleBlockEntity.this.temperature = value;
                };
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    public int size() {
        return this.inventory.size();
    }

    public void addLiquid(String liquid, int amount) {
        this.volumeContents.put(liquid, volumeContents.getOrDefault(liquid, 0) + amount);
    }

    public boolean isFull() {
        int totalAmount = volumeContents.values().stream().mapToInt(Integer::intValue).sum();
        return totalAmount >= 54; // Max capacity is 9 blocks 1 pixel = ingot
    }

    public void onOpen(PlayerEntity player) {
        this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }

    protected Text getContainerName() {
        return Text.translatable("container." + FirstSteps.MOD_ID + ".fired_crucible");
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.readInventoryNbt(nbt, registryLookup);
        if (nbt.contains("crucible.temperature", 3)) {
            this.temperature = nbt.getInt("crucible.temperature");
        }
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("crucible.temperature", this.temperature);
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, false, registryLookup);
        }
    }

    public void readInventoryNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt) && nbt.contains("Items", 9)) {
            Inventories.readNbt(nbt, this.inventory, registryLookup);
        }
    }

    protected DefaultedList<ItemStack> method_11282() {
        return this.inventory;
    }
  
     protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }
  
     public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }
  
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof CrucibleBlock);
    }
  
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CrucibleScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state, CrucibleBlockEntity blockEntity) {
        if (!world.isClient()) {
            boolean dirty = false;

            // Go through inventory, check if temperature is melting point for metal inside
            // Add to melted pool nbt if so and remove item from inventory

            if (dirty) {
                markDirty(world, pos, state);
            }
        }
    }

    @Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}

    @Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}
}
