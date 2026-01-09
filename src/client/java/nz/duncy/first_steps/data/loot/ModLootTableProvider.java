package nz.duncy.first_steps.data.loot;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.RockBlock;
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

}
