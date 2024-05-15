package nz.duncy.first_steps.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.custom.TinSourceItem;
import nz.duncy.first_steps.item.custom.CopperSourceItem;
import nz.duncy.first_steps.item.custom.IronSourceItem;
import nz.duncy.first_steps.item.custom.KnifeItem;
import nz.duncy.first_steps.item.custom.MulliteSourceItem;
import nz.duncy.first_steps.item.custom.SpearItem;

public class ModItems {
    // Raw metals
    public static final Item RAW_TIN = registerItem("raw_tin", new TinSourceItem(new FabricItemSettings()));
    public static final Item RAW_STONE_COPPER = registerItem("raw_stone_copper", new CopperSourceItem(new FabricItemSettings()));
    public static final Item RAW_STONE_IRON = registerItem("raw_stone_iron", new IronSourceItem(new FabricItemSettings()));
    public static final Item RAW_DEEPSLATE_COPPER = registerItem("raw_deepslate_copper", new CopperSourceItem(new FabricItemSettings()));
    public static final Item RAW_DEEPSLATE_IRON = registerItem("raw_deepslate_iron", new IronSourceItem(new FabricItemSettings()));
    public static final Item RAW_MULLITE = registerItem("raw_mullite", new MulliteSourceItem(new FabricItemSettings()));

    // Tool heads
    // Axe heads
    public static final Item STONE_HEAD_AXE = registerItem("stone_head_axe", new Item(new FabricItemSettings())); 
    public static final Item BASALT_HEAD_AXE = registerItem("basalt_head_axe", new Item(new FabricItemSettings()));
    
    // Shovel heads
    public static final Item STONE_HEAD_SHOVEL = registerItem("stone_head_shovel", new Item(new FabricItemSettings())); 
    public static final Item BASALT_HEAD_SHOVEL = registerItem("basalt_head_shovel", new Item(new FabricItemSettings())); 
    
    // Knife heads
    public static final Item STONE_HEAD_KNIFE = registerItem("stone_head_knife", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_KNIFE = registerItem("basalt_head_knife", new Item(new FabricItemSettings()));

    // Spear heads
    public static final Item STONE_HEAD_SPEAR = registerItem("stone_head_spear", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_SPEAR = registerItem("basalt_head_spear", new Item(new FabricItemSettings()));

    // Arrow heads
    public static final Item STONE_HEAD_ARROW = registerItem("stone_head_arrow", new Item(new FabricItemSettings()));
    public static final Item FLINT_HEAD_ARROW = registerItem("flint_head_arrow", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_ARROW = registerItem("basalt_head_arrow", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_ARROW = registerItem("obsidian_head_arrow", new Item(new FabricItemSettings()));
    public static final Item COPPER_HEAD_ARROW = registerItem("copper_head_arrow", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_ARROW = registerItem("bronze_head_arrow", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_ARROW = registerItem("iron_head_arrow", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_ARROW = registerItem("steel_head_arrow", new Item(new FabricItemSettings()));
   
    // Hoes
    public static final Item BASALT_HOE = registerItem("basalt_hoe", new HoeItem(ModToolMaterial.BASALT, 9, 0.8f, new FabricItemSettings())); 

    // Shovels
    public static final Item BASALT_SHOVEL = registerItem("basalt_shovel", new ShovelItem(ModToolMaterial.BASALT, 9, 0.8f, new FabricItemSettings())); 

    // Axes
    public static final Item BASALT_AXE = registerItem("basalt_axe", new AxeItem(ModToolMaterial.BASALT, 9, 0.8f, new FabricItemSettings())); 

    // Spears
    public static final Item STONE_SPEAR = registerItem("stone_spear", new SpearItem(ToolMaterials.STONE, 3, new FabricItemSettings()));
    public static final Item BASALT_SPEAR =  registerItem("basalt_spear", new SpearItem(ModToolMaterial.BASALT, 3, new FabricItemSettings()));

    // Knives
    public static final Item STONE_KNIFE = registerItem("stone_knife", new KnifeItem(ToolMaterials.STONE, 0, new FabricItemSettings()));
    public static final Item BASALT_KNIFE = registerItem("basalt_knife", new KnifeItem(ModToolMaterial.BASALT, 0, new FabricItemSettings()));

    // Ingots
    public static final Item TIN_INGOT = registerItem("tin_ingot", new Item(new FabricItemSettings()));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", new Item(new FabricItemSettings()));

    // Arrows
    public static final Item STONE_ARROW = registerItem("stone_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item FLINT_ARROW = registerItem("flint_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item BASALT_ARROW = registerItem("basalt_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item OBSIDIAN_ARROW = registerItem("obsidian_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item COPPER_ARROW = registerItem("copper_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item BRONZE_ARROW = registerItem("bronze_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item IRON_ARROW = registerItem("iron_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item STEEL_ARROW = registerItem("steel_arrow", new ArrowItem(new FabricItemSettings()));


    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(STONE_HEAD_AXE);
        entries.add(BASALT_HEAD_AXE);

        entries.add(STONE_HEAD_SHOVEL);
        entries.add(BASALT_HEAD_SHOVEL);

        entries.add(STONE_HEAD_KNIFE);
        entries.add(BASALT_HEAD_KNIFE);
        
        entries.add(STONE_HEAD_SPEAR);
        entries.add(BASALT_HEAD_SPEAR);

        entries.add(STONE_HEAD_ARROW);
        entries.add(FLINT_HEAD_ARROW);
        entries.add(BASALT_HEAD_ARROW);
        entries.add(OBSIDIAN_HEAD_ARROW);
        entries.add(COPPER_HEAD_ARROW);
        entries.add(BRONZE_HEAD_ARROW);
        entries.add(IRON_HEAD_ARROW);
        entries.add(STEEL_HEAD_ARROW);

        entries.add(TIN_INGOT);
        entries.add(BRONZE_INGOT);
    }

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(BASALT_HOE);
        entries.add(BASALT_SHOVEL);
        entries.add(BASALT_AXE);
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(STONE_KNIFE);
        entries.add(STONE_SPEAR);

        entries.add(BASALT_KNIFE);
        entries.add(BASALT_SPEAR);

        entries.add(STONE_ARROW);
        entries.add(FLINT_ARROW);
        entries.add(BASALT_ARROW);
        entries.add(OBSIDIAN_ARROW);
        entries.add(COPPER_ARROW);
        entries.add(BRONZE_ARROW);
        entries.add(IRON_ARROW);
        entries.add(STEEL_ARROW);
    }

     
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FirstSteps.MOD_ID, name), item);
    }

    public static void registerModItems() {
        FirstSteps.LOGGER.info("Registering mod items for " + FirstSteps.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsTabItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatTabItemGroup);
    }
}
