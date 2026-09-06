package nz.duncy.first_steps.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Inject(method = "isUsableForCrafting", at = @At("RETURN"), cancellable = true)
    private static void firststeps$allowDamagedItemsForCrafting(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.isDamageableItem() && itemStack.isDamaged()) {
            cir.setReturnValue(true);
        }
    }
}
