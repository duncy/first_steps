package nz.duncy.first_steps.world.level.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.inventory.KnappingMenu;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public class RockBlock extends Block {
    public static final MapCodec<RockBlock> CODEC = simpleCodec(RockBlock::new);
    public static final int MAX_ROCKS = 4;
    public static final IntegerProperty ROCKS;
    private static final VoxelShape SHAPE_ONE;
    private static final VoxelShape SHAPE_TWO;

    public RockBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROCKS, 1));
    }

    public MapCodec<RockBlock> codec() {
        return CODEC;
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
        if (blockState.is(this)) {
            return (BlockState)blockState.setValue(ROCKS, Math.min(4, (Integer)blockState.getValue(ROCKS) + 1));
        } else {
            return (BlockState)super.getStateForPlacement(blockPlaceContext);
        }
    }
    
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return !blockState.getCollisionShape(blockGetter, blockPos).getFaceShape(Direction.UP).isEmpty() || blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
    }

    protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        return !blockPlaceContext.isSecondaryUseActive() && blockPlaceContext.getItemInHand().is(this.asItem()) && (Integer)blockState.getValue(ROCKS) < 4 ? true : super.canBeReplaced(blockState, blockPlaceContext);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        VoxelShape voxelShape;
        switch ((Integer)blockState.getValue(ROCKS)) {
            case 1:
            voxelShape = SHAPE_ONE;
            break;
            default:
            voxelShape = SHAPE_TWO;
            break;
        }
        return voxelShape;
    }

    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROCKS);
    }

    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (itemStack.is(ModItemTags.IS_ROCK) && blockState.getValue(ROCKS) == 1) {
            if (!level.isClientSide()) {
                player.openMenu(blockState.getMenuProvider(level, blockPos));
            }
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }

    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((i, inventory, player) -> {
            Item item = asItem();
            if (item == Items.AIR) item = Items.FLINT;
            SingleInputSet<StonecutterRecipe> recipes = ((ServerLevel) player.level()).recipeAccess().stonecutterRecipes().selectByInput(new ItemStack(item));
            return new KnappingMenu(i, inventory, recipes, blockPos);
        }, Component.empty());
    }

    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        return direction == Direction.DOWN && !this.canSurvive(blockState, levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        Item item = asItem();
        if (item == Items.AIR) {
            item = Items.FLINT;
        }

        return new ItemStack(item);
    }

    static {
        ROCKS = ModBlockStateProperties.ROCKS;
        SHAPE_ONE = Block.box(4.0, 0, 4.0, 12.0, 1, 12.0);
        SHAPE_TWO = Block.box(1.0, 0, 1.0, 15.0, 1, 15.0);
    }
}