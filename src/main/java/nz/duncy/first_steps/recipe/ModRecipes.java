package nz.duncy.first_steps.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipes {

   public static final RecipeType<KilningRecipe> KILNING_TYPE = Registry.register(
      Registries.RECIPE_TYPE, 
      Identifier.of(FirstSteps.MOD_ID, "kilning"),
      new RecipeType<KilningRecipe>() {
         public String toString() {
            return "kilning";
         }
      }
   );

   public static final RecipeSerializer<KilningRecipe> KILNING_SERIALIZER = Registry.register(
      Registries.RECIPE_SERIALIZER, 
      Identifier.of(FirstSteps.MOD_ID, "kilning"), 
      new KilningRecipe.Serializer()
   );

   public static void registerRecipes() {
      FirstSteps.LOGGER.info("Registering ModRecipes for " + FirstSteps.MOD_ID);
      
   }

}
