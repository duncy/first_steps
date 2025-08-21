package nz.duncy.first_steps.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SingleStackRecipe;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.World;
import nz.duncy.first_steps.recipe.book.KilningRecipeCategory;
import nz.duncy.first_steps.recipe.book.ModRecipeBookCategories;

public class KilningRecipe extends SingleStackRecipe  {
   private final KilningRecipeCategory category;
   protected final ItemStack result;
   protected final Ingredient ingredient;
   private final int cookingTime;
   private IngredientPlacement ingredientPlacement;

   public KilningRecipe(KilningRecipeCategory category, Ingredient ingredient, ItemStack result, int cookingTime) {
      super("kilning", ingredient, result);
      this.category = category;
      this.ingredient = ingredient;
      this.result = result;
      this.cookingTime = cookingTime;
   }

   @Override
	public RecipeBookCategory getRecipeBookCategory() {
		return switch (this.getCategory()) {
			case FUEL_WASTE -> ModRecipeBookCategories.KILNING_FUEL_WASTE;
			case ROASTING -> ModRecipeBookCategories.KILNING_ROASTING;
			case MISC -> ModRecipeBookCategories.KILNING_MISC;
		};
	}

   public KilningRecipeCategory getCategory() {
		return this.category;
	}

   @Override
   public RecipeSerializer<KilningRecipe> getSerializer() {
      return ModRecipes.KILNING_SERIALIZER;
   }

   @Override
   public RecipeType<KilningRecipe> getType() {
      return ModRecipes.KILNING_TYPE;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public Ingredient getIngredient() {
      return this.ingredient;
   }

   public int getCookingTime() {
      return this.cookingTime;
   }


   @Override
   public boolean matches(SingleStackRecipeInput input, World world) {
      if (world.isClient) return false;
      return this.ingredient.test(input.item());
   }

   @Override
   public ItemStack craft(SingleStackRecipeInput input, WrapperLookup registries) {
      return this.result.copy();
   }

   @Override
   public IngredientPlacement getIngredientPlacement() {
      if (this.ingredientPlacement == null) {
			this.ingredientPlacement = IngredientPlacement.forSingleSlot(this.ingredient);
		}

		return this.ingredientPlacement;
   }
   
   @FunctionalInterface
	public interface RecipeFactory<T extends KilningRecipe> {
		T create(KilningRecipeCategory category, Ingredient ingredient, ItemStack result, int cookingTime);
	}

   public static class Serializer implements RecipeSerializer<KilningRecipe> {
      private final MapCodec<KilningRecipe> codec = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                     KilningRecipeCategory.CODEC.fieldOf("category").orElse(KilningRecipeCategory.MISC).forGetter(KilningRecipe::getCategory),
                     Ingredient.CODEC.fieldOf("ingredient").forGetter(KilningRecipe::getIngredient),
                     ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(KilningRecipe::getResult),
                     // DEfault cooking time of 100, change if bad
                     Codec.INT.fieldOf("cookingtime").orElse(100).forGetter(KilningRecipe::getCookingTime)
                  )
                  .apply(instance, KilningRecipe::new)
      );

      private final PacketCodec<RegistryByteBuf, KilningRecipe> packetCodec = PacketCodec.tuple(
            KilningRecipeCategory.PACKET_CODEC,
            KilningRecipe::getCategory,
            Ingredient.PACKET_CODEC,
            KilningRecipe::getIngredient,
            ItemStack.PACKET_CODEC,
            KilningRecipe::getResult,
            PacketCodecs.INTEGER,
            KilningRecipe::getCookingTime,
            KilningRecipe::new
      );

      @Override
      public MapCodec<KilningRecipe> codec() {
         return this.codec;
      }

      @Override
      public PacketCodec<RegistryByteBuf, KilningRecipe> packetCodec() {
         return this.packetCodec;
      }
   }

}

