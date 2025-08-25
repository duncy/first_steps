package nz.duncy.first_steps.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return FirstSteps.MOD_ID + " Recipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        
        new ModMiscRecipes(wrapperLookup, recipeExporter).generate();    
        new ModKilningRecipes(wrapperLookup, recipeExporter).generate();
        new ModKnappingRecipes(wrapperLookup, recipeExporter).generate();
        return new ModToolRecipes(wrapperLookup, recipeExporter);
    }

}


    
