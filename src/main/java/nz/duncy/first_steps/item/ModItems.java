package nz.duncy.first_steps.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.custom.TinSourceItem;
import nz.duncy.first_steps.item.custom.AlumSourceItem;
import nz.duncy.first_steps.item.custom.CopperSourceItem;
import nz.duncy.first_steps.item.custom.IronSourceItem;
import nz.duncy.first_steps.item.custom.SpearItem;

public class ModItems {
    // Raw metals
    public static final Item RAW_TIN = registerItem("raw_tin", new TinSourceItem(new FabricItemSettings()));
    public static final Item RAW_ALUNITE = registerItem("raw_alunite", new AlumSourceItem(new FabricItemSettings()));
    public static final Item RAW_STONE_COPPER = registerItem("raw_stone_copper", new CopperSourceItem(new FabricItemSettings()));
    public static final Item RAW_STONE_IRON = registerItem("raw_stone_iron", new IronSourceItem(new FabricItemSettings()));
    public static final Item RAW_DEEPSLATE_COPPER = registerItem("raw_deepslate_copper", new CopperSourceItem(new FabricItemSettings()));
    public static final Item RAW_DEEPSLATE_IRON = registerItem("raw_deepslate_iron", new IronSourceItem(new FabricItemSettings()));

    // Tool heads
    // Axe heads
    public static final Item STONE_HEAD_AXE = registerItem("stone_head_axe", new Item(new FabricItemSettings())); 
    public static final Item BLACKSTONE_HEAD_AXE = registerItem("blackstone_head_axe", new Item(new FabricItemSettings()));
    
    // Shovel heads
    public static final Item STONE_HEAD_SHOVEL = registerItem("stone_head_shovel", new Item(new FabricItemSettings())); 
    public static final Item BLACKSTONE_HEAD_SHOVEL = registerItem("blackstone_head_shovel", new Item(new FabricItemSettings())); 
    
    // Knife heads
    public static final Item STONE_HEAD_KNIFE = registerItem("stone_head_knife", new Item(new FabricItemSettings()));
    public static final Item BLACKSTONE_HEAD_KNIFE = registerItem("blackstone_head_knife", new Item(new FabricItemSettings()));

    // Spear heads
    public static final Item STONE_HEAD_SPEAR = registerItem("stone_head_spear", new Item(new FabricItemSettings()));
    public static final Item BLACKSTONE_HEAD_SPEAR = registerItem("blackstone_head_spear", new Item(new FabricItemSettings()));
   
    // Axes
    public static final Item BLACKSTONE_AXE = registerItem("blackstone_axe", new AxeItem(ModToolMaterial.BLACKSTONE, 9, 0.8f, new FabricItemSettings())); 

    // Spears
    public static final Item BLACKSTONE_SPEAR =  registerItem("blackstone_spear", new SpearItem(ModToolMaterial.BLACKSTONE, 9, 0.8f, new FabricItemSettings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(STONE_HEAD_AXE);
        entries.add(BLACKSTONE_HEAD_AXE);

        entries.add(STONE_HEAD_SHOVEL);
        entries.add(BLACKSTONE_HEAD_SHOVEL);

        entries.add(STONE_HEAD_KNIFE);
        entries.add(BLACKSTONE_HEAD_KNIFE);
        
        entries.add(STONE_HEAD_SPEAR);
        entries.add(BLACKSTONE_HEAD_SPEAR);
        
        entries.add(BLACKSTONE_AXE);
        entries.add(BLACKSTONE_SPEAR);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(FirstSteps.MOD_ID, name), item);
    }

    public static void registerModItems() {
        FirstSteps.LOGGER.info("Registering mod items for " + FirstSteps.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
