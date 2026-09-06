package nz.duncy.first_steps.data.tags;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.ModItems;

public class ModItemTagProvider extends ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(@NonNull Provider wrapperLookup) {
        valueLookupBuilder(ModItemTags.IS_ROCK)
            .add(ModItems.STONE_ROCK)
            .add(Items.FLINT)
            .add(ModItems.BASALT_ROCK)
            .add(ModItems.OBSIDIAN_ROCK);

        valueLookupBuilder(ModItemTags.TINDER)
            .add(Items.DRY_SHORT_GRASS)
            .add(Items.DRY_TALL_GRASS)
            .add(Items.PAPER)
            .add(Items.WHEAT);

        valueLookupBuilder(ItemTags.AXES)
            .add(ModItems.FLINT_AXE)
            .add(ModItems.BASALT_AXE)
            .add(ModItems.OBSIDIAN_AXE);

        valueLookupBuilder(ItemTags.SHOVELS)
            .add(ModItems.FLINT_SHOVEL)
            .add(ModItems.BASALT_SHOVEL)
            .add(ModItems.OBSIDIAN_SHOVEL);

        valueLookupBuilder(ItemTags.HOES)
            .add(ModItems.FLINT_HOE)
            .add(ModItems.BASALT_HOE)
            .add(ModItems.OBSIDIAN_HOE);

        valueLookupBuilder(ItemTags.SPEARS)
            .add(ModItems.FLINT_SPEAR)
            .add(ModItems.BASALT_SPEAR)
            .add(ModItems.OBSIDIAN_SPEAR);

        valueLookupBuilder(ModItemTags.KNIVES)
            .add(ModItems.STONE_KNIFE)
            .add(ModItems.FLINT_KNIFE)
            .add(ModItems.BASALT_KNIFE)
            .add(ModItems.OBSIDIAN_KNIFE);

        valueLookupBuilder(ModItemTags.SMITHING_HAMMERS)
            .add(ModItems.COPPER_SMITHING_HAMMER)
            .add(ModItems.BRONZE_SMITHING_HAMMER)
            .add(ModItems.IRON_SMITHING_HAMMER);

        valueLookupBuilder(ItemTags.MELEE_WEAPON_ENCHANTABLE)
            .addTag(ModItemTags.KNIVES);

        valueLookupBuilder(ItemTags.BREAKS_DECORATED_POTS)
            .addTag(ModItemTags.KNIVES)
            .addTag(ModItemTags.SMITHING_HAMMERS);

        valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)
            .addTag(ModItemTags.KNIVES)
            .addTag(ModItemTags.SMITHING_HAMMERS);

        valueLookupBuilder(ModItemTags.NINE_INGOTS_EQUIVALENT)
            .add(Items.RAW_COPPER_BLOCK)
            .add(Items.RAW_IRON_BLOCK)
            .add(Items.RAW_GOLD_BLOCK)
            .add(ModItems.RAW_BRONZE_BLOCK)
            .add(ModItems.TIN_BLOCK)
            .add(ModItems.RAW_TIN_BLOCK)
            .add(ModItems.BRONZE_BLOCK)
            .add(Items.COPPER_BLOCK)
            .add(Items.IRON_BLOCK)
            .add(Items.GOLD_BLOCK)
            .add(Items.NETHERITE_BLOCK);

        valueLookupBuilder(ModItemTags.SMITHABLE_INGOTS)
            .add(Items.COPPER_INGOT)
            .add(Items.IRON_INGOT)
            .add(Items.GOLD_INGOT)
            .add(ModItems.BRONZE_INGOT)
            .add(ModItems.TIN_INGOT)
            .add(Items.NETHERITE_INGOT);

        valueLookupBuilder(ModItemTags.ONE_INGOT_EQUIVALENT)
            .add(Items.RAW_COPPER)
            .add(Items.RAW_IRON)
            .add(Items.RAW_GOLD)
            .add(ModItems.RAW_BRONZE)
            .add(ModItems.RAW_TIN)
            .addTag(ModItemTags.SMITHABLE_INGOTS);

        valueLookupBuilder(ModItemTags.ONE_NINTH_INGOT_EQUIVALENT)
            .add(ModItems.TIN_NUGGET)
            .add(Items.COPPER_NUGGET)
            .add(ModItems.BRONZE_NUGGET)
            .add(Items.IRON_NUGGET)
            .add(Items.GOLD_NUGGET);

        valueLookupBuilder(ModItemTags.TIN_SOURCE)
            .add(ModItems.TIN_NUGGET)
            .add(ModItems.TIN_INGOT)
            .add(ModItems.RAW_TIN)
            .add(ModItems.TIN_BLOCK)
            .add(ModItems.RAW_TIN_BLOCK);

        valueLookupBuilder(ModItemTags.COPPER_SOURCE)
            .add(Items.RAW_COPPER_BLOCK)
            .add(Items.COPPER_BLOCK)
            .add(Items.RAW_COPPER)
            .add(Items.COPPER_INGOT)
            .add(Items.COPPER_NUGGET);

        valueLookupBuilder(ModItemTags.BRONZE_SOURCE)
            .add(ModItems.BRONZE_INGOT)
            .add(ModItems.BRONZE_NUGGET)
            .add(ModItems.RAW_BRONZE_BLOCK)
            .add(ModItems.RAW_BRONZE)
            .add(ModItems.BRONZE_BLOCK);

        valueLookupBuilder(ModItemTags.IRON_SOURCE)
            .add(Items.RAW_IRON_BLOCK)
            .add(Items.IRON_BLOCK)
            .add(Items.RAW_IRON)
            .add(Items.IRON_INGOT)
            .add(Items.IRON_NUGGET);

        valueLookupBuilder(ModItemTags.GOLD_SOURCE)
            .add(Items.RAW_GOLD_BLOCK)
            .add(Items.GOLD_BLOCK)
            .add(Items.RAW_GOLD)
            .add(Items.GOLD_INGOT)
            .add(Items.GOLD_NUGGET);

        valueLookupBuilder(ModItemTags.CRUCIBLE_ACCEPTS)
            .addTag(ModItemTags.TIN_SOURCE)
            .addTag(ModItemTags.COPPER_SOURCE)
            .addTag(ModItemTags.BRONZE_SOURCE)
            .addTag(ModItemTags.IRON_SOURCE)
            .addTag(ModItemTags.GOLD_SOURCE);

        valueLookupBuilder(ModItemTags.WAX)
            .add(Items.HONEYCOMB);
            // .add(ModItems.TALLOW);

    }
}
