package nz.duncy.first_steps.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import nz.duncy.first_steps.FirstSteps;

public class ModRockGeneration {
    public static void generateRocks() {

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, of("stone_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, of("flint_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.VEGETAL_DECORATION, of("basalt_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.VEGETAL_DECORATION, of("obsidian_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.VEGETAL_DECORATION, of("obsidian_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, of("copper_rock"));
        }

        public static RegistryKey<PlacedFeature> of(String id) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(FirstSteps.MOD_ID, id));
	}
}
