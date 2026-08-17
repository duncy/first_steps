package nz.duncy.first_steps.network.cache;

import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;

public final class ClientAnvilState {
    public static SingleInputSet<AnvilRecipe> pendingRecipes = SingleInputSet.empty();
}
