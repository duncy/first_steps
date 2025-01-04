package nz.duncy.first_steps.block.custom;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.block.entity.PottersWheelBlockEntity;
import nz.duncy.first_steps.stat.ModStats;


public class PottersWheelBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = VoxelShapes.union(
        VoxelShapes.union(
            VoxelShapes.cuboid(0.4375, 0.0625, 0.4375, 0.5625, 0.8125, 0.5625), // Rod
            VoxelShapes.union(
                VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.0625, 0.75), // Base
                VoxelShapes.cuboid(0, 0.8125, 0, 1, 1, 1) // wheel
            )
        ),
        // Stand
        VoxelShapes.union(
            // Legs
            VoxelShapes.union(
                VoxelShapes.union(
                    VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.625, 0.25), 
                    VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.625, 0.875)
                ),
                VoxelShapes.union(
                    VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.625, 0.875), 
                    VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.625, 0.25)
                )
            ),
            // Stand lid
            VoxelShapes.cuboid(0.125, 0.625, 0.125, 0.875, 0.75, 0.875)
        )
    );

    public static final MapCodec<PottersWheelBlock> CODEC = PottersWheelBlock.createCodec(PottersWheelBlock::new);


    public PottersWheelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResult.CONSUME;
        } else {
            // Get blockentity for potters wheel
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PottersWheelBlockEntity) {
            PottersWheelBlockEntity pottersWheelBlockEntity = (PottersWheelBlockEntity)blockEntity;
            // Check if can use potters wheel, i.e if it is not already spinning.
                if (!pottersWheelBlockEntity.isSpinning()) {
                    pottersWheelBlockEntity.startSpinning();
                    player.incrementStat(ModStats.POTTERS_WHEEL_SPINS);
                    pottersWheelBlockEntity.markDirty();
                    world.updateListeners(pottersWheelBlockEntity.getPos(), pottersWheelBlockEntity.getCachedState(), pottersWheelBlockEntity.getCachedState(), Block.NOTIFY_LISTENERS);

                    // Check what is above wheel, check if it is pottery.
                    BlockPos abovePos = pos.up();
                    BlockState blockState = world.getBlockState(abovePos);
                    if (blockState.getBlock() == ModBlocks.CLAY) {
                        switch (blockState.get(ClayBlock.CLAY_LAYERS)) {
                            case 1:
                            world.setBlockState(abovePos, ModBlocks.UNFIRED_FLOWER_POT.getDefaultState());
                            break;

                            case 2:
                            break;

                            case 3:
                            world.setBlockState(abovePos, ModBlocks.UNFIRED_CRUCIBLE.getDefaultState());
                            break;

                            case 4:
                            world.setBlockState(abovePos, ModBlocks.UNFIRED_DECORATED_POT.getDefaultState());
                            break;

                            default:
                            break;
                        }

                        world.playSound(null, pos, SoundEvents.BLOCK_MUD_FALL, SoundCategory.BLOCKS, 1f, 1f);

                        ServerWorld serverWorld = (ServerWorld) world;
                        serverWorld.spawnParticles(ParticleTypes.POOF, true, true, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), 5, 0.0, 0.0, 0.0, 0.0);
                    } else if (blockState.getBlock() == Blocks.CLAY) {
                        world.setBlockState(abovePos, Blocks.DECORATED_POT.getDefaultState());
                        
                        world.playSound(null, pos, SoundEvents.BLOCK_MUD_HIT, SoundCategory.BLOCKS, 1f, 1f);

                        ServerWorld serverWorld = (ServerWorld) world;
                        serverWorld.spawnParticles(ParticleTypes.POOF, true, true, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), 5, 0.0, 0.0, 0.0, 0.0);
                    }

                    return ActionResult.SUCCESS;
                }
                    
                return ActionResult.CONSUME;
            } else {
                return ActionResult.PASS;
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PottersWheelBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.POTTERS_WHEEL_BLOCK_ENTITY,
            (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity));
    }
}
