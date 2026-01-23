package nz.duncy.first_steps.world.level.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SelectableRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nz.duncy.first_steps.world.inventory.PottersWheelMenu;
import nz.duncy.first_steps.world.item.crafting.ModRecipeType;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;
import nz.duncy.first_steps.world.level.block.entity.ModBlockEntityType;
import nz.duncy.first_steps.world.level.block.entity.PottersWheelBlockEntity;

public class PottersWheelBlock extends BaseEntityBlock {
    public static final MapCodec<PottersWheelBlock> CODEC = simpleCodec(PottersWheelBlock::new);
    private static final VoxelShape SHAPE;

    protected PottersWheelBlock(Properties properties) {
        super(properties);
    }

    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof PottersWheelBlockEntity) {
            ItemLike item = level.getBlockState(blockPos.above()).getBlock();
            if (item == Blocks.CLAY) {
                if (!level.isClientSide()) {
                    SingleInputSet<PottersWheelRecipe> recipes = getRecipes(level).selectByInput(new ItemStack(item));
                    if (recipes.size() > 0) {
                        player.openMenu(getMenuProvider(blockState, level, blockPos, recipes));
                    }
                }
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return InteractionResult.PASS;
    }

    private SingleInputSet<PottersWheelRecipe> getRecipes(Level level) {
        Collection<RecipeHolder<PottersWheelRecipe>> all = ((ServerLevel) level).recipeAccess().getAllOfType(ModRecipeType.POTTERS_WHEEL);
        List<SelectableRecipe.SingleInputEntry<PottersWheelRecipe>> list = new ArrayList<>();

        for (RecipeHolder<PottersWheelRecipe> recipeHolder : all) {
            PottersWheelRecipe recipe = recipeHolder.value();

            SlotDisplay slotDisplay = recipe.resultDisplay();

            Optional<RecipeHolder<PottersWheelRecipe>> optional = Optional.of(recipeHolder);

            SelectableRecipe<PottersWheelRecipe> selectableRecipe = new SelectableRecipe<PottersWheelRecipe>(slotDisplay, optional);

            list.add(new SelectableRecipe.SingleInputEntry<PottersWheelRecipe>(recipe.input(), selectableRecipe));
        }

        return new SingleInputSet<PottersWheelRecipe>(list);
    }

    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos, SingleInputSet<PottersWheelRecipe> recipes) {
        return new SimpleMenuProvider((i, inventory, player) -> {
            return new PottersWheelMenu(i, inventory, recipes, blockPos);  
        }, Component.empty());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PottersWheelBlockEntity(blockPos, blockState);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntityType.POTTERS_WHEEL, this::tick);
    }

    protected <T extends PottersWheelBlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T entity) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof PottersWheelBlockEntity) {
            ((PottersWheelBlockEntity)blockEntity).updateAnimation(level, blockPos, blockState);
        }
    }
    
    static {
        SHAPE = Shapes.join(
            Shapes.join(
                Shapes.create(0.4375, 0.0625, 0.4375, 0.5625, 0.8125, 0.5625), // Rod
                Shapes.join(
                    Shapes.create(0.25, 0, 0.25, 0.75, 0.0625, 0.75), // Base
                    Shapes.create(0, 0.8125, 0, 1, 1, 1), // wheel
                    BooleanOp.OR
                ),
                BooleanOp.OR
            ),
            // Stand
            Shapes.join(
                // Legs
                Shapes.join(
                    Shapes.join(
                        Shapes.create(0.125, 0, 0.125, 0.25, 0.625, 0.25), 
                        Shapes.create(0.75, 0, 0.75, 0.875, 0.625, 0.875),
                        BooleanOp.OR
                    ),
                    Shapes.join(
                        Shapes.create(0.125, 0, 0.75, 0.25, 0.625, 0.875), 
                        Shapes.create(0.75, 0, 0.125, 0.875, 0.625, 0.25),
                        BooleanOp.OR
                    ),
                    BooleanOp.OR
                ),
                // Stand lid
                Shapes.create(0.125, 0.625, 0.125, 0.875, 0.75, 0.875),
                BooleanOp.OR
            ),
            BooleanOp.OR
        );
    }
}
