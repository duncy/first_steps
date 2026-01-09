package nz.duncy.first_steps.network.cache;

import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;

public final class ClientKnappingState {
    public static SingleInputSet<StonecutterRecipe> pendingRecipes = SingleInputSet.empty();
}
