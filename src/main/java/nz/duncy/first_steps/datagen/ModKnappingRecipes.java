package nz.duncy.first_steps.datagen;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.item.ModItems;

public class ModKnappingRecipes extends RecipeGenerator {

    public ModKnappingRecipes(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        super(wrapperLookup, recipeExporter);
    }

    private void generateToolHeadRecipe(RecipeExporter exporter, ItemConvertible ingredient, ItemConvertible result) {
        // new KnappingRecipeJsonBuilder(RecipeCategory.MISC, result, ingredient, KnappingRecipe::new)
        // .criterion("has_rock", this.conditionsFromTag(ItemTags.LOGS_THAT_BURN))
        //    .offerTo(exporter);

        
        // exporter.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.STONE_STAIRS, Blocks.STONE);
        offerStonecuttingRecipe(RecipeCategory.MISC, result, ingredient);
    }

    @Override
    public void generate() {
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_AXE);
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_SHOVEL);
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_HOE);
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_KNIFE);
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_SPEAR);
        generateToolHeadRecipe(exporter, ModBlocks.STONE_ROCK, ModItems.STONE_HEAD_ARROW);

        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_AXE);
        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_SHOVEL);
        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_HOE);
        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_KNIFE);
        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_SPEAR);
        generateToolHeadRecipe(exporter, Items.FLINT, ModItems.FLINT_HEAD_ARROW);

        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_AXE);
        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_SHOVEL);
        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_HOE);
        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_KNIFE);
        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_SPEAR);
        generateToolHeadRecipe(exporter, ModBlocks.FLINT_ROCK, ModItems.FLINT_HEAD_ARROW);

        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_AXE);
        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_SHOVEL);
        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_HOE);
        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_KNIFE);
        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_SPEAR);
        generateToolHeadRecipe(exporter, ModBlocks.BASALT_ROCK, ModItems.BASALT_HEAD_ARROW);

        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_AXE);
        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_SHOVEL);
        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_HOE);
        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_KNIFE);
        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_SPEAR);
        generateToolHeadRecipe(exporter, ModBlocks.OBSIDIAN_ROCK, ModItems.OBSIDIAN_HEAD_ARROW);
    }
    
}
