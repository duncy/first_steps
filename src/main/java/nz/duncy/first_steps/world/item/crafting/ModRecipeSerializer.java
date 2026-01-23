package nz.duncy.first_steps.world.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nz.duncy.first_steps.FirstSteps;

public interface ModRecipeSerializer {
    RecipeSerializer<PottersWheelRecipe> POTTERS_WHEEL = register("potters_wheel", new PottersWheelRecipe.Serializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String string, S recipeSerializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), recipeSerializer);
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering recipe serializers for " + FirstSteps.MOD_ID);
    }
}