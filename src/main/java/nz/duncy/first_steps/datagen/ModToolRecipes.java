package nz.duncy.first_steps.datagen;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import nz.duncy.first_steps.item.ModItems;

public class ModToolRecipes extends RecipeGenerator {
    private final RegistryEntryLookup<Item> itemLookup;

    public ModToolRecipes(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        super(wrapperLookup, recipeExporter);
        this.itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
    }

    private void generateToolRecipe(RecipeExporter exporter, Item head, Item tool) {
       ShapedRecipeJsonBuilder.create(this.itemLookup, RecipeCategory.TOOLS, tool, 1)
           .pattern("H")
           .pattern("S")
           .input('H', head)
           .input('S', Items.STICK)
           .criterion(hasItem(head), conditionsFromItem(head))
           .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
           .offerTo(this.exporter);
    }

    @Override
    public void generate() {
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_AXE, Items.STONE_AXE);
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_SHOVEL, Items.STONE_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_SPEAR, ModItems.BASALT_SPEAR);
    }
    
}
