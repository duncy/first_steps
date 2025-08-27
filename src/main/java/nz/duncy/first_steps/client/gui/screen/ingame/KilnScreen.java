package nz.duncy.first_steps.client.gui.screen.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.screen.KilnScreenHandler;

public class KilnScreen extends HandledScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/gui/kiln_gui.png");

    public KilnScreen(KilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        // titleY = 1000;
        // playerInventoryTitleY = 1000;
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        // RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        // RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        // RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);

        renderBurnProgress(context, x, y);
        renderTemperatureBar(context, x, y);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        String temperatureBarTitle = String.valueOf(handler.getTemperature()) + "°C";
        context.drawText(this.textRenderer, temperatureBarTitle, this.backgroundWidth - 8 - this.textRenderer.getWidth(temperatureBarTitle), this.titleY, 4210752, false);
     }

    private void renderBurnProgress(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            int burningProgress = handler.getBurningProgress();
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 80, y + (65 - burningProgress), 180, 14 - burningProgress, 14, burningProgress, 256, 256);
        }
    }

    private void renderTemperatureBar(DrawContext context, int x, int y) {
        int temperatureBarValue = handler.getTemperatureBarValue();
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x + 149, y  + (71 - temperatureBarValue), 176, 55 - temperatureBarValue, 4, temperatureBarValue, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}