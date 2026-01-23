package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;

public record PottersWheelSelectionPacketPayload(int selection) implements CustomPacketPayload {
    public static final Identifier POTTERS_WHEEL_SELECTION_PAYLOAD_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "potters_wheel_selection");
	public static final CustomPacketPayload.Type<PottersWheelSelectionPacketPayload> TYPE = new CustomPacketPayload.Type<>(POTTERS_WHEEL_SELECTION_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, PottersWheelSelectionPacketPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, PottersWheelSelectionPacketPayload::selection, PottersWheelSelectionPacketPayload::new);

    @Override
    public Type<PottersWheelSelectionPacketPayload> type() {
        return TYPE;
    }
    
}
