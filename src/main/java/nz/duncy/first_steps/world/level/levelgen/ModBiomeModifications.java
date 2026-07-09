package nz.duncy.first_steps.world.level.levelgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import nz.duncy.first_steps.FirstSteps;


public class ModBiomeModifications {
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

    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                Biomes.WINDSWEPT_HILLS,
                Biomes.WINDSWEPT_GRAVELLY_HILLS,
                Biomes.WINDSWEPT_FOREST,
                Biomes.OLD_GROWTH_PINE_TAIGA,
                Biomes.OLD_GROWTH_SPRUCE_TAIGA,
                Biomes.TAIGA,
                Biomes.STONY_SHORE,
                Biomes.SAVANNA_PLATEAU,
                Biomes.SAVANNA,
                Biomes.STONY_PEAKS),
            GenerationStep.Decoration.UNDERGROUND_ORES, of("ore_tin_stone")
        );

        BiomeModifications.create(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "ore_copper")).add(
            ModificationPhase.REPLACEMENTS, 
            BiomeSelectors.foundInOverworld(), 
            context -> {
                context.getGenerationSettings().removeFeature(ResourceKey.create(Registries.PLACED_FEATURE, Identifier.withDefaultNamespace("ore_copper")));
                context.getGenerationSettings().removeFeature(ResourceKey.create(Registries.PLACED_FEATURE, Identifier.withDefaultNamespace("ore_copper_large")));
                context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, of("ore_copper_stone"));
                context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, of("ore_copper_deepslate"));
            }
        );

        BiomeModifications.create(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "ore_iron")).add(
            ModificationPhase.REPLACEMENTS, 
            BiomeSelectors.foundInOverworld(), 
            context -> {
                context.getGenerationSettings().removeFeature(ResourceKey.create(Registries.PLACED_FEATURE, Identifier.withDefaultNamespace("ore_iron_small")));
                context.getGenerationSettings().removeFeature(ResourceKey.create(Registries.PLACED_FEATURE, Identifier.withDefaultNamespace("ore_iron_middle")));
                context.getGenerationSettings().removeFeature(ResourceKey.create(Registries.PLACED_FEATURE, Identifier.withDefaultNamespace("ore_iron_upper")));
                context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, of("ore_iron_stone"));
                context.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, of("ore_iron_deepslate"));
            }
        );
    }

    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, id));
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering biome modifications for " + FirstSteps.MOD_ID);
        generateRocks();
        generateOres();
    }
}      
