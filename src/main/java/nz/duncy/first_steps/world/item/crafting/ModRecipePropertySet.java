package nz.duncy.first_steps.world.item.crafting;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipePropertySet;
import nz.duncy.first_steps.FirstSteps;

public class ModRecipePropertySet {
    public static final ResourceKey<RecipePropertySet> KILN = register("kiln");

    private static ResourceKey<RecipePropertySet> register(String string) {
        return ResourceKey.create(RecipePropertySet.TYPE_KEY, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string));
    }
}
