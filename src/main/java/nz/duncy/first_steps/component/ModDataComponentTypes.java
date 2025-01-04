package nz.duncy.first_steps.component;

import java.util.function.UnaryOperator;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import nz.duncy.first_steps.FirstSteps;

public class ModDataComponentTypes {
    public static final ComponentType<Integer> TEMPERATURE = register("temperature", builder -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		    return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(FirstSteps.MOD_ID, id),
            builderOperator.apply(ComponentType.builder()).build());
	}

    public static void registerDataComponentTypes() {
        FirstSteps.LOGGER.info("Registering Data Component Types for " + FirstSteps.MOD_ID);;
    }
}
