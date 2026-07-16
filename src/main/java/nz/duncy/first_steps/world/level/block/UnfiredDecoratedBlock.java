package nz.duncy.first_steps.world.level.block;

import java.util.Iterator;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import nz.duncy.first_steps.world.level.block.entity.UnfiredDecoratedBlockEntity;

public abstract class UnfiredDecoratedBlock extends HoriztonalFacingUnfiredBlock {
    public static final Identifier SHERDS_DYNAMIC_DROP_ID;


    public UnfiredDecoratedBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if (blockEntity instanceof UnfiredDecoratedBlockEntity) {
            if (level.isClientSide()) {
                return InteractionResult.CONSUME;
            } else {
                if (itemStack.is(ItemTags.DECORATED_POT_SHERDS)) {
                    UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity = (UnfiredDecoratedBlockEntity) blockEntity;

                    PotDecorations potDecorations = unfiredDecoratedBlockEntity.getDecorations();

                    switch (unfiredDecoratedBlockEntity.getDirection().ordinal()) {
                        case 3: // South
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 3: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 5: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 4: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 2: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 5: // East
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 5: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 2: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 3: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 4: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4: // West
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 4: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 3: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 2: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 5: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2: // Nouth
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 2: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 4: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 5: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 3: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }

                    unfiredDecoratedBlockEntity.setChanged();
                    level.sendBlockUpdated(unfiredDecoratedBlockEntity.getBlockPos(), unfiredDecoratedBlockEntity.getBlockState(), unfiredDecoratedBlockEntity.getBlockState(), Block.UPDATE_CLIENTS);
                }
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity) {
            builder.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, (consumer) -> {
                Iterator<Item> decorationsIterator = unfiredDecoratedBlockEntity.getDecorations().ordered().iterator();

                while(decorationsIterator.hasNext()) {
                    Item item = (Item)decorationsIterator.next();
                    if (item == Items.BRICK) {
                        item = Items.CLAY_BALL;
                    }
                    consumer.accept(item.getDefaultInstance());
                }

            });
        }

        return super.getDrops(blockState, builder);
    }

    protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        BlockEntity blockEntity = levelReader.getBlockEntity(blockPos);
        if (blockEntity instanceof UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity) {
            PotDecorations potDecorations = unfiredDecoratedBlockEntity.getDecorations();
            return unfiredDecoratedBlockEntity.createUnfiredDecoratedItem(potDecorations);
        } else {
            return super.getCloneItemStack(levelReader, blockPos, blockState, bl);
        }
    }

    static {
        SHERDS_DYNAMIC_DROP_ID = Identifier.withDefaultNamespace("sherds");
    }
}
