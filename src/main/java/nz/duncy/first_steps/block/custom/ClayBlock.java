package nz.duncy.first_steps.block.custom;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.state.ModProperties;

public class ClayBlock extends Block {
    public static final MapCodec<ClayBlock> CODEC = createCodec(ClayBlock::new);
    public static final int MAX_CLAY_LAYERS = 4;
    public static final IntProperty CLAY_LAYERS;
    protected static final VoxelShape[] CLAY_LAYERS_TO_SHAPE;

    public ClayBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(CLAY_LAYERS, 1));
    }
    
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CLAY_LAYERS_TO_SHAPE[(Integer)state.get(CLAY_LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CLAY_LAYERS_TO_SHAPE[(Integer)state.get(CLAY_LAYERS)];
    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return CLAY_LAYERS_TO_SHAPE[(Integer)state.get(CLAY_LAYERS)];
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CLAY_LAYERS_TO_SHAPE[(Integer)state.get(CLAY_LAYERS)];
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return (Integer)state.get(CLAY_LAYERS) == 4 ? 0.4F : 1.0F;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP) || blockState.isOf(this) && (Integer)blockState.get(CLAY_LAYERS) == 4;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (stack.getItem() == Items.CLAY_BALL) {
            if (state.get(CLAY_LAYERS) < MAX_CLAY_LAYERS) {
                world.setBlockState(pos, state.with(CLAY_LAYERS, Math.min(MAX_CLAY_LAYERS, state.get(CLAY_LAYERS) + 1)));
                world.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1f, 1f);
                if (!player.getAbilities().creativeMode) stack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
	}

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = (Integer)state.get(CLAY_LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < MAX_CLAY_LAYERS) {
            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        FirstSteps.LOGGER.info("Blockstate is of this: " + blockState);
        if (blockState.isOf(this)) {
            int i = (Integer)blockState.get(CLAY_LAYERS);
            return (BlockState)blockState.with(CLAY_LAYERS, Math.min(MAX_CLAY_LAYERS, i + 1));
        } else {
            return super.getPlacementState(ctx);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CLAY_LAYERS});
    }

    static {
        CLAY_LAYERS = ModProperties.CLAY_LAYERS;
        CLAY_LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};
    }
}
