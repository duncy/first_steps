package nz.duncy.first_steps.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.level.block.entity.CrucibleBlockEntity;

public class CrucibleSlot extends Slot {
    public CrucibleSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    public boolean mayPlace(ItemStack itemStack) {
        if (container instanceof CrucibleBlockEntity сrucibleBlockEntity) {
            return !сrucibleBlockEntity.metalStorage.wouldThisOverfill(itemStack) && itemStack.is(ModItemTags.CRUCIBLE_ACCEPTS);
        } else {
            return false;
        }
        
    }

    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
