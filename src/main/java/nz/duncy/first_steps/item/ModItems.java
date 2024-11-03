package nz.duncy.first_steps.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.custom.TinSourceItem;
import nz.duncy.first_steps.item.custom.TongItem;
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
    // Hoe heads
    public static final Item STONE_HEAD_HOE = registerItem("stone_head_hoe", new Item(new FabricItemSettings())); 
    public static final Item FLINT_HEAD_HOE = registerItem("flint_head_hoe", new Item(new FabricItemSettings())); 
    public static final Item BASALT_HEAD_HOE = registerItem("basalt_head_hoe", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_HOE = registerItem("obsidian_head_hoe", new Item(new FabricItemSettings())); 
    public static final Item COPPER_HEAD_HOE = registerItem("copper_head_hoe", new Item(new FabricItemSettings())); 
    public static final Item BRONZE_HEAD_HOE = registerItem("bronze_head_hoe", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_HOE = registerItem("iron_head_hoe", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_HOE = registerItem("steel_head_hoe", new Item(new FabricItemSettings()));

    // Axe heads
    public static final Item STONE_HEAD_AXE = registerItem("stone_head_axe", new Item(new FabricItemSettings())); 
    public static final Item FLINT_HEAD_AXE = registerItem("flint_head_axe", new Item(new FabricItemSettings())); 
    public static final Item BASALT_HEAD_AXE = registerItem("basalt_head_axe", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_AXE = registerItem("obsidian_head_axe", new Item(new FabricItemSettings())); 
    public static final Item COPPER_HEAD_AXE = registerItem("copper_head_axe", new Item(new FabricItemSettings())); 
    public static final Item BRONZE_HEAD_AXE = registerItem("bronze_head_axe", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_AXE = registerItem("iron_head_axe", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_AXE = registerItem("steel_head_axe", new Item(new FabricItemSettings()));
    
    // Shovel heads
    public static final Item STONE_HEAD_SHOVEL = registerItem("stone_head_shovel", new Item(new FabricItemSettings())); 
    public static final Item FLINT_HEAD_SHOVEL = registerItem("flint_head_shovel", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_SHOVEL = registerItem("basalt_head_shovel", new Item(new FabricItemSettings())); 
    public static final Item OBSIDIAN_HEAD_SHOVEL = registerItem("obsidian_head_shovel", new Item(new FabricItemSettings()));
    public static final Item COPPER_HEAD_SHOVEL = registerItem("copper_head_shovel", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_SHOVEL = registerItem("bronze_head_shovel", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_SHOVEL = registerItem("iron_head_shovel", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_SHOVEL = registerItem("steel_head_shovel", new Item(new FabricItemSettings()));

    // Knife heads
    public static final Item STONE_HEAD_KNIFE = registerItem("stone_head_knife", new Item(new FabricItemSettings()));
    public static final Item FLINT_HEAD_KNIFE = registerItem("flint_head_knife", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_KNIFE = registerItem("basalt_head_knife", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_KNIFE = registerItem("obsidian_head_knife", new Item(new FabricItemSettings()));
    public static final Item COPPER_HEAD_KNIFE = registerItem("copper_head_knife", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_KNIFE = registerItem("bronze_head_knife", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_KNIFE = registerItem("iron_head_knife", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_KNIFE = registerItem("steel_head_knife", new Item(new FabricItemSettings()));

    // Spear heads
    public static final Item STONE_HEAD_SPEAR = registerItem("stone_head_spear", new Item(new FabricItemSettings()));
    public static final Item FLINT_HEAD_SPEAR = registerItem("flint_head_spear", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_SPEAR = registerItem("basalt_head_spear", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_SPEAR = registerItem("obsidian_head_spear", new Item(new FabricItemSettings()));
    public static final Item COPPER_HEAD_SPEAR = registerItem("copper_head_spear", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_SPEAR = registerItem("bronze_head_spear", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_SPEAR = registerItem("iron_head_spear", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_SPEAR = registerItem("steel_head_spear", new Item(new FabricItemSettings()));

    // Arrow heads
    public static final Item STONE_HEAD_ARROW = registerItem("stone_head_arrow", new Item(new FabricItemSettings()));
    public static final Item FLINT_HEAD_ARROW = registerItem("flint_head_arrow", new Item(new FabricItemSettings()));
    public static final Item BASALT_HEAD_ARROW = registerItem("basalt_head_arrow", new Item(new FabricItemSettings()));
    public static final Item OBSIDIAN_HEAD_ARROW = registerItem("obsidian_head_arrow", new Item(new FabricItemSettings()));
    public static final Item COPPER_HEAD_ARROW = registerItem("copper_head_arrow", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_ARROW = registerItem("bronze_head_arrow", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_ARROW = registerItem("iron_head_arrow", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_ARROW = registerItem("steel_head_arrow", new Item(new FabricItemSettings()));

    // Sword heads
    public static final Item COPPER_HEAD_SWORD = registerItem("copper_head_sword", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_SWORD = registerItem("bronze_head_sword", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_SWORD = registerItem("iron_head_sword", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_SWORD = registerItem("steel_head_sword", new Item(new FabricItemSettings()));

    // Pickaxe heads
    public static final Item COPPER_HEAD_PICKAXE = registerItem("copper_head_pickaxe", new Item(new FabricItemSettings()));
    public static final Item BRONZE_HEAD_PICKAXE = registerItem("bronze_head_pickaxe", new Item(new FabricItemSettings()));
    public static final Item IRON_HEAD_PICKAXE = registerItem("iron_head_pickaxe", new Item(new FabricItemSettings()));
    public static final Item STEEL_HEAD_PICKAXE = registerItem("steel_head_pickaxe", new Item(new FabricItemSettings()));
   
    // Hoes
    public static final Item FLINT_HOE = registerItem("flint_hoe", new HoeItem(ModToolMaterial.FLINT, -1, -2.0F, new FabricItemSettings())); 
    public static final Item BASALT_HOE = registerItem("basalt_hoe", new HoeItem(ModToolMaterial.BASALT, -1, -2.0F, new FabricItemSettings()));
    public static final Item OBSIDIAN_HOE = registerItem("obsidian_hoe", new HoeItem(ModToolMaterial.OBSIDIAN, -1, -2.0F, new FabricItemSettings()));
    public static final Item COPPER_HOE = registerItem("copper_hoe", new HoeItem(ModToolMaterial.COPPER, -1, -2.0F, new FabricItemSettings()));
    public static final Item BRONZE_HOE = registerItem("bronze_hoe", new HoeItem(ModToolMaterial.BRONZE, -1, -2.0F, new FabricItemSettings()));
    public static final Item STEEL_HOE = registerItem("steel_hoe", new HoeItem(ModToolMaterial.STEEL, -1, -2.0F, new FabricItemSettings()));

    // Shovels
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", new ShovelItem(ModToolMaterial.FLINT, 1.5F, -3.0F, new FabricItemSettings())); 
    public static final Item BASALT_SHOVEL = registerItem("basalt_shovel", new ShovelItem(ModToolMaterial.BASALT, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item OBSIDIAN_SHOVEL = registerItem("obsidian_shovel", new ShovelItem(ModToolMaterial.OBSIDIAN, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel", new ShovelItem(ModToolMaterial.COPPER, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item BRONZE_SHOVEL = registerItem("bronze_shovel", new ShovelItem(ModToolMaterial.BRONZE, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item STEEL_SHOVEL = registerItem("steel_shovel", new ShovelItem(ModToolMaterial.STEEL, 1.5F, -3.0F, new FabricItemSettings()));

    // Axes
    public static final Item FLINT_AXE = registerItem("flint_axe", new AxeItem(ModToolMaterial.FLINT, 7.0F, -3.2f, new FabricItemSettings())); 
    public static final Item BASALT_AXE = registerItem("basalt_axe", new AxeItem(ModToolMaterial.BASALT, 7.0F, -3.2f, new FabricItemSettings()));
    public static final Item OBSIDIAN_AXE = registerItem("obsidian_axe", new AxeItem(ModToolMaterial.OBSIDIAN, 7.0F, -3.2f, new FabricItemSettings()));
    public static final Item COPPER_AXE = registerItem("copper_axe", new AxeItem(ModToolMaterial.COPPER, 6.0F, -3.1f, new FabricItemSettings()));
    public static final Item BRONZE_AXE = registerItem("bronze_axe", new AxeItem(ModToolMaterial.BRONZE, 5.0F, -3.1f, new FabricItemSettings()));
    public static final Item STEEL_AXE = registerItem("steel_axe", new AxeItem(ModToolMaterial.STEEL, 5.0F, -3.0f, new FabricItemSettings()));

    // Pickaxes
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe", new PickaxeItem(ModToolMaterial.COPPER, 1, -2.8F, new FabricItemSettings()));
    public static final Item BRONZE_PICKAXE = registerItem("bronze_pickaxe", new PickaxeItem(ModToolMaterial.BRONZE, 1, -2.8F, new FabricItemSettings()));
    public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe", new PickaxeItem(ModToolMaterial.STEEL, 1, -2.8F, new FabricItemSettings()));

    // Spears
    public static final Item STONE_SPEAR = registerItem("stone_spear", new SpearItem(ToolMaterials.STONE, 3, new FabricItemSettings()));
    public static final Item FLINT_SPEAR =  registerItem("flint_spear", new SpearItem(ModToolMaterial.FLINT, 3, new FabricItemSettings()));
    public static final Item BASALT_SPEAR =  registerItem("basalt_spear", new SpearItem(ModToolMaterial.BASALT, 3, new FabricItemSettings()));
    public static final Item OBSIDIAN_SPEAR =  registerItem("obsidian_spear", new SpearItem(ModToolMaterial.OBSIDIAN, 3, new FabricItemSettings()));
    public static final Item COPPER_SPEAR =  registerItem("copper_spear", new SpearItem(ModToolMaterial.COPPER, 3, new FabricItemSettings()));
    public static final Item BRONZE_SPEAR =  registerItem("bronze_spear", new SpearItem(ModToolMaterial.BRONZE, 3, new FabricItemSettings()));
    public static final Item IRON_SPEAR =  registerItem("iron_spear", new SpearItem(ToolMaterials.IRON, 3, new FabricItemSettings()));
    public static final Item STEEL_SPEAR =  registerItem("steel_spear", new SpearItem(ModToolMaterial.STEEL, 3, new FabricItemSettings()));

    // Knives
    public static final Item STONE_KNIFE = registerItem("stone_knife", new KnifeItem(ToolMaterials.STONE, 0, new FabricItemSettings()));
    public static final Item FLINT_KNIFE =  registerItem("flint_knife", new KnifeItem(ModToolMaterial.FLINT, 0, new FabricItemSettings()));
    public static final Item BASALT_KNIFE =  registerItem("basalt_knife", new KnifeItem(ModToolMaterial.BASALT, 0, new FabricItemSettings()));
    public static final Item OBSIDIAN_KNIFE =  registerItem("obsidian_knife", new KnifeItem(ModToolMaterial.OBSIDIAN, 0, new FabricItemSettings()));
    public static final Item COPPER_KNIFE =  registerItem("copper_knife", new KnifeItem(ModToolMaterial.COPPER, 0, new FabricItemSettings()));
    public static final Item BRONZE_KNIFE =  registerItem("bronze_knife", new KnifeItem(ModToolMaterial.BRONZE, 0, new FabricItemSettings()));
    public static final Item IRON_KNIFE =  registerItem("iron_knife", new KnifeItem(ToolMaterials.IRON, 0, new FabricItemSettings()));
    public static final Item STEEL_KNIFE =  registerItem("steel_knife", new KnifeItem(ModToolMaterial.STEEL, 0, new FabricItemSettings()));

    // Swords
    public static final Item COPPER_SWORD = registerItem("copper_sword", new SwordItem(ModToolMaterial.COPPER, 3, -2.4f, new FabricItemSettings()));
    public static final Item BRONZE_SWORD = registerItem("bronze_sword", new SwordItem(ModToolMaterial.BRONZE, 3, -2.4f, new FabricItemSettings()));
    public static final Item STEEL_SWORD = registerItem("steel_sword", new SwordItem(ModToolMaterial.STEEL, 3, -2.4f, new FabricItemSettings()));

    // Ingots
    public static final Item TIN_INGOT = registerItem("tin_ingot", new Item(new FabricItemSettings()));
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", new Item(new FabricItemSettings()));
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));

    // Arrows
    public static final Item STONE_ARROW = registerItem("stone_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item FLINT_ARROW = registerItem("flint_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item BASALT_ARROW = registerItem("basalt_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item OBSIDIAN_ARROW = registerItem("obsidian_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item COPPER_ARROW = registerItem("copper_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item BRONZE_ARROW = registerItem("bronze_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item IRON_ARROW = registerItem("iron_arrow", new ArrowItem(new FabricItemSettings()));
    public static final Item STEEL_ARROW = registerItem("steel_arrow", new ArrowItem(new FabricItemSettings()));

    // Tongs
    public static final Item WOODEN_TONGS = registerItem("wooden_tongs", new TongItem(new FabricItemSettings()));

    // Pure raw metal nuggets
    public static final Item RAW_TIN_NUGGET = registerItem("raw_tin_nugget", new TinSourceItem(new FabricItemSettings()));
    public static final Item RAW_COPPER_NUGGET = registerItem("raw_copper_nugget", new CopperSourceItem(new FabricItemSettings()));
    public static final Item RAW_IRON_NUGGET = registerItem("raw_iron_nugget", new IronSourceItem(new FabricItemSettings()));

    private static void addItemsToIngredientsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(STONE_HEAD_HOE);
        entries.add(FLINT_HEAD_HOE);
        entries.add(BASALT_HEAD_HOE);
        entries.add(OBSIDIAN_HEAD_HOE);
        entries.add(COPPER_HEAD_HOE);
        entries.add(BRONZE_HEAD_HOE);
        entries.add(IRON_HEAD_HOE);
        entries.add(STEEL_HEAD_HOE);

        entries.add(STONE_HEAD_AXE);
        entries.add(FLINT_HEAD_AXE);
        entries.add(BASALT_HEAD_AXE);
        entries.add(OBSIDIAN_HEAD_AXE);
        entries.add(COPPER_HEAD_AXE);
        entries.add(BRONZE_HEAD_AXE);
        entries.add(IRON_HEAD_AXE);
        entries.add(STEEL_HEAD_AXE);

        entries.add(STONE_HEAD_SHOVEL);
        entries.add(FLINT_HEAD_SHOVEL);
        entries.add(BASALT_HEAD_SHOVEL);
        entries.add(OBSIDIAN_HEAD_SHOVEL);
        entries.add(COPPER_HEAD_SHOVEL);
        entries.add(BRONZE_HEAD_SHOVEL);
        entries.add(IRON_HEAD_SHOVEL);
        entries.add(STEEL_HEAD_SHOVEL);

        entries.add(STONE_HEAD_KNIFE);
        entries.add(FLINT_HEAD_KNIFE);
        entries.add(BASALT_HEAD_KNIFE);
        entries.add(OBSIDIAN_HEAD_KNIFE);
        entries.add(COPPER_HEAD_KNIFE);
        entries.add(BRONZE_HEAD_KNIFE);
        entries.add(IRON_HEAD_KNIFE);
        entries.add(STEEL_HEAD_KNIFE);
        
        entries.add(STONE_HEAD_SPEAR);
        entries.add(FLINT_HEAD_SPEAR);
        entries.add(BASALT_HEAD_SPEAR);
        entries.add(OBSIDIAN_HEAD_SPEAR);
        entries.add(COPPER_HEAD_SPEAR);
        entries.add(BRONZE_HEAD_SPEAR);
        entries.add(IRON_HEAD_SPEAR);
        entries.add(STEEL_HEAD_SPEAR);

        entries.add(STONE_HEAD_ARROW);
        entries.add(FLINT_HEAD_ARROW);
        entries.add(BASALT_HEAD_ARROW);
        entries.add(OBSIDIAN_HEAD_ARROW);
        entries.add(COPPER_HEAD_ARROW);
        entries.add(BRONZE_HEAD_ARROW);
        entries.add(IRON_HEAD_ARROW);
        entries.add(STEEL_HEAD_ARROW);

        entries.add(COPPER_HEAD_SWORD);
        entries.add(BRONZE_HEAD_SWORD);
        entries.add(IRON_HEAD_SWORD);
        entries.add(STEEL_HEAD_SWORD);

        entries.add(COPPER_HEAD_PICKAXE);
        entries.add(BRONZE_HEAD_PICKAXE);
        entries.add(IRON_HEAD_PICKAXE);
        entries.add(STEEL_HEAD_PICKAXE);

        entries.add(TIN_INGOT);
        entries.add(BRONZE_INGOT);
        entries.add(STEEL_INGOT);
    }

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {

        entries.add(FLINT_HOE);
        entries.add(FLINT_SHOVEL);
        entries.add(FLINT_AXE);

        entries.add(BASALT_HOE);
        entries.add(BASALT_SHOVEL);
        entries.add(BASALT_AXE);

        entries.add(OBSIDIAN_HOE);
        entries.add(OBSIDIAN_SHOVEL);
        entries.add(OBSIDIAN_AXE);

        entries.add(COPPER_HOE);
        entries.add(COPPER_SHOVEL);
        entries.add(COPPER_AXE);
        entries.add(COPPER_PICKAXE);

        entries.add(BRONZE_HOE);
        entries.add(BRONZE_SHOVEL);
        entries.add(BRONZE_AXE);
        entries.add(BRONZE_PICKAXE);
        
        entries.add(STEEL_HOE);
        entries.add(STEEL_SHOVEL);
        entries.add(STEEL_AXE);
        entries.add(STEEL_PICKAXE);
    }

    private static void addItemsToCombatTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(STONE_KNIFE);
        entries.add(STONE_SPEAR);

        entries.add(FLINT_KNIFE);
        entries.add(FLINT_SPEAR);

        entries.add(BASALT_KNIFE);
        entries.add(BASALT_SPEAR);

        entries.add(OBSIDIAN_KNIFE);
        entries.add(OBSIDIAN_SPEAR);

        entries.add(COPPER_KNIFE);
        entries.add(COPPER_SPEAR);
        entries.add(COPPER_SWORD);

        entries.add(BRONZE_KNIFE);
        entries.add(BRONZE_SPEAR);
        entries.add(BRONZE_SWORD);

        entries.add(IRON_KNIFE);
        entries.add(IRON_SPEAR);

        entries.add(STEEL_KNIFE);
        entries.add(STEEL_SPEAR);
        entries.add(STEEL_SWORD);

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
