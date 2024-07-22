package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.block.entity.renderer.RockBlockEntityRenderer;
import nz.duncy.first_steps.item.ModItems;
import nz.duncy.first_steps.item.entity.renderer.ModEntityRenderer;
import nz.duncy.first_steps.item.renderer.TongItemRenderer;
import nz.duncy.first_steps.screen.CrucibleScreen;
import nz.duncy.first_steps.screen.KilnScreen;
import nz.duncy.first_steps.screen.ModScreenHandlers;

public class FirstStepsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.ROCK_BLOCK_ENTITY, RockBlockEntityRenderer::new);
        ModEntityRenderer.registerSpearEntities();
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODEN_TONGS, new TongItemRenderer());
        // ModelLoadingPlugin.register(null);


        HandledScreens.register(ModScreenHandlers.KILN_SCREEN_HANDLER, KilnScreen::new);
        HandledScreens.register(ModScreenHandlers.CRUCIBLE_SCREEN_HANDLER, CrucibleScreen::new);
    }

}
