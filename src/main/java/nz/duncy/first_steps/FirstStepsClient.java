package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.client.render.ModTexturedRenderLayers;
import nz.duncy.first_steps.client.render.block.entity.PottersWheelBlockEntityRenderer;
import nz.duncy.first_steps.client.render.block.entity.RockBlockEntityRenderer;
import nz.duncy.first_steps.client.render.block.entity.UnfiredDecoratedPotBlockEntityRenderer;
import nz.duncy.first_steps.client.render.entity.ModEntityRenderer;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.item.ModBuiltinModelItemRenderer;
import nz.duncy.first_steps.client.render.item.TongItemRenderer;
import nz.duncy.first_steps.item.ModItems;
import nz.duncy.first_steps.screen.CrucibleScreen;
import nz.duncy.first_steps.screen.KilnScreen;
import nz.duncy.first_steps.screen.ModScreenHandlers;

public class FirstStepsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register Block Entity Renderers
        BlockEntityRendererFactories.register(ModBlockEntities.ROCK_BLOCK_ENTITY, RockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.POTTERS_WHEEL_BLOCK_ENTITY, PottersWheelBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.UNFIRED_DECORATED_POT_BLOCK_ENTITY, UnfiredDecoratedPotBlockEntityRenderer::new);

        // Register Entity Renderers
        ModEntityRenderer.registerModEntityRenderers();

        // Register Entity Model Layers
        ModEntityModelLayers.registerEntityModelLayers();
        
        // Register Item Renderers
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODEN_TONGS, new TongItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.POTTERS_WHEEL.asItem(), new ModBuiltinModelItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.UNFIRED_DECORATED_POT.asItem(), new ModBuiltinModelItemRenderer());

        // Register Screen Handlers
        HandledScreens.register(ModScreenHandlers.KILN_SCREEN_HANDLER, KilnScreen::new);
        HandledScreens.register(ModScreenHandlers.CRUCIBLE_SCREEN_HANDLER, CrucibleScreen::new);
    }
}
