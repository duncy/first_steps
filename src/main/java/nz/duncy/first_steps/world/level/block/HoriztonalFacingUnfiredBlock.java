package nz.duncy.first_steps.world.level.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import nz.duncy.first_steps.world.level.block.entity.ModBlockEntityType;
import nz.duncy.first_steps.world.level.block.entity.UnfiredBlockEntity;
import nz.duncy.first_steps.world.level.block.entity.UnfiredGenericBlockEntity;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public abstract class HoriztonalFacingUnfiredBlock extends HorizontalFacingWaterloggedCrackedEntityBlock {
    public static final IntegerProperty FIRING_PROGRESS;

    protected HoriztonalFacingUnfiredBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FIRING_PROGRESS, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FIRING_PROGRESS);
    }

    protected SoundType getSoundType(BlockState blockState) {
        return SoundType.GRAVEL;
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext).setValue(FIRING_PROGRESS, 0);
    }

    protected <T extends UnfiredBlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof UnfiredBlockEntity) {
            ((UnfiredBlockEntity)blockEntity).recheckFiringProgress(level, blockPos);
        }
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityType.UNFIRED_GENERIC_BLOCK, this::tick);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new UnfiredGenericBlockEntity(blockPos, blockState);
    }

    static {
        FIRING_PROGRESS = ModBlockStateProperties.FIRING_PROGRESS;
    }
}
