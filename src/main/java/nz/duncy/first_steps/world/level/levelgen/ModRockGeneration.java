package nz.duncy.first_steps.world.level.levelgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import nz.duncy.first_steps.FirstSteps;


public class ModRockGeneration {
    public static void generateRocks() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
            GenerationStep.Decoration.VEGETAL_DECORATION, of("stone_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
            GenerationStep.Decoration.VEGETAL_DECORATION, of("flint_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
            GenerationStep.Decoration.VEGETAL_DECORATION, of("basalt_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
            GenerationStep.Decoration.VEGETAL_DECORATION, of("obsidian_rock"));

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
            GenerationStep.Decoration.VEGETAL_DECORATION, of("obsidian_rock"));
    }

    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, id));
    }
}      
