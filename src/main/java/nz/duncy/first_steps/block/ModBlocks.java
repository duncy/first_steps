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
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.OreRockBlock;
import nz.duncy.first_steps.block.custom.RockBlock;

public class ModBlocks {
    // ORES
    public static final Block STONE_TIN_ORE = registerStoneOreBlock("stone_tin_ore");
    public static final Block STONE_COPPER_ORE = registerStoneOreBlock("stone_copper_ore");
    public static final Block STONE_IRON_ORE = registerStoneOreBlock("stone_iron_ore");
    public static final Block DEEPSLATE_COPPER_ORE = registerDeepslateOreBlock("deepslate_copper_ore");
    public static final Block DEEPSLATE_IRON_ORE = registerDeepslateOreBlock("deepslate_iron_ore");
    public static final Block BASALT_MULLITE_ORE = registerBasaltOreBlock("basalt_mullite_ore");

    // ROCKS
    public static final Block STONE_ROCK = registerBlock("stone_rock",
        new RockBlock(FabricBlockSettings.create().strength(0.0F, 0.0F).pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.STONE_GRAY), new Identifier("minecraft:block/stone")));
    
    public static Block FLINT_ROCK = registerFlintRockBlock("flint_rock", 
       new RockBlock(FabricBlockSettings.copyOf(STONE_ROCK).mapColor(MapColor.BLACK), new Identifier(FirstSteps.MOD_ID, "block/flint_rock")));

    public static final Block BASALT_ROCK = registerBlock("basalt_rock",
        new RockBlock(FabricBlockSettings.copyOf(STONE_ROCK), new Identifier("minecraft:block/basalt_side")));

    public static final Block OBSIDIAN_ROCK = registerBlock("obsidian_rock",
        new RockBlock(FabricBlockSettings.copyOf(FLINT_ROCK), new Identifier("minecraft:block/obsidian")));

    public static final Block COPPER_ROCK = registerBlock("copper_rock",
        new OreRockBlock(FabricBlockSettings.copyOf(STONE_ROCK)));

    // CRAFTING
    // CRUCIBLES
    public static final Block CLAY_UNFIRED_CRUCIBLE = registerBlock("clay_unfired_crucible", new Block(FabricBlockSettings.copyOf(Blocks.CLAY)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
    }

    public static Block registerFlintRockBlock(String name, Block block) {
        RegistryEditEvent.EVENT.register(manipulator -> {
            manipulator.Redirect(Registries.ITEM, Items.FLINT, new BlockItem(block, new FabricItemSettings()));
        });
        return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(FirstSteps.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
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
