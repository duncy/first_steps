package nz.duncy.first_steps.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record PottersWheelRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<PottersWheelRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(SlotDisplay.CODEC.fieldOf("input").forGetter(PottersWheelRecipeDisplay::input), SlotDisplay.CODEC.fieldOf("result").forGetter(PottersWheelRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(PottersWheelRecipeDisplay::craftingStation)).apply(instance, PottersWheelRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PottersWheelRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, PottersWheelRecipeDisplay::input, SlotDisplay.STREAM_CODEC, PottersWheelRecipeDisplay::result, SlotDisplay.STREAM_CODEC, PottersWheelRecipeDisplay::craftingStation, PottersWheelRecipeDisplay::new);
    public static final RecipeDisplay.Type<PottersWheelRecipeDisplay> TYPE = new RecipeDisplay.Type<PottersWheelRecipeDisplay>(MAP_CODEC, STREAM_CODEC);

    public PottersWheelRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) {
        this.input = input;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public RecipeDisplay.Type<PottersWheelRecipeDisplay> type() {
        return TYPE;
    }

    public SlotDisplay input() {
        return this.input;
    }

    public SlotDisplay result() {
        return this.result;
    }

    public SlotDisplay craftingStation() {
        return this.craftingStation;
    }
}