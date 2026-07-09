package nz.duncy.first_steps.world.item.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record KilnRecipeDisplay(SlotDisplay ingredient, SlotDisplay fuel, SlotDisplay result, SlotDisplay craftingStation, int duration, float experience) implements RecipeDisplay {
    public static final MapCodec<KilnRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				SlotDisplay.CODEC.fieldOf("ingredient").forGetter(KilnRecipeDisplay::ingredient),
				SlotDisplay.CODEC.fieldOf("fuel").forGetter(KilnRecipeDisplay::fuel),
				SlotDisplay.CODEC.fieldOf("result").forGetter(KilnRecipeDisplay::result),
				SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(KilnRecipeDisplay::craftingStation),
				Codec.INT.fieldOf("duration").forGetter(KilnRecipeDisplay::duration),
				Codec.FLOAT.fieldOf("experience").forGetter(KilnRecipeDisplay::experience)
			)
			.apply(instance, KilnRecipeDisplay::new)
	);
    public static final StreamCodec<RegistryFriendlyByteBuf, KilnRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
		SlotDisplay.STREAM_CODEC,
		KilnRecipeDisplay::ingredient,
		SlotDisplay.STREAM_CODEC,
		KilnRecipeDisplay::fuel,
		SlotDisplay.STREAM_CODEC,
		KilnRecipeDisplay::result,
		SlotDisplay.STREAM_CODEC,
		KilnRecipeDisplay::craftingStation,
		ByteBufCodecs.VAR_INT,
		KilnRecipeDisplay::duration,
		ByteBufCodecs.FLOAT,
		KilnRecipeDisplay::experience,
		KilnRecipeDisplay::new
	);
    public static final RecipeDisplay.Type<KilnRecipeDisplay> TYPE = new RecipeDisplay.Type<KilnRecipeDisplay>(MAP_CODEC, STREAM_CODEC);

    public RecipeDisplay.Type<KilnRecipeDisplay> type() {
        return TYPE;
    }

    @Override
	public boolean isEnabled(FeatureFlagSet featureFlagSet) {
		return this.ingredient.isEnabled(featureFlagSet) && this.fuel().isEnabled(featureFlagSet) && RecipeDisplay.super.isEnabled(featureFlagSet);
	}
}