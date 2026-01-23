package nz.duncy.first_steps.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import nz.duncy.first_steps.world.level.block.entity.DecoratedJarBlockEntity;

import java.util.Iterator;
import java.util.List;

import org.jspecify.annotations.Nullable;

public class DecoratedJarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<DecoratedJarBlock> CODEC = simpleCodec(DecoratedJarBlock::new);
    public static final Identifier SHERDS_DYNAMIC_DROP_ID = Identifier.withDefaultNamespace("sherds");
    public static final EnumProperty<Direction> HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED;
    public static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE;
    public static final BooleanProperty OPEN;

    public MapCodec<DecoratedJarBlock> codec() {
        return CODEC;
    }

    public DecoratedJarBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH)).setValue(WATERLOGGED, false)).setValue(CRACKED, false).setValue(OPEN, false));
    }

    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverLevel) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof DecoratedJarBlockEntity decoratedJarBlockEntity) {
                player.openMenu(decoratedJarBlockEntity);
                player.awardStat(Stats.OPEN_BARREL);
                PiglinAi.angerNearbyPiglins(serverLevel, player, true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof DecoratedJarBlockEntity) {
            ((DecoratedJarBlockEntity)blockEntity).recheckOpen();
        }

    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection())).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)).setValue(CRACKED, false).setValue(OPEN, false);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HORIZONTAL_FACING, WATERLOGGED, CRACKED, OPEN});
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DecoratedJarBlockEntity(blockPos, blockState);
    }

    protected boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    protected List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        BlockEntity blockEntity = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof DecoratedJarBlockEntity decoratedJarBlockEntity) {
            builder.withDynamicDrop(SHERDS_DYNAMIC_DROP_ID, (consumer) -> {
                Iterator<Item> decorations = decoratedJarBlockEntity.getDecorations().ordered().iterator();

                while(decorations.hasNext()) {
                Item decoration = (Item)decorations.next();
                consumer.accept(decoration.getDefaultInstance());
                }

            });
        }

        return super.getDrops(blockState, builder);
    }

    public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        ItemStack itemStack = player.getMainHandItem();
        BlockState setBlockState = blockState;
        if (itemStack.is(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasTag(itemStack, EnchantmentTags.PREVENTS_DECORATED_POT_SHATTERING)) {
            setBlockState = (BlockState)blockState.setValue(CRACKED, true);
            level.setBlock(blockPos, setBlockState, 260);
        }

        return super.playerWillDestroy(level, blockPos, setBlockState, player);
    }

    protected FluidState getFluidState(BlockState blockState) {
        return (Boolean)blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected SoundType getSoundType(BlockState blockState) {
        return (Boolean)blockState.getValue(CRACKED) ? SoundType.DECORATED_POT_CRACKED : SoundType.DECORATED_POT;
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
        if (blockEntity instanceof DecoratedJarBlockEntity decoratedJarBlockEntity) {
            PotDecorations potDecorations = decoratedJarBlockEntity.getDecorations();
            return DecoratedJarBlockEntity.createDecoratedJarItem(potDecorations);
        } else {
            return super.getCloneItemStack(levelReader, blockPos, blockState, bl);
        }
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.setValue(HORIZONTAL_FACING, rotation.rotate((Direction)blockState.getValue(HORIZONTAL_FACING)));
    }

    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation((Direction)blockState.getValue(HORIZONTAL_FACING)));
    }

    static {
        HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
        CRACKED = BlockStateProperties.CRACKED;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = Block.column(14.0, 0.0, 14.0);
        OPEN = BlockStateProperties.OPEN;
    }
}
