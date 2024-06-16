package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.item.ModItems;
// import nz.duncy.first_steps.recipe.KilnRecipe;
// import nz.duncy.first_steps.recipe.ModRecipes;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        generateToolRecipes(exporter);
        // generateKilnRecipes(exporter);
    }

    private void generateToolRecipes(RecipeExporter exporter) {
        generateToolRecipe(exporter, ModItems.STONE_HEAD_AXE, Items.STONE_AXE);
        generateToolRecipe(exporter, ModItems.STONE_HEAD_SHOVEL, Items.STONE_SHOVEL);

        generateToolRecipe(exporter, ModItems.BASALT_HEAD_SPEAR, ModItems.BASALT_SPEAR);

    }

    private void generateToolRecipe(RecipeExporter exporter, Item head, Item tool) {
       ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, tool, 1)
           .pattern("H")
           .pattern("S")
           .input('H', head)
           .input('S', Items.STICK)
           .criterion(hasItem(head), conditionsFromItem(head))
           .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
           .offerTo(exporter, new Identifier(getRecipeName(tool)));
    }

    // private void generateKilnRecipes(RecipeExporter exporter) {
    //     generateKilnRecipe(exporter, ItemTags.LOGS, Items.CHARCOAL, "logs");
    // }

    // private void generateKilnRecipe(RecipeExporter exporter, ItemConvertible input, Item output) {
    //     KilnRecipeJsonBuilder.create(Ingredient.ofItems(new ItemConvertible[]{input}), RecipeCategory.MISC, output, 1.0f, 60, ModRecipes.KILN_SERIALIZER, KilnRecipe::new)
    //         .group("kiln")
    //         .criterion(hasItem(input), conditionsFromItem(input))
    //         .offerTo(exporter, getItemPath(output) + "_from_kilning_" + getItemPath(input));
    // }

    // private void generateKilnRecipe(RecipeExporter exporter, TagKey<Item> tag, Item output, String tagpath) {
    //     KilnRecipeJsonBuilder.create(Ingredient.fromTag(tag), RecipeCategory.MISC, output, 1.0f, 60, ModRecipes.KILN_SERIALIZER, KilnRecipe::new)
    //         .group("kiln")
    //         .criterion("has_" + tagpath, conditionsFromTag(tag))
    //         .offerTo(exporter, getItemPath(output) + "_from_kilning_" + tagpath);
    // }

}


    
