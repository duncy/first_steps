package nz.duncy.first_steps.util.events;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.custom.RockBlock;
import nz.duncy.first_steps.item.custom.ModItems;
import nz.duncy.first_steps.loot.condition.BiomeLootCondition;

public class ModEvents {

    public static void registerModEvents() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isOf(Items.FLINT)) {
                    BlockHitResult hit = (BlockHitResult) player.raycast(5.0, 0f, false);
                    if (!(world.getBlockState(hit.getBlockPos()).getBlock() instanceof RockBlock)) {
                        ItemPlacementContext context = new ItemPlacementContext(player, hand, itemStack, hit);
                        return ((BlockItem) ModBlocks.FLINT_ROCK.asItem()).place(context);
                    }  
                }
            return ActionResult.PASS;
        });

        ItemTooltipCallback.EVENT.register((stack, context, type, text) -> {
            if (stack.getItem() == Items.CLAY_BALL) {
                text.add(1, Text.translatable("tooltip.first_steps.clay").formatted(Formatting.GRAY));
            } else if (stack.getItem() == Items.FLINT) {
                text.add(1, Text.translatable("tooltip.first_steps.rock_block").formatted(Formatting.GRAY));
            }
        });

        LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
            RegistryEntryLookup<Item> itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
            RegistryKey<LootTable> itemKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("blocks/short_grass"));

            if (key.equals(itemKey)) {
                tableBuilder.pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(ItemEntry.builder(ModItems.FLAX_SEEDS)
                        .conditionally(BiomeLootCondition.create().of(BiomeTags.IS_FOREST, BiomeTags.IS_TAIGA))
                        .conditionally(RandomChanceLootCondition.builder(0.25f))
                    )
                    .with(ItemEntry.builder(ModItems.COTTON_SEEDS)
                        .conditionally(BiomeLootCondition.create().of(BiomeTags.IS_JUNGLE, BiomeTags.IS_SAVANNA))
                        .conditionally(RandomChanceLootCondition.builder(0.25f))
                    )
                );

                tableBuilder.pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .with(ItemEntry.builder(Items.SHORT_GRASS)
                        .conditionally(MatchToolLootCondition.builder(
                            ItemPredicate.Builder.create().items(itemLookup, 
                                ModItems.STONE_KNIFE,
                                ModItems.FLINT_KNIFE,
                                ModItems.BASALT_KNIFE,
                                ModItems.OBSIDIAN_KNIFE,
                                ModItems.COPPER_KNIFE,
                                ModItems.BRONZE_KNIFE,
                                ModItems.IRON_KNIFE
                            )
                        )
                    ))    
                );
            }
        });


    }
}
