package nz.duncy.first_steps.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.util.ModTags;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_STONE_ROCK)
            .add(ModBlocks.STONE_ROCK)
            .add(ModBlocks.BASALT_ROCK);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_OBSIDIAN_ROCK)
            .add(ModBlocks.OBSIDIAN_ROCK);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(ModBlocks.STONE_TIN_ORE)
            .add(ModBlocks.STONE_COPPER_ORE)
            .add(ModBlocks.STONE_IRON_ORE)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_IRON_ORE)
            .add(ModBlocks.BASALT_MULLITE_ORE)
            .add(ModBlocks.KILN);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_COPPER_TOOL)
            .add(ModBlocks.STONE_TIN_ORE)
            .add(ModBlocks.STONE_IRON_ORE)
            .add(ModBlocks.STONE_COPPER_ORE);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_BRONZE_TOOL)
            .add(ModBlocks.DEEPSLATE_IRON_ORE)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.BASALT_MULLITE_ORE)
            .add(Blocks.OBSIDIAN);
    }
}
