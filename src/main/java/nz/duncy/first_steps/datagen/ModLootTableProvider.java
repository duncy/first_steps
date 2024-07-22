package nz.duncy.first_steps.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
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
import nz.duncy.first_steps.block.custom.CrucibleBlock;
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

        addDrop(ModBlocks.STONE_ROCK);
        addDrop(ModBlocks.FLINT_ROCK, Items.FLINT);
        addDrop(ModBlocks.BASALT_ROCK);
        addDrop(ModBlocks.OBSIDIAN_ROCK);
        addDrop(ModBlocks.COPPER_ROCK);
        
        addDrop(ModBlocks.KILN);

        // nameableContainerDrops(ModBlocks.CLAY_FIRED_CRUCIBLE);
        addDrop(ModBlocks.CLAY_FIRED_CRUCIBLE, crucibleDrops(ModBlocks.CLAY_FIRED_CRUCIBLE));
        addDrop(ModBlocks.CLAY_UNFIRED_CRUCIBLE);
    }

    public LootTable.Builder rawOreDrops(Block block, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(block, (LootPoolEntry.Builder<?>) this.applyExplosionDecay(block,
                    ((LeafEntry.Builder<?>) ItemEntry.builder(item)
                        .apply(SetCountLootFunction
                            .builder(UniformLootNumberProvider
                                .create(2.0f, 5.0f))))
                    .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    public LootTable.Builder crucibleDrops(Block drop) {
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
}