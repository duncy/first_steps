package nz.duncy.first_steps.data.recipes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import nz.duncy.first_steps.world.item.crafting.DurabilityLossShapelessRecipe;

import org.jspecify.annotations.Nullable;

public class DurabilityLossShapelessRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final ItemStack result;
	private final List<Ingredient> ingredients = new ArrayList<>();
	private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
	@Nullable
	private String group;

	private DurabilityLossShapelessRecipeBuilder(RecipeCategory recipeCategory, ItemStack result) {
		this.category = recipeCategory;
		this.result = result;
	}

	public static DurabilityLossShapelessRecipeBuilder shapeless(RecipeCategory recipeCategory, ItemStack itemStack) {
		return new DurabilityLossShapelessRecipeBuilder(recipeCategory, itemStack);
	}

	public static DurabilityLossShapelessRecipeBuilder shapeless(RecipeCategory recipeCategory, ItemLike itemLike) {
		return shapeless(recipeCategory, itemLike, 1);
	}

	public static DurabilityLossShapelessRecipeBuilder shapeless(RecipeCategory recipeCategory, ItemLike itemLike, int i) {
		return new DurabilityLossShapelessRecipeBuilder(recipeCategory, itemLike.asItem().getDefaultInstance().copyWithCount(i));
	}

	public DurabilityLossShapelessRecipeBuilder requires(Ingredient ingredient) {
        this.ingredients.add(ingredient);
		return this;
    }

	public DurabilityLossShapelessRecipeBuilder requires(ItemLike itemLike) {
		return this.requires(itemLike);
	}

	public DurabilityLossShapelessRecipeBuilder unlockedBy(String string, Criterion<?> criterion) {
		this.criteria.put(string, criterion);
		return this;
	}

	public DurabilityLossShapelessRecipeBuilder group(@Nullable String string) {
		this.group = string;
		return this;
	}

	@Override
	public Item getResult() {
		return this.result.getItem();
	}

	@Override
	public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
		this.ensureValid(resourceKey);
		Advancement.Builder builder = recipeOutput.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceKey))
			.rewards(AdvancementRewards.Builder.recipe(resourceKey))
			.requirements(AdvancementRequirements.Strategy.OR);
		this.criteria.forEach(builder::addCriterion);
		DurabilityLossShapelessRecipe shapelessRecipe = new DurabilityLossShapelessRecipe(
			(String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), this.result, this.ingredients
		);
		recipeOutput.accept(resourceKey, shapelessRecipe, builder.build(resourceKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
	}

	private void ensureValid(ResourceKey<Recipe<?>> resourceKey) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + resourceKey.identifier());
		}
	}
}
