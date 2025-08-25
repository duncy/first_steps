package nz.duncy.first_steps.datagen;

import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import nz.duncy.first_steps.util.ModTags;

public class ModMiscRecipes extends RecipeGenerator {

    public ModMiscRecipes(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        super(wrapperLookup, recipeExporter);
    }

    private void createVanillaBlockReplacementRecipes(RecipeExporter exporter) {
       this.createShaped(RecipeCategory.DECORATIONS, Blocks.CAMPFIRE)
			.input('L', ItemTags.LOGS)
			.input('S', Items.STICK)
			.input('T', ModTags.Items.TINDER)
			.pattern(" S ")
			.pattern("STS")
			.pattern("LLL")
			.criterion("has_stick", this.conditionsFromItem(Items.STICK))
			.criterion("has_grass", this.conditionsFromTag(ModTags.Items.TINDER))
			.offerTo(this.exporter);
    }

    @Override
    public void generate() {
        createVanillaBlockReplacementRecipes(exporter);
    }
}