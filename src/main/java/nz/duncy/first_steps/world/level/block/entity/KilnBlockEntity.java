package nz.duncy.first_steps.world.level.block.entity;

import org.jspecify.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.world.inventory.KilnMenu;

public class KilnBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected NonNullList<ItemStack> items;
    protected final ContainerData data;
    private final Reference2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed;
    // private final RecipeManager.CachedCheck<SingleRecipeInput, KilnRecipe> quickCheck;
    private static final Component DEFAULT_NAME = Component.translatable("container.first_steps.kiln");
    // private int temperature;
    // private int maxTemperature;
    // private static final int minTemperature = 20;
    int litTimeRemaining;
    int litTotalTime;

    public KilnBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.KILN, blockPos, blockState);
        this.items = NonNullList.withSize(4, ItemStack.EMPTY);
        this.recipesUsed = new Reference2IntOpenHashMap<ResourceKey<Recipe<?>>>();
        // this.quickCheck = RecipeManager.createCheck(ModRecipeType.KILN_RECIPE);

        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {
                
            }

            @Override
            public int getCount() {
                return items.size();
            }
        };
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public void fillStackedContents(StackedItemContents stackedItemContents) {
        for (ItemStack itemStack : this.items) {
            stackedItemContents.accountStack(itemStack);
        }
    }

    @Override
    public @Nullable RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceKey<Recipe<?>> resourceKey = recipeHolder.id();
            this.recipesUsed.addTo(resourceKey, 1);
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        if (direction == Direction.DOWN && i == 1) {
            return itemStack.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new KilnMenu(i, inventory, this, this.data);
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        this.getItems().set(i, itemStack);
        itemStack.limitSize(this.getMaxStackSize(itemStack));
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

}
