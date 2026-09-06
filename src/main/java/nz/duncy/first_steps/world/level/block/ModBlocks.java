package nz.duncy.first_steps.world.level.block;

import java.util.function.Function;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import nz.duncy.first_steps.FirstSteps;

public class ModBlocks {
    // Rocks
    public static final Block STONE_ROCK = register("stone_rock", (properties) -> {
        return new RockBlock(properties);
    }, Properties.of().mapColor(MapColor.STONE).instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY));

    public static final Block FLINT_ROCK = register("flint_rock", (properties) -> {
        return new RockBlock(properties);
    }, Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY));

    public static final Block BASALT_ROCK = register("basalt_rock", (properties) -> {
        return new RockBlock(properties);
    }, Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak().sound(SoundType.BASALT).pushReaction(PushReaction.DESTROY));

    public static final Block OBSIDIAN_ROCK = register("obsidian_rock", (properties) -> {
        return new RockBlock(properties);
    }, Properties.of().mapColor(MapColor.COLOR_BLACK).instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY));

    // Ores
    public static final Block STONE_TIN_ORE = register("stone_tin_ore", (properties) -> {
        return new Block(properties);
    }, Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block STONE_COPPER_ORE = register("stone_copper_ore", (properties) -> {
        return new Block(properties);
    }, Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block STONE_IRON_ORE = register("stone_iron_ore", (properties) -> {
        return new Block(properties);
    }, Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block DEEPSLATE_COPPER_ORE = register("deepslate_copper_ore", (properties) -> {
        return new Block(properties);
    }, Properties.of().sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5f));
    public static final Block DEEPSLATE_IRON_ORE = register("deepslate_iron_ore", (properties) -> {
        return new Block(properties);
    }, Properties.of().sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(3.5f));

    // Raw ore blocks
    public static final Block RAW_STONE_TIN = register("raw_stone_tin_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block RAW_STONE_COPPER = register("raw_stone_copper_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block RAW_STONE_IRON = register("raw_stone_iron_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block RAW_DEEPSLATE_COPPER = register("raw_deepslate_copper_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5f));
    public static final Block RAW_DEEPSLATE_IRON = register("raw_deepslate_iron_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5f));

    public static final Block RAW_TIN = register("raw_tin_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block RAW_BRONZE = register("raw_bronze_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));

    public static final Block TIN_BLOCK = register("tin_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));
    public static final Block BRONZE_BLOCK = register("bronze_block", (properties) -> {
        return new Block(properties);
    }, Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0f));

    // public static final Block COPPER_ROCK = registerBlock(
    //         "copper_rock",
    //         OreRockBlock::new,
    //         Settings.copy(Blocks.STONE),
    //         true
    // );

    // Fired pottery
    public static final Block DECORATED_JAR = register("decorated_jar", DecoratedJarBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block CRUCIBLE = register("crucible", CrucibleBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block INGOT_CAST = register("ingot_cast", IngotCastBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    // Unfired pottery
    public static final Block UNFIRED_DECORATED_POT = register("unfired_decorated_pot", UnfiredDecoratedPotBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block UNFIRED_DECORATED_JAR = register("unfired_decorated_jar", UnfiredDecoratedJarBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block UNFIRED_FLOWER_POT = register("unfired_flower_pot", UnfiredFlowerPotBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block UNFIRED_CRUCIBLE = register("unfired_crucible", UnfiredCrucibleBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    public static final Block UNFIRED_INGOT_CAST = register("unfired_ingot_cast", UnfiredIngotCastBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    // Crating stations
    public static final Block POTTERS_WHEEL = register("potters_wheel", PottersWheelBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).noOcclusion());
    public static final Block KILN = register("kiln", KilnBlock::new, Properties.of());

    // Unlit Torch
    public static final Block UNLIT_TORCH = register("unlit_torch", UnlitTorchBlock::new, Properties.of().noCollision().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final Block WALL_UNLIT_TORCH = register("wall_unlit_torch", WallUnlitTorchBlock::new, wallVariant(UNLIT_TORCH, true).noCollision().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));

    // Inset Molds
    // public static final Block UNFIRED_CASING_HOE = register("unfired_casing_hoe", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_SHOVEL = register("unfired_casing_shovel", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_AXE = register("unfired_casing_axe", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_KNIFE = register("unfired_casing_knife", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_SPEAR = register("unfired_casing_spear", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_PICKAXE = register("unfired_casing_pickaxe", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block UNFIRED_CASING_SWORD = register("unfired_casing_sword", UnfiredCasingBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    // public static final Block CASING_HOE = register("casing_hoe", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_SHOVEL = register("casing_shovel", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_AXE = register("casing_axe", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_KNIFE = register("casing_knife", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_SPEAR = register("casing_spear", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_PICKAXE = register("casing_pickaxe", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());
    // public static final Block CASING_SWORD = register("casing_sword", Block::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    public static final Block WOOD_PILE = register("wood_pile", WoodPileBlock::new,  Properties.of().mapColor(MapColor.WOOD).strength(1.0F).instrument(NoteBlockInstrument.BASS).pushReaction(PushReaction.DESTROY).noOcclusion().randomTicks().sound(SoundType.WOOD).ignitedByLava());


    private static BlockBehaviour.Properties wallVariant(Block block, boolean bl) {
        BlockBehaviour.Properties properties = Properties.of().overrideLootTable(block.getLootTable());
        if (bl) {
            properties = properties.overrideDescription(block.getDescriptionId());
        }

        return properties;
    }

    private static ResourceKey<Block> moddedBlockId(String string) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string));
    }

    private static Block register(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        return Blocks.register(moddedBlockId(string), function, properties);
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering mod blocks for " + FirstSteps.MOD_ID);
    }
}