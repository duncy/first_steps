package nz.duncy.first_steps.world;

import java.util.List;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.world.gen.feature.ModFeature;
import nz.duncy.first_steps.world.gen.feature.RockFeature;
import nz.duncy.first_steps.world.gen.feature.RockFeatureConfig;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<? ,?>> STONE_TIN_ORE_KEY = registerKey("stone_tin_ore");
    public static final RegistryKey<ConfiguredFeature<? ,?>> STONE_COPPER_ORE_KEY = registerKey("stone_copper_ore");
    public static final RegistryKey<ConfiguredFeature<? ,?>> STONE_IRON_ORE_KEY = registerKey("stone_iron_ore");
    public static final RegistryKey<ConfiguredFeature<? ,?>> DEEPSLATE_COPPER_ORE_KEY = registerKey("deepslate_copper_ore");
    public static final RegistryKey<ConfiguredFeature<? ,?>> DEEPSLATE_IRON_ORE_KEY = registerKey("deepslate_iron_ore");
    public static final RegistryKey<ConfiguredFeature<? ,?>> SILIMANTITE_ORE_KEY = registerKey("silimantite_ore");

    //public static final RegistryKey<ConfiguredFeature<? ,?>> STONE_ROCK_KEY = registerKey("stone_rock");
    // public static final RegistryKey<ConfiguredFeature<? ,?>> FLINT_ROCK_KEY = registerKey("flint_rock");
    // public static final RegistryKey<ConfiguredFeature<? ,?>> BASALT_ROCK_KEY = registerKey("basalt_rock");
    // public static final RegistryKey<ConfiguredFeature<? ,?>> OBSIDIAN_ROCK_KEY = registerKey("obsidian_rock");
    // public static final RegistryKey<ConfiguredFeature<? ,?>> COPPER_ROCK_KEY = registerKey("copper_rock");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplacables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplacables = new TagMatchRuleTest(BlockTags.NETHER_CARVER_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldStoneTinOres =
            List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.STONE_TIN_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldStoneCopperOres =
            List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.STONE_COPPER_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldStoneIronOres =
            List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.STONE_IRON_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldDeepslateCopperOres =
            List.of(OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_COPPER_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldDeepslateIronOres =
            List.of(OreFeatureConfig.createTarget(deepslateReplacables, ModBlocks.DEEPSLATE_IRON_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherSilimantiteOres =
           List.of(OreFeatureConfig.createTarget(netherReplacables, ModBlocks.BASALT_MULLITE_ORE.getDefaultState()));
        
        register(context, STONE_TIN_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldStoneTinOres, 16));
        register(context, STONE_COPPER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldStoneCopperOres, 12));
        register(context, STONE_IRON_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldStoneIronOres, 5));
        register(context, DEEPSLATE_COPPER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldDeepslateCopperOres, 14));
        register(context, DEEPSLATE_IRON_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldDeepslateIronOres, 10));
        register(context, SILIMANTITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherSilimantiteOres, 12));

        //register(context, STONE_ROCK_KEY, ModFeature.ROCK_FEATURE, new RockFeatureConfig(10, new Identifier(FirstSteps.MOD_ID, "stone_rock")));
    }
    
    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(FirstSteps.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, 
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
