package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import nz.duncy.first_steps.events.ModEventsClient;
import nz.duncy.first_steps.gui.screens.inventory.KnappingScreen;
import nz.duncy.first_steps.network.cache.ClientKnappingState;
import nz.duncy.first_steps.network.protocol.common.custom.KnappingRecipePacketPayload;
import nz.duncy.first_steps.world.inventory.ModMenuType;

public class FirstStepsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ModEventsClient.initialize();

        MenuScreens.register(ModMenuType.KNAPPING_SELECTION_MENU, KnappingScreen::new);

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
	}
}