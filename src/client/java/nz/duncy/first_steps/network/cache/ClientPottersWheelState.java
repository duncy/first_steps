package nz.duncy.first_steps.network.cache;

import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;

public final class ClientPottersWheelState {
    public static SingleInputSet<PottersWheelRecipe> pendingRecipes = SingleInputSet.empty();
}
