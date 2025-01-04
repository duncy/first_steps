// package nz.duncy.first_steps.compat;

// import me.shedaniel.math.Rectangle;
// import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
// import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
// import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
// import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
// import me.shedaniel.rei.api.common.util.EntryStacks;
// import nz.duncy.first_steps.block.ModBlocks;
// import nz.duncy.first_steps.recipe.KilningRecipe;
// import nz.duncy.first_steps.recipe.ModRecipes;
// import nz.duncy.first_steps.screen.KilnScreen;


// public class FirstStepsREIClientPlugin implements REIClientPlugin {
//     @Override
//     public void registerCategories(CategoryRegistry registry) {
//         registry.add(new KilningCategory());

//         registry.addWorkstations(KilningCategory.FUEL_WASTE, EntryStacks.of(ModBlocks.KILN));
//     }

//     @Override
//     public void registerDisplays(DisplayRegistry registry) {
//         registry.registerRecipeFiller(KilningRecipe.class, ModRecipes.KILNING_TYPE,
//                 KilningDisplay::new);
//     }

//     @Override
//     public void registerScreens(ScreenRegistry registry) {
//         registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), KilnScreen.class,
//                 KilningCategory.FUEL_WASTE);
//     }
// }
