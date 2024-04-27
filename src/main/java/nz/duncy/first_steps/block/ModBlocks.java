package nz.duncy.first_steps.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.RockBlock;

public class ModBlocks {
    // ORES
    public static final Block STONE_TIN_ORE = registerStoneOreBlock("stone_tin_ore");
    public static final Block STONE_ALUNITE_ORE = registerStoneOreBlock("stone_alunite_ore");
    public static final Block STONE_COPPER_ORE = registerStoneOreBlock("stone_copper_ore");
    public static final Block STONE_IRON_ORE = registerStoneOreBlock("stone_iron_ore");
    public static final Block DEEPSLATE_COPPER_ORE = registerDeepslateOreBlock("deepslate_copper_ore");
    public static final Block DEEPSLATE_IRON_ORE = registerDeepslateOreBlock("deepslate_iron_ore");

    // ROCKS
    public static final Block STONE_ROCK = registerBlock("stone_rock",
            new RockBlock(FabricBlockSettings.copyOf(Blocks.STONE), new Identifier("minecraft:block/stone")));
    
    public static final Block BLACKSTONE_ROCK = registerBlock("blackstone_rock",
            new RockBlock(FabricBlockSettings.copyOf(Blocks.STONE), new Identifier("minecraft:block/blackstone")));

    public static final Block OBSIDIAN_ROCK = registerBlock("obsidian_rock",
            new RockBlock(FabricBlockSettings.copyOf(Blocks.STONE), new Identifier("minecraft:block/obsidian")));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(FirstSteps.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
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

    public static void registerModBlocks() {
        FirstSteps.LOGGER.info("Registering ModBlocks for " + FirstSteps.MOD_ID);
    }
}
