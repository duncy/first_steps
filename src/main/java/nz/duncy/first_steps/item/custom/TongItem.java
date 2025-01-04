package nz.duncy.first_steps.item.custom;

import java.util.List;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.custom.CrucibleBlock;
import nz.duncy.first_steps.block.custom.KilnBlock;
import nz.duncy.first_steps.block.entity.CrucibleBlockEntity;
import nz.duncy.first_steps.block.entity.KilnBlockEntity;
import nz.duncy.first_steps.component.ModDataComponentTypes;

public class TongItem extends Item {
    private int temperature;

    public TongItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
        temperature = 20;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (!isEmpty(stack)) {
            tooltip.add(Text.translatable("tooltip.first_steps.tongs.holding", getHeldItem(stack).getName()).formatted(Formatting.GRAY));

        } else {
            tooltip.add(Text.translatable("tooltip.first_steps.tongs.hint").formatted(Formatting.GRAY));
        }
        
        super.appendTooltip(stack, context, tooltip, options);
    }

    // @Override
    // public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    //     ItemStack itemStack = user.getStackInHand(hand);
    //     // if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
    //     //     return TypedActionResult.fail(itemStack);
    //     // }
    //     // user.setCurrentHand(hand);
    //     // return TypedActionResult.consume(itemStack);
    //     return TypedActionResult.success(itemStack);
    // }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        Block block = world.getBlockState(pos).getBlock();

        if (block instanceof KilnBlock) {
            KilnBlockEntity kilnBlockEntity = (KilnBlockEntity) context.getWorld().getBlockEntity(pos);
            if (pickupItem(context.getStack(), kilnBlockEntity)) {
                return ActionResult.SUCCESS;
            }
        } else if (block instanceof CrucibleBlock) {
            CrucibleBlockEntity crucibleBlockEntity = (CrucibleBlockEntity) context.getWorld().getBlockEntity(pos);
            // if (pickupBlock(context.getStack(), crucibleBlockEntity)) {
            //     return ActionResult.SUCCESS;
            // }
        } else {
            ActionResult actionResult = place(new ItemPlacementContext(context));
            if (actionResult.isAccepted()) {
                setNotHolding(context.getStack());
            }
            return actionResult;
        }

        return ActionResult.FAIL;
    }

    private static void setNotHolding(ItemStack stack) {
        stack.remove(ModDataComponentTypes.TEMPERATURE);
        stack.remove(DataComponentTypes.CONTAINER);
    }

    private ActionResult place(ItemPlacementContext context) {
        if (context.canPlace()) {
            ItemStack heldItemStack = getHeldItem(context.getStack());
            
            if (heldItemStack != null) {
                Item heldItem = heldItemStack.getItem();
                BlockState blockState = ((BlockItem) heldItem).getBlock().getPlacementState(context);
                if (canPlace(context, blockState)) {
                    FirstSteps.LOGGER.info("can place");
                    if (context.getWorld().setBlockState(context.getBlockPos(), blockState, 11)) {
                        BlockPos blockPos = context.getBlockPos();
                        World world = context.getWorld();
                        PlayerEntity playerEntity = context.getPlayer();
                        BlockState worldBlockState = world.getBlockState(blockPos);
                        if (worldBlockState.isOf(blockState.getBlock())) {
                            worldBlockState = placeFromNbt(blockPos, world, heldItemStack, worldBlockState);
                            postPlacement(blockPos, world, playerEntity, heldItemStack, worldBlockState);
                            if (playerEntity instanceof ServerPlayerEntity) {
                                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, heldItemStack);
                            }
                        }

                        BlockSoundGroup blockSoundGroup = worldBlockState.getSoundGroup();
                        world.playSound(playerEntity, blockPos, getPlaceSound(worldBlockState), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
                        world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, Emitter.of(playerEntity, worldBlockState));

                        return ActionResult.success(world.isClient);
                    }
                }  
            }
        } 

        return ActionResult.FAIL;
    }

    protected SoundEvent getPlaceSound(BlockState state) {
        return state.getSoundGroup().getPlaceSound();
    }

    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return BlockItem.writeNbtToBlockEntity(world, player, pos, stack);
    }

    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockStateComponent blockStateComponent = stack.getOrDefault(DataComponentTypes.BLOCK_STATE, BlockStateComponent.DEFAULT);

        if (blockStateComponent.isEmpty()) {
			return state;
		} else {
			BlockState blockState = blockStateComponent.applyToState(state);
			if (blockState != state) {
				world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
			}

			return blockState;
		}
     }

    private boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
     }

    public static boolean isEmpty(ItemStack itemStack) {
        return getHeldItem(itemStack) == null;
    }

    private boolean pickupItem(ItemStack tongs, KilnBlockEntity blockentity) {
        if (isEmpty(tongs)) {
            ItemStack crucibleStack = blockentity.getCrucible();
            Item crucible = crucibleStack.getItem();
            if (crucible instanceof BlockItem) {
                if (((BlockItem) crucible).getBlock() instanceof CrucibleBlock) {
                    blockentity.removeCrucible();
                    takeCrucible(tongs, crucibleStack);
                    return true;
                } 
            }
        }

        return false;
    }

    private boolean pickupBlock(ItemStack tongs, World world, BlockPos pos) {
        if (isEmpty(tongs)) {
            // if the tongs are empty and this method is active it is a crucible
            // get the itemstack of the crucible OR its nbt
            // remove the block
            return false;
        }

        return false;
    }

    private void takeCrucible(ItemStack tongs, ItemStack crucible) {
        tongs.set(ModDataComponentTypes.TEMPERATURE, crucible.getOrDefault(ModDataComponentTypes.TEMPERATURE, 20));
        tongs.set(DataComponentTypes.CONTAINER, crucible.get(DataComponentTypes.CONTAINER));
    }

    private static ItemStack getHeldItem(ItemStack tongs) {
        if (tongs.contains(ModDataComponentTypes.TEMPERATURE) && tongs.contains(DataComponentTypes.CONTAINER)) {
            ItemStack itemStack = new ItemStack(ModBlocks.FIRED_CRUCIBLE.asItem());
            itemStack.set(ModDataComponentTypes.TEMPERATURE, tongs.get(ModDataComponentTypes.TEMPERATURE));
            itemStack.set(DataComponentTypes.CONTAINER, tongs.get(DataComponentTypes.CONTAINER));
            return itemStack;
        }
        
        return null;
    }

}
