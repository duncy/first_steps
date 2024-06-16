package nz.duncy.first_steps.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import nz.duncy.first_steps.block.entity.KilnBlockEntity;

public class KilnScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final KilnBlockEntity blockEntity;

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
            new ArrayPropertyDelegate(3));
    }

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.KILN_SCREEN_HANDLER, syncId);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((KilnBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 1, 80, 21));
        this.addSlot(new Slot(inventory, 0, 44, 50));
        this.addSlot(new Slot(inventory, 2, 116, 50));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1); // Max progress
        int progressArrowSize = 26; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getTemperature() {
        int temperature = this.propertyDelegate.get(2);
        int maxTemperature = 100;

        int temperatureBarSize = 59;

        return maxTemperature != 0 && temperature != 0 ? temperature * temperatureBarSize / maxTemperature : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        // Entity inventory at slot invSlot, 0 is fuel input, 1 is kiln input, 2 is output
        Slot slot = this.slots.get(invSlot);
        // If slot exists and has a stack already
        if (slot != null && slot.hasStack()) {
            // Get the itemstack from slot and copy it
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            // If the slot index is less than the entities inventory size
            if (invSlot < this.inventory.size()) {
                // If the item stack is a fuel item and was unable to be inserted into the fuel slot
                if (this.isFuel(originalStack)) {
                    if (!this.insertItem(originalStack, 0, 0, true)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    protected boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
    }
}
