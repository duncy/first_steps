package nz.duncy.first_steps.recipe.book;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

public enum KnappingRecipeCategory implements StringIdentifiable {
	TOOL_HEAD(0, "tool_head");

	private static final IntFunction<KnappingRecipeCategory> BY_ID = ValueLists.createIdToValueFunction(
		(KnappingRecipeCategory category) -> category.id, values(), ValueLists.OutOfBoundsHandling.ZERO
	);
	public static final Codec<KnappingRecipeCategory> CODEC = StringIdentifiable.createCodec(KnappingRecipeCategory::values);
	public static final PacketCodec<ByteBuf, KnappingRecipeCategory> PACKET_CODEC = PacketCodecs.indexed(BY_ID, category -> category.id);
	private final int id;
	private final String name;

	private KnappingRecipeCategory(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}
}
