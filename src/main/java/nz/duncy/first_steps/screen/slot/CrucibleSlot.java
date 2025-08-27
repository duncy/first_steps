package nz.duncy.first_steps.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import nz.duncy.first_steps.screen.CrucibleScreenHandler;
import nz.duncy.first_steps.util.ModTags;

public class CrucibleSlot extends Slot {
    private final CrucibleScreenHandler handler;

    public CrucibleSlot(CrucibleScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    public boolean canInsert(ItemStack stack) {
        return !this.handler.isFull() && isOre(stack);
    }
    
    public static boolean isOre(ItemStack stack) {
        return stack.isIn(ModTags.Items.ORES);
    }
    
    public int getMaxItemCount(ItemStack stack) {
        return 1;
    }

    

}
