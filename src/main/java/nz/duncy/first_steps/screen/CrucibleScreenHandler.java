package nz.duncy.first_steps.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CrucibleScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public CrucibleScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9),
            new ArrayPropertyDelegate(3));
    }

    public CrucibleScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.CRUCIBLE_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;

        // this.addSlot(new KilnFuelSlot(this, inventory, 0, 44, 50)); // Fuel input
        // this.addSlot(new KilnTopSlot(inventory, 1, 80, 21)); // Top input
        // this.addSlot(new KilnOutputSlot(playerInventory.player, inventory, 2, 116, 50)); // Fuel output
        for (int i = 0; i < 9; i++) {
            this.addSlot(new CrucibleSlot(this, inventory, i, 24 + (22 * (i % 3)), 17 + (18 * ((int) Math.ceil(i/3)))));
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isFull() {
        return false;
    }

    public boolean isSmelting() {
        return true;
    }

    public int getSmeltingProgress(int index) {
        return 12;
    }

    public int getTemperatureBarValue() {
        int temperature = this.propertyDelegate.get(0);
        int maxTemperature = 1600;

        int temperatureBarSize = 55;

        return maxTemperature != 0 && temperature != 0 ? temperature * temperatureBarSize / maxTemperature : 0;
    }

    public int getTemperature() {
        return this.propertyDelegate.get(0);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        Slot slot = this.slots.get(invSlot);
        ItemStack originalStack = slot.getStack();
        ItemStack newStack = ItemStack.EMPTY;

        // If the slot has an item
        if (slot != null && slot.hasStack()) {
            newStack = originalStack.copy();

            if (invSlot >= this.inventory.size()) {
                // Try inserting 1 item at a time instead of the whole stack
                if (!this.moveOneItem(originalStack)) {
                    newStack = ItemStack.EMPTY;
                }

                newStack = ItemStack.EMPTY;
            } else {
                if (!super.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    newStack = ItemStack.EMPTY;
                }
            }

        }

        // Update the slot if the original stack size is reduced
        if (originalStack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        // Mark the change and return the modified stack
        return newStack;
    }

    private boolean moveOneItem(ItemStack stack) {
        boolean success = false;
        for (int i = 0; i < this.inventory.size(); i++) {
            Slot targetSlot = this.slots.get(i);
            
            // Check if slot is valid for insertion and has space
            if (targetSlot.canInsert(stack) && (targetSlot.getStack().getCount() < targetSlot.getStack().getMaxCount())) {
                ItemStack singleItemStack = stack.copy();
                singleItemStack.setCount(1); // Set count to 1
                
                // Use your existing insertItem method
                if (this.insertItem(singleItemStack, i, i + 1, false)) {
                    stack.decrement(1); // Reduce original stack by 1
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 85 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 143));
        }
    }

    protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        boolean bl = false;
        int i = startIndex;
        if (fromLast) {
           i = endIndex - 1;
        }
  
        Slot slot;
        ItemStack itemStack;
  
        if (!stack.isEmpty()) {
           if (fromLast) {
              i = endIndex - 1;
           } else {
              i = startIndex;
           }
  
           while(true) {
              if (fromLast) {
                 if (i < startIndex) {
                    break;
                 }
              } else if (i >= endIndex) {
                 break;
              }
  
              slot = (Slot)this.slots.get(i);
              itemStack = slot.getStack();
              if (itemStack.isEmpty() && slot.canInsert(stack)) {
                 if (stack.getCount() > slot.getMaxItemCount()) {
                    slot.setStack(stack.split(slot.getMaxItemCount()));
                 } else {
                    slot.setStack(stack.split(stack.getCount()));
                 }
  
                 slot.markDirty();
                 bl = true;
                 break;
              }
  
              if (fromLast) {
                 --i;
              } else {
                 ++i;
              }
           }
        }
  
        return bl;
    }
    
}
