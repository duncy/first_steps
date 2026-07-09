package nz.duncy.first_steps.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class KilnFuelSlot extends Slot {
   private final KilnMenu menu;

   public KilnFuelSlot(KilnMenu kilnMenu, Container container, int i, int j, int k) {
      super(container, i, j, k);
      this.menu = kilnMenu;
   }

   public boolean mayPlace(ItemStack itemStack) {
      return menu.isFuel(itemStack) || isBucket(itemStack);
   }

   public int getMaxStackSize(ItemStack itemStack) {
      return isBucket(itemStack) ? 1 : super.getMaxStackSize(itemStack);
   }

   public static boolean isBucket(ItemStack itemStack) {
      return itemStack.is(Items.BUCKET);
   }
}
