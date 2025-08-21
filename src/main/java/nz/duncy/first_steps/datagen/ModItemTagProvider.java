package nz.duncy.first_steps.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import nz.duncy.first_steps.item.ModItems;
import nz.duncy.first_steps.util.ModTags;

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
            .add(ModItems.BRONZE_ARROW);
            // .add(ModItems.IRON_ARROW);
            // .add(ModItems.STEEL_ARROW);

        getOrCreateTagBuilder(ModTags.Items.ORES)
            .add(ModItems.RAW_STONE_COPPER)
            .add(ModItems.RAW_STONE_IRON)
            .add(Items.RAW_GOLD)
            .add(ModItems.RAW_TIN)
            .add(ModItems.RAW_DEEPSLATE_COPPER)
            .add(ModItems.RAW_DEEPSLATE_IRON);
    }
    
}
