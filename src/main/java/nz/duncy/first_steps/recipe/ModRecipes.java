package nz.duncy.first_steps.recipe;

import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipes {
    public static RecipeType<KilnRecipe> KILN_TYPE;
    public static RecipeSerializer<KilnRecipe> KILN_SERIALIZER;


    public static void registerRecipes() {
        KILN_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(FirstSteps.MOD_ID, "kiln"), new CookingRecipeSerializer<KilnRecipe>(KilnRecipe::new, 200));
        KILN_TYPE = Registry.register(Registries.RECIPE_TYPE, new Identifier(FirstSteps.MOD_ID, KilnRecipe.Type.ID), KilnRecipe.Type.INSTANCE);
    }
}