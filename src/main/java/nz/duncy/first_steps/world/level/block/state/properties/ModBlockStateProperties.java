package nz.duncy.first_steps.world.level.block.state.properties;

import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.metallurgy.Metal;

public class ModBlockStateProperties {
        public static final IntegerProperty ROCKS = IntegerProperty.create("rocks", 1, 4);
        // TODO: REMOVE FIRING PROCESS, IT NEEDS TO BE IN BLOCK ENTITY FOR PERFORMANCE REASONS
        public static final IntegerProperty FIRING_PROGRESS = IntegerProperty.create("firing_progress", 0, 1024);
        public static final EnumProperty<Metal> METAL = EnumProperty.create("metal", Metal.class);
        public static final IntegerProperty PILE_SIZE = IntegerProperty.create("pile_size", 1, 12);

        public static void initialize() {
            FirstSteps.LOGGER.info("Registering mod block state properties for " + FirstSteps.MOD_ID);
        }
    
}
