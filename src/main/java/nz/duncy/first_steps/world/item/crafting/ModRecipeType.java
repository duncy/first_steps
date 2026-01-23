package nz.duncy.first_steps.world.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplay.Type;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.crafting.display.PottersWheelRecipeDisplay;

public interface ModRecipeType {
    RecipeType<PottersWheelRecipe> POTTERS_WHEEL = register("potters_wheel");

    Type<?> TYPE = register(PottersWheelRecipeDisplay.TYPE, "potters_wheel");

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