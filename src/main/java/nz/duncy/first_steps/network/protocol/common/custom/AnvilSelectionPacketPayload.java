package nz.duncy.first_steps.network.protocol.common.custom;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;

public record AnvilSelectionPacketPayload(int selection) implements CustomPacketPayload {
    public static final Identifier ANVIL_SELECTION_PAYLOAD_ID = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "anvil_selection");
	public static final CustomPacketPayload.Type<AnvilSelectionPacketPayload> TYPE = new CustomPacketPayload.Type<>(ANVIL_SELECTION_PAYLOAD_ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, AnvilSelectionPacketPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, AnvilSelectionPacketPayload::selection, AnvilSelectionPacketPayload::new);

    @Override
    public Type<AnvilSelectionPacketPayload> type() {
        return TYPE;
    }
    
}
