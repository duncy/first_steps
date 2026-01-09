package nz.duncy.first_steps.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import nz.duncy.first_steps.FirstSteps;

public class ModBlockStateProperties {
        public static final IntegerProperty ROCKS = IntegerProperty.create("rocks", 1, 4);

        public static void initialize() {
            FirstSteps.LOGGER.info("Registering mod block state properties for " + FirstSteps.MOD_ID);
        }
    
}
