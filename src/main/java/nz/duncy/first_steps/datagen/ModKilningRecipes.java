package nz.duncy.first_steps.datagen;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import nz.duncy.first_steps.recipe.KilningRecipe;
import nz.duncy.first_steps.recipe.book.KilningRecipeCategory;

public class ModKilningRecipes extends RecipeGenerator {

    public ModKilningRecipes(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        super(wrapperLookup, recipeExporter);
    }

    private void generateFuelWasteRecipe(RecipeExporter exporter, Ingredient ingredient, Item result) {
        new KilningRecipeJsonBuilder(RecipeCategory.MISC, KilningRecipeCategory.FUEL_WASTE, result, ingredient, 200, KilningRecipe::new)
           .criterion("has_log", this.conditionsFromTag(ItemTags.LOGS_THAT_BURN))
           .offerTo(exporter);
    }

    @Override
    public void generate() {
        generateFuelWasteRecipe(exporter, this.ingredientFromTag(ItemTags.LOGS_THAT_BURN), Items.CHARCOAL);
    }
    
}
