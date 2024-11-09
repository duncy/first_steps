package nz.duncy.first_steps.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    @Inject(method = "Lnet/minecraft/block/CampfireBlock;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", at = @At("RETURN"), cancellable = true)
    private void injected(CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(cir.getReturnValue().with(CampfireBlock.LIT, false));
    }
}
