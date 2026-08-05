package nz.duncy.first_steps.world.item.crafting;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class DurabilityLossShapelessRecipe extends ShapelessRecipe {
    final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final List<Ingredient> ingredients;

	public DurabilityLossShapelessRecipe(String group, CraftingBookCategory category, ItemStack result, List<Ingredient> ingredients) {
        super(group, category, result, ingredients);
		this.group = group;
		this.category = category;
		this.result = result;
		this.ingredients = ingredients;
	}
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput) {
		NonNullList<ItemStack> remainingItems = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        for (int i = 0; i < remainingItems.size(); i++) {

			ItemStack stack = craftingInput.getItem(i);

            if (stack.isDamageableItem()) {
                ItemStack damagedStack = stack.copy();
                int damage = stack.getDamageValue() + 1;

                if (damage < stack.getMaxDamage()) {
                    damagedStack.setDamageValue(damage);
                    remainingItems.set(i, damagedStack);
                    continue;
                }
            }
                Item item = stack.getItem();
                remainingItems.set(i, item.getCraftingRemainder());
            
		}

		return remainingItems;
	}

    public RecipeSerializer getSerializer() {
        return ModRecipeSerializer.DURABILITY_LOSS_SHAPELESS; 
    }

    public static class Serializer implements RecipeSerializer<DurabilityLossShapelessRecipe> {
		private static final MapCodec<DurabilityLossShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.STRING.optionalFieldOf("group", "").forGetter(shapelessRecipe -> shapelessRecipe.group),
					CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(shapelessRecipe -> shapelessRecipe.category),
					ItemStack.STRICT_CODEC.fieldOf("result").forGetter(shapelessRecipe -> shapelessRecipe.result),
					Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter(shapelessRecipe -> shapelessRecipe.ingredients)
				)
				.apply(instance, DurabilityLossShapelessRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, DurabilityLossShapelessRecipe> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.STRING_UTF8,
			shapelessRecipe -> shapelessRecipe.group,
			CraftingBookCategory.STREAM_CODEC,
			shapelessRecipe -> shapelessRecipe.category,
			ItemStack.STREAM_CODEC,
			shapelessRecipe -> shapelessRecipe.result,
			Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
			shapelessRecipe -> shapelessRecipe.ingredients,
			DurabilityLossShapelessRecipe::new
		);

		@Override
		public MapCodec<DurabilityLossShapelessRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, DurabilityLossShapelessRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
