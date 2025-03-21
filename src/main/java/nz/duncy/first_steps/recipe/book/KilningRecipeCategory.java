package nz.duncy.first_steps.recipe.book;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

public enum KilningRecipeCategory implements StringIdentifiable {
	FUEL_WASTE(0, "fuel_waste"),
    ROASTING(1, "roasting"),
    MISC(2, "misc");

	private static final IntFunction<KilningRecipeCategory> BY_ID = ValueLists.createIdToValueFunction(
		(KilningRecipeCategory category) -> category.id, values(), ValueLists.OutOfBoundsHandling.ZERO
	);
	public static final Codec<KilningRecipeCategory> CODEC = StringIdentifiable.createCodec(KilningRecipeCategory::values);
	public static final PacketCodec<ByteBuf, KilningRecipeCategory> PACKET_CODEC = PacketCodecs.indexed(BY_ID, category -> category.id);
	private final int id;
	private final String name;

	private KilningRecipeCategory(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}
}
