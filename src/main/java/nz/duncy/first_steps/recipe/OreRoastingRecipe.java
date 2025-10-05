package nz.duncy.first_steps.recipe;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.random.Random;

public class OreRoastingRecipe extends CampfireCookingRecipe {
	private final int min;
	private final int max;

	public OreRoastingRecipe(String string, CookingRecipeCategory cookingRecipeCategory, Ingredient ingredient, ItemStack itemStack, float f, int i, int min, int max) {
		super(string, cookingRecipeCategory, ingredient, itemStack, f, i);
		this.min = min;
		this.max = max;
	}

	@FunctionalInterface
	public interface RecipeFactory<OreRoastingRecipe> {
		OreRoastingRecipe create(String group, CookingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, int min, int max);
	}


}