package nz.duncy.first_steps.world.item.crafting;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.item.crafting.display.CrucibleRecipeDisplay;

public class CrucibleRecipe extends SingleItemRecipe {
    public CrucibleRecipe(String string, Ingredient ingredient, ItemStack itemStack) {
        super(string, ingredient, itemStack);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.CRUCIBLE;
    }

    @Override
    public RecipeType<? extends SingleItemRecipe> getType() {
        return ModRecipeType.CRUCIBLE_RECIPE;
    }

    @Override
    public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
        return ModRecipeSerializer.CRUCIBLE;
    }

    public List<RecipeDisplay> display() {
        return List.of(new CrucibleRecipeDisplay(this.input().display(), this.resultDisplay(), new SlotDisplay.ItemSlotDisplay(ModItems.CRUCIBLE)));
    }

    public SlotDisplay resultDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.result());
    }

    public static class Serializer implements RecipeSerializer<CrucibleRecipe> {
        private final MapCodec<CrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group()),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.input()), 
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result())
        ).apply(instance, CrucibleRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            r -> r.group(),
            Ingredient.CONTENTS_STREAM_CODEC, 
            r -> r.input(), 
            ItemStack.STREAM_CODEC,
            r -> r.result(),
            CrucibleRecipe::new
        );

        @Override
        public MapCodec<CrucibleRecipe> codec() {
            return CODEC;
        }
        
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}