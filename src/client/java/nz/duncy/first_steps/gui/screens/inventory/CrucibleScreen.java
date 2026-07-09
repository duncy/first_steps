package nz.duncy.first_steps.gui.screens.inventory;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.renderer.ModSheets;
import nz.duncy.first_steps.world.inventory.CrucibleMenu;
import nz.duncy.first_steps.world.item.Metal;

public class CrucibleScreen extends AbstractContainerScreen<CrucibleMenu> {
    // TODO: Create temperature bar screen type to be used for this and kiln. as well as a whole parent BE. It needs it.
    private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "textures/gui/container/crucible.png");
    private static final TextureAtlasSprite METAL_FLOW_SPRITE = Minecraft.getInstance().getAtlasManager().get(ModSheets.METAL_FLOW);

    protected int imageHeight = 177;
    private static final int moltenDisplayWidth = 52;
    private static final int moltenDisplayHeight = 81;
    private static final int moltenDisplayXOffset = 76;
    private static final int moltenDisplayYOffset = -8;
    private static final int temperatureBarWidth = 4;
    private static final int temperatureBarHeight = 72;
    private static final int temperatureBarXOffset = 21;
    private static final int temperatureBarYOffset = 14;
    private static final int temperatureBarLabelY = 1;
    private static final int metalFlowSpriteSize = 32;

    public CrucibleScreen(CrucibleMenu crucibleMenu, Inventory inventory, Component component) {
        super(crucibleMenu, inventory, component);
        this.titleLabelY = 11;
        this.inventoryLabelY = this.imageHeight - 99;
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
       super.render(guiGraphics, i, j, f);
       renderMetalTooltips(guiGraphics, i, j);
       this.renderTooltip(guiGraphics, i, j);
    }

    @Override
    protected void renderLabels(@NonNull GuiGraphics guiGraphics, int i, int j) {
        super.renderLabels(guiGraphics, i, j);

        guiGraphics.drawString(
            this.font, 
            Component.translatable("container.first_steps.crucible.temperature", this.menu.getData(Metal.values().length)),
            this.titleLabelX + moltenDisplayXOffset + moltenDisplayWidth + temperatureBarWidth, 
            temperatureBarLabelY, 
            -12566464, 
            false
        );
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED, 
            CONTAINER_TEXTURE, 
            k, 
            l, 
            0.0F, 
            0.0F, 
            this.imageWidth, 
            this.imageHeight, 
            256, 
            256
        );

        renderMetalStack(guiGraphics, k, l);

        int temperatureBarValue = this.menu.getData(Metal.values().length);
        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED, 
            CONTAINER_TEXTURE, 
            k + moltenDisplayXOffset + moltenDisplayWidth + temperatureBarXOffset, 
            l - 1 - moltenDisplayYOffset + temperatureBarHeight - temperatureBarValue + temperatureBarYOffset, 
            this.imageWidth, 
            l - moltenDisplayYOffset - moltenDisplayHeight - temperatureBarValue, 
            temperatureBarWidth, 
            temperatureBarValue, 
            256, 
            256
        );

        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED, 
            CONTAINER_TEXTURE, 
            k + moltenDisplayXOffset, 
            l  - moltenDisplayYOffset, 
            this.imageWidth + 4, 
            l - moltenDisplayYOffset - moltenDisplayHeight - 85, 
            4, 
            81, 
            256, 
            256
        );
    }

    private void renderMetalStack(GuiGraphics guiGraphics, int k, int l) {
        for (int index = 0; index < Metal.values().length; index++) {
            int amount = this.menu.getData(index);
            if (amount <= 0) continue;
            
            int color = Metal.byIndex(index).getColor();
    
            renderTiledSprite(
                guiGraphics, 
                METAL_FLOW_SPRITE, 
                k + moltenDisplayXOffset, 
                l + moltenDisplayHeight - moltenDisplayYOffset - amount, 
                amount, 
                color
            );
        }
    }

    private void renderTiledSprite(GuiGraphics guiGraphics, TextureAtlasSprite sprite, int x, int y, int height, int color) {
        guiGraphics.enableScissor(x, y, x + moltenDisplayWidth, y + height);

        for (int currentX = x; currentX < x + moltenDisplayWidth; currentX += metalFlowSpriteSize) {
            for (int currentY = y; currentY < y + height; currentY += metalFlowSpriteSize) {
                guiGraphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    sprite,
                    currentX,
                    currentY,
                    metalFlowSpriteSize,
                    metalFlowSpriteSize,
                    color
                );
            }
        }
    
        guiGraphics.disableScissor();
    }

    private void renderMetalTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        
        int displayLeft = k + moltenDisplayXOffset;
        int displayTop = l - moltenDisplayYOffset + moltenDisplayHeight;
    
        if (mouseX >= displayLeft && mouseX < displayLeft + moltenDisplayWidth) {
            for (int index = 0; index < Metal.values().length; index++) {
                int amount = this.menu.getData(index);
                if (amount <= 0) continue;

                if (mouseY >= displayTop - amount && mouseY < displayTop) {
                    guiGraphics.setTooltipForNextFrame(
                        this.font, 
                        Metal.getAlloyTooltipComponents(index, amount), 
                        new ItemStack(Metal.byIndex(index).getItemIcon()).getTooltipImage(), 
                        mouseX, 
                        mouseY, 
                        new ItemStack(Metal.byIndex(index).getItemIcon()).get(DataComponents.TOOLTIP_STYLE)
                    );
                    break;
                }
            }
        }
    }
}
