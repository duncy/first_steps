// Source code is decompiled from a .class file using FernFlower decompiler.
package nz.duncy.first_steps.block.custom;

import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Sherds;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.entity.UnfiredDecoratedPotBlockEntity;
import org.jetbrains.annotations.Nullable;

public class UnfiredDecoratedPotBlock extends BlockWithEntity implements Waterloggable {
   public static final MapCodec<UnfiredDecoratedPotBlock> CODEC = createCodec(UnfiredDecoratedPotBlock::new);
   public static final Identifier SHERDS_DYNAMIC_DROP_ID = Identifier.ofVanilla("sherds");
   private static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
	private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty CRACKED = Properties.CRACKED;
	private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

   public MapCodec<UnfiredDecoratedPotBlock> getCodec() {
      return CODEC;
   }

   public UnfiredDecoratedPotBlock(AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState())
            .with(FACING, Direction.NORTH)).with(WATERLOGGED, false)).with(CRACKED, false));
   }

   public BlockState getStateForNeighborUpdate(BlockState state,
		WorldView world,
		ScheduledTickView tickView,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		Random random) {
      if ((Boolean) state.get(WATERLOGGED)) {
         tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
   }

   public BlockState getPlacementState(ItemPlacementContext ctx) {
      FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
      return (BlockState) ((BlockState) ((BlockState) this.getDefaultState().with(FACING,
            ctx.getHorizontalPlayerFacing())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER))
            .with(CRACKED, false);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      FirstSteps.LOGGER.info(String.valueOf(blockEntity));
      if (blockEntity instanceof UnfiredDecoratedPotBlockEntity) {
         if (world.isClient) {
            return ActionResult.CONSUME;
         } else {
            ItemStack itemStack = player.getStackInHand(player.getActiveHand());

            if (itemStack.isIn(ItemTags.DECORATED_POT_SHERDS)) { // Item is a sherd
               UnfiredDecoratedPotBlockEntity unfiredDecoratedPotBlockEntity = (UnfiredDecoratedPotBlockEntity) blockEntity;

               Sherds sherds = unfiredDecoratedPotBlockEntity.getSherds();
               FirstSteps.LOGGER.info(String.valueOf(unfiredDecoratedPotBlockEntity.getHorizontalFacing().ordinal()));
               switch (unfiredDecoratedPotBlockEntity.getHorizontalFacing().ordinal()) {
                  case 3: // south
                     switch (hit.getSide().ordinal()) {
                        case 3: // back
                           if (sherds.back().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 2, player);
                           }
                           break;
                        case 5: // right
                           if (sherds.left().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 1, player);
                           }
                           break;
                        case 4: // left
                           if (sherds.right().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 3, player);
                           }
                           break;
                        case 2: // front
                           if (sherds.front().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 0, player);
                           }
                           break;
                        default:
                           break;
                     }
                     break;
                  case 5: // east
                     switch (hit.getSide().ordinal()) {
                        case 5: // back
                           if (sherds.back().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 2, player);
                           }
                           break;
                        case 2: // right
                           if (sherds.left().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 1, player);
                           }
                           break;
                        case 3: // left
                           if (sherds.right().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 3, player);
                           }
                           break;
                        case 4: // front
                           if (sherds.front().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 0, player);
                           }
                           break;
                        default:
                           break;
                     }
                     break;
                  case 4: // west
                     FirstSteps.LOGGER.info(String.valueOf(hit.getSide().ordinal()));
                     switch (hit.getSide().ordinal()) {
                        case 4: // back
                           if (sherds.back().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 2, player);
                           }
                           break;
                        case 3: // right
                           if (sherds.left().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 1, player);
                           }
                           break;
                        case 2: // left
                           if (sherds.right().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 3, player);
                           }
                           break;
                        case 5: // front
                           if (sherds.front().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 0, player);
                           }
                           break;
                        default:
                           break;
                     }
                     break;
                  case 2: // north
                     switch (hit.getSide().ordinal()) {
                        case 2: // back
                           if (sherds.back().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 2, player);
                           }
                           break;
                        case 4: // right
                           if (sherds.left().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 1, player);
                           }
                           break;
                        case 5: // left
                           if (sherds.right().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 3, player);
                           }
                           break;
                        case 3: // front
                           if (sherds.front().isEmpty()) {
                              unfiredDecoratedPotBlockEntity.setSherd(itemStack, 0, player);
                           }
                           break;
                        default:
                           break;
                     }
                     break;
                  default:
                     break;
               }

               unfiredDecoratedPotBlockEntity.markDirty();
               world.updateListeners(unfiredDecoratedPotBlockEntity.getPos(),
                     unfiredDecoratedPotBlockEntity.getCachedState(), unfiredDecoratedPotBlockEntity.getCachedState(),
                     Block.NOTIFY_LISTENERS);
            }

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            return ActionResult.SUCCESS;
         }
      } else {
         return ActionResult.PASS;
      }
   }

   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(new Property[] { FACING, WATERLOGGED, CRACKED });
   }

   @Nullable
   public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new UnfiredDecoratedPotBlockEntity(pos, state);
   }

   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      ItemScatterer.onStateReplaced(state, newState, world, pos);
      super.onStateReplaced(state, world, pos, newState, moved);
   }

   public List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
      BlockEntity blockEntity = (BlockEntity) builder.getOptional(LootContextParameters.BLOCK_ENTITY);
      if (blockEntity instanceof UnfiredDecoratedPotBlockEntity unfiredDecoratedPotBlockEntity) {
         builder.addDynamicDrop(SHERDS_DYNAMIC_DROP_ID, lootConsumer -> {
				for (Item item : unfiredDecoratedPotBlockEntity.getSherds().toList()) {
					lootConsumer.accept(this.removeBrick(item));
				}
			});
      }

      return super.getDroppedStacks(state, builder);
   }

   private ItemStack removeBrick(Item item) {
      if (item == Items.BRICK) {
         return ItemStack.EMPTY;
      } else {
         return item.getDefaultStack();
      }

   }

   public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      ItemStack itemStack = player.getMainHandStack();
      BlockState blockState = state;
      if (itemStack.isIn(ItemTags.SHOVELS) && !EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
         blockState = (BlockState) state.with(CRACKED, true);
         world.setBlockState(pos, blockState, 4);
      }

      return super.onBreak(world, pos, blockState, player);
   }

   public FluidState getFluidState(BlockState state) {
      return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
		super.appendTooltip(stack, context, tooltip, options);
		Sherds sherds = stack.getOrDefault(DataComponentTypes.POT_DECORATIONS, Sherds.DEFAULT);
		if (!sherds.equals(Sherds.DEFAULT)) {
			tooltip.add(ScreenTexts.EMPTY);
			Stream.of(sherds.front(), sherds.left(), sherds.right(), sherds.back())
				.forEach(sherd -> tooltip.add(new ItemStack((ItemConvertible)sherd.orElse(Items.CLAY_BALL), 1).getName().copyContentOnly().formatted(Formatting.GRAY)));
		}
	}

   @Override
	protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		if (world.getBlockEntity(pos) instanceof UnfiredDecoratedPotBlockEntity unfiredDecoratedPotBlockEntity) {
			Sherds sherds = unfiredDecoratedPotBlockEntity.getSherds();
			return UnfiredDecoratedPotBlockEntity.getStackWith(sherds);
		} else {
			return super.getPickStack(world, pos, state, includeData);
		}
	}

}
