package nz.duncy.first_steps.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record AnvilRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<AnvilRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(SlotDisplay.CODEC.fieldOf("input").forGetter(AnvilRecipeDisplay::input), SlotDisplay.CODEC.fieldOf("result").forGetter(AnvilRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(AnvilRecipeDisplay::craftingStation)).apply(instance, AnvilRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AnvilRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, AnvilRecipeDisplay::input, SlotDisplay.STREAM_CODEC, AnvilRecipeDisplay::result, SlotDisplay.STREAM_CODEC, AnvilRecipeDisplay::craftingStation, AnvilRecipeDisplay::new);
    public static final RecipeDisplay.Type<AnvilRecipeDisplay> TYPE = new RecipeDisplay.Type<AnvilRecipeDisplay>(MAP_CODEC, STREAM_CODEC);

    public AnvilRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) {
        this.input = input;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public RecipeDisplay.Type<AnvilRecipeDisplay> type() {
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