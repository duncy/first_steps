package nz.duncy.first_steps.world;

import java.util.List;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import nz.duncy.first_steps.FirstSteps;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> STONE_TIN_ORE_PLACED_KEY = registerKey("stone_tin_ore_placed");
    public static final RegistryKey<PlacedFeature> STONE_COPPER_ORE_PLACED_KEY = registerKey("stone_copper_ore_placed");
    public static final RegistryKey<PlacedFeature> STONE_IRON_ORE_PLACED_KEY = registerKey("stone_iron_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_COPPER_ORE_PLACED_KEY = registerKey("deepslate_copper_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_IRON_ORE_PLACED_KEY = registerKey("deepslate_iron_ore_placed");
    // public static final RegistryKey<PlacedFeature> SILIMANTITE_ORE_PLACED_KEY = registerKey("silimantite_ore_placed");

    //public static final RegistryKey<PlacedFeature> STONE_ROCK_PLACED_KEY = registerKey(ModFeature.ROCK_FEATURE_ID);
    // public static final RegistryKey<PlacedFeature> FLINT_ROCK_ORE_PLACED_KEY = registerKey("flint_rock_placed");
    // public static final RegistryKey<PlacedFeature> BASALT_ROCK_ORE_PLACED_KEY = registerKey("basalt_rock_placed");
    // public static final RegistryKey<PlacedFeature> OBSIDIAN_ROCK_ORE_PLACED_KEY = registerKey("obsidian_rock_placed");
    // public static final RegistryKey<PlacedFeature> COPPER_ROCK_ORE_PLACED_KEY = registerKey("copper_rock_placed");



    public static void boostrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, STONE_TIN_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.STONE_TIN_ORE_KEY),
                ModOrePlacement.modifiersWithCount(3, // Veins per chunk
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(32), YOffset.fixed(64))));

        register(context, STONE_COPPER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.STONE_COPPER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12, // Veins per chunk
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(80))));

        register(context, STONE_IRON_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.STONE_IRON_ORE_KEY),
                ModOrePlacement.modifiersWithCount(4, // Veins per chunk
                    HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(16))));

        register(context, DEEPSLATE_COPPER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_COPPER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(16, // Veins per chunk
                    HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(0))));

        register(context, DEEPSLATE_IRON_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_IRON_ORE_KEY),
                ModOrePlacement.modifiersWithCount(10, // Veins per chunk
                    HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(0))));

        // register(context, SILIMANTITE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILIMANTITE_ORE_KEY),
        //         ModOrePlacement.modifiersWithCount(12, // Veins per chunk
        //             HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        //register(context, STONE_ROCK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.STONE_ROCK_KEY),
        //        List.of(SquarePlacementModifier.of()));

        // register(context, FLINT_ROCK_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLINT_ROCK_KEY),
        //         ModOrePlacement.modifiersWithCount(12, // Veins per chunk
        //             HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        // register(context, BASALT_ROCK_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BASALT_ROCK_KEY),
        //         ModOrePlacement.modifiersWithCount(12, // Veins per chunk
        //             HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        // register(context, OBSIDIAN_ROCK_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.OBSIDIAN_ROCK_KEY),
        //         ModOrePlacement.modifiersWithCount(12, // Veins per chunk
        //             HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        // register(context, COPPER_ROCK_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.COPPER_ROCK_KEY),
        //         ModOrePlacement.modifiersWithCount(12, // Veins per chunk
        //             HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FirstSteps.MOD_ID, name));
    }

    public static RegistryKey<PlacedFeature> registerKey(Identifier featureIdentifier) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, featureIdentifier);
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
