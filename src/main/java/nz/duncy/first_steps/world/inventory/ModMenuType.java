package nz.duncy.first_steps.world.inventory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import nz.duncy.first_steps.FirstSteps;

public class ModMenuType {
    public static final MenuType<KnappingMenu> KNAPPING_SELECTION_MENU = 
        register("knapping_selection", KnappingMenu::new);

    public static final MenuType<PottersWheelMenu> POTTERS_WHEEL_SELECTION_MENU = 
        register("potters_wheel_selection", PottersWheelMenu::new);

    public static final MenuType<CrucibleMenu> CRUCIBLE_MENU = 
        register("crucible", CrucibleMenu::new);

    public static final MenuType<KilnMenu> KILN_MENU = 
        register("kiln", KilnMenu::new);

    public static final MenuType<AnvilMenu> ANVIL_SELECTION_MENU = 
        register("anvil_selection", AnvilMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(String string, MenuSupplier<T> menuSupplier) {
        return Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), new MenuType<T>(menuSupplier, FeatureFlags.VANILLA_SET));
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering menu types for " + FirstSteps.MOD_ID);
    }
}
