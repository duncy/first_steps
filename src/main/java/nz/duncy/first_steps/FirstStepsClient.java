package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.item.model.special.SpecialModelTypes;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.client.gui.screen.ingame.CrucibleScreen;
import nz.duncy.first_steps.client.gui.screen.ingame.KilnScreen;
import nz.duncy.first_steps.client.gui.screen.ingame.KnappingSelectionScreen;
import nz.duncy.first_steps.client.gui.screen.ingame.MannequinScreen;
import nz.duncy.first_steps.client.render.block.entity.PottersWheelBlockEntityRenderer;
import nz.duncy.first_steps.client.render.block.entity.UnfiredDecoratedPotBlockEntityRenderer;
import nz.duncy.first_steps.client.render.entity.ModEntityRenderer;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.item.model.special.BasaltSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.BronzeSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.CopperSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.FlintSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.IronSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.ObsidianSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.PottersWheelModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.StoneSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.UnfiredDecoratedPotModelRenderer;
import nz.duncy.first_steps.network.packet.KnappingRecipePayload;
import nz.duncy.first_steps.screen.ModScreenHandlers;

public class FirstStepsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register Block Entity Renderers
        // BlockEntityRendererFactories.register(ModBlockEntities.ROCK_BLOCK_ENTITY, RockBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.POTTERS_WHEEL_BLOCK_ENTITY, PottersWheelBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.UNFIRED_DECORATED_POT_BLOCK_ENTITY, UnfiredDecoratedPotBlockEntityRenderer::new);

        // Register Entity Renderers
        ModEntityRenderer.registerModEntityRenderers();

        // Register Entity Model Layers
        ModEntityModelLayers.registerEntityModelLayers();

        // Register Special Model Types
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID,"unfired_decorated_pot"), UnfiredDecoratedPotModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID,"potters_wheel"), PottersWheelModelRenderer.Unbaked.CODEC);
        // BuiltinItemRendererRegistry.INSTANCE.register(ModItems.WOODEN_TONGS, new TongItemRenderer());
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "stone_spear"), StoneSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "flint_spear"), FlintSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "basalt_spear"), BasaltSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "obsidian_spear"), ObsidianSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "copper_spear"), CopperSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "bronze_spear"), BronzeSpearModelRenderer.Unbaked.CODEC);
        SpecialModelTypes.ID_MAPPER.put(Identifier.of(FirstSteps.MOD_ID, "iron_spear"), IronSpearModelRenderer.Unbaked.CODEC);

        // Register Screen Handlers
        HandledScreens.register(ModScreenHandlers.KILN_SCREEN_HANDLER, KilnScreen::new);
        HandledScreens.register(ModScreenHandlers.CRUCIBLE_SCREEN_HANDLER, CrucibleScreen::new);
        HandledScreens.register(ModScreenHandlers.KNAPPING_SELECTION_SCREEN_HANDLER, KnappingSelectionScreen::new);
        HandledScreens.register(ModScreenHandlers.MANNEQUIN_SCREEN_HANDLER, MannequinScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(KnappingRecipePayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().currentScreen instanceof KnappingSelectionScreen screen) {
                    screen.updateRecipes(context, payload.recipes());
                }
            });
        });
    }
}
