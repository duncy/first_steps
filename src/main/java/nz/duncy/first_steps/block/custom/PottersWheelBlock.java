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
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        BlockPos abovePos = pos.up();
        BlockState aboveBlockState = world.getBlockState(abovePos);
        Block aboveBlock = aboveBlockState.getBlock();
        if (aboveBlock == Blocks.CLAY || aboveBlock == ModBlocks.CLAY) {
            if (!world.isClient()) {
                if (world.getBlockEntity(pos) instanceof PottersWheelBlockEntity pottersWheelBlockEntity) {
                    if (!pottersWheelBlockEntity.isSpinning())
                        spinWheel(aboveBlockState, world, abovePos, player, pottersWheelBlockEntity, hit);
                }
            }
            return ActionResult.SUCCESS;
        }
    
        return ActionResult.PASS;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockPos abovePos = pos.up();
        BlockState aboveBlockState = world.getBlockState(abovePos);
        Block aboveBlock = aboveBlockState.getBlock();
        if (aboveBlock == Blocks.AIR) {
            if (hit.getSide() == Direction.UP) {
                if (stack.getItem() == Items.CLAY_BALL) {
                    ItemPlacementContext context = new ItemPlacementContext(player, hand, stack, hit);
                    ActionResult result = ((BlockItem) ModBlocks.CLAY.asItem()).place(context);
                    if (result.isAccepted()) {
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
	}

    private static void spinWheel(BlockState aboveBlockState, World world, BlockPos abovePos, PlayerEntity player, PottersWheelBlockEntity pottersWheelBlockEntity, BlockHitResult hit) {
        if (aboveBlockState.getBlock() == ModBlocks.CLAY) {
            wheelClayLayers(aboveBlockState, world, abovePos);
        } else if (aboveBlockState.getBlock() == Blocks.CLAY) {
            wheelClayBlock(aboveBlockState, world, abovePos);
        }

        successfulWheeling(world, abovePos, player, pottersWheelBlockEntity, hit);
    }

    private static void wheelClayLayers(BlockState aboveBlockState, World world, BlockPos abovePos) {
        switch (aboveBlockState.get(ClayBlock.CLAY_LAYERS)) {
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
    }

    private static void wheelClayBlock(BlockState blockState, World world, BlockPos abovePos) {
        world.setBlockState(abovePos, ModBlocks.UNFIRED_DECORATED_POT.getDefaultState());
    }

    private static void successfulWheeling(World world, BlockPos abovePos, PlayerEntity player, PottersWheelBlockEntity pottersWheelBlockEntity, BlockHitResult hit) {
        // Play sounds and spawn particles
        world.playSound(null, abovePos, SoundEvents.BLOCK_MUD_HIT, SoundCategory.BLOCKS, 1f, 1f);

        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticles(ParticleTypes.POOF, true, true, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), 5, 0.0, 0.0, 0.0, 0.0);

        // Spin wheel
		pottersWheelBlockEntity.startSpinning();
        player.incrementStat(ModStats.POTTERS_WHEEL_SPINS);
        pottersWheelBlockEntity.markDirty();
        world.updateListeners(pottersWheelBlockEntity.getPos(), pottersWheelBlockEntity.getCachedState(), pottersWheelBlockEntity.getCachedState(), Block.NOTIFY_LISTENERS);
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
        return validateTicker(type, ModBlockEntities.POTTERS_WHEEL_BLOCK_ENTITY, PottersWheelBlockEntity::tick);
    }
}
