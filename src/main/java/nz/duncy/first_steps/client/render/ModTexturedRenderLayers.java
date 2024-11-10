package nz.duncy.first_steps.client.render;

import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.DecoratedPotPattern;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.util.Identifier;

import nz.duncy.first_steps.FirstSteps;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
    public static final Identifier UNFIRED_DECORATED_POT_ATLAS_TEXTURE = Identifier.ofVanilla("textures/atlas/unfired_decorated_pot.png");

    public static final Map<RegistryKey<DecoratedPotPattern>, SpriteIdentifier> UNFIRED_DECORATED_POT_PATTERN_TEXTURES = (Map<RegistryKey<DecoratedPotPattern>, SpriteIdentifier>) Registries.DECORATED_POT_PATTERN
    .streamEntries()
    .collect(Collectors.toMap(Reference::registryKey, reference -> createUnfiredDecoratedPotPatternTextureId(((DecoratedPotPattern)reference.value()).assetId())));

    private static Identifier getTextureId(RegistryKey<String> key) {
        return Identifier.of(FirstSteps.MOD_ID, key.getValue().toString().split(":")[1]).withPrefixedPath("entity/unfired_decorated_pot/");
    }


	public static final SpriteIdentifier UNFIRED_DECORATED_POT_BASE = createUnfiredDecoratedPotPatternTextureId(Identifier.of(FirstSteps.MOD_ID, "unfired_decorated_pot_base"));
	public static final SpriteIdentifier UNFIRED_DECORATED_POT_SIDE = createUnfiredDecoratedPotPatternTextureId(Identifier.of(FirstSteps.MOD_ID, "unfired_decorated_pot_side"));

    private static SpriteIdentifier createUnfiredDecoratedPotPatternTextureId(Identifier patternId) {
        return new SpriteIdentifier(UNFIRED_DECORATED_POT_ATLAS_TEXTURE, patternId.withPrefixedPath("entity/decorated_pot/"));
    }

    @Nullable
    public static SpriteIdentifier getUnfiredDecoratedPotPatternTextureId(@Nullable RegistryKey<DecoratedPotPattern> potPatternKey) {
        return potPatternKey == null ? null : (SpriteIdentifier)UNFIRED_DECORATED_POT_PATTERN_TEXTURES.get(potPatternKey);
    }
}





