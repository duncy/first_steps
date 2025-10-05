package nz.duncy.first_steps.data.recipe;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import nz.duncy.first_steps.recipe.OreRoastingRecipe;

import org.jetbrains.annotations.Nullable;

public class OreRoastingRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
	private final RecipeCategory category;
	private final CookingRecipeCategory cookingCategory;
	private final Item output;
	private final Ingredient input;
	private final float experience;
	private final int cookingTime;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap();
	@Nullable
	private String group;
	private final OreRoastingRecipe.RecipeFactory<OreRoastingRecipe> recipeFactory;
	private final int min;
	private final int max;

	private OreRoastingRecipeJsonBuilder(RecipeCategory category, CookingRecipeCategory cookingCategory, ItemConvertible output, Ingredient input, float experience, int cookingTime, OreRoastingRecipe.RecipeFactory<OreRoastingRecipe> recipeFactory, int min, int max) {
		this.category = category;
		this.cookingCategory = cookingCategory;
		this.output = output.asItem();
		this.input = input;
		this.experience = experience;
		this.cookingTime = cookingTime;
		this.recipeFactory = recipeFactory;
		this.min = min;
		this.max = max;
	}

	public static OreRoastingRecipeJsonBuilder create(Ingredient input, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, RecipeSerializer<CampfireCookingRecipe> serializer, OreRoastingRecipe.RecipeFactory<OreRoastingRecipe> recipeFactory, int min, int max) {
		return new OreRoastingRecipeJsonBuilder(category, getCookingRecipeCategory(serializer, output), output, input, experience, cookingTime, recipeFactory, min, max);
	}

	// public static OreRoastingRecipeJsonBuilder createOreRoasting (Ingredient input, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, int min, int max
	// ) {
	// 	return new OreRoastingRecipeJsonBuilder(category, CookingRecipeCategory.MISC, output, input, experience, cookingTime, OreRoastingRecipe::new, min, max);
	// }
	public OreRoastingRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
		this.criteria.put(string, advancementCriterion);
		return this;
	}

	public OreRoastingRecipeJsonBuilder group(@Nullable String string) {
		this.group = string;
		return this;
	}

	@Override
	public Item getOutputItem() {
		return this.output;
	}

	@Override
	public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
		this.validate(recipeKey);
		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		this.criteria.forEach(builder::criterion);
		OreRoastingRecipe OreRoastingRecipe = this.recipeFactory
			.create((String)Objects.requireNonNullElse(this.group, ""), this.cookingCategory, this.input, new ItemStack(this.output), this.experience, this.cookingTime, this.min, this.max);
		exporter.accept(recipeKey, OreRoastingRecipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/")));
	}
	
	private static CookingRecipeCategory getCookingRecipeCategory(RecipeSerializer<CampfireCookingRecipe> serializer, ItemConvertible output) {
		return CookingRecipeCategory.MISC;
	}

	private void validate(RegistryKey<Recipe<?>> recipeKey) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
		}
	}
}
