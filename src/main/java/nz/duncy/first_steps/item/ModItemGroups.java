package nz.duncy.first_steps.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup FIRST_STEPS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(FirstSteps.MOD_ID, "clay_fired_crucible"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.first_steps"))
                .icon(() -> new ItemStack(ModItems.STONE_HEAD_AXE)).entries((displayContext, entries) -> {
                    // Tools heads
                    entries.add(ModItems.STONE_HEAD_AXE);
                    entries.add(ModItems.BASALT_HEAD_AXE);

                    entries.add(ModItems.STONE_HEAD_SHOVEL);
                    entries.add(ModItems.BASALT_HEAD_SHOVEL);

                    entries.add(ModItems.STONE_HEAD_KNIFE);
                    entries.add(ModItems.BASALT_HEAD_KNIFE);

                    entries.add(ModItems.STONE_HEAD_SPEAR);
                    entries.add(ModItems.BASALT_HEAD_SPEAR);

                    entries.add(ModItems.STONE_HEAD_ARROW);
                    entries.add(ModItems.FLINT_HEAD_ARROW);
                    entries.add(ModItems.BASALT_HEAD_ARROW);
                    entries.add(ModItems.OBSIDIAN_HEAD_ARROW);
                    entries.add(ModItems.COPPER_HEAD_ARROW);
                    entries.add(ModItems.BRONZE_HEAD_ARROW);
                    entries.add(ModItems.IRON_HEAD_ARROW);
                    entries.add(ModItems.STEEL_HEAD_ARROW);

                    // Raw metals
                    entries.add(ModItems.RAW_TIN);
                    entries.add(ModItems.RAW_STONE_COPPER);
                    entries.add(ModItems.RAW_STONE_IRON);
                    entries.add(ModItems.RAW_DEEPSLATE_COPPER);
                    entries.add(ModItems.RAW_DEEPSLATE_IRON);
                    entries.add(ModItems.RAW_MULLITE);

                    // Ore blocks
                    entries.add(ModBlocks.STONE_TIN_ORE);
                    entries.add(ModBlocks.STONE_COPPER_ORE);
                    entries.add(ModBlocks.STONE_IRON_ORE);
                    entries.add(ModBlocks.DEEPSLATE_COPPER_ORE);
                    entries.add(ModBlocks.DEEPSLATE_IRON_ORE);
                    entries.add(ModBlocks.BASALT_MULLITE_ORE);

                    // Rock blocks
                    entries.add(ModBlocks.STONE_ROCK);
                    entries.add(ModBlocks.FLINT_ROCK);
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
                    entries.add(ModBlocks.CLAY);
                    
                    // Tools
                    // Stone tools
                    entries.add(ModItems.STONE_KNIFE);
                    entries.add(ModItems.STONE_SPEAR);
                    
                    // Basalt tools
                    entries.add(ModItems.BASALT_HOE);
                    entries.add(ModItems.BASALT_SHOVEL);
                    entries.add(ModItems.BASALT_AXE);
                    entries.add(ModItems.BASALT_KNIFE);
                    entries.add(ModItems.BASALT_SPEAR);

                    // Ingots
                    entries.add(ModItems.TIN_INGOT);
                    entries.add(ModItems.BRONZE_INGOT);

                    // Arrows
                    entries.add(ModItems.STONE_ARROW);
                    entries.add(ModItems.FLINT_ARROW);
                    entries.add(ModItems.BASALT_ARROW);
                    entries.add(ModItems.OBSIDIAN_ARROW);
                    entries.add(ModItems.COPPER_ARROW);
                    entries.add(ModItems.BRONZE_ARROW);
                    entries.add(ModItems.IRON_ARROW);
                    entries.add(ModItems.STEEL_ARROW);

                }).build());

    public static void registerItemGroups() {
        FirstSteps.LOGGER.info("Registering Item Groups for" + FirstSteps.MOD_ID);
    }
}

