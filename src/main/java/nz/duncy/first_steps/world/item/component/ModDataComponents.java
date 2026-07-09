package nz.duncy.first_steps.world.item.component;

import java.util.function.UnaryOperator;

import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModDataComponents {
    public static final DataComponentType<CrucibleContainerContents> CRUCIBLE_CONTAINER_CONTENTS =  register("crucible_container_contents", 
        builder -> builder.persistent(CrucibleContainerContents.CODEC)
                        .networkSynchronized(CrucibleContainerContents.STREAM_CODEC)
                        .cacheEncoding()
    );

    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE, 
            Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), 
            unaryOperator.apply(DataComponentType.builder()).build()
        );
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering Data Component Types for " + FirstSteps.MOD_ID);
        ComponentTooltipAppenderRegistry.addBefore(DataComponents.CONTAINER, CRUCIBLE_CONTAINER_CONTENTS);
        
    }
}