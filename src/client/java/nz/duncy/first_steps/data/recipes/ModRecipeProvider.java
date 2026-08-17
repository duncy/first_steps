package nz.duncy.first_steps.data.recipes;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;
import nz.duncy.first_steps.world.item.crafting.CrucibleRecipe;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public @NonNull String getName() {
        return FirstSteps.MOD_ID + " Recipes";
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
			@Override
            public void buildRecipes() {
                buildStonecutterToolHeadRecipes();
                buildPottersWheelRecipes();
                buildAnvilRecipes();
                buildToolRecipes();
                vanillaReplacementRecipes();

                shaped(RecipeCategory.DECORATIONS, ModBlocks.UNLIT_TORCH)
                .pattern("T")
                .pattern("S")
                .define('S', Items.STICK)
                .define('T', ModItemTags.TINDER)
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_tinder", has(ModItemTags.TINDER))
                .save(output);

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_STONE_COPPER), RecipeCategory.MISC, Items.COPPER_NUGGET, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_STONE_COPPER), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.COPPER_NUGGET, ModItems.RAW_STONE_COPPER));

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_STONE_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_STONE_IRON), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.IRON_NUGGET, ModItems.RAW_STONE_IRON));

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_STONE_COPPER), RecipeCategory.MISC, Items.RAW_COPPER, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_STONE_COPPER), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.RAW_COPPER, ModItems.RAW_STONE_COPPER));

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_STONE_IRON), RecipeCategory.MISC, Items.RAW_IRON, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_STONE_IRON), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.RAW_IRON, ModItems.RAW_STONE_IRON));

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_DEEPSLATE_COPPER), RecipeCategory.MISC, Items.RAW_COPPER, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_DEEPSLATE_COPPER), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.RAW_COPPER, ModItems.RAW_DEEPSLATE_COPPER));

                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_DEEPSLATE_IRON), RecipeCategory.MISC, Items.RAW_IRON, 0.1F, 100).unlockedBy(getHasName(ModItems.RAW_DEEPSLATE_IRON), this.has(ModItems.RAW_STONE_COPPER)).save(this.output, getRoastingName(Items.RAW_IRON, ModItems.RAW_DEEPSLATE_IRON));

                buildBlockRecipes(ModItems.RAW_STONE_TIN, ModItems.RAW_STONE_TIN_BLOCK);
                buildBlockRecipes(ModItems.RAW_STONE_COPPER, ModItems.RAW_STONE_COPPER_BLOCK);
                buildBlockRecipes(ModItems.RAW_STONE_IRON, ModItems.RAW_STONE_IRON_BLOCK);
                
                crucibleResultFromBase(RecipeCategory.MISC, ModItems.RAW_BRONZE_BLOCK, ModItems.BRONZE_BLOCK);

                DurabilityLossShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WOOD_PILE_BlOCK, 4)
                .requires(tag(ItemTags.LOGS))
                .requires(tag(ItemTags.AXES))
                .unlockedBy("has_log", has(ItemTags.LOGS))
                .unlockedBy("has_axe", has(ItemTags.AXES))
                .save(output);
                // buildUnfiredCasingRecipes();
                // buildWaxPatternRecipes();
            }

            // private void buildWaxPatternRecipes() {
            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_HOE)
            //     .pattern("WW")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);
                
            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_SHOVEL)
            //     .pattern("W")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);

            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_AXE)
            //     .pattern("WW")
            //     .pattern("W ")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);

            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_KNIFE)
            //     .pattern(" W")
            //     .pattern("W ")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);

            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_SPEAR)
            //     .pattern("WW")
            //     .pattern(" W")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);

            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_PICKAXE)
            //     .pattern("WWW")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);

            //     shaped(RecipeCategory.MISC, ModItems.PATTERN_HEAD_SWORD)
            //     .pattern("W")
            //     .pattern("W")
            //     .pattern("W")
            //     .define('W', ModItemTags.WAX)
            //     .unlockedBy("has_wax", has(ModItemTags.WAX))
            //     .save(output);
            // }

            // private void buildUnfiredCasingRecipes() {
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_HOE_BLOCK, ModItems.PATTERN_HEAD_HOE);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_SHOVEL_BLOCK, ModItems.PATTERN_HEAD_SHOVEL);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_AXE_BLOCK, ModItems.PATTERN_HEAD_AXE);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_KNIFE_BLOCK, ModItems.PATTERN_HEAD_KNIFE);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_SPEAR_BLOCK, ModItems.PATTERN_HEAD_SPEAR);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_PICKAXE_BLOCK, ModItems.PATTERN_HEAD_PICKAXE);
            //     buildUnfiredCasingRecipe(ModItems.UNFIRED_CASING_SWORD_BLOCK, ModItems.PATTERN_HEAD_SWORD);
            // }

            private void vanillaReplacementRecipes() {
                shaped(RecipeCategory.DECORATIONS, Blocks.CAMPFIRE)
                .pattern("LL")
                .pattern("TT")
                .define('L', ItemTags.LOGS)
                .define('T', ModItemTags.TINDER)
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .unlockedBy("has_tinder", has(ModItemTags.TINDER))
                .save(output);

                shaped(RecipeCategory.DECORATIONS, Blocks.SOUL_CAMPFIRE)
                .pattern("LL")
                .pattern("SS")
                .define('L', ItemTags.LOGS)
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy("has_logs", has(ItemTags.LOGS))
                .unlockedBy("has_soul_fire_base_blocks", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
                .save(output);
            }

            private String getRoastingName(ItemLike result, ItemLike ingredient) {
                return getItemName(result) + "_from_roasting_" + getItemName(ingredient);
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

                buildToolRecipe(Items.COPPER_AXE, ModItems.COPPER_HEAD_AXE);
                buildToolRecipe(ModItems.COPPER_KNIFE, ModItems.COPPER_HEAD_KNIFE);
                buildToolRecipe(Items.COPPER_SHOVEL, ModItems.COPPER_HEAD_SHOVEL);
                buildToolRecipe(Items.COPPER_HOE, ModItems.COPPER_HEAD_HOE);
                buildToolRecipe(Items.COPPER_SPEAR, ModItems.COPPER_HEAD_SPEAR);
                buildToolRecipe(Items.COPPER_SWORD, ModItems.COPPER_HEAD_SWORD);
                buildToolRecipe(Items.COPPER_PICKAXE, ModItems.COPPER_HEAD_PICKAXE);

                buildToolRecipe(ModItems.BRONZE_AXE, ModItems.BRONZE_HEAD_AXE);
                buildToolRecipe(ModItems.BRONZE_KNIFE, ModItems.BRONZE_HEAD_KNIFE);
                buildToolRecipe(ModItems.BRONZE_SHOVEL, ModItems.BRONZE_HEAD_SHOVEL);
                buildToolRecipe(ModItems.BRONZE_HOE, ModItems.BRONZE_HEAD_HOE);
                buildToolRecipe(ModItems.BRONZE_SPEAR, ModItems.BRONZE_HEAD_SPEAR);
                buildToolRecipe(ModItems.BRONZE_SWORD, ModItems.BRONZE_HEAD_SWORD);
                buildToolRecipe(ModItems.BRONZE_PICKAXE, ModItems.BRONZE_HEAD_PICKAXE);

                buildToolRecipe(Items.IRON_AXE, ModItems.IRON_HEAD_AXE);
                buildToolRecipe(ModItems.IRON_KNIFE, ModItems.IRON_HEAD_KNIFE);
                buildToolRecipe(Items.IRON_SHOVEL, ModItems.IRON_HEAD_SHOVEL);
                buildToolRecipe(Items.IRON_HOE, ModItems.IRON_HEAD_HOE);
                buildToolRecipe(Items.IRON_SPEAR, ModItems.IRON_HEAD_SPEAR);
                buildToolRecipe(Items.IRON_SWORD, ModItems.IRON_HEAD_SWORD);
                buildToolRecipe(Items.IRON_PICKAXE, ModItems.IRON_HEAD_PICKAXE);

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

            // public void buildUnfiredCasingRecipe(Item casing, Item pattern) {
            //     shaped(RecipeCategory.MISC, casing, 1)
            //     .pattern("CCC")
            //     .pattern("CPC")
            //     .pattern("CCC")
            //     .define('P', pattern)
            //     .define('C', Items.CLAY_BALL)
            //     .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
            //     .unlockedBy(getHasName(pattern), has(pattern))
            //     .save(output);
            // }

            public void buildBlockRecipes(Item item, Item block) {
                this.nineBlockStorageRecipesRecipesWithCustomUnpacking(RecipeCategory.MISC, item, RecipeCategory.BUILDING_BLOCKS, block, getItemName(item) + "_from_" + getItemName(block), getItemName(item));
                // this.nineBlockStorageRecipesWithCustomPacking(RecipeCategory.MISC, nugget, RecipeCategory.MISC, item, getItemName(item) + "_from_" + getItemName(nugget), getItemName(item));
            }
            
            public void pottersWheelResultFromBase(RecipeCategory recipeCategory, ItemLike result, ItemLike ingredient) {
                SingleItemRecipeBuilder singleItemRecipeBuilder = new SingleItemRecipeBuilder(recipeCategory, PottersWheelRecipe::new, Ingredient.of(ingredient), result, 1)
                    .unlockedBy(getHasName(ingredient), has(ingredient));
                String conversionRecipeName = getConversionRecipeName(result, ingredient);
                singleItemRecipeBuilder.save(this.output, conversionRecipeName + "_potters_wheel");
            }

            public void anvilResultFromBase(RecipeCategory recipeCategory, ItemLike result, ItemLike ingredient) {
                SingleItemRecipeBuilder singleItemRecipeBuilder = new SingleItemRecipeBuilder(recipeCategory, AnvilRecipe::new, Ingredient.of(ingredient), result, 1)
                    .unlockedBy(getHasName(ingredient), has(ingredient));
                String conversionRecipeName = getConversionRecipeName(result, ingredient);
                singleItemRecipeBuilder.save(this.output, conversionRecipeName + "_anvil");
            }

            public void crucibleResultFromBase(RecipeCategory recipeCategory, ItemLike result, ItemLike ingredient) {
                SingleItemRecipeBuilder singleItemRecipeBuilder = new SingleItemRecipeBuilder(recipeCategory, CrucibleRecipe::new, Ingredient.of(ingredient), result, 1)
                    .unlockedBy(getHasName(ingredient), has(ingredient));
                String conversionRecipeName = getConversionRecipeName(result, ingredient);
                singleItemRecipeBuilder.save(this.output, conversionRecipeName + "_crucible");
            }

            public final <T extends AbstractCookingRecipe> void potteryBakingRecipe(int cookingTime, ItemLike input, ItemLike result, float experience) {
                SimpleCookingRecipeBuilder builder = SimpleCookingRecipeBuilder.generic(
                    Ingredient.of(input), 
                    RecipeCategory.MISC, 
                    result, 
                    experience, 
                    cookingTime, 
                    RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 
                    CampfireCookingRecipe::new
                ).unlockedBy(
                    getHasName(input), 
                    this.has(input)
                );
                builder.save(this.output, getItemName(result) + "_from_baking");
            }

            public void buildAnvilRecipes() {
                anvilResultFromBase(RecipeCategory.DECORATIONS, ModItems.IRON_HEAD_HOE, Items.IRON_INGOT);
            }

            public void buildPottersWheelRecipes() {
                pottersWheelResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.UNFIRED_DECORATED_JAR, Blocks.CLAY);
                pottersWheelResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.UNFIRED_DECORATED_POT, Blocks.CLAY);
                pottersWheelResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.UNFIRED_CRUCIBLE, Blocks.CLAY);
                pottersWheelResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.UNFIRED_FLOWER_POT, Blocks.CLAY);
                pottersWheelResultFromBase(RecipeCategory.DECORATIONS, ModBlocks.UNFIRED_INGOT_CAST, Blocks.CLAY);

                shaped(RecipeCategory.DECORATIONS, ModBlocks.POTTERS_WHEEL, 1)
                .pattern(" H ")
                .pattern("SSS")
                .pattern("SBS")
                .define('H', ModItems.POTTERS_WHEEL_HEAD)
                .define('S', Items.STICK)
                .define('B', Items.BRICK)
                .group(FirstSteps.MOD_ID + "_potters_wheel")
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .unlockedBy(getHasName(Items.BRICK), has(Items.BRICK))
                .unlockedBy(getHasName(ModItems.POTTERS_WHEEL_HEAD), has(ModItems.POTTERS_WHEEL_HEAD))
                .save(output);
                
                shaped(RecipeCategory.DECORATIONS, ModItems.UNFIRED_POTTERS_WHEEL_HEAD, 1)
                .pattern("CCC")
                .define('C', Items.CLAY_BALL)
                .group(FirstSteps.MOD_ID + "_unfired_potters_wheel_head")
                .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
                .save(output);

                shaped(RecipeCategory.DECORATIONS, ModItems.UNFIRED_BRICK, 1)
                .pattern("C")
                .define('C', Items.CLAY_BALL)
                .group(FirstSteps.MOD_ID + "_unfired_bricks")
                .unlockedBy(getHasName(Items.CLAY_BALL), has(Items.CLAY_BALL))
                .save(output);

                potteryBakingRecipe(600, ModItems.UNFIRED_BRICK, Items.BRICK, 0.35F);
                potteryBakingRecipe(600, ModItems.UNFIRED_POTTERS_WHEEL_HEAD, ModItems.POTTERS_WHEEL_HEAD, 0.35F);
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
