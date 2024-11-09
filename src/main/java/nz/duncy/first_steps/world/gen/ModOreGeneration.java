package nz.duncy.first_steps.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import nz.duncy.first_steps.world.ModPlacedFeatures;

public class ModOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                    BiomeKeys.WINDSWEPT_HILLS,
                    BiomeKeys.WINDSWEPT_GRAVELLY_HILLS,
                    BiomeKeys.WINDSWEPT_FOREST,
                    BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                    BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA,
                    BiomeKeys.TAIGA,
                    BiomeKeys.STONY_SHORE,
                    BiomeKeys.SAVANNA_PLATEAU,
                    BiomeKeys.SAVANNA,
                    BiomeKeys.STONY_PEAKS),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STONE_TIN_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STONE_COPPER_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.STONE_IRON_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DEEPSLATE_COPPER_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.DEEPSLATE_IRON_ORE_PLACED_KEY);

        // BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
        //         GenerationStep.Feature.UNDERGROUND_ORES , ModPlacedFeatures.SILIMANTITE_ORE_PLACED_KEY);


    }
}
