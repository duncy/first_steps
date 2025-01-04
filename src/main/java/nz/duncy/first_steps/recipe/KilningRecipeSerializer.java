package nz.duncy.first_steps.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;

public class KilningRecipeSerializer implements RecipeSerializer<KilningRecipe> {
    private final KilningRecipe.RecipeFactory<KilningRecipe> recipeFactory;
    private final MapCodec<KilningRecipe> codec;
    private final PacketCodec<RegistryByteBuf, KilningRecipe> packetCodec;


   public KilningRecipeSerializer(KilningRecipe.RecipeFactory<KilningRecipe> recipeFactory, int processTime) {
      this.recipeFactory = recipeFactory;
      this.codec = RecordCodecBuilder.mapCodec(
         instance -> instance.group(
            Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
            ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
         ).apply(instance, recipeFactory::create)
      );
      this.packetCodec = PacketCodec.ofStatic(this::write, this::read);
   }

   @Override
   public MapCodec<KilningRecipe> codec() {
      return this.codec;
   }

   @Override
   public PacketCodec<RegistryByteBuf, KilningRecipe> packetCodec() {
      return this.packetCodec;
   }


   private KilningRecipe read(RegistryByteBuf buf) {
      Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
      ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
      return this.recipeFactory.create(ingredient, itemStack);
   }

   private void write(RegistryByteBuf buf, KilningRecipe recipe) {
      Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
      ItemStack.PACKET_CODEC.encode(buf, recipe.result);
   }

   public KilningRecipe create(Ingredient ingredient, ItemStack result) {
      return this.recipeFactory.create(ingredient, result);
   }
}