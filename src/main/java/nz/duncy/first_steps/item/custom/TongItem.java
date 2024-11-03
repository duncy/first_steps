package nz.duncy.first_steps.item.custom;

import java.util.List;
import java.util.Iterator;

import org.jetbrains.annotations.Nullable;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
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

public class TongItem extends Item {
    private int temperature;

    public TongItem(Settings settings) {
        super(settings);
        temperature = 20;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (!isEmpty(stack)) {
            tooltip.add(Text.translatable("tooltip.first_steps.tongs.holding", getHeldItem(stack).getName()).formatted(Formatting.GRAY));

        } else {
            tooltip.add(Text.translatable("tooltip.first_steps.tongs.hint").formatted(Formatting.GRAY));
        }
        
        super.appendTooltip(stack, world, tooltip, context);
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
            FirstSteps.LOGGER.info("kiln block interaction");
            if (pickupItem(context.getStack(), kilnBlockEntity)) {
                FirstSteps.LOGGER.info("crucibo should be taken");
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
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        NbtList nbtList = new NbtList();
        nbtCompound.put("tongs.holding", nbtList);
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

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return (BlockState)property.parse(name).map((value) -> {
           return (BlockState)state.with(property, value);
        }).orElse(state);
    }

    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
           NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
           StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();
           Iterator<String> nbtIterator = nbtCompound2.getKeys().iterator();
  
           while(nbtIterator.hasNext()) {
              String string = (String)nbtIterator.next();
              Property<?> property = stateManager.getProperty(string);
              if (property != null) {
                 String string2 = nbtCompound2.get(string).asString();
                 blockState = with(blockState, property, string2);
              }
           }
        }
  
        if (blockState != state) {
           world.setBlockState(pos, blockState, 2);
        }
  
        return blockState;
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
                    putCrucible(tongs, crucibleStack);
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

    private void putCrucible(ItemStack tongs, ItemStack crucible) {
        NbtCompound nbtCompound = tongs.getOrCreateNbt();
        NbtList nbtList;
        if (nbtCompound.contains("tongs.holding", 9)) {
            nbtList = nbtCompound.getList("tongs.holding", 10);
        } else {
            nbtList = new NbtList();
        }

        nbtList.add(crucible.getNbt());
        FirstSteps.LOGGER.info("nbtlist: " + String.valueOf(nbtList));
        nbtCompound.put("tongs.holding", nbtList);
    }

    private static ItemStack getHeldItem(ItemStack tongs) {
        NbtCompound nbtCompound = tongs.getNbt(); 

        if (nbtCompound != null && nbtCompound.contains("tongs.holding", 9)) {
            NbtList nbtList = nbtCompound.getList("tongs.holding", 10);
            if (!nbtList.isEmpty()) {
                NbtCompound nbtListCompound = nbtList.getCompound(0);
                ItemStack itemStack = new ItemStack(ModBlocks.FIRED_CRUCIBLE.asItem());
                itemStack.setNbt(nbtListCompound);
                return itemStack;
            }
        }
        
        return null;
    }

}
