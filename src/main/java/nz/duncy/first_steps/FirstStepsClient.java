package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.block.entity.renderer.RockBlockEntityRenderer;
import nz.duncy.first_steps.item.entity.renderer.ModEntityRenderer;
import nz.duncy.first_steps.screen.KilnScreen;
import nz.duncy.first_steps.screen.ModScreenHandlers;

public class FirstStepsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.ROCK_BLOCK_ENTITY, RockBlockEntityRenderer::new);
        ModEntityRenderer.registerSpearEntities();

        HandledScreens.register(ModScreenHandlers.KILN_SCREEN_HANDLER, KilnScreen::new);
    }

}
