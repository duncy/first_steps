package nz.duncy.first_steps.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.world.item.ModItems;

public class KilnCrucibleSlot extends Slot {
    public KilnCrucibleSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.is(ModItems.CRUCIBLE);
    }
}
