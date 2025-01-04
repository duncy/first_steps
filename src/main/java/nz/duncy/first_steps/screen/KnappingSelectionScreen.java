package nz.duncy.first_steps.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class KnappingSelectionScreen extends HandledScreen<KnappingSelectionScreenHandler> {
   static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("gamemode_switcher/slot");
   static final Identifier SELECTION_TEXTURE = Identifier.ofVanilla("gamemode_switcher/selection");
   private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/gui/switcher.png");
   private static final int UI_WIDTH = KnappingSelection.values().length * 31 - 5;
   private static final Text SELECT_TEXT;
   private KnappingSelection selection;
   private int lastMouseX;
   private int lastMouseY;
   private boolean mouseUsedForSelection;
   private boolean canClose;
   private final List<ButtonWidget> buttons = Lists.newArrayList();

   public KnappingSelectionScreen(KnappingSelectionScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      this.selection = null;
      this.canClose = false;

   }

   protected void init() {
      super.init();

      for(int i = 0; i < KnappingSelection.values().length; ++i) {
         KnappingSelection selection = KnappingSelection.VALUES[i];
         this.buttons.add(new ButtonWidget(selection, this.width / 2 - UI_WIDTH / 2 + i * 31, this.height / 2 - 31));
      }

   }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!this.checkForClose()) {
            context.getMatrices().push();
            RenderSystem.enableBlend();
            int i = this.width / 2 - 62;
            int j = this.height / 2 - 31 - 27;
            context.drawTexture(TEXTURE, i, j, 0.0F, 0.0F, 125, 75, 128, 128);
            context.getMatrices().pop();
            super.render(context, mouseX, mouseY, delta);
            if (this.selection != null) {
               context.drawCenteredTextWithShadow(this.textRenderer, this.selection.getText(), this.width / 2, this.height / 2 - 31 - 20, -1);
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
      for(int i = 0; i < KnappingSelection.values().length; ++i) {
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
         int selection = mouseIsOnButton(mouseX, mouseY);
         if (selection >= 0) {
            if (selection == this.selection.ordinal()) {
               FirstSteps.LOGGER.info(String.valueOf(this.selection));
               handler.setSelection(this.selection);
               this.canClose = true;
            }
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }
   

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
   }

   public void drawForeground(DrawContext context, int mouseX, int mouseY) {
   }

   private boolean checkForClose() {
      if (InputUtil.isKeyPressed(this.client.getWindow().getHandle(), InputUtil.GLFW_KEY_ESCAPE) || this.canClose == true) {
         this.client.setScreen((Screen)null);
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
