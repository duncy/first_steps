package nz.duncy.first_steps.world.item;

import java.util.function.Function;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModItems {
    // Items
    // Tool heads
    // Hoe heads
    public static final Item STONE_HEAD_HOE = registerItem("stone_head_hoe"); 
    public static final Item FLINT_HEAD_HOE = registerItem("flint_head_hoe");
    public static final Item BASALT_HEAD_HOE = registerItem("basalt_head_hoe");
    public static final Item OBSIDIAN_HEAD_HOE = registerItem("obsidian_head_hoe");
    // public static final Item COPPER_HEAD_HOE = registerItem("copper_head_hoe");
    // public static final Item BRONZE_HEAD_HOE = registerItem("bronze_head_hoe");
    // public static final Item IRON_HEAD_HOE = registerItem("iron_head_hoe");

    // Axe heads
    public static final Item STONE_HEAD_AXE = registerItem("stone_head_axe");
    public static final Item FLINT_HEAD_AXE = registerItem("flint_head_axe");
    public static final Item BASALT_HEAD_AXE = registerItem("basalt_head_axe");
    public static final Item OBSIDIAN_HEAD_AXE = registerItem("obsidian_head_axe");
    // public static final Item COPPER_HEAD_AXE = registerItem("copper_head_axe");
    // public static final Item BRONZE_HEAD_AXE = registerItem("bronze_head_axe");
    // public static final Item IRON_HEAD_AXE = registerItem("iron_head_axe");

    // Shovel heads
    public static final Item STONE_HEAD_SHOVEL = registerItem("stone_head_shovel");
    public static final Item FLINT_HEAD_SHOVEL = registerItem("flint_head_shovel");
    public static final Item BASALT_HEAD_SHOVEL = registerItem("basalt_head_shovel");
    public static final Item OBSIDIAN_HEAD_SHOVEL = registerItem("obsidian_head_shovel");
    // public static final Item COPPER_HEAD_SHOVEL = registerItem("copper_head_shovel");
    // public static final Item BRONZE_HEAD_SHOVEL = registerItem("bronze_head_shovel");
    // public static final Item IRON_HEAD_SHOVEL = registerItem("iron_head_shovel");

    // Knife heads
    public static final Item STONE_HEAD_KNIFE = registerItem("stone_head_knife");
    public static final Item FLINT_HEAD_KNIFE = registerItem("flint_head_knife");
    public static final Item BASALT_HEAD_KNIFE = registerItem("basalt_head_knife");
    public static final Item OBSIDIAN_HEAD_KNIFE = registerItem("obsidian_head_knife");
    // public static final Item COPPER_HEAD_KNIFE = registerItem("copper_head_knife");
    // public static final Item BRONZE_HEAD_KNIFE = registerItem("bronze_head_knife");
    // public static final Item IRON_HEAD_KNIFE = registerItem("iron_head_knife");

    // Spear heads
    public static final Item STONE_HEAD_SPEAR = registerItem("stone_head_spear");
    public static final Item FLINT_HEAD_SPEAR = registerItem("flint_head_spear");
    public static final Item BASALT_HEAD_SPEAR = registerItem("basalt_head_spear");
    public static final Item OBSIDIAN_HEAD_SPEAR = registerItem("obsidian_head_spear");
    // public static final Item COPPER_HEAD_SPEAR = registerItem("copper_head_spear");
    public static final Item BRONZE_HEAD_SPEAR = registerItem("bronze_head_spear");
    // public static final Item IRON_HEAD_SPEAR = registerItem("iron_head_spear");

    // Arrow heads
    // public static final Item STONE_HEAD_ARROW = registerItem("stone_head_arrow");
    // public static final Item FLINT_HEAD_ARROW = registerItem("flint_head_arrow");
    // public static final Item BASALT_HEAD_ARROW = registerItem("basalt_head_arrow");
    // public static final Item OBSIDIAN_HEAD_ARROW = registerItem("obsidian_head_arrow");
    // public static final Item COPPER_HEAD_ARROW = registerItem("copper_head_arrow");
    // public static final Item BRONZE_HEAD_ARROW = registerItem("bronze_head_arrow");
    // public static final Item IRON_HEAD_ARROW = registerItem("iron_head_arrow");

    // Pickaxe heads
    // public static final Item COPPER_HEAD_PICKAXE = registerItem("copper_head_pickaxe");
    // public static final Item BRONZE_HEAD_PICKAXE = registerItem("bronze_head_pickaxe");
    // public static final Item IRON_HEAD_PICKAXE = registerItem("iron_head_pickaxe");

    // Sword heads
    // public static final Item COPPER_HEAD_SWORD = registerItem("copper_head_sword");
    // public static final Item BRONZE_HEAD_SWORD = registerItem("bronze_head_sword");
    // public static final Item IRON_HEAD_SWORD = registerItem("iron_head_sword");

    // Tools
    // Hoes
    public static final Item FLINT_HOE = registerItem("flint_hoe", (properties) -> {
         return new HoeItem(ModToolMaterial.FLINT, -1.0F, -2.0F, properties);
    });
    public static final Item BASALT_HOE = registerItem("basalt_hoe", (properties) -> {
         return new HoeItem(ModToolMaterial.BASALT, -1.0F, -2.0F, properties);
    });
    public static final Item OBSIDIAN_HOE = registerItem("obsidian_hoe", (properties) -> {
         return new HoeItem(ModToolMaterial.OBSIDIAN, -1.0F, -2.0F, properties);
    });
    // public static final Item BRONZE_HOE = registerItem("bronze_hoe", (properties) -> {
    //      return new HoeItem(ModToolMaterial.BRONZE, -2.0F, -1.0F, properties);
    // });

    // Shovels
    public static final Item FLINT_SHOVEL = registerItem("flint_shovel", (properties) -> {
         return new ShovelItem(ModToolMaterial.FLINT, 1.5F, -3.0F, properties);
    });
    public static final Item BASALT_SHOVEL = registerItem("basalt_shovel", (properties) -> {
         return new ShovelItem(ModToolMaterial.BASALT, 1.5F, -3.0F, properties);
    });
    public static final Item OBSIDIAN_SHOVEL = registerItem("obsidian_shovel", (properties) -> {
         return new ShovelItem(ModToolMaterial.OBSIDIAN, 1.5F, -3.0F, properties);
    });
    // public static final Item BRONZE_SHOVEL = registerItem("bronze_shovel", (properties) -> {
    //      return new ShovelItem(ModToolMaterial.BRONZE, 1.5F, -3.0F, properties);
    // });

    // Axes
    public static final Item FLINT_AXE = registerItem("flint_axe", (properties) -> {
        return new AxeItem(ModToolMaterial.FLINT, 7.0F, -3.2F, properties);
    }); 
    public static final Item BASALT_AXE = registerItem("basalt_axe", (properties) -> {
        return new AxeItem(ModToolMaterial.BASALT, 7.0F, -3.2F, properties);
    }); 
    public static final Item OBSIDIAN_AXE = registerItem("obsidian_axe", (properties) -> {
        return new AxeItem(ModToolMaterial.OBSIDIAN, 7.0F, -3.2F, properties);
    }); 
    // public static final Item BRONZE_AXE = registerItem("bronze_axe", (properties) -> {
    //     return new AxeItem(ModToolMaterial.BRONZE, 6.0F, -3.1F, properties);
    // });

    // Pickaxes
    // public static final Item BRONZE_PICKAXE = registerItem("bronze_pickaxe", settings -> new PickaxeItem(ModToolMaterial.BRONZE, 1.0F, -2.8F, settings));

    // Spears
    public static final Item FLINT_SPEAR = registerItem("flint_spear", (new Item.Properties()).spear(ModToolMaterial.FLINT, 0.75F, 0.82F, 0.7F, 4.5F, 10.0F, 9.0F, 5.1F, 13.75F, 4.6F));
    public static final Item BASALT_SPEAR =  registerItem("basalt_spear", (new Item.Properties()).spear(ModToolMaterial.BASALT, 0.75F, 0.82F, 0.7F, 4.5F, 10.0F, 9.0F, 5.1F, 13.75F, 4.6F));
    public static final Item OBSIDIAN_SPEAR =  registerItem("obsidian_spear", (new Item.Properties()).spear(ModToolMaterial.OBSIDIAN, 0.75F, 0.82F, 0.7F, 4.5F, 10.0F, 9.0F, 5.1F, 13.75F, 4.6F));
    // public static final Item BRONZE_SPEAR = registerItem("bronze_spear", (new Item.Properties()).spear(ModToolMaterial.BRONZE, 0.80F, 0.82F, 0.65F, 3.0F, 9.0F, 7.25F, 5.1F, 11.5F, 4.6F));

    // Knives
    public static final Item STONE_KNIFE = registerItem("stone_knife", (new Item.Properties()).sword(ToolMaterial.STONE, -0.5F, 1.5F));
    public static final Item FLINT_KNIFE = registerItem("flint_knife", (new Item.Properties()).sword(ModToolMaterial.FLINT, -0.5F, 1.5F));
    public static final Item BASALT_KNIFE = registerItem("basalt_knife", (new Item.Properties()).sword(ModToolMaterial.BASALT, -0.5F, 1.5F));
    public static final Item OBSIDIAN_KNIFE = registerItem("obsidian_knife", (new Item.Properties()).sword(ModToolMaterial.OBSIDIAN, -0.5F, 1.5F));
    // public static final Item COPPER_KNIFE = registerItem("copper_knife", (new Item.Properties()).sword(ToolMaterial.COPPER, -0.5F, 1.0F));
    // public static final Item BRONZE_KNIFE = registerItem("bronze_knife", (new Item.Properties()).sword(ModToolMaterial.BRONZE, 0.0F, 1.5F));
    // public static final Item IRON_KNIFE = registerItem("iron_knife", (new Item.Properties()).sword(ToolMaterial.IRON, 0.0F, 1.5F)); 

    // Swords
    // public static final Item BRONZE_SWORD = registerItem("bronze_sword", (new Item.Properties()).sword(ModToolMaterial.BRONZE, 3.0F, -2.4F));

    // Firestarter
    public static final Item FIRESTARTER = registerItem("firestarter", FireStarterItem::new, (new Item.Properties()).durability(16));

    public static final Item POTTERS_WHEEL_HEAD = registerItem("potters_wheel_head");
    public static final Item UNFIRED_POTTERS_WHEEL_HEAD = registerItem("unfired_potters_wheel_head");
    public static final Item UNFIRED_BRICK = registerItem("unfired_brick");

    // Blocks
    public static final Item STONE_ROCK = Items.registerBlock(ModBlocks.STONE_ROCK, RockBlockItem::new);
    public static final Item BASALT_ROCK = Items.registerBlock(ModBlocks.BASALT_ROCK, RockBlockItem::new);
    public static final Item OBSIDIAN_ROCK = Items.registerBlock(ModBlocks.OBSIDIAN_ROCK, RockBlockItem::new);

    public static final Item DECORATED_JAR = Items.registerBlock(ModBlocks.DECORATED_JAR);

    public static final Item UNFIRED_DECORATED_POT = Items.registerBlock(ModBlocks.UNFIRED_DECORATED_POT);
    public static final Item UNFIRED_DECORATED_JAR = Items.registerBlock(ModBlocks.UNFIRED_DECORATED_JAR);

    public static final Item POTTERS_WHEEL = Items.registerBlock(ModBlocks.POTTERS_WHEEL);

    public static final Item UNLIT_TORCH = Items.registerBlock(ModBlocks.UNLIT_TORCH, (block, properties) -> {
        return new UnlitTorchBlockItem(block, ModBlocks.WALL_UNLIT_TORCH, Direction.DOWN, properties);
    });

    public static Item registerItem(String string) {
        return Items.registerItem(moddedItemId(string), Item::new, new Item.Properties());
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function) {
        return Items.registerItem(moddedItemId(string), function, new Item.Properties());
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties) {
        return Items.registerItem(moddedItemId(string), function, properties);
    }

    public static Item registerItem(String string, Item.Properties properties) {
        return Items.registerItem(moddedItemId(string), Item::new, properties);
    }

    private static ResourceKey<Item> moddedItemId(String string) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string));
    }

    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, "item_group"));
    public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.STONE_HEAD_AXE))
            .title(Component.translatable("itemGroup.first_steps"))
            .build();

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering mod items for " + FirstSteps.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
		.register((itemGroup) -> itemGroup.accept(ModItems.STONE_HEAD_KNIFE));

        // Register the group.
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            // Tools heads
            itemGroup.accept(ModItems.STONE_HEAD_HOE);
            itemGroup.accept(ModItems.FLINT_HEAD_HOE);
            itemGroup.accept(ModItems.BASALT_HEAD_HOE);
            itemGroup.accept(ModItems.OBSIDIAN_HEAD_HOE);
            // itemGroup.accept(ModItems.COPPER_HEAD_HOE);
            // itemGroup.accept(ModItems.BRONZE_HEAD_HOE);
            // itemGroup.accept(ModItems.IRON_HEAD_HOE);

            itemGroup.accept(ModItems.STONE_HEAD_AXE);
            itemGroup.accept(ModItems.FLINT_HEAD_AXE);
            itemGroup.accept(ModItems.BASALT_HEAD_AXE);
            itemGroup.accept(ModItems.OBSIDIAN_HEAD_AXE);
            // itemGroup.accept(ModItems.COPPER_HEAD_AXE);
            // itemGroup.accept(ModItems.BRONZE_HEAD_AXE);
            // itemGroup.accept(ModItems.IRON_HEAD_AXE);

            itemGroup.accept(ModItems.STONE_HEAD_SHOVEL);
            itemGroup.accept(ModItems.FLINT_HEAD_SHOVEL);
            itemGroup.accept(ModItems.BASALT_HEAD_SHOVEL);
            itemGroup.accept(ModItems.OBSIDIAN_HEAD_SHOVEL);
            // itemGroup.accept(ModItems.COPPER_HEAD_SHOVEL);
            // itemGroup.accept(ModItems.BRONZE_HEAD_SHOVEL);
            // itemGroup.accept(ModItems.IRON_HEAD_SHOVEL);

            itemGroup.accept(ModItems.STONE_HEAD_KNIFE);
            itemGroup.accept(ModItems.FLINT_HEAD_KNIFE);
            itemGroup.accept(ModItems.BASALT_HEAD_KNIFE);
            itemGroup.accept(ModItems.OBSIDIAN_HEAD_KNIFE);
            // itemGroup.accept(ModItems.COPPER_HEAD_KNIFE);
            // itemGroup.accept(ModItems.BRONZE_HEAD_KNIFE);
            // itemGroup.accept(ModItems.IRON_HEAD_KNIFE);

            itemGroup.accept(ModItems.STONE_HEAD_SPEAR);
            itemGroup.accept(ModItems.FLINT_HEAD_SPEAR);
            itemGroup.accept(ModItems.BASALT_HEAD_SPEAR);
            itemGroup.accept(ModItems.OBSIDIAN_HEAD_SPEAR);
            // itemGroup.accept(ModItems.COPPER_HEAD_SPEAR);
            // itemGroup.accept(ModItems.BRONZE_HEAD_SPEAR);
            // itemGroup.accept(ModItems.IRON_HEAD_SPEAR);

            // itemGroup.accept(ModItems.STONE_HEAD_ARROW);
            // itemGroup.accept(ModItems.FLINT_HEAD_ARROW);
            // itemGroup.accept(ModItems.BASALT_HEAD_ARROW);
            // itemGroup.accept(ModItems.OBSIDIAN_HEAD_ARROW);
            // itemGroup.accept(ModItems.COPPER_HEAD_ARROW);
            // itemGroup.accept(ModItems.BRONZE_HEAD_ARROW);
            // itemGroup.accept(ModItems.IRON_HEAD_ARROW);

            // itemGroup.accept(ModItems.COPPER_HEAD_SWORD);
            // itemGroup.accept(ModItems.BRONZE_HEAD_SWORD);
            // itemGroup.accept(ModItems.IRON_HEAD_SWORD);

            // itemGroup.accept(ModItems.COPPER_HEAD_PICKAXE);
            // itemGroup.accept(ModItems.BRONZE_HEAD_PICKAXE);
            // itemGroup.accept(ModItems.IRON_HEAD_PICKAXE);

            // Tools
            // Stone tools
            itemGroup.accept(ModItems.STONE_KNIFE);
            
            // Flint tools
            itemGroup.accept(ModItems.FLINT_HOE);
            itemGroup.accept(ModItems.FLINT_SHOVEL);
            itemGroup.accept(ModItems.FLINT_AXE);
            itemGroup.accept(ModItems.FLINT_KNIFE);
            itemGroup.accept(ModItems.FLINT_SPEAR);

            // Basalt tools
            itemGroup.accept(ModItems.BASALT_HOE);
            itemGroup.accept(ModItems.BASALT_SHOVEL);
            itemGroup.accept(ModItems.BASALT_AXE);
            itemGroup.accept(ModItems.BASALT_KNIFE);
            itemGroup.accept(ModItems.BASALT_SPEAR);

            // Obsidian tools
            itemGroup.accept(ModItems.OBSIDIAN_HOE);
            itemGroup.accept(ModItems.OBSIDIAN_SHOVEL);
            itemGroup.accept(ModItems.OBSIDIAN_AXE);
            itemGroup.accept(ModItems.OBSIDIAN_KNIFE);
            itemGroup.accept(ModItems.OBSIDIAN_SPEAR);

            // Copper tools
            // itemGroup.accept(ModItems.COPPER_KNIFE);

            // Bronze tools
            // itemGroup.accept(ModItems.BRONZE_HOE);
            // itemGroup.accept(ModItems.BRONZE_SHOVEL);
            // itemGroup.accept(ModItems.BRONZE_AXE);
            // itemGroup.accept(ModItems.BRONZE_KNIFE);
            // itemGroup.accept(ModItems.BRONZE_SPEAR);
            // itemGroup.accept(ModItems.BRONZE_PICKAXE);
            // itemGroup.accept(ModItems.BRONZE_SWORD);

            // Iron tools
            // itemGroup.accept(ModItems.IRON_KNIFE);

            // Firestarter
            itemGroup.accept(FIRESTARTER);

            itemGroup.accept(POTTERS_WHEEL_HEAD);
            itemGroup.accept(UNFIRED_POTTERS_WHEEL_HEAD);
            itemGroup.accept(UNFIRED_BRICK);

            // Blocks
            // Rocks
            itemGroup.accept(ModBlocks.STONE_ROCK);
            itemGroup.accept(ModBlocks.BASALT_ROCK);
            itemGroup.accept(ModBlocks.OBSIDIAN_ROCK);

            itemGroup.accept(ModBlocks.DECORATED_JAR);
            itemGroup.accept(ModBlocks.UNFIRED_DECORATED_POT);
            itemGroup.accept(ModBlocks.UNFIRED_DECORATED_JAR);
            itemGroup.accept(ModBlocks.POTTERS_WHEEL);

            itemGroup.accept(ModBlocks.UNLIT_TORCH);
        });
    }
}
