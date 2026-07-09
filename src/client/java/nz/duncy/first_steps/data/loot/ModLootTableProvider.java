package nz.duncy.first_steps.data.loot;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.DecoratedJarBlock;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.RockBlock;
import nz.duncy.first_steps.world.level.block.UnfiredDecoratedBlock;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate.Builder;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropRock(ModBlocks.STONE_ROCK);
        dropRock(ModBlocks.FLINT_ROCK, Items.FLINT);
        dropRock(ModBlocks.BASALT_ROCK);
        dropRock(ModBlocks.OBSIDIAN_ROCK);

        add(ModBlocks.DECORATED_JAR, this::createDecoratedJarTable);
        add(ModBlocks.UNFIRED_DECORATED_POT, this::createUnfiredDecoratedTable);
        add(ModBlocks.UNFIRED_DECORATED_JAR, this::createUnfiredDecoratedTable);

        dropSelf(ModBlocks.POTTERS_WHEEL);

        dropSelf(ModBlocks.UNLIT_TORCH);
        dropSelf(ModBlocks.WALL_UNLIT_TORCH);

        add(ModBlocks.STONE_TIN_ORE, createOreDrop(ModBlocks.STONE_TIN_ORE, ModItems.RAW_STONE_TIN));
        add(ModBlocks.STONE_COPPER_ORE, createOreDrop(ModBlocks.STONE_COPPER_ORE, ModItems.RAW_STONE_COPPER));
        add(ModBlocks.STONE_IRON_ORE, createOreDrop(ModBlocks.STONE_IRON_ORE, ModItems.RAW_STONE_IRON));
        add(ModBlocks.DEEPSLATE_COPPER_ORE, createOreDrops(ModBlocks.DEEPSLATE_COPPER_ORE, ModItems.RAW_DEEPSLATE_COPPER, 3, 4));
        add(ModBlocks.DEEPSLATE_IRON_ORE, createOreDrops(ModBlocks.DEEPSLATE_IRON_ORE, ModItems.RAW_DEEPSLATE_IRON, 2, 3));

        dropSelf(ModBlocks.RAW_STONE_TIN);
        dropSelf(ModBlocks.RAW_STONE_COPPER);
        dropSelf(ModBlocks.RAW_STONE_IRON);
        dropSelf(ModBlocks.RAW_DEEPSLATE_COPPER);
        dropSelf(ModBlocks.RAW_DEEPSLATE_IRON);

        add(ModBlocks.CRUCIBLE, createShulkerBoxDrop(ModBlocks.CRUCIBLE));
    }

    private LootTable.Builder createDecoratedJarTable(Block block) {
        LootPoolSingletonContainer.Builder<?> entrySherds = DynamicLoot.dynamicEntry(DecoratedJarBlock.SHERDS_DYNAMIC_DROP_ID);

        LootItemBlockStatePropertyCondition.Builder hasBlockStateProperties = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);

        StatePropertiesPredicate.Builder isCracked = Builder.properties().hasProperty(DecoratedJarBlock.CRACKED, true);
        
        LootPoolSingletonContainer.Builder<?> blockItem = LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(DataComponents.POT_DECORATIONS));

        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add((entrySherds.when(hasBlockStateProperties.setProperties(isCracked))).otherwise(blockItem)));
    }

    private LootTable.Builder createUnfiredDecoratedTable(Block block) {
        LootPoolSingletonContainer.Builder<?> entrySherds = DynamicLoot.dynamicEntry(DecoratedJarBlock.SHERDS_DYNAMIC_DROP_ID);

        LootItemBlockStatePropertyCondition.Builder hasBlockStateProperties = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);

        StatePropertiesPredicate.Builder isCracked = Builder.properties().hasProperty(UnfiredDecoratedBlock.CRACKED, true);
        
        LootPoolSingletonContainer.Builder<?> blockItem = LootItem.lootTableItem(block).apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(DataComponents.POT_DECORATIONS));

        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add((entrySherds.when(hasBlockStateProperties.setProperties(isCracked))).otherwise(blockItem)));
    }
    
    private final void dropRock(Block brokenBlock) {
        dropRock(brokenBlock, brokenBlock);
    }

    private final void dropRock(Block brokenBlock, ItemLike item) {
        add(brokenBlock, (block) -> {
            return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(this.applyExplosionDecay(brokenBlock, LootItem.lootTableItem(item).apply(List.of(2, 3, 4), (integer) -> {
                    return SetItemCountFunction.setCount(ConstantValue.exactly((float)integer))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                        .setProperties(Builder.properties().hasProperty(RockBlock.ROCKS, integer)));
                    }
            ))));
        });
    }

    public LootTable.Builder createOreDrops(Block block, ItemLike item, float min, float max) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
    }
}
