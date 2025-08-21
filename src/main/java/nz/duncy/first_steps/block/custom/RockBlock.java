package nz.duncy.first_steps.block.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nz.duncy.first_steps.block.entity.RockBlockEntity;
import nz.duncy.first_steps.item.custom.RockBlockItem;

public class RockBlock extends Block implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 1, 12);
    // private Identifier TEXTURE_ID;
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

    public RockBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
        // TEXTURE_ID = texture_id;
    }

    // public Identifier getTextureId() {
    //     return TEXTURE_ID;
    // }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof RockBlockItem || stack.getItem() == Items.FLINT) {
            if (!world.isClient) {
                player.openHandledScreen((NamedScreenHandlerFactory) world.getBlockEntity(pos) );
            }
            return ActionResult.SUCCESS_SERVER; // Prevent block placement
        }

        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // RockBlockEntity rockBlockEntity = (RockBlockEntity) world.getBlockEntity(pos);
        // if (rockBlockEntity == null || this.shape_index > 0) {
        return SHAPE;
        // }
        // return rockBlockEntity.getShape();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RockBlockEntity(pos, state);
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return BlockEntityProvider.super.getTicker(world, state, type);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.first_steps.rock_block").formatted(Formatting.GRAY, Formatting.ITALIC));
        super.appendTooltip(stack, context, tooltip, options);
    }

    // private void openScreen(BlockEntity blockEntity, PlayerEntity player) {
    //     player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
    // }

    // static {
    //     FACING = HorizontalFacingBlock.FACING;
        // SHAPES = new VoxelShape[]{Block.createCuboidShape(4, 0, 4, 12, 1, 12),  // Normal
        //     // Hoe
        //     VoxelShapes.union(
        //         Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 1.0, 5.0),
        //         VoxelShapes.union(
        //             Block.createCuboidShape(4.0, 0.0, 5.0, 6.0, 1.0, 7.0),
        //             VoxelShapes.union(
        //                 Block.createCuboidShape(6.0, 0.0, 5.0, 7.0, 1.0, 6.0),
        //                 VoxelShapes.union(
        //                     Block.createCuboidShape(4.0, 0.0, 7.0, 5.0, 1.0, 12.0),
        //                     VoxelShapes.union(
        //                         Block.createCuboidShape(5.0, 0.0, 9.0, 8.0, 1.0, 12.0),
        //                         VoxelShapes.union(
        //                             Block.createCuboidShape(8.0, 0.0, 10.0, 9.0, 1.0, 12.0),
        //                             VoxelShapes.union(
        //                                 Block.createCuboidShape(9.0, 0.0, 11.0, 12.0, 1.0, 12.0),
        //                                 VoxelShapes.union(
        //                                     Block.createCuboidShape(10.0, 0.0, 8.0, 12.0, 1.0, 9.0),
        //                                     VoxelShapes.union(
        //                                         Block.createCuboidShape(9.0, 0.0, 5.0, 12.0, 1.0, 8.0),
        //                                         Block.createCuboidShape(11.0, 0.0, 9.0, 12.0, 1.0, 11.0)
        //                                     )
        //                                 )
        //                             )
        //                         )
        //                     )
        //                 )
        //             )
        //         )
        //     ),

        //     // Shovel
        //     Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),

        //     // Axe
        //     Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),

        //     // Spear
        //     Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),

        //     // Knife
        //     Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),

        //     // Arrowhead
        //     Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0)
        // };          
}
// }
