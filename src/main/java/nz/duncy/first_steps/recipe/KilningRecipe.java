package nz.duncy.first_steps.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class KilningRecipe implements Recipe<SingleStackRecipeInput> {
   protected final ItemStack result;
   protected final Ingredient ingredient;

   public KilningRecipe(Ingredient ingredient, ItemStack result) {
      this.ingredient = ingredient;
      this.result = result;
   }

   @Override
   public ItemStack craft(SingleStackRecipeInput recipeInput, RegistryWrapper.WrapperLookup registriesLookup) {
      return this.result.copy();
   }

   @Override
   public boolean fits(int width, int height) {
      return true;
   }

   @Override
   public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
      return this.result;
   }

   public DefaultedList<Ingredient> getIngredients() {
      DefaultedList<Ingredient> list = DefaultedList.ofSize(1);
      list.add(this.ingredient);
      return list;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return ModRecipes.KILNING_SERIALIZER;
   }

   @Override
   public RecipeType<?> getType() {
      return ModRecipes.KILNING_TYPE;
   }

   @Override
   public boolean matches(SingleStackRecipeInput singleStackRecipeInput, World world) {
      if (world.isClient) return false;
      return this.ingredient.test(singleStackRecipeInput.item());
   }

   public interface RecipeFactory<T extends KilningRecipe> {
      T create(Ingredient ingredient, ItemStack result);
   }
}