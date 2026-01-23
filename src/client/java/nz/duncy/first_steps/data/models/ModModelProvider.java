package nz.duncy.first_steps.data.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import nz.duncy.first_steps.data.models.model.ModModelTemplates;
import nz.duncy.first_steps.renderer.special.DecoratedJarSpecialRenderer;
import nz.duncy.first_steps.renderer.special.PottersWheelSpecialRenderer;
import nz.duncy.first_steps.renderer.special.UnfiredDecoratedJarSpecialRenderer;
import nz.duncy.first_steps.renderer.special.UnfiredDecoratedPotSpecialRenderer;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        createRock(blockStateModelGenerator, ModBlocks.STONE_ROCK, Blocks.STONE);
        createRock(blockStateModelGenerator, ModBlocks.FLINT_ROCK);
        createRock(blockStateModelGenerator, ModBlocks.BASALT_ROCK, Blocks.SMOOTH_BASALT);
        createRock(blockStateModelGenerator, ModBlocks.OBSIDIAN_ROCK, Blocks.OBSIDIAN);
        blockStateModelGenerator.createParticleOnlyBlock(ModBlocks.DECORATED_JAR, Blocks.TERRACOTTA);
        blockStateModelGenerator.generateSimpleSpecialItemModel(ModBlocks.DECORATED_JAR, new DecoratedJarSpecialRenderer.Unbaked());
        blockStateModelGenerator.createParticleOnlyBlock(ModBlocks.UNFIRED_DECORATED_POT, Blocks.CLAY);
        blockStateModelGenerator.generateSimpleSpecialItemModel(ModBlocks.UNFIRED_DECORATED_POT, new UnfiredDecoratedPotSpecialRenderer.Unbaked());
        blockStateModelGenerator.createParticleOnlyBlock(ModBlocks.UNFIRED_DECORATED_JAR, Blocks.CLAY);
        blockStateModelGenerator.generateSimpleSpecialItemModel(ModBlocks.UNFIRED_DECORATED_JAR, new UnfiredDecoratedJarSpecialRenderer.Unbaked());
        blockStateModelGenerator.createParticleOnlyBlock(ModBlocks.POTTERS_WHEEL, Blocks.OAK_LOG);
        blockStateModelGenerator.generateSimpleSpecialItemModel(ModBlocks.POTTERS_WHEEL, new PottersWheelSpecialRenderer.Unbaked());
        blockStateModelGenerator.createNormalTorch(ModBlocks.UNLIT_TORCH, ModBlocks.WALL_UNLIT_TORCH);
    }

    public final void createRock(BlockModelGenerators blockStateModelGenerator, Block block) {
        createRock(blockStateModelGenerator, block, block);
    }

    public final void createRock(BlockModelGenerators blockStateModelGenerator, Block block, Block textureBlock) {
        blockStateModelGenerator.registerSimpleFlatItemModel(block.asItem());
        TextureMapping textureMapping = TextureMapping.cube(TextureMapping.getBlockTexture(textureBlock));
        MultiVariant multiVariant1 = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModModelTemplates.ROCK.createWithSuffix(block, "_one_rock", textureMapping, blockStateModelGenerator.modelOutput)));
        MultiVariant multiVariant2 = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModModelTemplates.TWO_ROCKS.createWithSuffix(block, "_two_rocks", textureMapping, blockStateModelGenerator.modelOutput)));
        MultiVariant multiVariant3 = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModModelTemplates.THREE_ROCKS.createWithSuffix(block, "_three_rocks", textureMapping, blockStateModelGenerator.modelOutput)));
        MultiVariant multiVariant4 = BlockModelGenerators.createRotatedVariants(BlockModelGenerators.plainModel(ModModelTemplates.FOUR_ROCKS.createWithSuffix(block, "_four_rocks", textureMapping, blockStateModelGenerator.modelOutput)));
        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator
            .dispatch(block)
            .with(PropertyDispatch.initial(ModBlockStateProperties.ROCKS)
            .select(1, multiVariant1)
            .select(2, multiVariant2)
            .select(3, multiVariant3)
            .select(4, multiVariant4)));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        // Tool heads
        // Stone tool heads
        itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.STONE_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Flint tool heads
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.FLINT_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Basalt tool heads
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BASALT_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Obsidian tool heads
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Copper tool heads
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_SWORD, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_PICKAXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Bronze tool heads
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_SWORD, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_PICKAXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Iron tool heads
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_HOE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_AXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_SHOVEL, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_SPEAR, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_KNIFE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_SWORD, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_PICKAXE, ModelTemplates.FLAT_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.IRON_HEAD_ARROW, ModelTemplates.FLAT_ITEM);

        // Stone tools
        itemModelGenerator.generateFlatItem(ModItems.STONE_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);

        // Flint tools
        itemModelGenerator.generateFlatItem(ModItems.FLINT_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.FLINT_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateSpear(ModItems.FLINT_SPEAR);
        
        // Basalt tools
        itemModelGenerator.generateFlatItem(ModItems.BASALT_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BASALT_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateSpear(ModItems.BASALT_SPEAR);

        // Obsidian tools
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.OBSIDIAN_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateSpear(ModItems.OBSIDIAN_SPEAR);

        // Copper tools
        // itemModelGenerator.generateFlatItem(ModItems.COPPER_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);

        // Bronze tools
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);
        // itemModelGenerator.generateSpear(ModItems.BRONZE_SPEAR);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        // itemModelGenerator.generateFlatItem(ModItems.BRONZE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);

        // Iron tools
        // itemModelGenerator.generateFlatItem(ModItems.IRON_KNIFE, ModelTemplates.FLAT_HANDHELD_ITEM);

        // Firestarter
        itemModelGenerator.generateFlatItem(ModItems.FIRESTARTER, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerator.generateFlatItem(ModItems.POTTERS_WHEEL_HEAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.UNFIRED_POTTERS_WHEEL_HEAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.UNFIRED_BRICK, ModelTemplates.FLAT_ITEM);

        // itemModelGenerator.generate

    }

    @Override
	public String getName() {
		return "FirstStepsModelProvider";
	}
}
