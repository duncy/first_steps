package nz.duncy.first_steps.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import nz.duncy.first_steps.world.level.block.UnlitTorchBlock;
import nz.duncy.first_steps.world.level.block.WallUnlitTorchBlock;

public class FireStarterItem extends Item {
    private static final float IGNITE_CHANCE = 0.25f;

    public FireStarterItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
            if (blockState.getBlock() instanceof UnlitTorchBlock unlitTorchBlock) {
                if (!level.isClientSide()) {
                    if (level.random.nextFloat() < IGNITE_CHANCE ) {
                        BlockState placeBlockState;
                        if (unlitTorchBlock instanceof WallUnlitTorchBlock) {
                            placeBlockState = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, level.getBlockState(blockPos).getValue(WallUnlitTorchBlock.FACING));
                        } else {
                            placeBlockState = Blocks.TORCH.defaultBlockState();
                        }
                        level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                        level.setBlock(blockPos, placeBlockState, Block.UPDATE_ALL);
                    } else {
                        level.playSound(null, blockPos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    }
                    useOnContext.getItemInHand().hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
                }
                return InteractionResult.SUCCESS;
            }

            BlockPos placeBlockPos = blockPos.relative(useOnContext.getClickedFace());
            if (BaseFireBlock.canBePlacedAt(level, placeBlockPos, useOnContext.getHorizontalDirection())) {
                ItemStack itemStack = useOnContext.getItemInHand();
                
                    if (!level.isClientSide()) {
                        if (level.random.nextFloat() < IGNITE_CHANCE ) {
                            BlockState placeBlockState = BaseFireBlock.getState(level, placeBlockPos);
                            level.playSound(null, placeBlockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                            level.setBlock(placeBlockPos, placeBlockState, Block.UPDATE_ALL_IMMEDIATE);
                            level.gameEvent(player, GameEvent.BLOCK_PLACE, placeBlockPos);
                            if (player instanceof ServerPlayer) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placeBlockPos, itemStack);
                            }
                        } else {
                            level.playSound(null, placeBlockPos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                        }

                        itemStack.hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
                    }

                return InteractionResult.SUCCESS;
            }
        } else {
            if (!level.isClientSide()) {
                if (level.random.nextFloat() < IGNITE_CHANCE ) {
                    level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    level.setBlock(blockPos, (BlockState)blockState.setValue(BlockStateProperties.LIT, true), Block.UPDATE_ALL_IMMEDIATE);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                } else {
                    level.playSound(null, blockPos, SoundEvents.GRASS_STEP, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                }

                useOnContext.getItemInHand().hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
