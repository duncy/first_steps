package nz.duncy.first_steps.world.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay.Type;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.crafting.display.CrucibleRecipeDisplay;
import nz.duncy.first_steps.world.item.crafting.display.KilnRecipeDisplay;
import nz.duncy.first_steps.world.item.crafting.display.PottersWheelRecipeDisplay;

public interface ModRecipeType {
    RecipeType<PottersWheelRecipe> POTTERS_WHEEL_RECIPE = register("potters_wheel");
    Type<?> POTTERS_WHEEL_RECIPE_DISPLAY = register(PottersWheelRecipeDisplay.TYPE, "potters_wheel");

    RecipeType<CrucibleRecipe> CRUCIBLE_RECIPE = register("crucible");
    Type<?> CRUCIBLE_RECIPE_DISPLAY = register(CrucibleRecipeDisplay.TYPE, "crucible");

    RecipeType<KilnRecipe> KILN_RECIPE = register("kiln");
    Type<?> KILN_RECIPE_DISPLAY = register(KilnRecipeDisplay.TYPE, "klin");

    static <T extends Recipe<?>> RecipeType<T> register(String string) {
        return Registry.register(
            BuiltInRegistries.RECIPE_TYPE, 
            Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), 
            new RecipeType<T>() {
                @Override
                public String toString() {
                    return string;
                }
            }
        );
    }

    static RecipeDisplay.Type<?> register(RecipeDisplay.Type<?> type, String string) {
        return Registry.register(
            BuiltInRegistries.RECIPE_DISPLAY, 
            Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), 
            type
        );
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering recipe types for " + FirstSteps.MOD_ID);
    }
}