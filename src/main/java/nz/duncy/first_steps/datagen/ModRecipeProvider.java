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

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        generateToolRecipe(exporter, ModItems.STONE_HEAD_AXE, Items.STONE_AXE);
        generateToolRecipe(exporter, ModItems.STONE_HEAD_SHOVEL, Items.STONE_SHOVEL);
        //generateToolRecipe(exporter, ModItems.STONE_HEAD_SPEAR, ModItems.STONE_SPEAR);
        //generateToolRecipe(exporter, ModItems.STONE_HEAD_KNIFE, ModItems.STONE_KNIFE);

        //generateToolRecipe(exporter, ModItems.BASALT_HEAD_AXE, Items.BASALT_AXE);
        //generateToolRecipe(exporter, ModItems.BASALT_HEAD_SHOVEL, Items.BASALT_SHOVEL);
        generateToolRecipe(exporter, ModItems.BASALT_HEAD_SPEAR, ModItems.BASALT_SPEAR);
        //generateToolRecipe(exporter, ModItems.BASALT_HEAD_KNIFE, ModItems.BASALT_KNIFE);
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
}


    
