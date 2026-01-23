package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.events.ModEventsClient;
import nz.duncy.first_steps.gui.screens.inventory.KnappingScreen;
import nz.duncy.first_steps.gui.screens.inventory.PottersWheelScreen;
import nz.duncy.first_steps.model.geom.ModModelLayers;
import nz.duncy.first_steps.network.cache.ClientKnappingState;
import nz.duncy.first_steps.network.cache.ClientPottersWheelState;
import nz.duncy.first_steps.network.protocol.common.custom.KnappingRecipePacketPayload;
import nz.duncy.first_steps.network.protocol.common.custom.PottersWheelRecipePayload;
import nz.duncy.first_steps.renderer.blockentity.DecoratedJarRenderer;
import nz.duncy.first_steps.renderer.blockentity.PottersWheelRenderer;
import nz.duncy.first_steps.renderer.blockentity.UnfiredDecoratedJarRenderer;
import nz.duncy.first_steps.renderer.blockentity.UnfiredDecoratedPotRenderer;
import nz.duncy.first_steps.renderer.special.DecoratedJarSpecialRenderer;
import nz.duncy.first_steps.renderer.special.PottersWheelSpecialRenderer;
import nz.duncy.first_steps.renderer.special.UnfiredDecoratedJarSpecialRenderer;
import nz.duncy.first_steps.renderer.special.UnfiredDecoratedPotSpecialRenderer;
import nz.duncy.first_steps.world.inventory.ModMenuType;
import nz.duncy.first_steps.world.level.block.entity.ModBlockEntityType;

public class FirstStepsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        BlockEntityRenderers.register(ModBlockEntityType.DECORATED_JAR, DecoratedJarRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityType.UNFIRED_DECORATED_POT, UnfiredDecoratedPotRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityType.UNFIRED_DECORATED_JAR, UnfiredDecoratedJarRenderer::new);
        BlockEntityRenderers.register(ModBlockEntityType.POTTERS_WHEEL, PottersWheelRenderer::new);

        ModModelLayers.initialize();

        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "decorated_jar"), DecoratedJarSpecialRenderer.Unbaked.MAP_CODEC);
        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_pot"), UnfiredDecoratedPotSpecialRenderer.Unbaked.MAP_CODEC);
        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "unfired_decorated_jar"), UnfiredDecoratedJarSpecialRenderer.Unbaked.MAP_CODEC);
        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel"), PottersWheelSpecialRenderer.Unbaked.MAP_CODEC);

        ModEventsClient.initialize();

        MenuScreens.register(ModMenuType.KNAPPING_SELECTION_MENU, KnappingScreen::new);
        MenuScreens.register(ModMenuType.POTTERS_WHEEL_SELECTION_MENU, PottersWheelScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(KnappingRecipePacketPayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().screen != null) {
                    if (context.client().screen instanceof KnappingScreen screen) {
                        screen.updateRecipes(payload.recipes());
                    }
                }
                
                ClientKnappingState.pendingRecipes = payload.recipes();
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PottersWheelRecipePayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                if (context.client().screen != null) {
                    if (context.client().screen instanceof PottersWheelScreen screen) {
                        screen.updateRecipes(payload.recipes());
                    }
                }
                
                ClientPottersWheelState.pendingRecipes = payload.recipes();
            });
        });

        FirstSteps.LOGGER.info("Finished client initialisation of " + FirstSteps.MOD_ID + ", have fun! :^)");
	}
}