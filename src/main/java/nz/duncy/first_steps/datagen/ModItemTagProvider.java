package nz.duncy.first_steps.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import nz.duncy.first_steps.item.custom.ModItems;
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

        getOrCreateTagBuilder(ModTags.Items.TINDER)
            .add(Items.SHORT_GRASS)
            .add(Items.FERN)
            .add(Items.WHEAT)
            .add(Items.PAPER);

        getOrCreateTagBuilder(ModTags.Items.BASE_LAYER_ARMOR)
            .add(ModItems.GAMBESON)
            .add(ModItems.LEATHER_JERKIN);

        getOrCreateTagBuilder(ModTags.Items.TOP_LAYER_ARMOR)
            .add(ModItems.LEATHER_LAMELLAR)
            .add(ModItems.COPPER_LAMELLAR)
            .add(ModItems.BRONZE_LAMELLAR)
            .add(ModItems.IRON_LAMELLAR)
            .add(ModItems.COPPER_BRIGANDINE)
            .add(ModItems.BRONZE_BRIGANDINE)
            .add(ModItems.IRON_BRIGANDINE)
            .add(ModItems.COPPER_LAMINAR)
            .add(ModItems.BRONZE_LAMINAR)
            .add(ModItems.IRON_LAMINAR)
            .add(ModItems.IRON_PLATE_HARNESS);

        getOrCreateTagBuilder(ModTags.Items.SHOULDER_SUPPORTING_ARMOR)
            .add(ModItems.COPPER_LAMINAR)
            .add(ModItems.BRONZE_LAMINAR)
            .add(ModItems.IRON_LAMINAR)
            .add(ModItems.IRON_PLATE_HARNESS);

        getOrCreateTagBuilder(ModTags.Items.HAND_SUPPORTING_ARMOR)
            .add(ModItems.COPPER_BRIGANDINE)
            .add(ModItems.BRONZE_BRIGANDINE)
            .add(ModItems.IRON_BRIGANDINE)
            .add(ModItems.IRON_PLATE_HARNESS);

        getOrCreateTagBuilder(ModTags.Items.SHOULDER_ARMOR)
            .add(ModItems.IRON_PAULDRON);

        getOrCreateTagBuilder(ModTags.Items.HAND_ARMOR)
            .add(ModItems.IRON_GAUNTLET);
    }    
}
