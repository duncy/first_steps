package nz.duncy.first_steps.client.gui.screen.ingame;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.display.CuttingRecipeDisplay.Grouping;
import net.minecraft.recipe.display.SlotDisplayContexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextParameterMap;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.gui.screen.ButtonWidget;
import nz.duncy.first_steps.network.packet.KnappingSelectionPayload;
import nz.duncy.first_steps.screen.KnappingSelectionScreenHandler;

public class KnappingSelectionScreen extends HandledScreen<KnappingSelectionScreenHandler> {
   public static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("gamemode_switcher/slot");
   public static final Identifier SELECTION_TEXTURE = Identifier.ofVanilla("gamemode_switcher/selection");
   private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/gui/switcher.png");
   private int UI_WIDTH = 0;
   private static final Text SELECT_TEXT;
   private ItemStack selection;
   private Grouping<StonecuttingRecipe> recipes = Grouping.empty();
   private int lastMouseX;
   private int lastMouseY;
   private boolean mouseUsedForSelection;
   private boolean canClose;
   private final List<ButtonWidget> buttons = Lists.newArrayList();

   public KnappingSelectionScreen(KnappingSelectionScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      this.canClose = false;
      this.selection = null;
   }

   public void updateRecipes(Context context, Grouping<StonecuttingRecipe> recipes) {
         this.recipes = recipes;
         this.UI_WIDTH = this.recipes.size() * 31 - 5;

         ContextParameterMap contextParameterMap = SlotDisplayContexts.createParameters(this.client.world);
         for(int i = 0; i < this.recipes.size(); ++i ){
            this.buttons.add(new ButtonWidget(this.recipes.entries().get(i).recipe().optionDisplay().getStacks(contextParameterMap).getFirst(), this.width / 2 - UI_WIDTH / 2 + i * 31, this.height / 2 - 31));
         }
   }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      if (!this.checkForClose()) {
         context.getMatrices().push();
         RenderSystem.enableBlend();
         int i = this.width / 2 - 62;
         int j = this.height / 2 - 31 - 27;
         context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, i, j, 0.0F, 0.0F, 125, 75, 128, 128);
         context.getMatrices().pop();
         super.render(context, mouseX, mouseY, delta);
         if (this.selection != null) {
            context.drawCenteredTextWithShadow(this.textRenderer, this.selection.getName(), this.width / 2, this.height / 2 - 31 - 20, -1);
         } else {
            context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.switcher"), this.width / 2, this.height / 2 - 31 - 20, -1);
         }
         
         context.drawCenteredTextWithShadow(this.textRenderer, SELECT_TEXT, this.width / 2, this.height / 2 + 5, 16777215);

         if (!this.mouseUsedForSelection) {
               this.lastMouseX = mouseX;
               this.lastMouseY = mouseY;
               this.mouseUsedForSelection = true;
         }

         boolean bl = this.lastMouseX == mouseX && this.lastMouseY == mouseY;
         Iterator<ButtonWidget> iterator = this.buttons.iterator();

         while(iterator.hasNext()) {
               ButtonWidget buttonWidget = (ButtonWidget)iterator.next();
               buttonWidget.render(context, mouseX, mouseY, delta);
               buttonWidget.setSelected(this.selection == buttonWidget.selection);
               if (!bl && buttonWidget.isSelected()) {
                  this.selection = buttonWidget.selection;
               }
         }
      }
   }

   private int mouseIsOnButton(double mouseX, double mouseY) {
      for(int i = 0; i < this.recipes.size(); ++i) {
         if (mouseX >= this.width / 2 - UI_WIDTH / 2 + i * 31 && mouseX <= (this.width / 2 - UI_WIDTH / 2 + i * 31) + 26) {
            if (mouseY >= this.height / 2 - 31 && mouseY <= (this.height / 2 - 31) + 26) {
               return i;
            }
         }
      
      }
      return -1;
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
         int index = mouseIsOnButton(mouseX, mouseY);
         if (index >= 0) {
            ContextParameterMap contextParameterMap = SlotDisplayContexts.createParameters(this.client.world);
            if (this.recipes.entries().get(index).recipe().optionDisplay().getFirst(contextParameterMap) == this.selection) {
               this.sendSelection(index);
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }
   
   private void sendSelection(int selection) {
      if (this.client.world.isClient()) {
         KnappingSelectionPayload payload = new KnappingSelectionPayload(selection);
         ClientPlayNetworking.send(payload);
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
   }

   public void drawForeground(DrawContext context, int mouseX, int mouseY) {
   }

   private boolean checkForClose() {
      if (InputUtil.isKeyPressed(this.client.getWindow().getHandle(), InputUtil.GLFW_KEY_ESCAPE) || this.canClose == true) {
         this.close();
         return true;
      } else {
         return false;
      }
   }

   public boolean shouldPause() {
      return false;
   }

   static {
      SELECT_TEXT = Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.select", new Object[]{Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.press_enter").formatted(Formatting.AQUA)});
   }

   @Override
   protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
   }

   
}
