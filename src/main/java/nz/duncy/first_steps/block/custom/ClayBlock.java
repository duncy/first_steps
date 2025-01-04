package nz.duncy.first_steps.block.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
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

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = (Integer)state.get(CLAY_LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 4) {
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
        if (blockState.isOf(this)) {
            int i = (Integer)blockState.get(CLAY_LAYERS);
            return (BlockState)blockState.with(CLAY_LAYERS, Math.min(4, i + 1));
        } else {
            return super.getPlacementState(ctx);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CLAY_LAYERS});
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.first_steps.clay").formatted(Formatting.GRAY, Formatting.ITALIC));
        super.appendTooltip(stack, context, tooltip, options);
    }

    static {
        CLAY_LAYERS = ModProperties.CLAY_LAYERS;
        CLAY_LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};
    }
}
