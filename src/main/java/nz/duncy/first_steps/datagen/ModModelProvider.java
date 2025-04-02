package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantSetting;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.render.item.model.special.TridentModelRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.client.render.item.model.special.BasaltSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.BronzeSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.CopperSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.FlintSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.IronSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.ObsidianSpearModelRenderer;
import nz.duncy.first_steps.client.render.item.model.special.StoneSpearModelRenderer;
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
        // blockStateModelGenerator.registerAxisRotated(ModBlocks.BASALT_MULLITE_ORE, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);

        // RAW ORE BLOCK
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_TIN);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_STONE_COPPER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_STONE_IRON);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_DEEPSLATE_COPPER);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_DEEPSLATE_IRON);

        
        // ROCKS
        // blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.STONE_ROCK, new Identifier("minecraft:block/stone"));
        // blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FLINT_ROCK, new Identifier(FirstSteps.MOD_ID, "block/flint_rock"));
        // blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.BASALT_ROCK, new Identifier("minecraft:block/basalt_side"));
        // blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.OBSIDIAN_ROCK, new Identifier("minecraft:block/obsidian"));

        // KILN
        blockStateModelGenerator.registerCooker(ModBlocks.KILN, TexturedModel.ORIENTABLE_WITH_BOTTOM);

        // UNFIRED
        blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.UNFIRED_DECORATED_POT, Blocks.CLAY);

        // CLAY
        registerClays(blockStateModelGenerator);
    }

    private void registerClays(BlockStateModelGenerator blockStateModelGenerator) {
        // TextureMap textureMap = TextureMap.all(Blocks.CLAY);
        // Identifier identifier = Models.GENERATED.g .CUBE_ALL upload(Blocks.CLAY, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.CLAY).coordinate(BlockStateVariantMap.create(ModProperties.CLAY_LAYERS).register((height) -> {
           BlockStateVariant blockStateVariant = BlockStateVariant.create();
           VariantSetting<Identifier> variantSetting = VariantSettings.MODEL;
           Identifier id;
           if (height < 4) {
              id = ModelIds.getBlockSubModelId(ModBlocks.CLAY, "_height" + height * 4);
           } else {
            id = Identifier.ofVanilla("block/clay");
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
        // itemModelGenerator.register(ModItems.STEEL_HEAD_HOE, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_AXE, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_SHOVEL, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_SPEAR, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_KNIFE, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_SWORD, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_PICKAXE, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_HEAD_ARROW, Models.GENERATED);

        // Stone tools
        itemModelGenerator.register(ModItems.STONE_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.STONE_SPEAR, itemModelGenerator, new StoneSpearModelRenderer.Unbaked());

        // Flint tools
        itemModelGenerator.register(ModItems.FLINT_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FLINT_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.FLINT_SPEAR, itemModelGenerator, new FlintSpearModelRenderer.Unbaked());
        
        // Basalt tools
        itemModelGenerator.register(ModItems.BASALT_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BASALT_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.BASALT_SPEAR, itemModelGenerator, new BasaltSpearModelRenderer.Unbaked());

        // Obsidian tools
        itemModelGenerator.register(ModItems.OBSIDIAN_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.OBSIDIAN_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.OBSIDIAN_SPEAR, itemModelGenerator, new ObsidianSpearModelRenderer.Unbaked());

        // Copper tools
        itemModelGenerator.register(ModItems.COPPER_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.COPPER_SPEAR, itemModelGenerator, new CopperSpearModelRenderer.Unbaked());
        itemModelGenerator.register(ModItems.COPPER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.COPPER_PICKAXE, Models.HANDHELD);

        // Bronze tools
        itemModelGenerator.register(ModItems.BRONZE_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.BRONZE_SPEAR, itemModelGenerator, new BronzeSpearModelRenderer.Unbaked());
        itemModelGenerator.register(ModItems.BRONZE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BRONZE_PICKAXE, Models.HANDHELD);

        // Iron tools
        itemModelGenerator.register(ModItems.IRON_KNIFE, Models.HANDHELD);
        registerSpear(ModItems.IRON_SPEAR, itemModelGenerator, new IronSpearModelRenderer.Unbaked());

        // Steel tools
        // itemModelGenerator.register(ModItems.STEEL_HOE, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_SHOVEL, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_AXE, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_KNIFE, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_SPEAR, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_SWORD, Models.HANDHELD);
        // itemModelGenerator.register(ModItems.STEEL_PICKAXE, Models.HANDHELD);


        // Raw ores
        // Raw metals
        itemModelGenerator.register(ModItems.RAW_TIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_STONE_IRON, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_COPPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DEEPSLATE_IRON, Models.GENERATED);
        // Raw minerals
        // itemModelGenerator.register(ModItems.RAW_MULLITE, Models.GENERATED);

        // Ingots
        itemModelGenerator.register(ModItems.TIN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_INGOT, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);

        // Arrows
        itemModelGenerator.register(ModItems.STONE_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLINT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.BASALT_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSIDIAN_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.COPPER_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.BRONZE_ARROW, Models.GENERATED);
        itemModelGenerator.register(ModItems.IRON_ARROW, Models.GENERATED);
        // itemModelGenerator.register(ModItems.STEEL_ARROW, Models.GENERATED);

        // Fired pottery
        itemModelGenerator.register(ModBlocks.FIRED_CRUCIBLE.asItem(), Models.GENERATED);

        // Unfired pottery
        itemModelGenerator.register(ModBlocks.UNFIRED_CRUCIBLE.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.UNFIRED_FLOWER_POT.asItem(), Models.GENERATED);

        // Molds
        itemModelGenerator.register(ModBlocks.HOE_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.AXE_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SHOVEL_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.KNIFE_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SPEAR_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.ARROW_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.PICKAXE_HEAD_MOLD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SWORD_HEAD_MOLD.asItem(), Models.GENERATED);


        // // Tongs
        itemModelGenerator.register(ModItems.WOODEN_TONGS, Models.GENERATED);
    }

    private final void registerSpear(Item item, ItemModelGenerator itemModelGenerator, SpecialModelRenderer.Unbaked unbaked) {
		ItemModel.Unbaked unbakedBase = ItemModels.basic(itemModelGenerator.upload(item, Models.GENERATED));
		ItemModel.Unbaked unbakedHand = ItemModels.special(Identifier.of(FirstSteps.MOD_ID, "item/spear_in_hand"), unbaked);
		ItemModel.Unbaked unbakedThrowing = ItemModels.special(Identifier.of(FirstSteps.MOD_ID, "item/spear_throwing"), unbaked);
		ItemModel.Unbaked unbakedConditions = ItemModels.condition(ItemModels.usingItemProperty(), unbakedThrowing, unbakedHand);
		itemModelGenerator.output.accept(item, ItemModelGenerator.createModelWithInHandVariant(unbakedBase, unbakedConditions));
	}
}
