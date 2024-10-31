package nz.duncy.first_steps.state;

import net.minecraft.state.property.IntProperty;

public class ModProperties {

    public static final IntProperty CLAY_LAYERS; 
    
    static {
        CLAY_LAYERS = IntProperty.of("clay_layers", 1, 4);
    }
}

