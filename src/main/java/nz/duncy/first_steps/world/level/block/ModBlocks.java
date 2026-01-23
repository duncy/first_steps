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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import nz.duncy.first_steps.FirstSteps;

public class ModBlocks {
    // ROCKS
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

    public static final Block DECORATED_JAR = register("decorated_jar", DecoratedJarBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    public static final Block UNFIRED_DECORATED_POT = register("unfired_decorated_pot", UnfiredDecoratedPotBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    public static final Block UNFIRED_DECORATED_JAR = register("unfired_decorated_jar", UnfiredDecoratedJarBlock::new, Properties.of().mapColor(MapColor.CLAY).strength(0.0F, 0.0F).pushReaction(PushReaction.DESTROY).noOcclusion());

    public static final Block POTTERS_WHEEL = register("potters_wheel", PottersWheelBlock::new, Properties.of().mapColor(MapColor.TERRACOTTA_RED).noOcclusion());

    public static final Block UNLIT_TORCH = register("unlit_torch", UnlitTorchBlock::new, Properties.of().noCollision().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    
    public static final Block WALL_UNLIT_TORCH = register("wall_unlit_torch", WallUnlitTorchBlock::new, wallVariant(UNLIT_TORCH, true).noCollision().instabreak().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));

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

