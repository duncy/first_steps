package nz.duncy.first_steps.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.StonecutterScreenHandler;
import nz.duncy.first_steps.FirstSteps;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "selection"), KilnScreenHandler::new);

    public static final ScreenHandlerType<CrucibleScreenHandler> CRUCIBLE_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "selection"), CrucibleScreenHandler::new);

    public static final ScreenHandlerType<KnappingSelectionScreenHandler> KNAPPING_SELECTION_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "selection"), KnappingSelectionScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType.Factory<T> factory) {
		return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
	}

    public static void registerScreenHandlers() {
        FirstSteps.LOGGER.info("Registering ScreenHandlers for " + FirstSteps.MOD_ID);
    }
}
