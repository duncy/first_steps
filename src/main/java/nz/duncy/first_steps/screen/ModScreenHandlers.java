package nz.duncy.first_steps.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import nz.duncy.first_steps.FirstSteps;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<KilnScreenHandler> KILN_SCREEN_HANDLER = 
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(FirstSteps.MOD_ID, "kiln"),
        new ExtendedScreenHandlerType<>(KilnScreenHandler::new));

    public static final ScreenHandlerType<CrucibleScreenHandler> CRUCIBLE_SCREEN_HANDLER = 
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(FirstSteps.MOD_ID, "crucible"),
        new ExtendedScreenHandlerType<>(CrucibleScreenHandler::new));
    
    public static void registerScreenHandlers() {
        FirstSteps.LOGGER.info("Registering ScreenHandlers for " + FirstSteps.MOD_ID);
    }
}
