package nz.duncy.first_steps.world.level.block;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import nz.duncy.first_steps.world.level.block.entity.UnfiredBlockEntity;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public abstract class UnfiredBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty CRACKED;
    public static final BooleanProperty WATERLOGGED;
    public static final IntegerProperty FIRING_PROGRESS;

    protected UnfiredBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(CRACKED, false).setValue(FIRING_PROGRESS, 0));
    }

    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(CRACKED, false).setValue(FIRING_PROGRESS, 0);
    }

    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, CRACKED, FIRING_PROGRESS);
    }

    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        ItemStack itemStack = player.getMainHandItem();
        BlockState blockState2 = blockState;
        if (itemStack.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasTag(itemStack, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
            blockState2 = (BlockState)blockState.setValue(CRACKED, true);
            level.setBlock(blockPos, blockState2, 260);
        }

        return super.playerWillDestroy(level, blockPos, blockState2, player);
    }

    protected FluidState getFluidState(BlockState blockState) {
        return (Boolean)blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected SoundType getSoundType(BlockState blockState) {
        return SoundType.GRAVEL;
    }

    protected void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (level instanceof ServerLevel serverLevel) {
            if (projectile.mayInteract(serverLevel, blockPos) && projectile.mayBreak(serverLevel)) {
                level.setBlock(blockPos, (BlockState)blockState.setValue(CRACKED, true), 260);
                level.destroyBlock(blockPos, true, projectile);
            }
        }
    }

    protected <T extends UnfiredBlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof UnfiredBlockEntity) {
            ((UnfiredBlockEntity)blockEntity).recheckFiringProgress(level, blockPos);
        }
    }

    @Nullable
    @Override
    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type);

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    protected abstract MapCodec<? extends BaseEntityBlock> codec();

    static {
        CRACKED = BlockStateProperties.CRACKED;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FIRING_PROGRESS = ModBlockStateProperties.FIRING_PROGRESS;
    }
}
