package nz.duncy.first_steps.data.recipes;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return FirstSteps.MOD_ID + " Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
			@Override
            public void buildRecipes() {
                buildStonecutterToolHeadRecipes();
                buildToolRecipes();
                vanillaReplacementRecipes();
            }

            private void vanillaReplacementRecipes() {
                shaped(RecipeCategory.DECORATIONS, Blocks.CAMPFIRE)
                .pattern("LL")
                .pattern("TT")
                .define('L', ItemTags.LOGS)
                .define('T', ModItemTags.TINDER)
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .unlockedBy("has_tinder", has(ModItemTags.TINDER))
                .save(output);
            }

            private void buildToolRecipes() {
                buildToolRecipe(Items.STONE_AXE, ModItems.STONE_HEAD_AXE);
                buildToolRecipe(ModItems.STONE_KNIFE, ModItems.STONE_HEAD_KNIFE);
                buildToolRecipe(Items.STONE_SHOVEL, ModItems.STONE_HEAD_SHOVEL);
                buildToolRecipe(Items.STONE_HOE, ModItems.STONE_HEAD_HOE);
                buildToolRecipe(Items.STONE_SPEAR, ModItems.STONE_HEAD_SPEAR);

                buildToolRecipe(ModItems.FLINT_AXE, ModItems.FLINT_HEAD_AXE);
                buildToolRecipe(ModItems.FLINT_KNIFE, ModItems.FLINT_HEAD_KNIFE);
                buildToolRecipe(ModItems.FLINT_SHOVEL, ModItems.FLINT_HEAD_SHOVEL);
                buildToolRecipe(ModItems.FLINT_HOE, ModItems.FLINT_HEAD_HOE);
                buildToolRecipe(ModItems.FLINT_SPEAR, ModItems.FLINT_HEAD_SPEAR);

                buildToolRecipe(ModItems.BASALT_AXE, ModItems.BASALT_HEAD_AXE);
                buildToolRecipe(ModItems.BASALT_KNIFE, ModItems.BASALT_HEAD_KNIFE);
                buildToolRecipe(ModItems.BASALT_SHOVEL, ModItems.BASALT_HEAD_SHOVEL);
                buildToolRecipe(ModItems.BASALT_HOE, ModItems.BASALT_HEAD_HOE);
                buildToolRecipe(ModItems.BASALT_SPEAR, ModItems.BASALT_HEAD_SPEAR);

                buildToolRecipe(ModItems.OBSIDIAN_AXE, ModItems.OBSIDIAN_HEAD_AXE);
                buildToolRecipe(ModItems.OBSIDIAN_KNIFE, ModItems.OBSIDIAN_HEAD_KNIFE);
                buildToolRecipe(ModItems.OBSIDIAN_SHOVEL, ModItems.OBSIDIAN_HEAD_SHOVEL);
                buildToolRecipe(ModItems.OBSIDIAN_HOE, ModItems.OBSIDIAN_HEAD_HOE);
                buildToolRecipe(ModItems.OBSIDIAN_SPEAR, ModItems.OBSIDIAN_HEAD_SPEAR);

                shaped(RecipeCategory.TOOLS, ModItems.FIRESTARTER, 1)
                .pattern(" S")
                .pattern("ST")
                .define('T', ModItemTags.TINDER)
                .define('S', Items.STICK)
                .group(FirstSteps.MOD_ID + "_firestarter")
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .unlockedBy("has_tinder", has(ModItemTags.TINDER))
                .save(output);
            }

            public void buildToolRecipe(Item tool, Item toolHead) {
                shaped(RecipeCategory.TOOLS, tool, 1)
                .pattern("H")
                .pattern("S")
                .define('H', toolHead)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(toolHead), has(toolHead))
                .save(output);
            }

			public void buildStonecutterToolHeadRecipes() {
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_AXE, ModBlocks.STONE_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_SHOVEL, ModBlocks.STONE_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_HOE, ModBlocks.STONE_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_KNIFE, ModBlocks.STONE_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_SPEAR, ModBlocks.STONE_ROCK);
                // stonecutterResultFromBase(RecipeCategory.MISC, ModItems.STONE_HEAD_ARROW, ModBlocks.STONE_ROCK);

                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_AXE, Items.FLINT);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_SHOVEL, Items.FLINT);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_HOE, Items.FLINT);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_KNIFE, Items.FLINT);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_SPEAR, Items.FLINT);
                // stonecutterResultFromBase(RecipeCategory.MISC, ModItems.FLINT_HEAD_ARROW, Items.FLINT);

                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_AXE, ModBlocks.BASALT_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_SHOVEL, ModBlocks.BASALT_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_HOE, ModBlocks.BASALT_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_KNIFE, ModBlocks.BASALT_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_SPEAR, ModBlocks.BASALT_ROCK);
                // stonecutterResultFromBase(RecipeCategory.MISC, ModItems.BASALT_HEAD_ARROW, ModBlocks.BASALT_ROCK);

                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_AXE, ModBlocks.OBSIDIAN_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_SHOVEL, ModBlocks.OBSIDIAN_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_HOE, ModBlocks.OBSIDIAN_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_KNIFE, ModBlocks.OBSIDIAN_ROCK);
                stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_SPEAR, ModBlocks.OBSIDIAN_ROCK);
                // stonecutterResultFromBase(RecipeCategory.MISC, ModItems.OBSIDIAN_HEAD_ARROW, ModBlocks.OBSIDIAN_ROCK);
			}
		};
    }
    
}
