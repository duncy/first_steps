package nz.duncy.first_steps.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import nz.duncy.first_steps.block.ModBlocks;

public class KilnTopSlot extends Slot {

   public KilnTopSlot(Inventory inventory, int index, int x, int y) {
      super(inventory, index, x, y);
   }

   public boolean canInsert(ItemStack stack) {
      return stack.getItem() == ModBlocks.FIRED_CRUCIBLE.asItem();
   }
   
   @Override
   public boolean canTakeItems(PlayerEntity playerEntity) {
      int temperature = 20;
      ItemStack crucibleItemStack = this.inventory.getStack(1);
      NbtCompound nbtCompound = crucibleItemStack.getNbt();
      if (nbtCompound.contains("crucible.temperature", 3)) {
         temperature = nbtCompound.getInt("crucible.temperature");
      }
      
      return temperature == 20;
   }
}
