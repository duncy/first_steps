package nz.duncy.first_steps.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.BlockHitResult;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.inventory.AnvilMenu;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;
import nz.duncy.first_steps.world.item.crafting.ModRecipeType;
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
            HolderGetter<Item> itemLookup = provider.lookupOrThrow(Registries.ITEM);
            Identifier locationID = key.identifier();
            String path = locationID.getPath();

            if (path.startsWith("blocks/")) {
                Identifier blockID = Identifier.withDefaultNamespace(path.substring(7));
                Block block = BuiltInRegistries.BLOCK.getOptional(blockID).orElse(null);

                if (block != null) {
                    BlockState blockState = block.defaultBlockState();
                    // if (blockState.is(BlockTags.LEAVES)) {
                    //     tableBuilder.pool(LootPool.lootPool()
                    //         .setRolls(ConstantValue.exactly(1))
                    //         .add(LootItem.lootTableItem(Items.STICK))
                    //         .when(LootItemRandomChanceCondition.randomChance(1.0f))
                    //         .build()
                    //     );
                    // } else 
                    if (blockState.is(Blocks.BUSH)) {
                        tableBuilder.pool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.STICK))
                            .when(LootItemRandomChanceCondition.randomChance(0.75f))
                            .build()
                        );
                    } else if (
                        blockState.is(Blocks.SHORT_GRASS) || 
                        blockState.is(Blocks.SHORT_DRY_GRASS) ||
                        blockState.is(Blocks.TALL_GRASS) ||
                        blockState.is(Blocks.TALL_DRY_GRASS)
                    ) {
                        tableBuilder.pool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.DRY_SHORT_GRASS))
                            .when(MatchTool.toolMatches(
                                ItemPredicate.Builder.item().of(itemLookup, 
                                    ModItems.STONE_KNIFE, 
                                    ModItems.FLINT_KNIFE,
                                    ModItems.BASALT_KNIFE,
                                    ModItems.OBSIDIAN_KNIFE
                                )
                            )).build()
                        );
                    }
                }
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof Zombie zombie) {
                if (zombie.getMainHandItem().isEmpty() && !zombie.isBaby()) {
                    DifficultyInstance difficultyInstance = level.getCurrentDifficultyAt(zombie.blockPosition());

                    float effectiveDifficulty = difficultyInstance.getEffectiveDifficulty();

                    float equipChance = Math.min(0.15f + effectiveDifficulty * 0.05f, 0.5f);

                    if (level.random.nextFloat() < equipChance) {
                        List<WeaponEntry> pool = null;

                        if (effectiveDifficulty >= 4.0f) {
                            pool = TIER_2;
                        } else if (effectiveDifficulty >= 2.0f) {
                            pool = TIER_1;
                        }

                        if (pool != null) {
                            WeaponEntry chosen = weightedPick(level.random, pool);

                            ItemStack weapon = new ItemStack(chosen.item());

                            zombie.setItemSlot(EquipmentSlot.MAINHAND, weapon);
                            zombie.setDropChance(EquipmentSlot.MAINHAND, chosen.dropChance());
                        }
                    }
                }
            }
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            ItemStack itemStack = player.getItemInHand(hand);
                if (itemStack.is(Items.FLINT)) {
                    BlockHitResult hit = (BlockHitResult) player.pick(5.0, 0f, false);
                    BlockPos pos = hit.getBlockPos();
                    Direction face = hit.getDirection();
                    BlockPos placePos = pos.relative(face);
                    BlockState placePosBlockState = level.getBlockState(placePos);
                    BlockState blockState = ModBlocks.FLINT_ROCK.defaultBlockState();

                    if (blockState.canSurvive(level, placePos) && placePosBlockState.canBeReplaced()) {
                        if (!level.isClientSide()) {
                            level.setBlock(placePos, blockState, 3);
                            level.playSound(
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
                        BlockState posBlockState = level.getBlockState(pos);
                        if (posBlockState.getBlock() == ModBlocks.FLINT_ROCK) {
                            int rocks = posBlockState.getValue(RockBlock.ROCKS);

                            if (rocks < RockBlock.MAX_ROCKS) {
                                if (!level.isClientSide()) {
                                    level.setBlock(pos, posBlockState.setValue(RockBlock.ROCKS, rocks + 1), 3);
                                    level.playSound(
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
                                if (!level.isClientSide()) {
                                    level.setBlock(placePos, placePosBlockState.setValue(RockBlock.ROCKS, rocks + 1), 3);
                                    level.playSound(
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

        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack ingotItemStack = player.getMainHandItem();
            ItemStack hammerItemStack = player.getOffhandItem();

            if (ingotItemStack.is(ModItemTags.SMITHABLE_INGOTS)) {
                if (hammerItemStack.is(ModItemTags.SMITHING_HAMMERS)) {
                    BlockPos pos = hitResult.getBlockPos();
                    BlockState blockState = level.getBlockState(pos);

                    if (blockState.is(BlockTags.ANVIL)) {
                        if (!level.isClientSide()) {
                            SingleInputSet<AnvilRecipe> recipes = getAnvilRecipes(level).selectByInput(ingotItemStack);

                            if (recipes.size() > 0) {
                                player.openMenu(getAnvilMenuProvider(blockState, level, pos, recipes, ingotItemStack, hammerItemStack));
                            }
                        }
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }


    private static SingleInputSet<AnvilRecipe> getAnvilRecipes(Level level) {
        Collection<RecipeHolder<AnvilRecipe>> all = ((ServerLevel) level).recipeAccess().getAllOfType(ModRecipeType.ANVIL_RECIPE);
        List<SelectableRecipe.SingleInputEntry<AnvilRecipe>> list = new ArrayList<>();
    
        for (RecipeHolder<AnvilRecipe> recipeHolder : all) {
            AnvilRecipe recipe = recipeHolder.value();
    
            SlotDisplay slotDisplay = recipe.resultDisplay();
    
            Optional<RecipeHolder<AnvilRecipe>> optional = Optional.of(recipeHolder);
    
            SelectableRecipe<AnvilRecipe> selectableRecipe = new SelectableRecipe<AnvilRecipe>(slotDisplay, optional);
    
            list.add(new SelectableRecipe.SingleInputEntry<AnvilRecipe>(recipe.input(), selectableRecipe));
        }
    
        return new SingleInputSet<AnvilRecipe>(list);
    }
    
    protected static MenuProvider getAnvilMenuProvider(BlockState blockState, Level level, BlockPos blockPos, SingleInputSet<AnvilRecipe> recipes, ItemStack ingotItemStack, ItemStack hammerItemStack) {
        return new SimpleMenuProvider((i, inventory, player) -> {
            return new AnvilMenu(i, inventory, recipes, blockPos, ingotItemStack, hammerItemStack);  
        }, Component.empty());
    }
}