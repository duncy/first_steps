package nz.duncy.first_steps.gui.screens.inventory;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.inventory.RecipeSelectionMenu;

public abstract class RecipeSelectionScreen<T extends SingleItemRecipe, M extends RecipeSelectionMenu<T, M>> extends AbstractContainerScreen<M> {
    static final Identifier SLOT_SPRITE = Identifier.withDefaultNamespace("gamemode_switcher/slot");
    static final Identifier SELECTION_SPRITE = Identifier.withDefaultNamespace("gamemode_switcher/selection");
    private static final Identifier RECIPE_SWITCHER_SPRITE = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "textures/gui/container/recipe_switcher.png");
    private ItemStack currentlyHovered;
    SingleInputSet<T> recipes = SingleInputSet.empty();
    private int firstMouseX;
    private int firstMouseY;
    private boolean setFirstMousePos;
    private final List<RecipeSlot> slots = Lists.newArrayList();
    private int UI_WIDTH = 0;

    public RecipeSelectionScreen(M menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    protected void init() {
        super.init();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        if (mouseButtonEvent.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            int index = mouseIsOnButton(mouseButtonEvent.x(), mouseButtonEvent.y());
            if (index >= 0) {
                ContextMap contextMap = SlotDisplayContext.fromLevel(this.minecraft.level);
                if (this.recipes.entries().get(index).recipe().optionDisplay().resolveForFirstStack(contextMap) == this.currentlyHovered) {
                    this.sendSelection(index);
                }
            }
        }

        return super.mouseClicked(mouseButtonEvent, bl);
    }

    private int mouseIsOnButton(double mouseX, double mouseY) {
        for (int i = 0; i < this.recipes.size(); ++i) {
           if (mouseX >= this.width / 2 - UI_WIDTH / 2 + i * 31 && mouseX <= (this.width / 2 - UI_WIDTH / 2 + i * 31) + 26) {
              if (mouseY >= this.height / 2 - 31 && mouseY <= (this.height / 2 - 31) + 26) {
                 return i;
              }
           }
        
        }
        return -1;
    }    
        
        public void updateRecipes(SingleInputSet<T> recipes) {
            this.recipes = recipes;
            this.UI_WIDTH = this.recipes.size() * 31 - 5;
    
            ContextMap contextMap = SlotDisplayContext.fromLevel(this.minecraft.level);
            for(int i = 0; i < this.recipes.size(); ++i ){
                RecipeSlot slot = new RecipeSlot(this.recipes.entries().get(i).recipe().optionDisplay().resolveForStacks(contextMap).getFirst(), this.width / 2 - UI_WIDTH / 2 + i * 31, this.height / 2 - 31);
                this.slots.add(slot);
            }
        }
    
        protected abstract void sendSelection(int selection);
    
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
            if (this.currentlyHovered != null) {
                guiGraphics.drawCenteredString(this.font, this.currentlyHovered.getItemName(), this.width / 2, this.height / 2 - 31 - 20, -1);
            } else {
                guiGraphics.drawCenteredString(this.font, getTitle(), this.width / 2, this.height / 2 - 31 - 20, -1);
            }
    
            // guiGraphics.drawCenteredString(this.font, Component.translatable("screen." + FirstSteps.MOD_ID + ".knapping.select", new Object[]{Component.translatable("screen." + FirstSteps.MOD_ID + ".knapping.press_enter").withStyle(ChatFormatting.AQUA)}), this.width / 2, this.height / 2 + 5, 16777215);
    
            if (!this.setFirstMousePos) {
                this.firstMouseX = mouseX;
                this.firstMouseY = mouseY;
                this.setFirstMousePos = true;
            }
    
            boolean bl = this.firstMouseX == mouseX && this.firstMouseY == mouseY;
            Iterator<RecipeSlot> iterator = this.slots.iterator();
    
            while(iterator.hasNext()) {
                RecipeSlot recipeSlot = iterator.next();
                recipeSlot.render(guiGraphics, mouseX, mouseY, delta);
                recipeSlot.setSelected(this.currentlyHovered == recipeSlot.selection);
                if (!bl && recipeSlot.isHoveredOrFocused()) {
                    this.currentlyHovered = recipeSlot.selection;
                }
            }
        }
    
        public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
            int k = this.width / 2 - 64;
            int l = this.height / 2 - 31 - 27;
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, RECIPE_SWITCHER_SPRITE, k, l, 0.0F, 0.0F, 128, 75, 128, 128);
        }

        @Override
        public abstract Component getTitle();

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {}

    public static class RecipeSlot extends AbstractWidget {
        final ItemStack selection;
        private boolean isSelected;

        public RecipeSlot(ItemStack itemStack, int x, int y) {
            super(x, y, 26, 26, itemStack.getItemName());
            this.selection = itemStack;
        }

        public void renderWidget(GuiGraphics guiGraphics, int x, int y, float delta) {
            this.drawSlot(guiGraphics);
            if (this.isSelected) {
                this.drawSelection(guiGraphics);
            }

            guiGraphics.renderItem(this.selection, this.getX() + 5, this.getY() + 5);
        }

        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }

        public boolean isHoveredOrFocused() {
            return super.isHoveredOrFocused() || this.isSelected;
        }

        public void setSelected(boolean bl) {
            this.isSelected = bl;
        }

        private void drawSlot(GuiGraphics guiGraphics) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RecipeSelectionScreen.SLOT_SPRITE, this.getX(), this.getY(), 26, 26);
        }

        private void drawSelection(GuiGraphics guiGraphics) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, RecipeSelectionScreen.SELECTION_SPRITE, this.getX(), this.getY(), 26, 26);
        }
    }
    
}




