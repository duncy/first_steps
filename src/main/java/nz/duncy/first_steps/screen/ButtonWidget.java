package nz.duncy.first_steps.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ButtonWidget extends ClickableWidget {
   private boolean selected;
   public final ItemStack selection;

   public ButtonWidget(ItemStack stack, int x, int y) {
      super(x, y, 26, 26, stack.getName());
      this.selection = stack;
   }

   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      this.drawBackground(context);
      renderIcon(context, this.getX() + 5, this.getY() + 5, this.selection);
      if (this.selected) {
         this.drawSelectionBox(context);
      }

   }

   void renderIcon(DrawContext context, int x, int y, ItemStack stack) {
              context.drawItem(stack, x, y);
   }

   public void appendClickableNarrations(NarrationMessageBuilder builder) {
      this.appendDefaultNarrations(builder);
   }

   public boolean isSelected() {
      return super.isSelected() || this.selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   private void drawBackground(DrawContext context) {
      context.drawGuiTexture(RenderLayer::getGuiTextured, KnappingSelectionScreen.SLOT_TEXTURE, this.getX(), this.getY(), 26, 26);
   }

   private void drawSelectionBox(DrawContext context) {
      context.drawGuiTexture(RenderLayer::getGuiTextured, KnappingSelectionScreen.SELECTION_TEXTURE, this.getX(), this.getY(), 26, 26);
   }
}
