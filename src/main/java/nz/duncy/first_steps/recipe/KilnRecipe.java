package nz.duncy.first_steps.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CookingRecipeCategory;
import nz.duncy.first_steps.block.ModBlocks;

public class KilnRecipe extends AbstractCookingRecipe {
    public KilnRecipe(String group, CookingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(ModRecipes.KILN_TYPE, group, category, ingredient, result, experience, cookingTime);
    }

    public ItemStack createIcon() {
    return new ItemStack(ModBlocks.KILN);
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.KILN_SERIALIZER;
    }
    
    public static class Type implements RecipeType<KilnRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "kiln";
    }
}