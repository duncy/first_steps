package nz.duncy.first_steps.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.component.ModDataComponentTypes;

public class KilnTopSlot extends Slot {

   public KilnTopSlot(Inventory inventory, int index, int x, int y) {
      super(inventory, index, x, y);
   }

   public boolean canInsert(ItemStack stack) {
      return stack.getItem() == ModBlocks.FIRED_CRUCIBLE.asItem();
   }
   
   @Override
   public boolean canTakeItems(PlayerEntity playerEntity) {
      ItemStack crucibleItemStack = this.inventory.getStack(1);
      return crucibleItemStack.getOrDefault(ModDataComponentTypes.TEMPERATURE, 20) == 20;
   }
}
