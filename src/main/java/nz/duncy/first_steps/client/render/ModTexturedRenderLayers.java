package nz.duncy.first_steps.client.render;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import nz.duncy.first_steps.FirstSteps;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
    public static final Identifier UNFIRED_DECORATED_POT_ATLAS_TEXTURE = Identifier.ofVanilla("textures/atlas/unfired_decorated_pot.png");

    public static final Map<RegistryKey<String>, SpriteIdentifier> UNFIRED_DECORATED_POT_PATTERN_TEXTURES;

    private static Identifier getTextureId(RegistryKey<String> key) {
        return Identifier.of(FirstSteps.MOD_ID, key.getValue().toString().split(":")[1]).withPrefixedPath("entity/unfired_decorated_pot/");
     }

    private static SpriteIdentifier createUnfiredDecoratedPotPatternTextureId(RegistryKey<String> potPatternKey) {
        return new SpriteIdentifier(UNFIRED_DECORATED_POT_ATLAS_TEXTURE, getTextureId(potPatternKey));
    }

    @Nullable
    public static SpriteIdentifier getUnfiredDecoratedPotPatternTextureId(@Nullable RegistryKey<String> potPatternKey) {
        return potPatternKey == null ? null : (SpriteIdentifier)UNFIRED_DECORATED_POT_PATTERN_TEXTURES.get(potPatternKey);
    }


    static {
        UNFIRED_DECORATED_POT_PATTERN_TEXTURES = (Map)Registries.DECORATED_POT_PATTERN.getKeys().stream().collect(Collectors.toMap(Function.identity(), ModTexturedRenderLayers::createUnfiredDecoratedPotPatternTextureId));
    }
}





