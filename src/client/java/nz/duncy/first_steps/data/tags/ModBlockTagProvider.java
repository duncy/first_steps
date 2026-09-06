package nz.duncy.first_steps.data.tags;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import nz.duncy.first_steps.tags.ModBlockTags;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModBlockTagProvider extends BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(@NonNull Provider wrapperLookup) {
        valueLookupBuilder(BlockTags.INCORRECT_FOR_COPPER_TOOL)
            .add(Blocks.DEEPSLATE)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_IRON_ORE);
    
        valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_IRON_ORE);


        valueLookupBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_IRON_ORE);


        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.STONE_TIN_ORE)
            .add(ModBlocks.STONE_COPPER_ORE)
            .add(ModBlocks.STONE_IRON_ORE)
            .add(ModBlocks.DEEPSLATE_COPPER_ORE)
            .add(ModBlocks.DEEPSLATE_IRON_ORE)
            .add(ModBlocks.RAW_STONE_TIN)
            .add(ModBlocks.RAW_STONE_COPPER)
            .add(ModBlocks.RAW_STONE_IRON)
            .add(ModBlocks.RAW_DEEPSLATE_COPPER)
            .add(ModBlocks.RAW_DEEPSLATE_IRON);

        valueLookupBuilder(ModBlockTags.WOOD_PILES)
            .add(ModBlocks.WOOD_PILE);

        valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
            .addTag(ModBlockTags.WOOD_PILES);
    }
    
}
