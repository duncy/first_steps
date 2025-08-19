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
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_HOE, Items.STONE_HOE);
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_KNIFE, ModItems.STONE_KNIFE);
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_SPEAR, ModItems.STONE_SPEAR);
        generateToolRecipe(this.exporter, ModItems.STONE_HEAD_ARROW, ModItems.STONE_ARROW);

        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_AXE, ModItems.FLINT_AXE);
        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_SHOVEL, ModItems.FLINT_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_HOE, ModItems.FLINT_HOE);
        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_KNIFE, ModItems.FLINT_KNIFE);
        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_SPEAR, ModItems.FLINT_SPEAR);
        generateToolRecipe(this.exporter, ModItems.FLINT_HEAD_ARROW, ModItems.FLINT_ARROW);

        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_AXE, ModItems.BASALT_AXE);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_SHOVEL, ModItems.BASALT_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_HOE, ModItems.BASALT_HOE);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_KNIFE, ModItems.BASALT_KNIFE);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_SPEAR, ModItems.BASALT_SPEAR);
        generateToolRecipe(this.exporter, ModItems.BASALT_HEAD_ARROW, ModItems.BASALT_ARROW);

        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_AXE, ModItems.OBSIDIAN_AXE);
        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_SHOVEL, ModItems.OBSIDIAN_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_HOE, ModItems.OBSIDIAN_HOE);
        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_KNIFE, ModItems.OBSIDIAN_KNIFE);
        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_SPEAR, ModItems.OBSIDIAN_SPEAR);
        generateToolRecipe(this.exporter, ModItems.OBSIDIAN_HEAD_ARROW, ModItems.OBSIDIAN_ARROW);

        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_AXE, ModItems.COPPER_AXE);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_SHOVEL, ModItems.COPPER_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_HOE, ModItems.COPPER_HOE);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_KNIFE, ModItems.COPPER_KNIFE);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_SPEAR, ModItems.COPPER_SPEAR);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_ARROW, ModItems.COPPER_ARROW);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_SWORD, ModItems.COPPER_SWORD);
        generateToolRecipe(this.exporter, ModItems.COPPER_HEAD_PICKAXE, ModItems.COPPER_PICKAXE);

        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_AXE, ModItems.BRONZE_AXE);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_SHOVEL, ModItems.BRONZE_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_HOE, ModItems.BRONZE_HOE);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_KNIFE, ModItems.BRONZE_KNIFE);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_SPEAR, ModItems.BRONZE_SPEAR);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_ARROW, ModItems.BRONZE_ARROW);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_SWORD, ModItems.BRONZE_SWORD);
        generateToolRecipe(this.exporter, ModItems.BRONZE_HEAD_PICKAXE, ModItems.BRONZE_PICKAXE);

        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_AXE, Items.IRON_AXE);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_SHOVEL, Items.IRON_SHOVEL);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_HOE, Items.IRON_HOE);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_KNIFE, ModItems.IRON_KNIFE);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_SPEAR, ModItems.IRON_SPEAR);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_ARROW, ModItems.IRON_ARROW);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_SWORD, Items.IRON_SWORD);
        generateToolRecipe(this.exporter, ModItems.IRON_HEAD_PICKAXE, Items.IRON_PICKAXE);


    }
    
}
