package nz.duncy.first_steps.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record CrucibleRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    public static final MapCodec<CrucibleRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(SlotDisplay.CODEC.fieldOf("input").forGetter(CrucibleRecipeDisplay::input), SlotDisplay.CODEC.fieldOf("result").forGetter(CrucibleRecipeDisplay::result), SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(CrucibleRecipeDisplay::craftingStation)).apply(instance, CrucibleRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipeDisplay> STREAM_CODEC = StreamCodec.composite(SlotDisplay.STREAM_CODEC, CrucibleRecipeDisplay::input, SlotDisplay.STREAM_CODEC, CrucibleRecipeDisplay::result, SlotDisplay.STREAM_CODEC, CrucibleRecipeDisplay::craftingStation, CrucibleRecipeDisplay::new);
    public static final RecipeDisplay.Type<CrucibleRecipeDisplay> TYPE = new RecipeDisplay.Type<CrucibleRecipeDisplay>(MAP_CODEC, STREAM_CODEC);

    public CrucibleRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) {
        this.input = input;
        this.result = result;
        this.craftingStation = craftingStation;
    }

    public RecipeDisplay.Type<CrucibleRecipeDisplay> type() {
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