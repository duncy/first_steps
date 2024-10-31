package nz.duncy.first_steps.recipe;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import nz.duncy.first_steps.FirstSteps;

public class KilningRecipe implements Recipe<Inventory> {
   private final ItemStack result;
   private final Ingredient ingredient;

   public KilningRecipe(List<Ingredient> ingredients, ItemStack result) {
      this.ingredient = ingredients.get(0);
      this.result = result;
   }

   @Override
   public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
      return this.result.copy();
   }

   @Override
   public boolean fits(int width, int height) {
      return true;
   }

   @Override
   public ItemStack getResult(DynamicRegistryManager registryManager) {
      return this.result.copy();
   }

   public DefaultedList<Ingredient> getIngredients() {
      DefaultedList<Ingredient> list = DefaultedList.ofSize(1);
      list.add(this.ingredient);
      return list;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return Serializer.INSTANCE;
   }

   @Override
   public RecipeType<?> getType() {
      return Type.INSTANCE;
   }

   @Override
   public boolean matches(Inventory inventory, World world) {
      if (world.isClient()) {
         return false;
      }

      FirstSteps.LOGGER.info(String.valueOf(inventory));
      return this.ingredient.test(inventory.getStack(0));
   }

   public static class Type implements RecipeType<KilningRecipe> {
      public static final Type INSTANCE = new Type();
      public static final String ID = "kilning";
   }

   public static class Serializer implements RecipeSerializer<KilningRecipe> {
      public static final Serializer INSTANCE = new Serializer();
      public static final String ID = "kilning";

      public static final Codec<KilningRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
         validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 1).fieldOf("ingredient").forGetter(KilningRecipe::getIngredients),
         ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter(r -> r.result)
      ).apply(instance, KilningRecipe::new));

      private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
         return Codecs.validate(Codecs.validate(
            delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
         ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredient!") : DataResult.success(list));
      }

      @Override
      public Codec<KilningRecipe> codec() {
         return CODEC;
      }

      @Override
      public KilningRecipe read(PacketByteBuf buf) {
         DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < ingredients.size(); i++) {
               ingredients.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack result = buf.readItemStack();
            return new KilningRecipe(ingredients, result);
      }

      @Override
      public void write(PacketByteBuf buf, KilningRecipe recipe) {
         buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getResult(null));
      }

   }

}

























// // public class KilningRecipe implements Recipe<Inventory> {
// //    protected final RecipeType<?> type;
// //    protected final CookingRecipeCategory category;
// //    protected final String group;
// //    protected final Ingredient ingredient;
// //    protected final ItemStack result;
// //    protected final float experience;
// //    protected final int cookingTime;

// //    public KilningRecipe(String group, CookingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
// //       this.type = ModRecipes.KILNING_TYPE;
// //       this.category = category;
// //       this.group = group;
// //       this.ingredient = ingredient;
// //       this.result = result;
// //       this.experience = experience;
// //       this.cookingTime = cookingTime;
// //    }

// //    public ItemStack createIcon() {
// //       return new ItemStack(ModBlocks.KILN);
// //    }

// //    public RecipeSerializer<?> getSerializer() {
// //       return RecipeSerializer.SMELTING;
// //    }

// //    public boolean matches(Inventory inventory, World world) {
// //       return this.ingredient.test(inventory.getStack(0));
// //    }

// //    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
// //       return this.result.copy();
// //    }

// //    public boolean fits(int width, int height) {
// //       return true;
// //    }

// //    public DefaultedList<Ingredient> getIngredients() {
// //       DefaultedList<Ingredient> defaultedList = DefaultedList.of();
// //       defaultedList.add(this.ingredient);
// //       return defaultedList;
// //    }

// //    public float getExperience() {
// //       return this.experience;
// //    }

// //    public ItemStack getResult(DynamicRegistryManager registryManager) {
// //       return this.result;
// //    }

// //    public String getGroup() {
// //       return this.group;
// //    }

// //    public int getCookingTime() {
// //       return this.cookingTime;
// //    }

// //    public RecipeType<?> getType() {
// //       return this.type;
// //    }

// //    public CookingRecipeCategory getCategory() {
// //       return this.category;
// //    }


// // }
