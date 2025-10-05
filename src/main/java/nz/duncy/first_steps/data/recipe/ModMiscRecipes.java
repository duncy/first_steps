package nz.duncy.first_steps.data.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.item.custom.ModItems;
import nz.duncy.first_steps.recipe.OreRoastingRecipe;
import nz.duncy.first_steps.util.ModTags;

public class ModMiscRecipes extends RecipeGenerator {
    public ModMiscRecipes(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        super(wrapperLookup, recipeExporter);
    }

    private void createVanillaBlockReplacementRecipes(RecipeExporter exporter) {
       this.createShaped(RecipeCategory.DECORATIONS, Blocks.CAMPFIRE)
			.input('L', ItemTags.LOGS)
			.input('S', Items.STICK)
			.input('T', ModTags.Items.TINDER)
			.pattern(" S ")
			.pattern("STS")
			.pattern("LLL")
			.criterion("has_stick", this.conditionsFromItem(Items.STICK))
			.criterion("has_grass", this.conditionsFromTag(ModTags.Items.TINDER))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.DECORATIONS, Blocks.WHITE_BED)
			.input('W', ItemTags.WOOL)
			.input('C', ModItems.CLOTH)
			.input('P', ItemTags.PLANKS)
			.pattern("WCC")
			.pattern("PPP")
			.criterion("has_wool", this.conditionsFromTag(ItemTags.WOOL))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.criterion("has_planks", this.conditionsFromTag(ItemTags.PLANKS))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.DECORATIONS, Blocks.WHITE_BANNER)
			.input('S', Items.STICK)
			.input('C', ModItems.CLOTH)
			.pattern(" C ")
			.pattern(" C ")
			.pattern(" S ")
			.criterion("has_stick", this.conditionsFromItem(Items.STICK))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.BLACK_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.BLACK_DYE)
			.group("banner")
			.criterion("has_black_dye", this.conditionsFromItem(Items.BLACK_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.BLUE_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.BLUE_DYE)
			.group("banner")
			.criterion("has_blue_dye", this.conditionsFromItem(Items.BLUE_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.BROWN_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.BROWN_DYE)
			.group("banner")
			.criterion("has_brown_dye", this.conditionsFromItem(Items.BROWN_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.CYAN_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.CYAN_DYE)
			.group("banner")
			.criterion("has_cyan_dye", this.conditionsFromItem(Items.CYAN_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.GREEN_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.GREEN_DYE)
			.group("banner")
			.criterion("has_green_dye", this.conditionsFromItem(Items.GREEN_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.LIGHT_BLUE_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.LIGHT_BLUE_DYE)
			.group("banner")
			.criterion("has_light_blue_dye", this.conditionsFromItem(Items.LIGHT_BLUE_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.LIGHT_GRAY_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.LIGHT_GRAY_DYE)
			.group("banner")
			.criterion("has_light_gray_dye", this.conditionsFromItem(Items.LIGHT_GRAY_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.LIME_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.LIME_DYE)
			.group("banner")
			.criterion("has_lime_dye", this.conditionsFromItem(Items.LIME_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.MAGENTA_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.MAGENTA_DYE)
			.group("banner")
			.criterion("has_magenta_dye", this.conditionsFromItem(Items.MAGENTA_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.ORANGE_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.ORANGE_DYE)
			.group("banner")
			.criterion("has_orange_dye", this.conditionsFromItem(Items.ORANGE_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.PINK_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.PINK_DYE)
			.group("banner")
			.criterion("has_pink_dye", this.conditionsFromItem(Items.PINK_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.RED_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.RED_DYE)
			.group("banner")
			.criterion("has_red_dye", this.conditionsFromItem(Items.RED_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, Blocks.YELLOW_BANNER, 1)
			.input(Blocks.WHITE_BANNER)
			.input(Items.YELLOW_DYE)
			.group("banner")
			.criterion("has_yellow_dye", this.conditionsFromItem(Items.YELLOW_DYE))
			.criterion("has_banner", this.conditionsFromItem(Blocks.WHITE_BANNER))
			.offerTo(exporter);
    }

	private void createCraftingStationRecipes(RecipeExporter exporter) {
       this.createShaped(RecipeCategory.DECORATIONS, ModBlocks.POTTERS_WHEEL)
			.input('B', Items.BRICK)
			.input('S', Items.STICK)
			.pattern("BBB")
			.pattern("S S")
			.pattern("SBS")
			.criterion("has_stick", this.conditionsFromItem(Items.STICK))
			.criterion("has_brick", this.conditionsFromItem(Items.BRICK))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.DECORATIONS, ModItems.ARMORERS_MANNEQUIN)
			.input('P', ModItems.PADDED_CLOTH)
			.input('A', Items.ARMOR_STAND)
			.pattern("PPP")
			.pattern("PAP")
			.pattern("P P")
			.criterion("has_padded_cloth", this.conditionsFromItem(ModItems.PADDED_CLOTH))
			.criterion("has_armor_stand", this.conditionsFromItem(Items.ARMOR_STAND))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.DECORATIONS, ModBlocks.KILN)
			.input('B', Blocks.BRICKS)
			.pattern("BBB")
			.pattern("B B")
			.pattern("B B")
			.criterion("has_bricks", this.conditionsFromItem(Blocks.BRICKS))
			.offerTo(exporter);
    }

	private void createIngredientRecipes(RecipeExporter exporter) {
		this.createShaped(RecipeCategory.MISC, ModItems.CLOTH)
			.input('C', ModItems.COTTON)
			.pattern("CC")
			.pattern("CC")
			.criterion("has_cotton", this.conditionsFromItem(ModItems.COTTON))
			.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(FirstSteps.MOD_ID, "cloth_from_cotton")));

		this.createShaped(RecipeCategory.MISC, ModItems.CLOTH)
			.input('F', ModItems.FLAX)
			.pattern("FF")
			.pattern("FF")
			.criterion("has_flax", this.conditionsFromItem(ModItems.FLAX))
			.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(FirstSteps.MOD_ID, "cloth_from_flax")));

		this.createShaped(RecipeCategory.MISC, ModItems.PADDED_CLOTH)
			.input('C', ModItems.CLOTH)
			.input('W', ItemTags.WOOL)
			.pattern("CCC")
			.pattern("CWC")
			.pattern("CCC")
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.criterion("has_wool", this.conditionsFromTag(ItemTags.WOOL))
			.offerTo(exporter);

		this.createShapeless(RecipeCategory.MISC, ModItems.LEATHER_STRIP, 1)
			.input(ModItems.STONE_KNIFE)
			.input(Items.LEATHER)
			.group("leather_strip")
			.criterion("has_knife", this.conditionsFromItem(ModItems.STONE_KNIFE))
			.criterion("has_leather", this.conditionsFromItem(Items.LEATHER))
			.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(FirstSteps.MOD_ID, "leather_strip_from_knife")));

		generateOreRoastingRecipes("ore_roasting", RecipeSerializer.CAMPFIRE_COOKING, OreRoastingRecipe::new, 1200);

	}

	private void generateOreRoastingRecipes(String cooker, RecipeSerializer<CampfireCookingRecipe> serializer, OreRoastingRecipe.RecipeFactory<OreRoastingRecipe> recipeFactory, int cookingTime) {
		offerOreRoastingRecipe(cooker, serializer, recipeFactory, cookingTime, ModItems.RAW_STONE_COPPER, ModItems.COPPER_NUGGET, 0.35F, 1, 4);
		offerOreRoastingRecipe(cooker, serializer, recipeFactory, cookingTime, ModItems.RAW_STONE_IRON, Items.IRON_NUGGET, 0.35F, 1, 4);
		offerOreRoastingRecipe(cooker, serializer, recipeFactory, cookingTime, ModItems.RAW_DEEPSLATE_COPPER, Items.RAW_COPPER, 0.35F, 1, 1);
		offerOreRoastingRecipe(cooker, serializer, recipeFactory, cookingTime, ModItems.RAW_DEEPSLATE_IRON, Items.RAW_IRON, 0.35F, 1, 1);
		offerOreRoastingRecipe(cooker, serializer, recipeFactory, cookingTime, ModItems.RAW_TIN, ModItems.TIN_NUGGET, 0.35F, 2, 6);
	}
	
	private final void offerOreRoastingRecipe(String cooker, RecipeSerializer<CampfireCookingRecipe> serializer, OreRoastingRecipe.RecipeFactory<OreRoastingRecipe> recipeFactory, int cookingTime, ItemConvertible input, ItemConvertible output, float experience, int min, int max) {
		OreRoastingRecipeJsonBuilder.create(Ingredient.ofItem(input), RecipeCategory.MISC, output, experience, cookingTime, serializer, recipeFactory, min, max)
			.criterion(hasItem(input), this.conditionsFromItem(input))
			.offerTo(this.exporter, getItemPath(output) + "_from_" + cooker);
	}

	private void createArmorRecipes(RecipeExporter exporter) {
       this.createShaped(RecipeCategory.COMBAT, ModItems.ARMING_CAP)
			.input('P', ModItems.PADDED_CLOTH)
			.input('C', ModItems.CLOTH)
			.pattern("PPP")
			.pattern("C C")
			.criterion("has_padded_cloth", this.conditionsFromItem(ModItems.PADDED_CLOTH))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.COMBAT, ModItems.GAMBESON)
			.input('P', ModItems.PADDED_CLOTH)
			.input('S', ModItems.LEATHER_STRIP)
			.pattern("P P")
			.pattern("PSP")
			.pattern("PSP")
			.criterion("has_padded_cloth", this.conditionsFromItem(ModItems.PADDED_CLOTH))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.criterion("has_leather_strip", this.conditionsFromItem(ModItems.LEATHER_STRIP))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.COMBAT, ModItems.CLOTH_CHAUSSES)
			.input('P', ModItems.PADDED_CLOTH)
			.input('C', ModItems.CLOTH)
			.pattern("PCP")
			.pattern("P P")
			.pattern("P P")
			.criterion("has_padded_cloth", this.conditionsFromItem(ModItems.PADDED_CLOTH))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.offerTo(exporter);

		this.createShaped(RecipeCategory.COMBAT, ModItems.TURNSHOES)
			.input('L', Items.LEATHER)
			.input('C', ModItems.CLOTH)
			.pattern("C C")
			.pattern("L L")
			.criterion("has_padded_cloth", this.conditionsFromItem(Items.LEATHER))
			.criterion("has_cloth", this.conditionsFromItem(ModItems.CLOTH))
			.offerTo(exporter);
    }

    @Override
    public void generate() {
        createVanillaBlockReplacementRecipes(exporter);
		createCraftingStationRecipes(exporter);
		createIngredientRecipes(exporter);
		createArmorRecipes(exporter);
    }
}