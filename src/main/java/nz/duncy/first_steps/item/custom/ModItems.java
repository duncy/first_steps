package nz.duncy.first_steps.item.custom;

import java.util.function.Function;

import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;

public class ModItems {
    // Raw metals

    public static final Item RAW_TIN = registerItem("raw_tin", TinSourceItem::new);
    public static final Item RAW_STONE_COPPER = registerItem("raw_stone_copper", CopperSourceItem::new);
    public static final Item RAW_STONE_IRON = registerItem("raw_stone_iron", IronSourceItem::new);
    public static final Item RAW_DEEPSLATE_COPPER = registerItem("raw_deepslate_copper", CopperSourceItem::new);
    public static final Item RAW_DEEPSLATE_IRON = registerItem("raw_deepslate_iron", IronSourceItem::new);
    // public static final Item RAW_MULLITE = registerItem("raw_mullite", MulliteSourceItem::new);

    // Tool heads
    // Hoe heads
    public static final Item STONE_HEAD_HOE = registerItem("stone_head_hoe", Item::new); 
    public static final Item FLINT_HEAD_HOE = registerItem("flint_head_hoe", Item::new); 
    public static final Item BASALT_HEAD_HOE = registerItem("basalt_head_hoe", Item::new);
    public static final Item OBSIDIAN_HEAD_HOE = registerItem("obsidian_head_hoe", Item::new); 
    public static final Item COPPER_HEAD_HOE = registerItem("copper_head_hoe", Item::new); 
    public static final Item BRONZE_HEAD_HOE = registerItem("bronze_head_hoe", Item::new);
    public static final Item IRON_HEAD_HOE = registerItem("iron_head_hoe", Item::new);
    // public static final Item STEEL_HEAD_HOE = registerItem("steel_head_hoe", Item::new);

    // Axe heads
    public static final Item STONE_HEAD_AXE = registerItem("stone_head_axe", Item::new); 
    public static final Item FLINT_HEAD_AXE = registerItem("flint_head_axe", Item::new); 
    public static final Item BASALT_HEAD_AXE = registerItem("basalt_head_axe", Item::new);
    public static final Item OBSIDIAN_HEAD_AXE = registerItem("obsidian_head_axe", Item::new); 
    public static final Item COPPER_HEAD_AXE = registerItem("copper_head_axe", Item::new); 
    public static final Item BRONZE_HEAD_AXE = registerItem("bronze_head_axe", Item::new);
    public static final Item IRON_HEAD_AXE = registerItem("iron_head_axe", Item::new);
    // public static final Item STEEL_HEAD_AXE = registerItem("steel_head_axe", Item::new);
    
    // Shovel heads
    public static final Item STONE_HEAD_SHOVEL = registerItem("stone_head_shovel", Item::new); 
    public static final Item FLINT_HEAD_SHOVEL = registerItem("flint_head_shovel", Item::new);
    public static final Item BASALT_HEAD_SHOVEL = registerItem("basalt_head_shovel", Item::new); 
    public static final Item OBSIDIAN_HEAD_SHOVEL = registerItem("obsidian_head_shovel", Item::new);
    public static final Item COPPER_HEAD_SHOVEL = registerItem("copper_head_shovel", Item::new);
    public static final Item BRONZE_HEAD_SHOVEL = registerItem("bronze_head_shovel", Item::new);
    public static final Item IRON_HEAD_SHOVEL = registerItem("iron_head_shovel", Item::new);
    // public static final Item STEEL_HEAD_SHOVEL = registerItem("steel_head_shovel", Item::new);

    // Knife heads
    public static final Item STONE_HEAD_KNIFE = registerItem("stone_head_knife", Item::new);
    public static final Item FLINT_HEAD_KNIFE = registerItem("flint_head_knife", Item::new);
    public static final Item BASALT_HEAD_KNIFE = registerItem("basalt_head_knife", Item::new);
    public static final Item OBSIDIAN_HEAD_KNIFE = registerItem("obsidian_head_knife", Item::new);
    public static final Item COPPER_HEAD_KNIFE = registerItem("copper_head_knife", Item::new);
    public static final Item BRONZE_HEAD_KNIFE = registerItem("bronze_head_knife", Item::new);
    public static final Item IRON_HEAD_KNIFE = registerItem("iron_head_knife", Item::new);
    // public static final Item STEEL_HEAD_KNIFE = registerItem("steel_head_knife", Item::new);

    // Spear heads
    public static final Item STONE_HEAD_SPEAR = registerItem("stone_head_spear", Item::new);
    public static final Item FLINT_HEAD_SPEAR = registerItem("flint_head_spear", Item::new);
    public static final Item BASALT_HEAD_SPEAR = registerItem("basalt_head_spear", Item::new);
    public static final Item OBSIDIAN_HEAD_SPEAR = registerItem("obsidian_head_spear", Item::new);
    public static final Item COPPER_HEAD_SPEAR = registerItem("copper_head_spear", Item::new);
    public static final Item BRONZE_HEAD_SPEAR = registerItem("bronze_head_spear", Item::new);
    public static final Item IRON_HEAD_SPEAR = registerItem("iron_head_spear", Item::new);
    // public static final Item STEEL_HEAD_SPEAR = registerItem("steel_head_spear", Item::new);

    // Arrow heads
    public static final Item STONE_HEAD_ARROW = registerItem("stone_head_arrow", Item::new);
    public static final Item FLINT_HEAD_ARROW = registerItem("flint_head_arrow", Item::new);
    public static final Item BASALT_HEAD_ARROW = registerItem("basalt_head_arrow", Item::new);
    public static final Item OBSIDIAN_HEAD_ARROW = registerItem("obsidian_head_arrow", Item::new);
    public static final Item COPPER_HEAD_ARROW = registerItem("copper_head_arrow", Item::new);
    public static final Item BRONZE_HEAD_ARROW = registerItem("bronze_head_arrow", Item::new);
    public static final Item IRON_HEAD_ARROW = registerItem("iron_head_arrow", Item::new);
    // public static final Item STEEL_HEAD_ARROW = registerItem("steel_heaHoeItemr_head_pickaxe", Item::new);

    // Pickaxe heads
    public static final Item COPPER_HEAD_PICKAXE = registerItem("copper_head_pickaxe", Item::new);
    public static final Item BRONZE_HEAD_PICKAXE = registerItem("bronze_head_pickaxe", Item::new);
    public static final Item IRON_HEAD_PICKAXE = registerItem("iron_head_pickaxe", Item::new);
    // public static final Item STEEL_HEAD_PICKAXE = registerItem("steel_head_pickaxe", Item::new);

    // Sword heads
    public static final Item COPPER_HEAD_SWORD = registerItem("copper_head_sword", Item::new);
    public static final Item BRONZE_HEAD_SWORD = registerItem("bronze_head_sword", Item::new);
    public static final Item IRON_HEAD_SWORD = registerItem("iron_head_sword", Item::new);
   
    // Hoes
    public static final Item FLINT_HOE = registerItem("flint_hoe", settings -> new HoeItem(ModToolMaterials.FLINT, -1.0F, -2.0F, settings)); 
    public static final Item BASALT_HOE = registerItem("basalt_hoe", settings -> new HoeItem(ModToolMaterials.BASALT, -1.0F, -2.0F, settings));
    public static final Item OBSIDIAN_HOE = registerItem("obsidian_hoe", settings -> new HoeItem(ModToolMaterials.OBSIDIAN, -1.0F, -2.0F, settings));
    public static final Item COPPER_HOE = registerItem("copper_hoe", settings -> new HoeItem(ModToolMaterials.COPPER, -1.5F, -1.5F, settings));
    public static final Item BRONZE_HOE = registerItem("bronze_hoe", settings -> new HoeItem(ModToolMaterials.BRONZE, -2.0F, -1.0F, settings));
    // public static final Item STEEL_HOE = registerItem("steel_hoe", HoeItem(ModToolMaterials.STEEL, -1, -2.0F, Item.Settings()));

    // Shovels
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", settings -> new ShovelItem(ModToolMaterials.FLINT, 1.5F, -3.0F, settings));
    public static final Item BASALT_SHOVEL = registerItem("basalt_shovel", settings -> new ShovelItem(ModToolMaterials.BASALT, 1.5F, -3.0F, settings));
    public static final Item OBSIDIAN_SHOVEL = registerItem("obsidian_shovel", settings -> new ShovelItem(ModToolMaterials.OBSIDIAN, 1.5F, -3.0F, settings));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel", settings -> new ShovelItem(ModToolMaterials.COPPER, 1.5F, -3.0F, settings));
    public static final Item BRONZE_SHOVEL = registerItem("bronze_shovel", settings -> new ShovelItem(ModToolMaterials.BRONZE, 1.5F, -3.0F, settings));
    // public static final Item STEEL_SHOVEL = registerItem("steel_shovel", ShovelItem(ModToolMaterials.STEEL, 1.5F, -3.0F, Item.Settings()));

    // Axes
    public static final Item FLINT_AXE = registerItem("flint_axe", settings -> new AxeItem(ModToolMaterials.FLINT, 7.0F, -3.2F, settings)); 
    public static final Item BASALT_AXE = registerItem("basalt_axe", settings -> new AxeItem(ModToolMaterials.BASALT, 7.0F, -3.2F, settings));
    public static final Item OBSIDIAN_AXE = registerItem("obsidian_axe", settings -> new AxeItem(ModToolMaterials.OBSIDIAN, 7.0F, -3.2F, settings));
    public static final Item COPPER_AXE = registerItem("copper_axe", settings -> new AxeItem(ModToolMaterials.COPPER, 6.0F, -3.1F, settings));
    public static final Item BRONZE_AXE = registerItem("bronze_axe", settings -> new AxeItem(ModToolMaterials.BRONZE, 6.0F, -3.1F, settings));
    // public static final Item STEEL_AXE = registerItem("steel_axe", AxeItem(ModToolMaterials.STEEL, 5.0F, -3.0f, Item.Settings()));

    // Pickaxes
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe", settings -> new PickaxeItem(ModToolMaterials.COPPER, 1.0F, -2.8F, settings));
    public static final Item BRONZE_PICKAXE = registerItem("bronze_pickaxe", settings -> new PickaxeItem(ModToolMaterials.BRONZE, 1.0F, -2.8F, settings));
    // public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe", PickaxeItem(ModToolMaterials.STEEL, 1, -2.8F, Item.Settings()));

    // Spears
    public static final Item STONE_SPEAR = registerItem("stone_spear", settings -> new StoneSpearItem(ToolMaterial.STONE, 1.0F, settings));
    public static final Item FLINT_SPEAR =  registerItem("flint_spear", settings -> new FlintSpearItem(ModToolMaterials.FLINT, 1.0F, settings));
    public static final Item BASALT_SPEAR =  registerItem("basalt_spear", settings -> new BasaltSpearItem(ModToolMaterials.BASALT, 1.0F, settings));
    public static final Item OBSIDIAN_SPEAR =  registerItem("obsidian_spear", settings -> new ObsidianSpearItem(ModToolMaterials.OBSIDIAN, 1.0F, settings));
    public static final Item COPPER_SPEAR =  registerItem("copper_spear", settings -> new CopperSpearItem(ModToolMaterials.COPPER, 1.0F, settings));
    public static final Item BRONZE_SPEAR =  registerItem("bronze_spear", settings -> new BronzeSpearItem(ModToolMaterials.BRONZE, 1.0F, settings));
    public static final Item IRON_SPEAR =  registerItem("iron_spear", settings -> new IronSpearItem(ToolMaterial.IRON, 1.0F, settings));
    // public static final Item STEEL_SPEAR =  registerItem("steel_spear", SpearItem(ModToolMaterials.STEEL, 3, Item.Settings()));

    // Knives
    public static final Item STONE_KNIFE = registerItem("stone_knife", settings -> new KnifeItem(ToolMaterial.STONE, settings));
    public static final Item FLINT_KNIFE =  registerItem("flint_knife", settings -> new KnifeItem(ModToolMaterials.FLINT, settings));
    public static final Item BASALT_KNIFE =  registerItem("basalt_knife", settings -> new KnifeItem(ModToolMaterials.BASALT, settings));
    public static final Item OBSIDIAN_KNIFE =  registerItem("obsidian_knife", settings -> new KnifeItem(ModToolMaterials.OBSIDIAN, settings));
    public static final Item COPPER_KNIFE =  registerItem("copper_knife", settings -> new KnifeItem(ModToolMaterials.COPPER, settings));
    public static final Item BRONZE_KNIFE =  registerItem("bronze_knife", settings -> new KnifeItem(ModToolMaterials.BRONZE, settings));
    public static final Item IRON_KNIFE =  registerItem("iron_knife", settings -> new KnifeItem(ToolMaterial.IRON, settings));
    // public static final Item STEEL_KNIFE =  registerItem("steel_knife", KnifeItem(ModToolMaterials.STEEL, 0, Item.Settings()));

    // Swords
    public static final Item COPPER_SWORD = registerItem("copper_sword", settings -> new SwordItem(ModToolMaterials.COPPER, 3.0F, -2.4F, settings));
    public static final Item BRONZE_SWORD = registerItem("bronze_sword", settings -> new SwordItem(ModToolMaterials.BRONZE, 3.0F, -2.4F, settings));
    // public static final Item STEEL_SWORD = registerItem("steel_sword", SwordItem(ModToolMaterials.STEEL, 3, -2.4f, Item.Settings()));

    // Ingots
    public static final Item TIN_INGOT = registerItem("tin_ingot", Item::new);
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot", Item::new);
    // public static final Item STEEL_INGOT = registerItem("steel_ingot", Item::new);

    // Arrows
    public static final Item STONE_ARROW = registerItem("stone_arrow", ArrowItem::new);
    public static final Item FLINT_ARROW = registerItem("flint_arrow", ArrowItem::new);
    public static final Item BASALT_ARROW = registerItem("basalt_arrow", ArrowItem::new);
    public static final Item OBSIDIAN_ARROW = registerItem("obsidian_arrow", ArrowItem::new);
    public static final Item COPPER_ARROW = registerItem("copper_arrow", ArrowItem::new);
    public static final Item BRONZE_ARROW = registerItem("bronze_arrow", ArrowItem::new);
    // public static final Item IRON_ARROW = registerItem("iron_arrow", ArrowItem::new);
    // public static final Item STEEL_ARROW = registerItem("steel_arrow", ArrowItem::new);

    // Tongs
    public static final Item WOODEN_TONGS = registerItem("wooden_tongs", TongItem::new);

    // Metal nuggets
    public static final Item TIN_NUGGET = registerItem("tin_nugget", Item::new);
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget", Item::new);

    // Seeds
    public static final Item FLAX_SEEDS = registerItem("flax_seeds", setting -> new BlockItem(ModBlocks.FLAX_CROP, setting));
    public static final Item COTTON_SEEDS = registerItem("cotton_seeds", setting -> new BlockItem(ModBlocks.COTTON_CROP, setting));

    // Crops
    public static final Item FLAX = registerItem("flax", Item::new);
    public static final Item COTTON = registerItem("cotton", Item::new);

    // Hammer TODO: create item type and metal variants
    public static final Item HAMMER = registerItem("hammer", Item::new);

    // TODO add shear variants

    public static final Item CLOTH = registerItem("cloth", Item::new);

    public static final Item PADDED_CLOTH = registerItem("padded_cloth", Item::new);
    public static final Item LEATHER_STRIPS = registerItem("leather_strips", Item::new);

    public static final Item COPPER_RINGS = registerItem("copper_rings", Item::new);
    public static final Item BRONZE_RINGS = registerItem("bronze_rings", Item::new);
    public static final Item IRON_RINGS = registerItem("iron_rings", Item::new);

    public static final Item COPPER_BANDS = registerItem("copper_bands", Item::new);
    public static final Item BRONZE_BANDS = registerItem("bronze_bands", Item::new);
    public static final Item IRON_BANDS = registerItem("iron_bands", Item::new);

    // Armours
    public static final Item GAMBESON = registerItem("gambeson", Item::new);
    public static final Item LEATHER_JERKIN = registerItem("leather_jerkin", Item::new);

    public static final Item LEATHER_LAMELLAR = registerItem("leather_lamellar", Item::new);
    public static final Item COPPER_LAMELLAR = registerItem("copper_lamellar", Item::new);
    public static final Item BRONZE_LAMELLAR = registerItem("bronze_lamellar", Item::new);
    public static final Item IRON_LAMELLAR = registerItem("iron_lamellar", Item::new);

    public static final Item COPPER_BRIGANDINE = registerItem("copper_brigandine", Item::new);
    public static final Item BRONZE_BRIGANDINE = registerItem("bronze_brigandine", Item::new);
    public static final Item IRON_BRIGANDINE = registerItem("iron_brigandine", Item::new);

    public static final Item COPPER_LAMINAR = registerItem("copper_laminar", Item::new);
    public static final Item BRONZE_LAMINAR = registerItem("bronze_laminar", Item::new);
    public static final Item IRON_LAMINAR = registerItem("iron_laminar", Item::new);

    public static final Item IRON_PLATE_HARNESS = registerItem("iron_plate_harness", Item::new);

    

    private static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FirstSteps.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        return Registry.register(Registries.ITEM, itemKey, item);
    } 

    public static Item registerItem(String id, Function<Item.Settings, Item> factory) {
		return register(keyOf(id), factory, new Item.Settings());
	}

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		Item item = (Item)factory.apply(settings.registryKey(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registries.ITEM, key, item);
	}

    private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FirstSteps.MOD_ID, id));
	}

    public static void registerModItems() {
        FirstSteps.LOGGER.info("Registering mod items for " + FirstSteps.MOD_ID);
    }
}


