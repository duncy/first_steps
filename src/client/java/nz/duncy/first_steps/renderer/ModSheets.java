package nz.duncy.first_steps.renderer;

import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import nz.duncy.first_steps.FirstSteps;

public class ModSheets {
    public static final Identifier DECORATED_JAR_SHEET = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "decorated_jar");
    public static final MaterialMapper DECORATED_JAR_MAPPER = new MaterialMapper(DECORATED_JAR_SHEET, "entity/decorated_jar");
    public static final Material DECORATED_JAR_BASE = DECORATED_JAR_MAPPER.apply(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "decorated_jar_base"));

    public static final Identifier UNFIRED_DECORATED_POT_SHEET = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_pot");
    public static final MaterialMapper UNFIRED_DECORATED_POT_MAPPER = new MaterialMapper(UNFIRED_DECORATED_POT_SHEET, "entity/unfired_decorated_pot");
    public static final Material UNFIRED_DECORATED_POT_BASE = UNFIRED_DECORATED_POT_MAPPER.apply(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_pot_base"));
    public static final Material UNFIRED_DECORATED_POT_SIDE = UNFIRED_DECORATED_POT_MAPPER.apply(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_pot_side"));

    public static final Identifier UNFIRED_DECORATED_JAR_SHEET = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_jar");
    public static final MaterialMapper UNFIRED_DECORATED_JAR_MAPPER = new MaterialMapper(UNFIRED_DECORATED_JAR_SHEET, "entity/unfired_decorated_jar");
    public static final Material UNFIRED_DECORATED_JAR_BASE = UNFIRED_DECORATED_JAR_MAPPER.apply(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_jar_base"));

    public static final Identifier POTTERS_WHEEL_SHEET = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel");
    public static final MaterialMapper POTTERS_WHEEL_MAPPER = new MaterialMapper(POTTERS_WHEEL_SHEET, "entity/potters_wheel");
    public static final Material POTTERS_WHEEL = POTTERS_WHEEL_MAPPER.apply(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel"));

    public static final Identifier DECORATED_JAR_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "decorated_jar");
    public static final Identifier UNFIRED_DECORATED_POT_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_pot");
    public static final Identifier UNFIRED_DECORATED_JAR_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_jar");
    public static final Identifier POTTERS_WHEEL_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel");

    public static Identifier getUnfiredDecoratedPotTextureID(Identifier key) {
        return Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, key.getPath());
    }

    public static final Map<ResourceKey<DecoratedPotPattern>, Material> UNFIRED_DECORATED_POT_MATERIALS = BuiltInRegistries.DECORATED_POT_PATTERN.listElements()
        .collect(Collectors.toMap(Holder.Reference::key, (reference) -> {
            Identifier ID = ((DecoratedPotPattern)reference.value()).assetId();
            return UNFIRED_DECORATED_POT_MAPPER.apply(getUnfiredDecoratedPotTextureID(ID));
    }));

    public static @Nullable Material getUnfiredDecoratedPotMaterial(@Nullable ResourceKey<DecoratedPotPattern> resourceKey) {
        return resourceKey == null ? null : UNFIRED_DECORATED_POT_MATERIALS.get(resourceKey);
    }
}