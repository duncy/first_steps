package nz.duncy.first_steps.world.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;

public class KilnRecipe extends SingleItemRecipe {

    public KilnRecipe(Ingredient ingredient, ItemStack itemStack) {
        super("kiln", ingredient, itemStack);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipeBookCategories.KILN;
    }

    @Override
    public RecipeSerializer<KilnRecipe> getSerializer() {
        return ModRecipeSerializer.KILN;
    }

    @Override
    public RecipeType<KilnRecipe> getType() {
        return ModRecipeType.KILN_RECIPE;
    }
    
    public static class Serializer implements RecipeSerializer<KilnRecipe> {
        private final MapCodec<KilnRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.input()), 
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result())
        ).apply(instance, KilnRecipe::new));

        private final StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, 
            r -> r.input(), 
            ItemStack.STREAM_CODEC,
            r -> r.result(),
            KilnRecipe::new
        );

        @Override
        public MapCodec<KilnRecipe> codec() {
            return CODEC;
        }
        
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KilnRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
