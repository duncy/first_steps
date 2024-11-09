package nz.duncy.first_steps.mixin;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.ModTexturedRenderLayers;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin {
    @Shadow @Final @Mutable
    private static  Map<Identifier, Identifier> LAYERS_TO_LOADERS;

    @Inject(method="<clinit>", at = @At("TAIL"))
    private static void customAtlas(CallbackInfo callbackInfo) {
        LAYERS_TO_LOADERS = new HashMap<Identifier, Identifier>(LAYERS_TO_LOADERS);
        LAYERS_TO_LOADERS.put(ModTexturedRenderLayers.UNFIRED_DECORATED_POT_ATLAS_TEXTURE, Identifier.of(FirstSteps.MOD_ID, "unfired_decorated_pot"));
    }
}


