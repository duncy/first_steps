package nz.duncy.first_steps.block.custom;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nz.duncy.first_steps.block.entity.RockBlockEntity;

public class RockBlock extends Block implements BlockEntityProvider {
    private VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 1, 12);
    private Identifier TEXTURE_ID;

    public RockBlock(Settings settings, Identifier texture_id) {
        super(settings);
        TEXTURE_ID = texture_id;
    }

    public Identifier getTextureId() {
        return TEXTURE_ID;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (hit.getType() == HitResult.Type.BLOCK) {
                Item heldItem = player.getStackInHand(hand).getItem();
                if (heldItem instanceof BlockItem) {
                    if (((BlockItem) heldItem).getBlock() instanceof RockBlock) {
                        RockBlockEntity rockBlockEntity = (RockBlockEntity) world.getBlockEntity(pos);

                        // Get the coordinates of the clicked voxel within the block
                        double hitX = hit.getPos().getX() - pos.getX() - 0.25;
                        double hitZ = hit.getPos().getZ() - pos.getZ() - 0.25;

                        int voxelPos = (int) (Math.floor(hitX / 0.0625) + (Math.floor(hitZ / 0.0625) * 8));
                        if (!rockBlockEntity.getKnappedVoxels().get(voxelPos)) {
                            if (rockBlockEntity.edgeVoxel(voxelPos)) {
                                rockBlockEntity.knappVoxel(voxelPos);
                            
                                rockBlockEntity.markDirty();
                                world.updateListeners(rockBlockEntity.getPos(), rockBlockEntity.getCachedState(), rockBlockEntity.getCachedState(), Block.NOTIFY_LISTENERS);
                                world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1f, 1f);
                                
                                ServerWorld serverWorld = (ServerWorld) world;
                                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                                serverWorld.spawnParticles(serverPlayer, ParticleTypes.DUST_PLUME, true, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                                return ActionResult.SUCCESS;
                            }
                        }
                    }                    
                }              
            }              
        }
    return ActionResult.success(false);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        RockBlockEntity rockBlockEntity = (RockBlockEntity) world.getBlockEntity(pos);
        if (rockBlockEntity == null) {
            return SHAPE;
        }
        return rockBlockEntity.getShape();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
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
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.first_steps.rock_block").formatted(Formatting.GRAY, Formatting.ITALIC));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
