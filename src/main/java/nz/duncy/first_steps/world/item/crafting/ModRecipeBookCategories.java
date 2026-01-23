package nz.duncy.first_steps.world.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipeBookCategories {
    public static final RecipeBookCategory POTTERS_WHEEL = register("potters_wheel");

    private static RecipeBookCategory register(String string) {
           return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), new RecipeBookCategory());
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering recipe book for " + FirstSteps.MOD_ID);
    }
}