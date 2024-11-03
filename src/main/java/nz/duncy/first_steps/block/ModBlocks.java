package nz.duncy.first_steps.block;

import com.feintha.regedit.RegistryEditEvent;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.ClayBlock;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.block.custom.KilnBlock;
import nz.duncy.first_steps.block.custom.OreRockBlock;
import nz.duncy.first_steps.block.custom.RockBlock;
import nz.duncy.first_steps.block.custom.UnfiredCrucibleBlock;
import nz.duncy.first_steps.block.custom.UnfiredDecoratedPotBlock;
import nz.duncy.first_steps.block.custom.UnfiredMoldBlock;
import nz.duncy.first_steps.block.custom.PottersWheelBlock;

public class ModBlocks {
        // ORES
        public static final Block STONE_TIN_ORE = registerStoneOreBlock("stone_tin_ore");
        public static final Block STONE_COPPER_ORE = registerStoneOreBlock("stone_copper_ore");
        public static final Block STONE_IRON_ORE = registerStoneOreBlock("stone_iron_ore");
        public static final Block DEEPSLATE_COPPER_ORE = registerDeepslateOreBlock("deepslate_copper_ore");
        public static final Block DEEPSLATE_IRON_ORE = registerDeepslateOreBlock("deepslate_iron_ore");
        public static final Block BASALT_MULLITE_ORE = registerBasaltOreBlock("basalt_mullite_ore");

        // RAW ORE BLOCK
        public static final Block RAW_TIN = registerStoneOreBlock("raw_tin_block");
        public static final Block RAW_STONE_COPPER = registerStoneOreBlock("raw_stone_copper_block");
        public static final Block RAW_STONE_IRON = registerStoneOreBlock("raw_stone_iron_block");
        public static final Block RAW_DEEPSLATE_COPPER = registerDeepslateOreBlock("raw_deepslate_copper_block");
        public static final Block RAW_DEEPSLATE_IRON = registerDeepslateOreBlock("raw_deepslate_iron_block");

        // ROCKS
        public static final Block STONE_ROCK = registerBlock("stone_rock",
                        new RockBlock(FabricBlockSettings.create().strength(0.0F, 0.0F)
                                        .pistonBehavior(PistonBehavior.DESTROY)
                                        .mapColor(MapColor.STONE_GRAY), new Identifier("minecraft:block/stone")));

        public static Block FLINT_ROCK = registerBlockWithExistingVanillaItem("flint_rock",
                        new RockBlock(FabricBlockSettings.copyOf(STONE_ROCK).mapColor(MapColor.BLACK),
                                        new Identifier(FirstSteps.MOD_ID, "block/flint_rock")),
                                        Items.FLINT);

        public static final Block BASALT_ROCK = registerBlock("basalt_rock",
                        new RockBlock(FabricBlockSettings.copyOf(STONE_ROCK),
                                        new Identifier("minecraft:block/basalt_side")));

        public static final Block OBSIDIAN_ROCK = registerBlock("obsidian_rock",
                        new RockBlock(FabricBlockSettings.copyOf(FLINT_ROCK),
                                        new Identifier("minecraft:block/obsidian")));

        public static final Block COPPER_ROCK = registerBlock("copper_rock",
                        new OreRockBlock(FabricBlockSettings.copyOf(STONE_ROCK)));

        // CRAFTING
        // KILN
        public static final Block KILN = registerBlock("kiln", new KilnBlock(
                        FabricBlockSettings.copyOf(Blocks.TERRACOTTA)
                                        .luminance(Blocks.createLightLevelFromLitBlockState(13))));

        // POTTERY
        public static final Block POTTERS_WHEEL = registerBlock("potters_wheel",
                        new PottersWheelBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)));

        // FIRED
        public static final Block FIRED_CRUCIBLE = registerUniqueBlock("fired_crucible",
                        new CrucibleBlock(FabricBlockSettings.create().strength(0.0F, 0.0F)
                                        .pistonBehavior(PistonBehavior.DESTROY)
                                        .mapColor(MapColor.TERRACOTTA_RED).nonOpaque()
                                        .sounds(BlockSoundGroup.DECORATED_POT)));

        // UNFIRED
        public static final Block UNFIRED_CRUCIBLE = registerUniqueBlock("unfired_crucible",
                        new UnfiredCrucibleBlock(FabricBlockSettings.copyOf(Blocks.CLAY).breakInstantly()));
        public static final Block UNFIRED_FLOWER_POT = registerBlock("unfired_flower_pot",
                        new Block(FabricBlockSettings.copyOf(Blocks.CLAY).breakInstantly()));
        public static final Block UNFIRED_DECORATED_POT = registerBlock("unfired_decorated_pot",
                        new UnfiredDecoratedPotBlock(FabricBlockSettings.copyOf(Blocks.CLAY).breakInstantly()));

        // CLAY
        public static final Block CLAY = registerBlockWithExistingVanillaItem("clay", new ClayBlock(FabricBlockSettings.copyOf(Blocks.CLAY).blockVision((state, world, pos) -> {
                return (Integer)state.get(ClayBlock.CLAY_LAYERS) >= 4;
               }).pistonBehavior(PistonBehavior.DESTROY)),
               Items.CLAY_BALL);


        // CAST MOLDS
        public static final Block HOE_HEAD_MOLD = registerBlock("hoe_head_mold", new UnfiredMoldBlock(FabricBlockSettings.copyOf(Blocks.CLAY).breakInstantly()));



        private static Block registerBlock(String name, Block block) {
                registerBlockItem(name, block);
                return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
        }

        private static Block registerUniqueBlock(String name, Block block) {
                registerUniqueBlockItem(name, block);
                return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
        }

        public static Block registerBlockWithExistingVanillaItem(String name, Block block, Item vanillaItem) {
                RegistryEditEvent.EVENT.register(manipulator -> {
                        manipulator.Redirect(Registries.ITEM, vanillaItem,
                                        new BlockItem(block, new FabricItemSettings()));
                });
                return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
        }

        public static Item registerBlockItem(String name, Block block) {
                return Registry.register(Registries.ITEM, new Identifier(FirstSteps.MOD_ID, name),
                                new BlockItem(block, new FabricItemSettings()));
        }

        public static Item registerUniqueBlockItem(String name, Block block) {
                return Registry.register(Registries.ITEM, new Identifier(FirstSteps.MOD_ID, name),
                                new BlockItem(block, new FabricItemSettings().maxCount(1)));
        }

        public static Block registerStoneOreBlock(String name) {
                return registerBlock(name,
                                new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
        }

        public static Block registerDeepslateOreBlock(String name) {
                return registerBlock(name,
                                new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE)));
        }

        public static Block registerBasaltOreBlock(String name) {
                return registerBlock(name,
                                new PillarBlock(FabricBlockSettings.copyOf(Blocks.BASALT)));
        }

        public static void registerModBlocks() {
                FirstSteps.LOGGER.info("Registering ModBlocks for " + FirstSteps.MOD_ID);
        }
}
