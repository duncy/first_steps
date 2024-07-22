package nz.duncy.first_steps.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeatures;

public class ModRockGeneration {
    public static void generateRocks() {

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatures.of("first_steps:stone_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatures.of("first_steps:flint_rock"));

        // BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
        //         GenerationStep.Feature.TOP_LAYER_MODIFICATION, PlacedFeatures.of("first_steps:basalt_rock"));

        // BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
        //         GenerationStep.Feature.TOP_LAYER_MODIFICATION, PlacedFeatures.of("first_steps:obsidian_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, PlacedFeatures.of("first_steps:copper_rock"));
        }
}
