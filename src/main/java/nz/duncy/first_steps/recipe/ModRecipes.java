package nz.duncy.first_steps.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipes {

   public static void registerRecipes() {
      FirstSteps.LOGGER.info("Registering ModRecipes for " + FirstSteps.MOD_ID);
      Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(FirstSteps.MOD_ID, KilningRecipe.Serializer.ID), KilningRecipe.Serializer.INSTANCE);
      Registry.register(Registries.RECIPE_TYPE, new Identifier(FirstSteps.MOD_ID, KilningRecipe.Type.ID), KilningRecipe.Type.INSTANCE);
   }

}
