package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;

public record KnappingSelectionPacketPayload(int selection) implements CustomPacketPayload {
    public static final Identifier KNAPPING_SELECTION_PAYLOAD_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "knapping_selection");
	public static final CustomPacketPayload.Type<KnappingSelectionPacketPayload> TYPE = new CustomPacketPayload.Type<>(KNAPPING_SELECTION_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, KnappingSelectionPacketPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, KnappingSelectionPacketPayload::selection, KnappingSelectionPacketPayload::new);

    @Override
    public Type<KnappingSelectionPacketPayload> type() {
        return TYPE;
    }
    
}
