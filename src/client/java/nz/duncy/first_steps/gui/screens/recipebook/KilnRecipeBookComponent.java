package nz.duncy.first_steps.gui.screens.recipebook;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import nz.duncy.first_steps.world.inventory.KilnMenu;
import nz.duncy.first_steps.world.item.crafting.display.KilnRecipeDisplay;

@Environment(EnvType.CLIENT)
public class KilnRecipeBookComponent extends RecipeBookComponent<KilnMenu> {
   private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled"), Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled"), Identifier.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"), Identifier.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted"));
   private final Component recipeFilterName;

   public KilnRecipeBookComponent(KilnMenu kilnMenu, Component component, List<RecipeBookComponent.TabInfo> list) {
      super(kilnMenu, list);
      this.recipeFilterName = component;
   }

   protected WidgetSprites getFilterButtonTextures() {
      return FILTER_SPRITES;
   }

   protected boolean isCraftingSlot(Slot slot) {
      boolean var10000;
      switch (slot.index) {
         case 0:
         case 1:
         case 2:
            var10000 = true;
            break;
         default:
            var10000 = false;
      }

      return var10000;
   }

   protected void fillGhostRecipe(GhostSlots ghostSlots, RecipeDisplay recipeDisplay, ContextMap contextMap) {
      ghostSlots.setResult(((KilnMenu)this.menu).getResultSlot(), contextMap, recipeDisplay.result());
      if (recipeDisplay instanceof KilnRecipeDisplay kilnRecipeDisplay) {
         ghostSlots.setInput((Slot)((KilnMenu)this.menu).slots.get(0), contextMap, kilnRecipeDisplay.ingredient());
         Slot slot = (Slot)((KilnMenu)this.menu).slots.get(1);
         if (slot.getItem().isEmpty()) {
            ghostSlots.setInput(slot, contextMap, kilnRecipeDisplay.fuel());
         }
      }

   }

   protected Component getRecipeFilterName() {
      return this.recipeFilterName;
   }

   protected void selectMatchingRecipes(RecipeCollection recipeCollection, StackedItemContents stackedItemContents) {
      recipeCollection.selectRecipes(stackedItemContents, (recipeDisplay) -> recipeDisplay instanceof KilnRecipeDisplay);
   }
}
