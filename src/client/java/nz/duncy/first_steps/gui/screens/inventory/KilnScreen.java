package nz.duncy.first_steps.gui.screens.inventory;

import java.util.List;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.SearchRecipeBookCategory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.gui.screens.recipebook.KilnRecipeBookComponent;
import nz.duncy.first_steps.world.inventory.KilnMenu;

public class KilnScreen extends AbstractRecipeBookScreen<KilnMenu> {
    protected int imageHeight = 177;
    private static final Identifier LIT_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/lit_progress");
	private static final Identifier BURN_PROGRESS_SPRITE = Identifier.withDefaultNamespace("container/furnace/burn_progress");
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "textures/gui/container/kiln.png");
	private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.smeltable");
	private static final List<RecipeBookComponent.TabInfo> TABS = List.of(
		new RecipeBookComponent.TabInfo(SearchRecipeBookCategory.FURNACE),
		new RecipeBookComponent.TabInfo(Items.PORKCHOP, RecipeBookCategories.FURNACE_FOOD),
		new RecipeBookComponent.TabInfo(Items.STONE, RecipeBookCategories.FURNACE_BLOCKS),
		new RecipeBookComponent.TabInfo(Items.LAVA_BUCKET, Items.EMERALD, RecipeBookCategories.FURNACE_MISC)
	);
    private static final int temperatureBarWidth = 4;

    public KilnScreen(KilnMenu kilnMenu, Inventory inventory, Component component) {
        super(kilnMenu, new KilnRecipeBookComponent(kilnMenu, FILTER_NAME, TABS), inventory, component);
        this.titleLabelX = (this.imageWidth - 50) / 2;
		this.titleLabelY = 17;
        this.inventoryLabelY = this.imageHeight - 94;
   }

   @Override
    protected void init() {
        super.init();
        this.topPos -= 11;
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 20, this.height / 2 - 49);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        super.renderLabels(guiGraphics, i, j);

        guiGraphics.drawString(
            this.font, 
            Component.translatable("container.first_steps.crucible.temperature", 20),
            this.titleLabelX + 73 + temperatureBarWidth, 
            1 + 6, 
            -12566464, 
            false
        );
    }

    @Override
    protected void renderBg(@NonNull GuiGraphics guiGraphics, float f, int i, int j) {
        int k = this.leftPos;
        int l = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, k, l, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        if (((KilnMenu)this.menu).isLit()) {
            int n = Mth.ceil(((KilnMenu)this.menu).getLitProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS_SPRITE, 14, 14, 0, 14 - n, k + 56, l + 36 + 14 - n, 14, n);
        }

        int n = Mth.ceil(((KilnMenu)this.menu).getBurnProgress() * 24.0F);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS_SPRITE, 24, 16, 0, 0, k + 79, l + 34, n, 16);

        int temperatureBarValue = 20;//this.menu.getData(0);
        guiGraphics.blit(
            RenderPipelines.GUI_TEXTURED, 
            TEXTURE, 
            k + 76 + 52 + 21, 
            l + 3 + 72 - temperatureBarValue + 14, 
            this.imageWidth, 
            l + 8 - 81 - temperatureBarValue, 
            temperatureBarWidth, 
            temperatureBarValue, 
            256, 
            256
        );
    }
    
}
