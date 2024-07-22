package nz.duncy.first_steps.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class CrucibleScreen extends HandledScreen<CrucibleScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(FirstSteps.MOD_ID, "textures/gui/crucible_gui.png");

    public CrucibleScreen(CrucibleScreenHandler handler, PlayerInventory inventory, Text title) {
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
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // renderBurnProgress(context, x, y);
        // renderTemperatureBar(context, x, y);
    }

    // @Override
    // protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    //     super.drawForeground(context, mouseX, mouseY);
    //     String temperatureBarTitle = String.valueOf(handler.getTemperature()) + "°C";
    //     context.drawText(this.textRenderer, temperatureBarTitle, this.backgroundWidth - 8 - this.textRenderer.getWidth(temperatureBarTitle), this.titleY, 4210752, false);
    //  }

    // private void renderBurnProgress(DrawContext context, int x, int y) {
    //     if (handler.isCrafting()) {
    //         int burningProgress = handler.getBurningProgress();
    //         context.drawTexture(TEXTURE, x + 80, y + (65 - burningProgress), 180, 14 - burningProgress, 14, burningProgress);
    //     }
    // }

    // private void renderTemperatureBar(DrawContext context, int x, int y) {
    //     int temperatureBarValue = handler.getTemperatureBarValue();
    //     context.drawTexture(TEXTURE, x + 149, y  + (71 - temperatureBarValue), 176, 55 - temperatureBarValue, 4, temperatureBarValue);
    // }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}