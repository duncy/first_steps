package nz.duncy.first_steps.block.custom;

import java.util.List;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class UnfiredMoldBlock extends Block {
    public static final MapCodec<UnfiredMoldBlock> CODEC = createCodec(UnfiredMoldBlock::new);
    protected static final VoxelShape SHAPE = VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), 
    VoxelShapes.union(Block.createCuboidShape(0.0, 3.0, 0.0, 16.0, 4.0, 6.0), 
    VoxelShapes.union(Block.createCuboidShape(0.0, 3.0, 6.0, 5.0, 4.0, 16.0), 
    VoxelShapes.union(Block.createCuboidShape(5.0, 3.0, 10.0, 11.0, 4.0, 16.0), 
    VoxelShapes.union(Block.createCuboidShape(5.0, 3.0, 7.0, 7.0, 4.0, 10.0), 
    VoxelShapes.union(Block.createCuboidShape(7.0, 3.0, 8.0, 8.0, 4.0, 10.0), 
    VoxelShapes.union(Block.createCuboidShape(8.0, 3.0, 9.0, 9.0, 4.0, 10.0), 
    VoxelShapes.union(Block.createCuboidShape(9.0, 3.0, 6.0, 11.0, 4.0, 8.0), 
    VoxelShapes.union(Block.createCuboidShape(8.0, 3.0, 6.0, 9.0, 4.0, 7.0), 
    VoxelShapes.union(Block.createCuboidShape(10.0, 3.0, 8.0, 11.0, 4.0, 9.0), 
    Block.createCuboidShape(11.0, 3.0, 6.0, 16.0, 4.0, 16.0))))))))))); 
    

    public UnfiredMoldBlock(Settings settings) {
        super(settings);
    }
    
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.first_steps.unfired_mold_block").formatted(Formatting.GRAY, Formatting.ITALIC));
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    protected MapCodec<UnfiredMoldBlock> getCodec() {
        return CODEC;
    }

}
