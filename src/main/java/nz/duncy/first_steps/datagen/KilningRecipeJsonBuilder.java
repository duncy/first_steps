package nz.duncy.first_steps.datagen;

import java.util.LinkedHashMap;
import java.util.Map;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.recipe.KilningRecipe;
import nz.duncy.first_steps.recipe.book.KilningRecipeCategory;

import org.jetbrains.annotations.Nullable;

public class KilningRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
	private final RecipeCategory category;
	private final KilningRecipeCategory kilningCategory;
	private final Item output;
	private final Ingredient input;
	private final int cookingTime;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap();
	@Nullable
	private String group;
	private final KilningRecipe.RecipeFactory<?> recipeFactory;

	public KilningRecipeJsonBuilder (
		RecipeCategory category,
		KilningRecipeCategory kilningCategory,
		ItemConvertible output,
		Ingredient input,
		int cookingTime,
		KilningRecipe.RecipeFactory<?> recipeFactory
	) {
		this.category = category;
		this.kilningCategory = kilningCategory;
		this.output = output.asItem();
		this.input = input;
		this.cookingTime = cookingTime;
		this.recipeFactory = recipeFactory;
	}
    
    public static KilningRecipeJsonBuilder create(Ingredient input, RecipeCategory category, ItemConvertible output, int cookingTime) {
        return new KilningRecipeJsonBuilder(category, getKilningCategory(output), output, input, cookingTime, KilningRecipe::new);
	}

	public KilningRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
		this.criteria.put(string, advancementCriterion);
		return this;
	}

	public KilningRecipeJsonBuilder group(@Nullable String string) {
		this.group = string;
		return this;
	}

	@Override
	public Item getOutputItem() {
		return this.output;
	}

    @Override
    public void offerTo(RecipeExporter exporter) {
        this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, getItemId(this.getOutputItem())));
    }

	@Override
	public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
		this.validate(recipeKey);
		Advancement.Builder builder = exporter.getAdvancementBuilder()
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		this.criteria.forEach(builder::criterion);
		KilningRecipe recipe = this.recipeFactory
			.create(this.kilningCategory, this.input, new ItemStack(this.output), this.cookingTime);
		exporter.accept(recipeKey, recipe, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/")));
	}

	private static KilningRecipeCategory getKilningCategory(ItemConvertible output) {
		return KilningRecipeCategory.FUEL_WASTE;
	}

    static Identifier getItemId(ItemConvertible item) {
		return Registries.ITEM.getId(item.asItem());
	}

	private void validate(RegistryKey<Recipe<?>> recipeKey) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipeKey.getValue());
		}
	}
}
