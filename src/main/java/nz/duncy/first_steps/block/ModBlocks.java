package nz.duncy.first_steps.block;

import java.util.function.Function;


// import com.feintha.regedit.RegistryEditEvent;

import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
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
import nz.duncy.first_steps.block.custom.UnfiredFlowerPotBlock;
import nz.duncy.first_steps.block.custom.UnfiredMoldBlock;
import nz.duncy.first_steps.item.custom.RockBlockItem;
import nz.duncy.first_steps.block.custom.PottersWheelBlock;

public class ModBlocks {
        // ORES
        public static final Block STONE_TIN_ORE = registerStoneOreBlock("stone_tin_ore");
        public static final Block STONE_COPPER_ORE = registerStoneOreBlock("stone_copper_ore");
        public static final Block STONE_IRON_ORE = registerStoneOreBlock("stone_iron_ore");
        public static final Block DEEPSLATE_COPPER_ORE = registerDeepslateOreBlock("deepslate_copper_ore");
        public static final Block DEEPSLATE_IRON_ORE = registerDeepslateOreBlock("deepslate_iron_ore");
        // public static final Block BASALT_MULLITE_ORE = registerBasaltOreBlock("basalt_mullite_ore");

        // RAW ORE BLOCK
        public static final Block RAW_TIN = registerStoneOreBlock("raw_tin_block");
        public static final Block RAW_STONE_COPPER = registerStoneOreBlock("raw_stone_copper_block");
        public static final Block RAW_STONE_IRON = registerStoneOreBlock("raw_stone_iron_block");
        public static final Block RAW_DEEPSLATE_COPPER = registerDeepslateOreBlock("raw_deepslate_copper_block");
        public static final Block RAW_DEEPSLATE_IRON = registerDeepslateOreBlock("raw_deepslate_iron_block");

        // ROCKS
        public static final Block STONE_ROCK = registerRockBlock("stone_rock",
                        Settings.create().strength(0.0F, 0.0F)
                                        .pistonBehavior(PistonBehavior.DESTROY)
                                        .mapColor(MapColor.STONE_GRAY),
                                        true);

        public static Block FLINT_ROCK = registerRockBlock("flint_rock",
                        Settings.copy(STONE_ROCK)
                                        .mapColor(MapColor.BLACK),
                                        true);
                                        // Items.FLINT);

        public static final Block BASALT_ROCK = registerRockBlock("basalt_rock",
                        Settings.copy(STONE_ROCK),
                        true);

        public static final Block OBSIDIAN_ROCK = registerRockBlock("obsidian_rock",
                        Settings.copy(FLINT_ROCK),
                        true);

        public static final Block COPPER_ROCK = registerBlock(
                        "copper_rock",
                        OreRockBlock::new,
                        Settings.copy(Blocks.STONE),
                        true
                );

        // CRAFTING
        // KILN
        public static final Block KILN = registerBlock(
                "kiln",
                KilnBlock::new,
                Settings.copy(Blocks.TERRACOTTA)
                        .luminance(Blocks.createLightLevelFromLitBlockState(13)),
                true
        );

        // POTTERY
        public static final Block POTTERS_WHEEL = registerBlock(
                "potters_wheel",
                PottersWheelBlock::new,
                Settings.copy(Blocks.TERRACOTTA),
                true
        );

        // FIRED
        public static final Block FIRED_CRUCIBLE = registerCrucible(
                "fired_crucible",
                Settings.copy(Blocks.TERRACOTTA)
        );

        // UNFIRED
        public static final Block UNFIRED_CRUCIBLE = registerBlock(
                "unfired_crucible",
                UnfiredCrucibleBlock::new,
                Settings.copy(Blocks.CLAY).breakInstantly(),
                true
        ); 

        public static final Block UNFIRED_FLOWER_POT = registerBlock(
                "unfired_flower_pot",
                UnfiredFlowerPotBlock::new,
                Settings.copy(Blocks.CLAY).breakInstantly(),
                true)
        ;

        public static final Block UNFIRED_DECORATED_POT = registerBlock(
                                "unfired_decorated_pot",
                                UnfiredDecoratedPotBlock::new,
                                Settings.copy(Blocks.CLAY).breakInstantly(),
                                true
        ); 

        // CLAY
        public static final Block CLAY = registerBlock(
                "clay",
                ClayBlock::new,
                Settings.create()
                        .mapColor(MapColor.LIGHT_BLUE_GRAY)
                        .replaceable()
                        .notSolid()
                        .ticksRandomly()
                        .strength(0.6F)
                        .sounds(BlockSoundGroup.GRAVEL)
                        .blockVision((state, world, pos) -> (Integer)state.get(ClayBlock.CLAY_LAYERS) >= 4)
                        .pistonBehavior(PistonBehavior.DESTROY),
                true
        );

        // CAST MOLDS
        public static final Block HOE_HEAD_MOLD = registerMoldBlock("hoe_head_mold");
        public static final Block AXE_HEAD_MOLD = registerMoldBlock("axe_head_mold");
        public static final Block SHOVEL_HEAD_MOLD = registerMoldBlock("shovel_head_mold");
        public static final Block KNIFE_HEAD_MOLD = registerMoldBlock("knife_head_mold");
        public static final Block SPEAR_HEAD_MOLD = registerMoldBlock("spear_head_mold");
        public static final Block ARROW_HEAD_MOLD = registerMoldBlock("arrow_head_mold");
        public static final Block PICKAXE_HEAD_MOLD = registerMoldBlock("pickaxe_head_mold");
        public static final Block SWORD_HEAD_MOLD = registerMoldBlock("sword_head_mold");
        

        private static Block registerBlock(String name, Function<Settings, Block> blockFactory, Settings settings, boolean shouldRegisterItem) {
                RegistryKey<Block> blockKey = keyOfBlock(name);

                Block block = blockFactory.apply(settings.registryKey(blockKey));
                
                if (shouldRegisterItem) {
                        // Items need to be registered with a different type of registry key, but the ID
                        // can be the same.
                        RegistryKey<Item> itemKey = keyOfItem(name);

                        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
                        Registry.register(Registries.ITEM, itemKey, blockItem);
                }

                return Registry.register(Registries.BLOCK, blockKey, block);
        }
        
        private static Block registerCrucible(String name, Settings settings) {
                Block block = registerBlock(
                        name,
                        CrucibleBlock::new,
                        settings,
                        false
                );
                registerUniqueBlockItem(name, block);

                return block;
        }

        private static Block registerRockBlock(String name, Settings settings, boolean shouldRegisterItem) {
                Block block =  registerBlock(
                        name,
                        RockBlock::new,
                        settings,
                        false
                );

                if (shouldRegisterItem) {
                        registerRockBlockItem(name, block);
                }

                return block;
        }


        // public static Block registerRockBlockWithExistingVanillaItem(String name, Block block, Item vanillaItem) {
        //         RegistryEditEvent.EVENT.register(manipulator -> {
        //                 manipulator.Redirect(Registries.ITEM, vanillaItem,
        //                                 new RockBlockItem(block, new Item.Settings()));
        //         });
        //         return Registry.register(Registries.BLOCK, Identifier.of(FirstSteps.MOD_ID, name), block);
        // }

        // public static Block registerBlockWithExistingVanillaItem(String name, Block block, Item vanillaItem) {
        //         RegistryEditEvent.EVENT.register(manipulator -> {
        //                 manipulator.Redirect(Registries.ITEM, vanillaItem,
        //                                 new BlockItem(block, new Item.Settings()));
        //         });
        //         return Registry.register(Registries.BLOCK, Identifier.of(FirstSteps.MOD_ID, name), block);
        // }

        public static Item registerRockBlockItem(String name, Block block) {
                RegistryKey<Item> itemKey = keyOfItem(name);

                BlockItem blockItem = new RockBlockItem(block, new Item.Settings().registryKey(itemKey));
                return Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        public static Item registerUniqueBlockItem(String name, Block block) {
                RegistryKey<Item> itemKey = keyOfItem(name);

                BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).maxCount(1));
                return Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        public static Block registerStoneOreBlock(String name) {
                return registerBlock(
                        name,
                        Block::new,
                        Settings.create().sounds(BlockSoundGroup.STONE),
                        true
                );
        }

        public static Block registerDeepslateOreBlock(String name) {
                return registerBlock(
                        name,
                        Block::new,
                        Settings.create().sounds(BlockSoundGroup.DEEPSLATE),
                        true
                );
        }

        public static Block registerBasaltOreBlock(String name) {
                return registerBlock(
                        name,
                        PillarBlock::new,
                        Settings.create().sounds(BlockSoundGroup.BASALT),
                        true
                );
        }

        public static Block registerMoldBlock(String name) {
                return registerBlock(
                        name,
                        UnfiredMoldBlock::new,
                        Settings.copy(Blocks.CLAY).breakInstantly(),
                        true
                ); 
        }

        private static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FirstSteps.MOD_ID, name));
	}

	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FirstSteps.MOD_ID, name));
	}

        public static void registerModBlocks() {
                FirstSteps.LOGGER.info("Registering ModBlocks for " + FirstSteps.MOD_ID);
        }
}
