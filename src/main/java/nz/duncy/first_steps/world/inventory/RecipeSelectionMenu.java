package nz.duncy.first_steps.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;

public abstract class RecipeSelectionMenu<T extends SingleItemRecipe, M extends RecipeSelectionMenu<T, M>> extends AbstractContainerMenu {
    private final SingleInputSet<T> recipes;
    private final BlockPos blockPos;

    public RecipeSelectionMenu(MenuType<M> type, int i, Inventory inventory) {
        super(type, i);
        this.recipes = null;
        this.blockPos = null;
    }

    public RecipeSelectionMenu(MenuType<M> type, int i, Inventory inventory, SingleInputSet<T> recipes, BlockPos blockPos) {
        super(type, i);
        this.recipes = recipes;
        this.blockPos = blockPos;

        sendRecipes(inventory.player);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public SingleInputSet<T> getRecipes() {
        return this.recipes;
    }

    protected abstract void sendRecipes(Player player);

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}