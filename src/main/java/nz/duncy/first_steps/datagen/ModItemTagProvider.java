package nz.duncy.first_steps.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import nz.duncy.first_steps.item.ModItems;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.ARROWS)
            .add(ModItems.STONE_ARROW)
            .add(ModItems.FLINT_ARROW)
            .add(ModItems.BASALT_ARROW)
            .add(ModItems.OBSIDIAN_ARROW)
            .add(ModItems.COPPER_ARROW)
            .add(ModItems.BRONZE_ARROW)
            .add(ModItems.IRON_ARROW)
            .add(ModItems.STEEL_ARROW);
    }
}
