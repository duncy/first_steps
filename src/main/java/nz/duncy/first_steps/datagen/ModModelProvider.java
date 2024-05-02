package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;
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
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BASALT_MULLITE_ORE);

        // ROCKS
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.STONE_ROCK, new Identifier("minecraft:block/stone"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.BASALT_ROCK, new Identifier("minecraft:block/basalt_side"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.OBSIDIAN_ROCK, new Identifier("minecraft:block/obsidian"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Rock block items
        itemModelGenerator.register(ModBlocks.STONE_ROCK.asItem(), Models.HANDHELD);
        itemModelGenerator.register(ModBlocks.BASALT_ROCK.asItem(), Models.HANDHELD);
        itemModelGenerator.register(ModBlocks.OBSIDIAN_ROCK.asItem(), Models.HANDHELD);

        // Tool heads
        // Stone tool heads
        itemModelGenerator.register(ModItems.STONE_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_KNIFE, Models.GENERATED);

        // Blackstone tool heads
        itemModelGenerator.register(ModItems.BASALT_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_KNIFE, Models.GENERATED);

        // Blackstone tools
        itemModelGenerator.register(ModItems.BASALT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SPEAR, Models.GENERATED);

        // Raw ores
        // Raw metals
        itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_IRON, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_IRON, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MULLITE, Models.GENERATED);
    }
}
