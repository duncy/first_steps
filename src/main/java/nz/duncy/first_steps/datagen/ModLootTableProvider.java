package nz.duncy.first_steps.datagen;

import net.minecraft.predicate.StatePredicate.Builder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext.EntityTarget;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetContentsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.CopyNameLootFunction.Source;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.custom.ClayBlock;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.block.custom.UnfiredDecoratedPotBlock;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }   

    @Override
    public void generate() {
        addDrop(ModBlocks.STONE_TIN_ORE, rawOreDrops(ModBlocks.STONE_TIN_ORE, ModItems.RAW_TIN));
        addDrop(ModBlocks.STONE_COPPER_ORE, rawOreDrops(ModBlocks.STONE_COPPER_ORE, ModItems.RAW_STONE_COPPER));
        addDrop(ModBlocks.STONE_IRON_ORE, rawOreDrops(ModBlocks.STONE_IRON_ORE, ModItems.RAW_STONE_IRON));
        addDrop(ModBlocks.DEEPSLATE_COPPER_ORE, rawOreDrops(ModBlocks.DEEPSLATE_COPPER_ORE, ModItems.RAW_DEEPSLATE_COPPER));
        addDrop(ModBlocks.DEEPSLATE_IRON_ORE, rawOreDrops(ModBlocks.DEEPSLATE_IRON_ORE, ModItems.RAW_DEEPSLATE_IRON));
        addDrop(ModBlocks.BASALT_MULLITE_ORE, rawOreDrops(ModBlocks.BASALT_MULLITE_ORE, ModItems.RAW_MULLITE));

        addDrop(ModBlocks.RAW_TIN);
        addDrop(ModBlocks.RAW_STONE_COPPER);
        addDrop(ModBlocks.RAW_STONE_IRON);
        addDrop(ModBlocks.RAW_DEEPSLATE_COPPER);
        addDrop(ModBlocks.RAW_DEEPSLATE_IRON);

        addDrop(ModBlocks.STONE_ROCK);
        addDrop(ModBlocks.FLINT_ROCK, Items.FLINT);
        addDrop(ModBlocks.BASALT_ROCK);
        addDrop(ModBlocks.OBSIDIAN_ROCK);
        addDrop(ModBlocks.COPPER_ROCK);
        
        addDrop(ModBlocks.KILN);

        // nameableContainerDrops(ModBlocks.CLAY_FIRED_CRUCIBLE);
        addDrop(ModBlocks.FIRED_CRUCIBLE, crucibleDrops(ModBlocks.FIRED_CRUCIBLE));
        addDrop(ModBlocks.UNFIRED_CRUCIBLE);

        addDrop(ModBlocks.UNFIRED_DECORATED_POT, this::unfiredDecoratedPotDrops);

        addDrop(ModBlocks.CLAY, (block) -> {
            return LootTable.builder().pool(LootPool.builder().conditionally(EntityPropertiesLootCondition.create(EntityTarget.THIS)).with(AlternativeEntry.builder(new LootPoolEntry.Builder[]{AlternativeEntry.builder(ClayBlock.CLAY_LAYERS.getValues(), (integer) -> {
               return ((LeafEntry.Builder)ItemEntry.builder(Items.CLAY_BALL).conditionally(BlockStatePropertyLootCondition.builder(block).properties(Builder.create().exactMatch(ClayBlock.CLAY_LAYERS, integer)))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)integer)));
            }).conditionally(WITHOUT_SILK_TOUCH), AlternativeEntry.builder(ClayBlock.CLAY_LAYERS.getValues(), (integer) -> {
               return (LootPoolEntry.Builder)(integer == 4 ? ItemEntry.builder(Blocks.CLAY) : ItemEntry.builder(Items.CLAY_BALL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create((float)integer))).conditionally(BlockStatePropertyLootCondition.builder(block).properties(Builder.create().exactMatch(ClayBlock.CLAY_LAYERS, integer))));
            })})));
        });
    }

    private LootTable.Builder rawOreDrops(Block block, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(block, (LootPoolEntry.Builder<?>) this.applyExplosionDecay(block,
                    ((LeafEntry.Builder<?>) ItemEntry.builder(item)
                        .apply(SetCountLootFunction
                            .builder(UniformLootNumberProvider
                                .create(2.0f, 5.0f))))
                    .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    private LootTable.Builder crucibleDrops(Block drop) {
        return LootTable.builder().pool(
            (LootPool.Builder) this.addSurvivesExplosionCondition(drop, LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0F))
                .with(ItemEntry.builder(drop)
                    .apply(CopyNameLootFunction.builder(Source.BLOCK_ENTITY))
                    .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                        .withOperation("Lock", "BlockEntityTag.Lock")
                        .withOperation("LootTable", "BlockEntityTag.LootTable")
                        .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed"))
                    .apply(SetContentsLootFunction.builder(ModBlockEntities.CRUCIBLE_BLOCK_ENTITY)
                        .withEntry(DynamicEntry.builder(CrucibleBlock.CONTENTS_DYNAMIC_DROP_ID))))));
    }

    private LootTable.Builder unfiredDecoratedPotDrops(Block block) {
        LootCondition.Builder ifCracked = BlockStatePropertyLootCondition.builder(block).properties(Builder.create().exactMatch(UnfiredDecoratedPotBlock.CRACKED, true));

        LootPoolEntry.Builder entryClay =  ItemEntry.builder(Items.CLAY_BALL).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0F)));

        LootPoolEntry.Builder entryPot =  ItemEntry.builder(block).apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                          .withOperation("sherds", "BlockEntityTag.sherds"))
                                            .conditionally(BlockStatePropertyLootCondition.builder(block)
                                            .properties(Builder.create().exactMatch(UnfiredDecoratedPotBlock.CRACKED, false)));

        LootPoolEntry.Builder entrySherds =  DynamicEntry.builder(UnfiredDecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID);

        LootPool.Builder sherdPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(ifCracked).with(entrySherds);

        LootPool.Builder clayPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(ifCracked).with(entryClay);

        LootPool.Builder potPool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(ifCracked.invert()).with(entryPot);

        return LootTable.builder().pool(sherdPool).pool(clayPool).pool(potPool);
        
    }

}

