package nz.duncy.first_steps.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import nz.duncy.first_steps.FirstSteps;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "kiln"), KilnScreenHandler::new);

    public static final ScreenHandlerType<CrucibleScreenHandler> CRUCIBLE_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "crucible"), CrucibleScreenHandler::new);

    public static final ScreenHandlerType<KnappingSelectionScreenHandler> KNAPPING_SELECTION_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "selection"), KnappingSelectionScreenHandler::new);

    public static final ScreenHandlerType<MannequinScreenHandler> MANNEQUIN_SCREEN_HANDLER = 
        register(Identifier.of(FirstSteps.MOD_ID, "mannequin"), MannequinScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType.Factory<T> factory) {
		return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
	}

    public static void registerScreenHandlers() {
        FirstSteps.LOGGER.info("Registering ScreenHandlers for " + FirstSteps.MOD_ID);
    }
}
