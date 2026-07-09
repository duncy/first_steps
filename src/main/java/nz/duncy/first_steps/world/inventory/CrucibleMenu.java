package nz.duncy.first_steps.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.world.item.Metal;

public class CrucibleMenu extends AbstractContainerMenu {
    private static final int CONTAINER_SIZE = 9;
    private final Container container;
    private final ContainerData data;

    protected CrucibleMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(CONTAINER_SIZE), new SimpleContainerData(Metal.values().length + 1));
    }

    public CrucibleMenu(int i, Inventory inventory, Container container, ContainerData data) {
        super(ModMenuType.CRUCIBLE_MENU, i);
        checkContainerSize(container, CONTAINER_SIZE);
        this.container = container;
        this.data = data;

        container.startOpen(inventory.player);

        for(int x = 0; x < 3; ++x) {
            for(int y = 0; y < 3; ++y) {
                this.addSlot(new CrucibleSlot(container, x + y * 3, 8 + x * 18, 22 + y * 18));
            }
        }

        this.addStandardInventorySlots(inventory, 8, 89);

        this.addDataSlots(data);
    }

    public int getData(int index) {
        return this.data.get(index);
    }

    public int getSize() {
        return this.data.getCount();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (i < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemStack2, this.container.getContainerSize(), this.slots.size(), true)) {
                return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
     }
    
}
