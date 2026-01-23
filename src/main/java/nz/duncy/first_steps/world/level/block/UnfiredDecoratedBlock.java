package nz.duncy.first_steps.world.level.block;

import com.mojang.serialization.MapCodec;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import nz.duncy.first_steps.world.level.block.entity.UnfiredDecoratedBlockEntity;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public abstract class UnfiredDecoratedBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final Identifier SHERDS_DYNAMIC_DROP_ID = Identifier.withDefaultNamespace("sherds");
    public static final EnumProperty<Direction> HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED;
    public static final BooleanProperty WATERLOGGED;
    public static final IntegerProperty FIRING_PROGRESS;

    public UnfiredDecoratedBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH)).setValue(WATERLOGGED, false)).setValue(CRACKED, false).setValue(FIRING_PROGRESS, 0));
    }

    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection())).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)).setValue(CRACKED, false).setValue(FIRING_PROGRESS, 0);
    }

    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if (blockEntity instanceof UnfiredDecoratedBlockEntity) {
            if (level.isClientSide()) {
                return InteractionResult.CONSUME;
            } else {
                if (itemStack.is(ItemTags.DECORATED_POT_SHERDS)) {
                    UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity = (UnfiredDecoratedBlockEntity) blockEntity;

                    PotDecorations potDecorations = unfiredDecoratedBlockEntity.getDecorations();

                    switch (unfiredDecoratedBlockEntity.getDirection().ordinal()) {
                        case 3: // South
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 3: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 5: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 4: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 2: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 5: // East
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 5: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 2: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 3: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 4: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 4: // West
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 4: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 3: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 2: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 5: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 2: // Nouth
                            switch (blockHitResult.getDirection().ordinal()) {
                                case 2: // Back
                                    
                                    if (potDecorations.back().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 2, player);
                                    }
                                    break;
                                case 4: // Left
                                    
                                    if (potDecorations.left().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 1, player);
                                    }
                                    break;
                                case 5: // Right
                                    
                                    if (potDecorations.right().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 3, player);
                                    }
                                    break;
                                case 3: // Front
                                    
                                    if (potDecorations.front().isEmpty()) {
                                        unfiredDecoratedBlockEntity.setDecoration(itemStack, 0, player);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }

                    unfiredDecoratedBlockEntity.setChanged();
                    level.sendBlockUpdated(unfiredDecoratedBlockEntity.getBlockPos(), unfiredDecoratedBlockEntity.getBlockState(), unfiredDecoratedBlockEntity.getBlockState(), Block.UPDATE_CLIENTS);
                }
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HORIZONTAL_FACING, WATERLOGGED, CRACKED, FIRING_PROGRESS});
    }

    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity) {
            builder.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, (consumer) -> {
                Iterator<Item> decorationsIterator = unfiredDecoratedBlockEntity.getDecorations().ordered().iterator();

                while(decorationsIterator.hasNext()) {
                    Item item = (Item)decorationsIterator.next();
                    if (item == Items.BRICK) {
                        item = Items.CLAY_BALL;
                    }
                    consumer.accept(item.getDefaultInstance());
                }

            });
        }

        return super.getDrops(blockState, builder);
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

    protected ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        BlockEntity blockEntity = levelReader.getBlockEntity(blockPos);
        if (blockEntity instanceof UnfiredDecoratedBlockEntity unfiredDecoratedBlockEntity) {
            PotDecorations potDecorations = unfiredDecoratedBlockEntity.getDecorations();
            return unfiredDecoratedBlockEntity.createUnfiredDecoratedItem(potDecorations);
        } else {
            return super.getCloneItemStack(levelReader, blockPos, blockState, bl);
        }
    }

    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.setValue(HORIZONTAL_FACING, rotation.rotate((Direction)blockState.getValue(HORIZONTAL_FACING)));
    }

    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation((Direction)blockState.getValue(HORIZONTAL_FACING)));
    }

    @Nullable
    @Override
    public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type);

    protected <T extends UnfiredDecoratedBlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof UnfiredDecoratedBlockEntity) {
            ((UnfiredDecoratedBlockEntity)blockEntity).recheckFiringProgress(level, blockPos);
        }
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    protected abstract MapCodec<? extends BaseEntityBlock> codec();

    static {
        HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
        CRACKED = BlockStateProperties.CRACKED;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FIRING_PROGRESS = ModBlockStateProperties.FIRING_PROGRESS;
    }
}
