package nz.duncy.first_steps.events;

import java.util.List;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.BlockHitResult;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.RockBlock;

public class ModEvents {
    record WeaponEntry(Item item, float weight, float dropChance) {}

    static final List<WeaponEntry> TIER_1 = List.of(
        new WeaponEntry(ModItems.FLINT_KNIFE, 0.3f, 0.03f),
        new WeaponEntry(ModItems.FLINT_SHOVEL, 0.4f, 0.03f),
        new WeaponEntry(ModItems.STONE_KNIFE, 0.3f, 0.03f)
    );

    static final List<WeaponEntry> TIER_2 = List.of(
        new WeaponEntry(ModItems.FLINT_SPEAR, 0.2f, 0.05f),
        new WeaponEntry(ModItems.FLINT_AXE, 0.2f, 0.05f),
        new WeaponEntry(ModItems.FLINT_KNIFE, 0.3f, 0.03f),
        new WeaponEntry(ModItems.STONE_KNIFE, 0.3f, 0.03f)
    );

    private static WeaponEntry weightedPick(RandomSource rand, List<WeaponEntry> entries) {
        float totalWeight = 0.0f;
        for (WeaponEntry entry : entries) {
            totalWeight += entry.weight();
        }

        float roll = rand.nextFloat() * totalWeight;

        for (WeaponEntry entry : entries) {
            roll -= entry.weight();
            if (roll <= 0.0f) {
                return entry;
            }
        }

        // Should never happen, but keeps the compiler happy
        return entries.get(entries.size() - 1);
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering events for " + FirstSteps.MOD_ID);

        LootTableEvents.MODIFY.register((key, tableBuilder, source, provider) -> {
            // Reference<Registry<Item>> itemLookup = provider.getOrThrow(Registries.ITEM);
            Identifier locationID = key.identifier();
            String path = locationID.getPath();

            if (path.startsWith("blocks/")) {
                Identifier blockID = Identifier.withDefaultNamespace(path.substring(7));
                Block block = BuiltInRegistries.BLOCK.getOptional(blockID).orElse(null);

                if (block != null) {
                    BlockState blockState = block.defaultBlockState();
                    if (blockState.is(BlockTags.LEAVES)) {
                        tableBuilder.pool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.STICK))
                            .when(LootItemRandomChanceCondition.randomChance(0.25f))
                            .build()
                        );
                    } else if (blockState.is(Blocks.BUSH)) {
                        tableBuilder.pool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.STICK))
                            .when(LootItemRandomChanceCondition.randomChance(0.75f))
                            .build()
                        );
                    }
                }
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Zombie zombie) {
                if (zombie.getMainHandItem().isEmpty() && !zombie.isBaby()) {
                    DifficultyInstance difficultyInstance = world.getCurrentDifficultyAt(zombie.blockPosition());

                    float effectiveDifficulty = difficultyInstance.getEffectiveDifficulty();

                    float equipChance = Math.min(0.15f + effectiveDifficulty * 0.05f, 0.5f);

                    if (world.random.nextFloat() < equipChance) {
                        List<WeaponEntry> pool = null;

                        if (effectiveDifficulty >= 4.0f) {
                            pool = TIER_2;
                        } else if (effectiveDifficulty >= 2.0f) {
                            pool = TIER_1;
                        }

                        if (pool != null) {
                            WeaponEntry chosen = weightedPick(world.random, pool);

                            ItemStack weapon = new ItemStack(chosen.item());

                            zombie.setItemSlot(EquipmentSlot.MAINHAND, weapon);
                            zombie.setDropChance(EquipmentSlot.MAINHAND, chosen.dropChance());
                        }
                    }
                }
            }
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(Items.FLINT)) {
                    BlockHitResult hit = (BlockHitResult) player.pick(5.0, 0f, false);
                    BlockPos pos = hit.getBlockPos();
                    Direction face = hit.getDirection();
                    BlockPos placePos = pos.relative(face);
                    BlockState placePosBlockState = world.getBlockState(placePos);
                    BlockState blockState = ModBlocks.FLINT_ROCK.defaultBlockState();

                    if (blockState.canSurvive(world, placePos) && placePosBlockState.canBeReplaced()) {
                        if (!world.isClientSide()) {
                            world.setBlock(placePos, blockState, 3);
                            world.playSound(
                                null,
                                placePos,
                                blockState.getSoundType().getPlaceSound(),
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                            );
                            if (!player.getAbilities().instabuild) itemStack.shrink(1);
                           
                        }
                        return InteractionResult.SUCCESS;
                    } else {
                        BlockState posBlockState = world.getBlockState(pos);
                        if (posBlockState.getBlock() == ModBlocks.FLINT_ROCK) {
                            int rocks = posBlockState.getValue(RockBlock.ROCKS);

                            if (rocks < RockBlock.MAX_ROCKS) {
                                if (!world.isClientSide()) {
                                    world.setBlock(pos, posBlockState.setValue(RockBlock.ROCKS, rocks + 1), 3);
                                    world.playSound(
                                        null,
                                        pos,
                                        posBlockState.getSoundType().getPlaceSound(),
                                        SoundSource.BLOCKS,
                                        1.0F,
                                        1.0F
                                    );

                                    if (!player.getAbilities().instabuild) itemStack.shrink(1);
                                }
                                return InteractionResult.SUCCESS;
                            }
                        } else if (placePosBlockState.getBlock() == ModBlocks.FLINT_ROCK) {
                            int rocks = placePosBlockState.getValue(RockBlock.ROCKS);

                            if (rocks < RockBlock.MAX_ROCKS) {
                                if (!world.isClientSide()) {
                                    world.setBlock(placePos, placePosBlockState.setValue(RockBlock.ROCKS, rocks + 1), 3);
                                    world.playSound(
                                        null,
                                        placePos,
                                        placePosBlockState.getSoundType().getPlaceSound(),
                                        SoundSource.BLOCKS,
                                        1.0F,
                                        1.0F
                                    );

                                    if (!player.getAbilities().instabuild) itemStack.shrink(1);
                                }
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }                      
                }
            return InteractionResult.PASS;
        });
    }
}
