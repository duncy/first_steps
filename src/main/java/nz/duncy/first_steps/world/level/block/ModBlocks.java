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

