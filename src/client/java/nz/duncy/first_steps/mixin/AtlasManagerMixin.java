package nz.duncy.first_steps.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.resources.model.AtlasManager;
import nz.duncy.first_steps.renderer.ModSheets;

@Mixin(AtlasManager.class)
public class AtlasManagerMixin {
    @Shadow @Final @Mutable
    private static List<AtlasManager.AtlasConfig> KNOWN_ATLASES;

    @Inject(method="<clinit>", at = @At("TAIL"))
    private static void customAtlas(CallbackInfo callbackInfo) {
        KNOWN_ATLASES = new ArrayList<AtlasManager.AtlasConfig>(KNOWN_ATLASES);
        KNOWN_ATLASES.add(new AtlasManager.AtlasConfig(ModSheets.DECORATED_JAR_SHEET, ModSheets.DECORATED_JAR_ID, false));
        KNOWN_ATLASES.add(new AtlasManager.AtlasConfig(ModSheets.UNFIRED_DECORATED_POT_SHEET, ModSheets.UNFIRED_DECORATED_POT_ID, false));
        KNOWN_ATLASES.add(new AtlasManager.AtlasConfig(ModSheets.UNFIRED_DECORATED_JAR_SHEET, ModSheets.UNFIRED_DECORATED_JAR_ID, false));
        KNOWN_ATLASES.add(new AtlasManager.AtlasConfig(ModSheets.POTTERS_WHEEL_SHEET, ModSheets.POTTERS_WHEEL_ID, false));
    }
}
