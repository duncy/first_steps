package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.client.VariantSetting;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.item.ModItems;
import nz.duncy.first_steps.state.ModProperties;

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

        // RAW ORE BLOCK
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_TIN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_STONE_COPPER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_STONE_IRON);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_DEEPSLATE_COPPER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_DEEPSLATE_IRON);

        
        // ROCKS
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.STONE_ROCK, new Identifier("minecraft:block/stone"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FLINT_ROCK, new Identifier(FirstSteps.MOD_ID, "block/flint_rock"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.BASALT_ROCK, new Identifier("minecraft:block/basalt_side"));
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.OBSIDIAN_ROCK, new Identifier("minecraft:block/obsidian"));

        // KILN
        blockStateModelGenerator.registerCooker(ModBlocks.KILN, TexturedModel.ORIENTABLE_WITH_BOTTOM);

        // UNFIRED
        blockStateModelGenerator.registerBuiltin(ModBlocks.UNFIRED_DECORATED_POT, Blocks.CLAY).includeWithoutItem(new Block[]{ModBlocks.UNFIRED_DECORATED_POT});

        // CLAY
        registerClays(blockStateModelGenerator);
    }

    private void registerClays(BlockStateModelGenerator blockStateModelGenerator) {
        TextureMap textureMap = TextureMap.all(Blocks.CLAY);
        // Identifier identifier = Models.GENERATED.g .CUBE_ALL upload(Blocks.CLAY, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.CLAY).coordinate(BlockStateVariantMap.create(ModProperties.CLAY_LAYERS).register((height) -> {
           BlockStateVariant blockStateVariant = BlockStateVariant.create();
           VariantSetting variantSetting = VariantSettings.MODEL;
           Identifier id;
           if (height < 4) {
              id = ModelIds.getBlockSubModelId(ModBlocks.CLAY, "_height" + height * 4);
           } else {
            id = new Identifier("minecraft:block/clay");
           }
  
           return blockStateVariant.put(variantSetting, id);
        })));
        // blockStateModelGenerator.blockStateCollector.accept(blockStateModelGenerator.createSingletonBlockState(Blocks.CLAY, identifier));
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
        itemModelGenerator.register(ModItems.STONE_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STONE_HEAD_ARROW, Models.GENERATED);

        // Flint tool heads
        itemModelGenerator.register(ModItems.FLINT_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_HEAD_ARROW, Models.GENERATED);

        // Basalt tool heads
        itemModelGenerator.register(ModItems.BASALT_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_HEAD_ARROW, Models.GENERATED);

        // Obsidian tool heads
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_HEAD_ARROW, Models.GENERATED);

        // Copper tool heads
        itemModelGenerator.register(ModItems.COPPER_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_SWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_PICKAXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_HEAD_ARROW, Models.GENERATED);

        // Bronze tool heads
        itemModelGenerator.register(ModItems.BRONZE_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_SWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_PICKAXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_HEAD_ARROW, Models.GENERATED);

        // Iron tool heads
        itemModelGenerator.register(ModItems.IRON_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_SWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_PICKAXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_HEAD_ARROW, Models.GENERATED);

        // Steel tool heads
        itemModelGenerator.register(ModItems.STEEL_HEAD_HOE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_AXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_SHOVEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_SPEAR, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_KNIFE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_SWORD, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_PICKAXE, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_HEAD_ARROW, Models.GENERATED);

        // Stone tools
        itemModelGenerator.register(ModItems.STONE_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STONE_SPEAR, Models.GENERATED);

        // Flint tools
        itemModelGenerator.register(ModItems.FLINT_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_SPEAR, Models.GENERATED);
        
        // Blackstone tools
        itemModelGenerator.register(ModItems.BASALT_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SPEAR, Models.GENERATED);

        // Obsidian tools
        itemModelGenerator.register(ModItems.OBSIDIAN_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_SPEAR, Models.GENERATED);

        // Copper tools
        itemModelGenerator.register(ModItems.COPPER_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SPEAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_PICKAXE, Models.HANDHELD);

        // Bronze tools
        itemModelGenerator.register(ModItems.BRONZE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_SPEAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_PICKAXE, Models.HANDHELD);

        // Iron tools
        itemModelGenerator.register(ModItems.IRON_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.IRON_SPEAR, Models.HANDHELD);

        // Steel tools
        itemModelGenerator.register(ModItems.STEEL_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_KNIFE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_SPEAR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.STEEL_PICKAXE, Models.HANDHELD);


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
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);

        // Arrows
        itemModelGenerator.register(ModItems.STONE_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.STEEL_ARROW, Models.GENERATED);

        // Crucible
        itemModelGenerator.register(ModBlocks.FIRED_CRUCIBLE.asItem(), Models.GENERATED);

        // Unfireds
        itemModelGenerator.register(ModBlocks.UNFIRED_CRUCIBLE.asItem(), Models.GENERATED);

        // // Tongs
        itemModelGenerator.register(ModItems.WOODEN_TONGS, Models.GENERATED);
    }
}
