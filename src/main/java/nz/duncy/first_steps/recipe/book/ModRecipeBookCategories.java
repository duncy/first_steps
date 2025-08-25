package nz.duncy.first_steps.recipe.book;


import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipeBookCategories {
	public static final RecipeBookCategory KILNING_FUEL_WASTE = register("kilning_fuel_waste");
    public static final RecipeBookCategory KILNING_ROASTING = register("kilning_roasting");
    public static final RecipeBookCategory KILNING_MISC = register("kilning_misc");
	public static final RecipeBookCategory KNAPPING_TOOL_HEAD = register("knapping_tool_head");


	private static RecipeBookCategory register(String id) {
		return Registry.register(Registries.RECIPE_BOOK_CATEGORY, id, new RecipeBookCategory());
	}
}
