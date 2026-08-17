package nz.duncy.first_steps.world.item.crafting;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class DurabilityLossShapelessRecipe implements CraftingRecipe {
    final String group;
	final CraftingBookCategory category;
	final ItemStack result;
	final List<Ingredient> ingredients;
    @Nullable
	private PlacementInfo placementInfo;

	public DurabilityLossShapelessRecipe(String group, CraftingBookCategory category, ItemStack result, List<Ingredient> ingredients) {
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

    public RecipeSerializer<DurabilityLossShapelessRecipe> getSerializer() {
        return ModRecipeSerializer.DURABILITY_LOSS_SHAPELESS; 
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        if (recipeInput.ingredientCount() != this.ingredients.size()) {
			return false;
		} else {
			return recipeInput.size() == 1 && this.ingredients.size() == 1
				? ((Ingredient)this.ingredients.getFirst()).test(recipeInput.getItem(0))
				: recipeInput.stackedContents().canCraft(this, null);
		}
    }

    @Override
    public ItemStack assemble(CraftingInput recipeInput, Provider provider) {
        return this.result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
			this.placementInfo = PlacementInfo.create(this.ingredients);
		}

		return this.placementInfo;
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
	public List<RecipeDisplay> display() {
		return List.of(
			new ShapelessCraftingRecipeDisplay(
				this.ingredients.stream().map(Ingredient::display).toList(),
				new SlotDisplay.ItemStackSlotDisplay(this.result),
				new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
			)
		);
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
