package nz.duncy.first_steps.block.entity;

import java.util.Map;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.google.common.collect.Maps;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.screen.CrucibleScreenHandler;

public class CrucibleBlockEntity extends LootableContainerBlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private DefaultedList<ItemStack> inventory;
    private int viewerCount;

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

    private static void updateNeighborStates(World world, BlockPos pos, BlockState state) {
        state.updateNeighbors(world, pos, 3);
        world.updateNeighborsAlways(pos, state.getBlock());
    }

    // public boolean onSyncedBlockEvent(int type, int data) {
    //     if (type == 1) {
    //        this.viewerCount = data;
    //        return true;
    //     } else {
    //        return super.onSyncedBlockEvent(type, data);
    //     }
    // }

    // public void onOpen(PlayerEntity player) {
    //     if (!this.removed && !player.isSpectator()) {
    //        if (this.viewerCount < 0) {
    //           this.viewerCount = 0;
    //        }
  
    //        ++this.viewerCount;
    //        this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
    //        if (this.viewerCount == 1) {
    //           this.world.emitGameEvent(player, GameEvent.CONTAINER_OPEN, this.pos);
    //           this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_DECORATED_POT_INSERT, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    //        }
    //     }
  
    // }

    // public void onClose(PlayerEntity player) {
    //     if (!this.removed && !player.isSpectator()) {
    //        --this.viewerCount;
    //        this.world.addSyncedBlockEvent(this.pos, this.getCachedState().getBlock(), 1, this.viewerCount);
    //        if (this.viewerCount <= 0) {
    //           this.world.emitGameEvent(player, GameEvent.CONTAINER_CLOSE, this.pos);
    //        }
    //     }
  
    // }

    protected Text getContainerName() {
        return Text.translatable("container." + FirstSteps.MOD_ID + ".clay_fired_crucible");
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.readInventoryNbt(nbt);
        if (nbt.contains("crucible.temperature", 3)) {
            this.temperature = nbt.getInt("crucible.temperature");
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("crucible.temperature", this.temperature);
        super.writeNbt(nbt);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, false);
        }
    }

    public void readInventoryNbt(NbtCompound nbt) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt) && nbt.contains("Items", 9)) {
            Inventories.readNbt(nbt, this.inventory);
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

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    // public CrucibleBlockEntity(BlockPos pos, BlockState state) {
    //     super(ModBlockEntities.CRUCIBLE_BLOCK_ENTITY, pos, state);

    //     this.temperature = 20;

    //     this.propertyDelegate = new PropertyDelegate() {
    //         @Override
    //         public int get(int index) {
    //             return switch (index) {
    //                 case 0 -> CrucibleBlockEntity.this.temperature;
    //                 default -> 0;
    //             };
    //         }

    //         @Override
    //         public void set(int index, int value) {
    //             switch (index) {
    //                 case 0 -> CrucibleBlockEntity.this.temperature = value;
    //             };
    //         }

    //         @Override
    //         public int size() {
    //             return 1;
    //         }
    //     };
    // }

    // @Override
    // public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
    //     buf.writeBlockPos(this.pos);
    // }

    // @Override
    // public Text getDisplayName() {
    //     return Text.translatable("container." + FirstSteps.MOD_ID + ".clay_fired_crucible");
    // }

    // @Override
    // public DefaultedList<ItemStack> getItems() {
    //     return inventory;
    // }

    // @Override
    // public void writeNbt(NbtCompound nbt) {
    //     super.writeNbt(nbt);
    //     Inventories.writeNbt(nbt, inventory);
    //     nbt.putInt("crucible.temperature", this.temperature);
    // }

    // @Override
    // public void readNbt(NbtCompound nbt) {
    //     super.readNbt(nbt);
    //     Inventories.readNbt(nbt, inventory);
    //     this.temperature = nbt.getInt("crucible.temperature");
    // }

    // @Nullable
    // @Override
    // public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    //     return new CrucibleScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    // }

    // protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
    //     return new CrucibleScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    // }

    // protected DefaultedList<ItemStack> method_11282() {
    //     return this.inventory;
    // }

    // protected Text getContainerName() {
    //     return Text.translatable("container." + FirstSteps.MOD_ID + ".clay_fired_crucible");
    // }

    // protected void setInvStackList(DefaultedList<ItemStack> list) {
    //     this.inventory = list;
    // }
    // // public void tick(World world, BlockPos pos, BlockState state, CrucibleBlockEntity blockEntity) {
    // //     if (world.isClient()) {
    // //         return;
    // //     }

    // //     boolean burning = blockEntity.isBurning();
    // //     boolean dirty = false;


    // //     ItemStack fuelItemstack = blockEntity.inventory.get(INPUT_SLOT_1);

    // //     if (blockEntity.isBurning()) {
    // //         --blockEntity.burnTime;
    // //         if (blockEntity.temperature < blockEntity.maxTemperature) {
    // //             ++blockEntity.temperature;
    // //         }
    // //         if (blockEntity.burnTime == 0) {
    // //             ItemStack result = getWasteProductRecipe(fuelItemstack);
    // //             craftWasteProduct(result);
    // //         }
    // //     } else if (blockEntity.temperature > minTemperature){
    // //         --blockEntity.temperature;
    // //     }

    // //     // Crucible is burning OR
    // //     // Fuel slot is not empty and output slot empty or can receive new items
    // //     if (blockEntity.isBurning() || (!isFuelSlotEmpty(blockEntity) && isOutputSlotEmptyOrReceivable())) {
            
    // //         if (!blockEntity.isBurning()) {
    // //             blockEntity.burnTime = getFuelTime(fuelItemstack); // Get fuel burn time
    // //             blockEntity.fuelTime = blockEntity.burnTime;
    // //             blockEntity.maxTemperature = getMaxTemperature(fuelItemstack);
    // //             if (blockEntity.isBurning()) {
    // //                 dirty = true;
    // //                 if (!isFuelSlotEmpty(blockEntity)) {
    // //                     Item remainderFuelItem = fuelItemstack.getItem().getRecipeRemainder(); 
    // //                     fuelItemstack.decrement(1); // Consume fuel
    // //                     if (fuelItemstack.isEmpty()) {
    // //                         // Add the empty bucket back to the fuel slot
    // //                         blockEntity.inventory.set(INPUT_SLOT_1, remainderFuelItem == null ? ItemStack.EMPTY : new ItemStack(remainderFuelItem)); 
    // //                     }
    // //                 }
    // //             }
    // //         }
    // //     }

    // //     if (burning != blockEntity.isBurning()) {
    // //         dirty = true;
    // //         state = (BlockState)state.with(CrucibleBlock.LIT, blockEntity.isBurning());
    // //         world.setBlockState(pos, state, 3);
    // //     }
        
    // //     if (dirty) {
    // //         markDirty(world, pos, state);
    // //     }
    // // }

    // protected int getFuelTime(ItemStack fuel) {
    //     if (fuel.isEmpty()) {
    //          return 0;
    //     } else {
    //         Item item = fuel.getItem();
    //         return (Integer)AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    //     }
    // }

    // // private boolean isFuelSlotEmpty(CrucibleBlockEntity blockEntity) {
    // //     return blockEntity.inventory.get(INPUT_SLOT_1).isEmpty();
    // // }

    // // private ItemStack getWasteProductRecipe(ItemStack fuel) {
    // //     boolean hasInput = fuel.getItem() == Items.ACACIA_LOG;
    // //     ItemStack result = new ItemStack(Items.CHARCOAL);

    // //     if (hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem())) {
    // //         return result;
    // //     } else {
    // //         return ItemStack.EMPTY;
    // //     }
    // // }

    // // private void craftWasteProduct(ItemStack result) {
    // //     if (result != ItemStack.EMPTY) {
    // //         this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    // //     }
    // // }

    // // private boolean canInsertItemIntoOutputSlot(Item item) {
    // //     return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    // // }

    // // private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
    // //     return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    // // }

    // // private boolean isOutputSlotEmptyOrReceivable() {
    // //     return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    // // }

    // // private boolean isBurning() {
    // //     return this.burnTime > 0;
    // // }

    // // private int getMaxTemperature(ItemStack itemStack) {
    // //     return switch (itemStack.getRegistryEntry().toString()) {
    // //         case "minecraft:lava_bucket" -> 1200;
    // //         case "minecraft:coal" -> 1600;
    // //         case "minecraft:coal_block" -> 1600;
    // //         case "minecraft:charcoal" -> 1200;
    // //         case "minecraft:dried_kelp_block" -> 50;
    // //         case "minecraft:blaze_rod" -> 1200;
    // //         default -> 950;
    // //     };
    // // }

}
