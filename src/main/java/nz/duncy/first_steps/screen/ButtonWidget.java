package nz.duncy.first_steps.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;

@Environment(EnvType.CLIENT)
public class ButtonWidget extends ClickableWidget {
   private boolean selected;
   public final KnappingSelection selection;

   public ButtonWidget(KnappingSelection selection, int x, int y) {
      super(x, y, 26, 26, selection.getText());
      this.selection = selection;
   }

   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      this.drawBackground(context);
      this.selection.renderIcon(context, this.getX() + 5, this.getY() + 5);
      if (this.selected) {
         this.drawSelectionBox(context);
      }

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
      context.drawGuiTexture(KnappingSelectionScreen.SLOT_TEXTURE, this.getX(), this.getY(), 26, 26);
   }

   private void drawSelectionBox(DrawContext context) {
      context.drawGuiTexture(KnappingSelectionScreen.SELECTION_TEXTURE, this.getX(), this.getY(), 26, 26);
   }
}
