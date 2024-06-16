package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // ORES
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_TIN_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_IRON_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_COPPER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_IRON_ORE);
        blockStateModelGenerator.registerAxisRotated(ModBlocks.BASALT_MULLITE_ORE, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        
        // ROCKS
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.STONE_ROCK, new Identifier("minecraft:block/stone"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FLINT_ROCK, new Identifier(FirstSteps.MOD_ID, "block/flint_rock"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.BASALT_ROCK, new Identifier("minecraft:block/basalt_side"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.OBSIDIAN_ROCK, new Identifier("minecraft:block/obsidian"));

        // KILN
        blockStateModelGenerator.registerCooker(ModBlocks.KILN, TexturedModel.ORIENTABLE_WITH_BOTTOM);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Rock block items
        itemModelGenerator.register(ModBlocks.STONE_ROCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.BASALT_ROCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.OBSIDIAN_ROCK.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.COPPER_ROCK.asItem(), Models.GENERATED);

        // Tool heads
        // Stone tool heads
        itemModelGenerator.register(ModItems.STONE_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_ARROW, Models.GENERATED);

        // Flint tool heads
        itemModelGenerator.register(ModItems.FLINT_HEAD_ARROW, Models.GENERATED);

        // Basalt tool heads
        itemModelGenerator.register(ModItems.BASALT_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_ARROW, Models.GENERATED);

        // Obsidian tool heads
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_ARROW, Models.GENERATED);

        // Copper tool heads
        itemModelGenerator.register(ModItems.COPPER_HEAD_ARROW, Models.GENERATED);

        // Bronze tool heads
        itemModelGenerator.register(ModItems.BRONZE_HEAD_ARROW, Models.GENERATED);

        // Iron tool heads
        itemModelGenerator.register(ModItems.IRON_HEAD_ARROW, Models.GENERATED);

        // Steel tool heads
        itemModelGenerator.register(ModItems.STEEL_HEAD_ARROW, Models.GENERATED);

        // Stone tools
        itemModelGenerator.register(ModItems.STONE_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STONE_SPEAR, Models.GENERATED);
        
        // Blackstone tools
        itemModelGenerator.register(ModItems.BASALT_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SPEAR, Models.GENERATED);

        // Raw ores
        // Raw metals
        itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_IRON, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_IRON, Models.GENERATED);
        // Raw minerals
        itemModelGenerator.register(ModItems.RAW_MULLITE, Models.GENERATED);

        // Ingots
        itemModelGenerator.register(ModItems.TIN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_INGOT, Models.GENERATED);

        // Arrows
        itemModelGenerator.register(ModItems.STONE_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_ARROW, Models.GENERATED);
    }
}
