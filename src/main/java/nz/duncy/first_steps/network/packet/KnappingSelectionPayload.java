package nz.duncy.first_steps.network.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public record KnappingSelectionPayload(int selection) implements CustomPayload {
    public static final Identifier KNAPPING_SELECTION_PAYLOAD_ID = Identifier.of(FirstSteps.MOD_ID, "knapping_selection");
	public static final CustomPayload.Id<KnappingSelectionPayload> ID = new CustomPayload.Id<>(KNAPPING_SELECTION_PAYLOAD_ID);
	public static final PacketCodec<RegistryByteBuf, KnappingSelectionPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, KnappingSelectionPayload::selection, KnappingSelectionPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
} 
