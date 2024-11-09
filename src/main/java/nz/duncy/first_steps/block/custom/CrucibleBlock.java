package nz.duncy.first_steps.block.custom;

import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.CrucibleBlockEntity;
import nz.duncy.first_steps.block.entity.ModBlockEntities;

public class CrucibleBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 10, 13);
    public static final BooleanProperty LIT = Properties.LIT;
    public static final Identifier CONTENTS_DYNAMIC_DROP_ID = Identifier.ofVanilla("contents");
    private static final Text UNKNOWN_CONTENTS_TEXT = Text.translatable("container." + FirstSteps.MOD_ID + ".crucible.unknownContents");

    public static final MapCodec<CrucibleBlock> CODEC = CrucibleBlock.createCodec(CrucibleBlock::new);

    public CrucibleBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, false)));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT});
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
            if (!world.isClient && player.isCreative() && !crucibleBlockEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                crucibleBlockEntity.generateLoot(player);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = (BlockEntity)builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof CrucibleBlockEntity crucibleBlockEntity) {
           builder = builder.addDynamicDrop(CONTENTS_DYNAMIC_DROP_ID, (lootConsumer) -> {
              for(int i = 0; i < crucibleBlockEntity.size(); ++i) {
                 lootConsumer.accept(crucibleBlockEntity.getStack(i));
              }
  
           });
        }
  
        return super.getDroppedStacks(state, builder);
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
  
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrucibleBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrucibleBlockEntity) {
                // ItemScatterer.spawn(world, pos, (CrucibleBlockEntity) blockEntity);
                world.updateComparators(pos, state.getBlock());

            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResult.CONSUME;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrucibleBlockEntity) {
                NamedScreenHandlerFactory screenHandlerFactory = (CrucibleBlockEntity) blockEntity;
                player.openHandledScreen(screenHandlerFactory);
                PiglinBrain.onGuardedBlockInteracted(player, true);
                return ActionResult.CONSUME;
            } else {
                return ActionResult.PASS;
            }
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState();
    }

    // @Nullable
    // @Override
    // public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    //     return validateTicker(type, ModBlockEntities.CRUCIBLE_BLOCK_ENTITY,
    //         (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity));
    // }

    // public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    //     if ((Boolean)state.get(LIT)) {
    //         double d = (double)pos.getX() + 0.5;
    //         double e = (double)pos.getY();
    //         double f = (double)pos.getZ() + 0.5;
    //         if (random.nextDouble() < 0.1) {
    //             world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    //         }

    //         Direction direction = (Direction)state.get(FACING);
    //         Direction.Axis axis = direction.getAxis();
    //         double g = 0.52;
    //         double h = random.nextDouble() * 0.6 - 0.3;
    //         double i = axis == Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
    //         double j = random.nextDouble() * 6.0 / 16.0;
    //         double k = axis == Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
    //         world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    //         world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
    //         world.addParticle(ParticleTypes.SMOKE, d, e + 1.1, f, 0.0, 0.0, 0.0);
    //     }
    // }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT)) {
			tooltip.add(UNKNOWN_CONTENTS_TEXT);
		}

        int temperature = stack.getTemperature();

        tooltip.add(Text.translatable("tooltip.first_steps.crucible.temperature", temperature).formatted(Formatting.GRAY, Formatting.ITALIC));

        int i = 0;
		int j = 0;

		for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
			j++;
			if (i <= 4) {
				i++;
				tooltip.add(Text.translatable("container." + FirstSteps.MOD_ID + ".crucible.itemCount", itemStack.getName(), itemStack.getCount()));
			}
		}

		if (j - i > 0) {
			tooltip.add(Text.translatable("container." + FirstSteps.MOD_ID + ".crucible.more", j - i).formatted(Formatting.ITALIC));
		}
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        world.getBlockEntity(pos, BlockEntityType.SHULKER_BOX).ifPresent(blockEntity -> blockEntity.setStackNbt(itemStack, world.getRegistryManager()));
        return itemStack;
    }

    public static ItemStack getItemStack() {
        return new ItemStack(ModBlocks.FIRED_CRUCIBLE);
    }
}
