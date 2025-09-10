package nz.duncy.first_steps.item.custom;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup FIRST_STEPS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FirstSteps.MOD_ID, "clay_fired_crucible"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.first_steps"))
                .icon(() -> new ItemStack(ModBlocks.FIRED_CRUCIBLE)).entries((displayContext, entries) -> {

                    // Tools heads
                    entries.add(ModItems.STONE_HEAD_HOE);
                    entries.add(ModItems.FLINT_HEAD_HOE);
                    entries.add(ModItems.BASALT_HEAD_HOE);
                    entries.add(ModItems.OBSIDIAN_HEAD_HOE);
                    entries.add(ModItems.COPPER_HEAD_HOE);
                    entries.add(ModItems.BRONZE_HEAD_HOE);
                    entries.add(ModItems.IRON_HEAD_HOE);
                    // entries.add(ModItems.STEEL_HEAD_HOE);

                    entries.add(ModItems.STONE_HEAD_AXE);
                    entries.add(ModItems.FLINT_HEAD_AXE);
                    entries.add(ModItems.BASALT_HEAD_AXE);
                    entries.add(ModItems.OBSIDIAN_HEAD_AXE);
                    entries.add(ModItems.COPPER_HEAD_AXE);
                    entries.add(ModItems.BRONZE_HEAD_AXE);
                    entries.add(ModItems.IRON_HEAD_AXE);
                    // entries.add(ModItems.STEEL_HEAD_AXE);

                    entries.add(ModItems.STONE_HEAD_SHOVEL);
                    entries.add(ModItems.FLINT_HEAD_SHOVEL);
                    entries.add(ModItems.BASALT_HEAD_SHOVEL);
                    entries.add(ModItems.OBSIDIAN_HEAD_SHOVEL);
                    entries.add(ModItems.COPPER_HEAD_SHOVEL);
                    entries.add(ModItems.BRONZE_HEAD_SHOVEL);
                    entries.add(ModItems.IRON_HEAD_SHOVEL);
                    // entries.add(ModItems.STEEL_HEAD_SHOVEL);

                    entries.add(ModItems.STONE_HEAD_KNIFE);
                    entries.add(ModItems.FLINT_HEAD_KNIFE);
                    entries.add(ModItems.BASALT_HEAD_KNIFE);
                    entries.add(ModItems.OBSIDIAN_HEAD_KNIFE);
                    entries.add(ModItems.COPPER_HEAD_KNIFE);
                    entries.add(ModItems.BRONZE_HEAD_KNIFE);
                    entries.add(ModItems.IRON_HEAD_KNIFE);
                    // entries.add(ModItems.STEEL_HEAD_KNIFE);

                    entries.add(ModItems.STONE_HEAD_SPEAR);
                    entries.add(ModItems.FLINT_HEAD_SPEAR);
                    entries.add(ModItems.BASALT_HEAD_SPEAR);
                    entries.add(ModItems.OBSIDIAN_HEAD_SPEAR);
                    entries.add(ModItems.COPPER_HEAD_SPEAR);
                    entries.add(ModItems.BRONZE_HEAD_SPEAR);
                    entries.add(ModItems.IRON_HEAD_SPEAR);
                    // entries.add(ModItems.STEEL_HEAD_SPEAR);

                    entries.add(ModItems.STONE_HEAD_ARROW);
                    entries.add(ModItems.FLINT_HEAD_ARROW);
                    entries.add(ModItems.BASALT_HEAD_ARROW);
                    entries.add(ModItems.OBSIDIAN_HEAD_ARROW);
                    entries.add(ModItems.COPPER_HEAD_ARROW);
                    entries.add(ModItems.BRONZE_HEAD_ARROW);
                    entries.add(ModItems.IRON_HEAD_ARROW);
                    // entries.add(ModItems.STEEL_HEAD_ARROW);

                    entries.add(ModItems.COPPER_HEAD_SWORD);
                    entries.add(ModItems.BRONZE_HEAD_SWORD);
                    entries.add(ModItems.IRON_HEAD_SWORD);
                    // entries.add(ModItems.STEEL_HEAD_SWORD);

                    entries.add(ModItems.COPPER_HEAD_PICKAXE);
                    entries.add(ModItems.BRONZE_HEAD_PICKAXE);
                    entries.add(ModItems.IRON_HEAD_PICKAXE);
                    // entries.add(ModItems.STEEL_HEAD_PICKAXE);

                    // Raw metals
                    entries.add(ModItems.RAW_TIN);
                    entries.add(ModItems.RAW_STONE_COPPER);
                    entries.add(ModItems.RAW_STONE_IRON);
                    entries.add(ModItems.RAW_DEEPSLATE_COPPER);
                    entries.add(ModItems.RAW_DEEPSLATE_IRON);
                    // entries.add(ModItems.RAW_MULLITE);

                    // Ore blocks
                    entries.add(ModBlocks.STONE_TIN_ORE);
                    entries.add(ModBlocks.STONE_COPPER_ORE);
                    entries.add(ModBlocks.STONE_IRON_ORE);
                    entries.add(ModBlocks.DEEPSLATE_COPPER_ORE);
                    entries.add(ModBlocks.DEEPSLATE_IRON_ORE);
                    // entries.add(ModBlocks.BASALT_MULLITE_ORE);

                    // Raw metal blocks
                    entries.add(ModBlocks.RAW_TIN);
                    entries.add(ModBlocks.RAW_STONE_COPPER);
                    entries.add(ModBlocks.RAW_STONE_IRON);
                    entries.add(ModBlocks.RAW_DEEPSLATE_COPPER);
                    entries.add(ModBlocks.RAW_DEEPSLATE_IRON);

                    // Rock blocks
                    entries.add(ModBlocks.STONE_ROCK);
                    // entries.add(ModBlocks.FLINT_ROCK);
                    entries.add(ModBlocks.BASALT_ROCK);
                    entries.add(ModBlocks.OBSIDIAN_ROCK);

                    // Kiln
                    entries.add(ModBlocks.KILN);

                    // Pottery
                    entries.add(ModBlocks.POTTERS_WHEEL);
                    // Fired
                    entries.add(ModBlocks.FIRED_CRUCIBLE);

                    // Unfired
                    entries.add(ModBlocks.UNFIRED_CRUCIBLE);
                    entries.add(ModBlocks.UNFIRED_FLOWER_POT);
                    entries.add(ModBlocks.UNFIRED_DECORATED_POT);

                    // Clay
                    // entries.add(ModBlocks.CLAY);
                    
                    // Tools
                    // Stone tools
                    entries.add(ModItems.STONE_KNIFE);
                    entries.add(ModItems.STONE_SPEAR);
                    
                    // Flint tools
                    entries.add(ModItems.FLINT_HOE);
                    entries.add(ModItems.FLINT_SHOVEL);
                    entries.add(ModItems.FLINT_AXE);
                    entries.add(ModItems.FLINT_KNIFE);
                    entries.add(ModItems.FLINT_SPEAR);

                    // Basalt tools
                    entries.add(ModItems.BASALT_HOE);
                    entries.add(ModItems.BASALT_SHOVEL);
                    entries.add(ModItems.BASALT_AXE);
                    entries.add(ModItems.BASALT_KNIFE);
                    entries.add(ModItems.BASALT_SPEAR);

                    // Obsidian tools
                    entries.add(ModItems.OBSIDIAN_HOE);
                    entries.add(ModItems.OBSIDIAN_SHOVEL);
                    entries.add(ModItems.OBSIDIAN_AXE);
                    entries.add(ModItems.OBSIDIAN_KNIFE);
                    entries.add(ModItems.OBSIDIAN_SPEAR);

                    // Copper tools
                    entries.add(ModItems.COPPER_HOE);
                    entries.add(ModItems.COPPER_SHOVEL);
                    entries.add(ModItems.COPPER_AXE);
                    entries.add(ModItems.COPPER_KNIFE);
                    entries.add(ModItems.COPPER_SPEAR);
                    entries.add(ModItems.COPPER_PICKAXE);
                    entries.add(ModItems.COPPER_SWORD);

                    // Bronze tools
                    entries.add(ModItems.BRONZE_HOE);
                    entries.add(ModItems.BRONZE_SHOVEL);
                    entries.add(ModItems.BRONZE_AXE);
                    entries.add(ModItems.BRONZE_KNIFE);
                    entries.add(ModItems.BRONZE_SPEAR);
                    entries.add(ModItems.BRONZE_PICKAXE);
                    entries.add(ModItems.BRONZE_SWORD);

                    // Iron tools
                    entries.add(ModItems.IRON_KNIFE);
                    entries.add(ModItems.IRON_SPEAR);

                    // Steel tools
                    // entries.add(ModItems.STEEL_HOE);
                    // entries.add(ModItems.STEEL_SHOVEL);
                    // entries.add(ModItems.STEEL_AXE);
                    // entries.add(ModItems.STEEL_KNIFE);
                    // entries.add(ModItems.STEEL_SPEAR);
                    // entries.add(ModItems.STEEL_PICKAXE);
                    // entries.add(ModItems.STEEL_SWORD);

                    // Ingots
                    entries.add(ModItems.TIN_INGOT);
                    entries.add(ModItems.BRONZE_INGOT);
                    // entries.add(ModItems.STEEL_INGOT);

                    // Arrows
                    entries.add(ModItems.STONE_ARROW);
                    entries.add(ModItems.FLINT_ARROW);
                    entries.add(ModItems.BASALT_ARROW);
                    entries.add(ModItems.OBSIDIAN_ARROW);
                    entries.add(ModItems.COPPER_ARROW);
                    entries.add(ModItems.BRONZE_ARROW);
                    // entries.add(ModItems.IRON_ARROW);
                    // entries.add(ModItems.STEEL_ARROW);

                    // Tongs
                    entries.add(ModItems.WOODEN_TONGS);

                    // Molds
                    entries.add(ModBlocks.HOE_HEAD_MOLD);
                    entries.add(ModBlocks.AXE_HEAD_MOLD);
                    entries.add(ModBlocks.SHOVEL_HEAD_MOLD);
                    entries.add(ModBlocks.KNIFE_HEAD_MOLD);
                    entries.add(ModBlocks.SPEAR_HEAD_MOLD);
                    entries.add(ModBlocks.ARROW_HEAD_MOLD);
                    entries.add(ModBlocks.PICKAXE_HEAD_MOLD);
                    entries.add(ModBlocks.SWORD_HEAD_MOLD);

                    // Crops
                    entries.add(ModItems.FLAX_SEEDS);
                    entries.add(ModItems.COTTON_SEEDS);
                    entries.add(ModItems.FLAX);
                    entries.add(ModItems.COTTON);

                    entries.add(ModItems.ARMORERS_MANNEQUIN);

                    entries.add(ModBlocks.FORGE);

                    entries.add(ModItems.HAMMER);

                    entries.add(ModItems.GAMBESON);
                    entries.add(ModItems.LEATHER_JERKIN);

                    entries.add(ModItems.LEATHER_LAMELLAR);
                    entries.add(ModItems.COPPER_LAMELLAR);
                    entries.add(ModItems.BRONZE_LAMELLAR);
                    entries.add(ModItems.IRON_LAMELLAR);

                    entries.add(ModItems.COPPER_BRIGANDINE);
                    entries.add(ModItems.BRONZE_BRIGANDINE);
                    entries.add(ModItems.IRON_BRIGANDINE);

                    entries.add(ModItems.COPPER_LAMINAR);
                    entries.add(ModItems.BRONZE_LAMINAR);
                    entries.add(ModItems.IRON_LAMINAR);

                    entries.add(ModItems.IRON_PLATE_HARNESS);

                    entries.add(ModItems.CLOTH);

                    entries.add(ModItems.PADDED_CLOTH);
                    entries.add(ModItems.LEATHER_STRIP);

                    entries.add(ModItems.COPPER_RINGS);
                    entries.add(ModItems.BRONZE_RINGS);
                    entries.add(ModItems.IRON_RINGS);

                    entries.add(ModItems.COPPER_BANDS);
                    entries.add(ModItems.BRONZE_BANDS);
                    entries.add(ModItems.IRON_BANDS);

                    entries.add(ModItems.TIN_NUGGET);
                    entries.add(ModItems.BRONZE_NUGGET);

                    entries.add(ModItems.COPPER_HAUBERK);
                    entries.add(ModItems.BRONZE_HAUBERK);
                    entries.add(ModItems.IRON_HAUBERK);

                    entries.add(ModItems.IRON_PAULDRON);
                    entries.add(ModItems.IRON_GAUNTLET);

    }).build());

        private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModItems.STONE_HEAD_HOE);
        entries.add(ModItems.FLINT_HEAD_HOE);
        entries.add(ModItems.BASALT_HEAD_HOE);
        entries.add(ModItems.OBSIDIAN_HEAD_HOE);
        entries.add(ModItems.COPPER_HEAD_HOE);
        entries.add(ModItems.BRONZE_HEAD_HOE);
        entries.add(ModItems.IRON_HEAD_HOE);
        // entries.add(ModItems.STEEL_HEAD_HOE);

        entries.add(ModItems.STONE_HEAD_AXE);
        entries.add(ModItems.FLINT_HEAD_AXE);
        entries.add(ModItems.BASALT_HEAD_AXE);
        entries.add(ModItems.OBSIDIAN_HEAD_AXE);
        entries.add(ModItems.COPPER_HEAD_AXE);
        entries.add(ModItems.BRONZE_HEAD_AXE);
        entries.add(ModItems.IRON_HEAD_AXE);
        // entries.add(ModItems.STEEL_HEAD_AXE);

        entries.add(ModItems.STONE_HEAD_SHOVEL);
        entries.add(ModItems.FLINT_HEAD_SHOVEL);
        entries.add(ModItems.BASALT_HEAD_SHOVEL);
        entries.add(ModItems.OBSIDIAN_HEAD_SHOVEL);
        entries.add(ModItems.COPPER_HEAD_SHOVEL);
        entries.add(ModItems.BRONZE_HEAD_SHOVEL);
        entries.add(ModItems.IRON_HEAD_SHOVEL);
        // entries.add(ModItems.STEEL_HEAD_SHOVEL);

        entries.add(ModItems.STONE_HEAD_KNIFE);
        entries.add(ModItems.FLINT_HEAD_KNIFE);
        entries.add(ModItems.BASALT_HEAD_KNIFE);
        entries.add(ModItems.OBSIDIAN_HEAD_KNIFE);
        entries.add(ModItems.COPPER_HEAD_KNIFE);
        entries.add(ModItems.BRONZE_HEAD_KNIFE);
        entries.add(ModItems.IRON_HEAD_KNIFE);
        // entries.add(ModItems.STEEL_HEAD_KNIFE);
        
        entries.add(ModItems.STONE_HEAD_SPEAR);
        entries.add(ModItems.FLINT_HEAD_SPEAR);
        entries.add(ModItems.BASALT_HEAD_SPEAR);
        entries.add(ModItems.OBSIDIAN_HEAD_SPEAR);
        entries.add(ModItems.COPPER_HEAD_SPEAR);
        entries.add(ModItems.BRONZE_HEAD_SPEAR);
        entries.add(ModItems.IRON_HEAD_SPEAR);
        // entries.add(ModItems.STEEL_HEAD_SPEAR);

        entries.add(ModItems.STONE_HEAD_ARROW);
        entries.add(ModItems.FLINT_HEAD_ARROW);
        entries.add(ModItems.BASALT_HEAD_ARROW);
        entries.add(ModItems.OBSIDIAN_HEAD_ARROW);
        entries.add(ModItems.COPPER_HEAD_ARROW);
        entries.add(ModItems.BRONZE_HEAD_ARROW);
        entries.add(ModItems.IRON_HEAD_ARROW);
        // entries.add(ModItems.STEEL_HEAD_ARROW);

        entries.add(ModItems.COPPER_HEAD_SWORD);
        entries.add(ModItems.BRONZE_HEAD_SWORD);
        entries.add(ModItems.IRON_HEAD_SWORD);
        // entries.add(ModItems.STEEL_HEAD_SWORD);

        entries.add(ModItems.COPPER_HEAD_PICKAXE);
        entries.add(ModItems.BRONZE_HEAD_PICKAXE);
        entries.add(ModItems.IRON_HEAD_PICKAXE);
        // entries.add(ModItems.STEEL_HEAD_PICKAXE);

        entries.add(ModItems.TIN_INGOT);
        entries.add(ModItems.BRONZE_INGOT);
        // entries.add(ModItems.STEEL_INGOT);

        entries.add(ModItems.CLOTH);

        entries.add(ModItems.PADDED_CLOTH);
        entries.add(ModItems.LEATHER_STRIP);

        entries.add(ModItems.COPPER_RINGS);
        entries.add(ModItems.BRONZE_RINGS);
        entries.add(ModItems.IRON_RINGS);

        entries.add(ModItems.COPPER_BANDS);
        entries.add(ModItems.BRONZE_BANDS);
        entries.add(ModItems.IRON_BANDS);

        entries.add(ModItems.FLAX);
        entries.add(ModItems.COTTON);

        entries.add(ModItems.RAW_STONE_COPPER);
        entries.add(ModItems.RAW_TIN);
        entries.add(ModItems.RAW_STONE_IRON);
        entries.add(ModItems.RAW_DEEPSLATE_COPPER);
        entries.add(ModItems.RAW_DEEPSLATE_IRON);

        entries.add(ModItems.TIN_NUGGET);
        entries.add(ModItems.BRONZE_NUGGET);
    }

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModItems.FLINT_HOE);
        entries.add(ModItems.FLINT_SHOVEL);
        entries.add(ModItems.FLINT_AXE);

        entries.add(ModItems.BASALT_HOE);
        entries.add(ModItems.BASALT_SHOVEL);
        entries.add(ModItems.BASALT_AXE);

        entries.add(ModItems.OBSIDIAN_HOE);
        entries.add(ModItems.OBSIDIAN_SHOVEL);
        entries.add(ModItems.OBSIDIAN_AXE);

        entries.add(ModItems.COPPER_HOE);
        entries.add(ModItems.COPPER_SHOVEL);
        entries.add(ModItems.COPPER_AXE);
        entries.add(ModItems.COPPER_PICKAXE);

        entries.add(ModItems.BRONZE_HOE);
        entries.add(ModItems.BRONZE_SHOVEL);
        entries.add(ModItems.BRONZE_AXE);
        entries.add(ModItems.BRONZE_PICKAXE);
        
        entries.add(ModItems.WOODEN_TONGS);

        entries.add(ModItems.HAMMER);

        // entries.add(ModItems.STEEL_HOE);
        // entries.add(ModItems.STEEL_SHOVEL);
        // entries.add(ModItems.STEEL_AXE);
        // entries.add(ModItems.STEEL_PICKAXE);
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModItems.STONE_KNIFE);
        entries.add(ModItems.STONE_SPEAR);

        entries.add(ModItems.FLINT_KNIFE);
        entries.add(ModItems.FLINT_SPEAR);

        entries.add(ModItems.BASALT_KNIFE);
        entries.add(ModItems.BASALT_SPEAR);

        entries.add(ModItems.OBSIDIAN_KNIFE);
        entries.add(ModItems.OBSIDIAN_SPEAR);

        entries.add(ModItems.COPPER_KNIFE);
        entries.add(ModItems.COPPER_SPEAR);
        entries.add(ModItems.COPPER_SWORD);

        entries.add(ModItems.BRONZE_KNIFE);
        entries.add(ModItems.BRONZE_SPEAR);
        entries.add(ModItems.BRONZE_SWORD);

        entries.add(ModItems.IRON_KNIFE);
        entries.add(ModItems.IRON_SPEAR);

        // entries.add(ModItems.STEEL_KNIFE);
        // entries.add(ModItems.STEEL_SPEAR);
        // entries.add(ModItems.STEEL_SWORD);

        entries.add(ModItems.STONE_ARROW);
        entries.add(ModItems.FLINT_ARROW);
        entries.add(ModItems.BASALT_ARROW);
        entries.add(ModItems.OBSIDIAN_ARROW);
        entries.add(ModItems.COPPER_ARROW);
        entries.add(ModItems.BRONZE_ARROW);
        // entries.add(ModItems.IRON_ARROW);
        // entries.add(ModItems.STEEL_ARROW);

        entries.add(ModItems.GAMBESON);
        entries.add(ModItems.LEATHER_JERKIN);

        entries.add(ModItems.LEATHER_LAMELLAR);
        entries.add(ModItems.COPPER_LAMELLAR);
        entries.add(ModItems.BRONZE_LAMELLAR);
        entries.add(ModItems.IRON_LAMELLAR);

        entries.add(ModItems.COPPER_BRIGANDINE);
        entries.add(ModItems.BRONZE_BRIGANDINE);
        entries.add(ModItems.IRON_BRIGANDINE);

        entries.add(ModItems.COPPER_LAMINAR);
        entries.add(ModItems.BRONZE_LAMINAR);
        entries.add(ModItems.IRON_LAMINAR);

        entries.add(ModItems.IRON_PLATE_HARNESS);

        entries.add(ModItems.COPPER_HAUBERK);
        entries.add(ModItems.BRONZE_HAUBERK);
        entries.add(ModItems.IRON_HAUBERK);

        entries.add(ModItems.IRON_PAULDRON);
        entries.add(ModItems.IRON_GAUNTLET);
        
    }

    private static void addItemsToNaturalTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.STONE_ROCK);
        entries.add(Items.FLINT);
        entries.add(ModBlocks.BASALT_ROCK);
        entries.add(ModBlocks.OBSIDIAN_ROCK);
        entries.add(ModBlocks.STONE_COPPER_ORE);
        entries.add(ModBlocks.STONE_TIN_ORE);
        entries.add(ModBlocks.STONE_IRON_ORE);
        entries.add(ModBlocks.DEEPSLATE_COPPER_ORE);
        entries.add(ModBlocks.DEEPSLATE_IRON_ORE);
        entries.add(ModItems.ARMORERS_MANNEQUIN);
        entries.add(ModBlocks.FORGE);
        entries.add(ModItems.FLAX_SEEDS);
        entries.add(ModItems.COTTON_SEEDS);
    }

    private static void addItemsToFunctionalBlocksTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(ModBlocks.POTTERS_WHEEL);
        entries.add(ModBlocks.UNFIRED_FLOWER_POT);
        entries.add(ModBlocks.UNFIRED_CRUCIBLE);
        entries.add(ModBlocks.UNFIRED_DECORATED_POT);
        entries.add(ModBlocks.FIRED_CRUCIBLE);
        entries.add(ModBlocks.KILN);
        entries.add(ModItems.ARMORERS_MANNEQUIN);
        entries.add(ModBlocks.FORGE);
    }

    public static void registerItemGroups() {
        FirstSteps.LOGGER.info("Registering Item Groups for" + FirstSteps.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItemGroups::addItemsToIngredientsTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItemGroups::addItemsToToolsTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItemGroups::addItemsToCombatTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItemGroups::addItemsToNaturalTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItemGroups::addItemsToFunctionalBlocksTabItemGroup);
    }
}

