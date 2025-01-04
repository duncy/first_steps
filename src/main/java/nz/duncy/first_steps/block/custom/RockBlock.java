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
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nz.duncy.first_steps.block.entity.RockBlockEntity;

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

    // @Override
    // public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    //     if (hit.getType() == HitResult.Type.BLOCK) {
    //         Item heldItem = player.getStackInHand(hand).getItem();
    //         if (heldItem instanceof BlockItem) {
    //             if (((BlockItem) heldItem).getBlock() instanceof RockBlock) {
    //                 RockBlockEntity rockBlockEntity = (RockBlockEntity) world.getBlockEntity(pos); 
    //                 if (!world.isClient) {
    //                     // Get the coordinates of the clicked voxel within the block
    //                     double hitX = hit.getPos().getX() - pos.getX() - 0.25;
    //                     double hitZ = hit.getPos().getZ() - pos.getZ() - 0.25;
    //                     int voxelPos = (int) (Math.floor(hitX / 0.0625) + (Math.floor(hitZ / 0.0625) * 8));
    //                     if (!rockBlockEntity.getKnappedVoxels().get(voxelPos)) {
    //                         if (rockBlockEntity.edgeVoxel(voxelPos)) {
    //                             rockBlockEntity.knappVoxel(voxelPos);
                            
    //                             rockBlockEntity.markDirty();
    //                             world.updateListeners(rockBlockEntity.getPos(), rockBlockEntity.getCachedState(), rockBlockEntity.getCachedState(), Block.NOTIFY_LISTENERS);
    //                             world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1f, 1f);
                                
    //                             ServerWorld serverWorld = (ServerWorld) world;
    //                             ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
    //                             serverWorld.spawnParticles(serverPlayer, ParticleTypes.DUST_PLUME, true, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), 1, 0.0, 0.0, 0.0, 0.0);
    //                             return ActionResult.SUCCESS;
    //                         }
    //                     }
    //                 }
    //             }                    
    //         }              
    //     }              
    //     return ActionResult.success(false);
    // }

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
