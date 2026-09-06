package nz.duncy.first_steps.world.level.block;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import nz.duncy.first_steps.tags.ModBlockTags;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;

public class WoodPileBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<WoodPileBlock> CODEC = simpleCodec(WoodPileBlock::new);
    public static final int MAX_HEIGHT;
    public static final IntegerProperty PILE_SIZE;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<Direction> HORIZONTAL_FACING;
    public static final BooleanProperty LIT;
    private static final VoxelShape[] SHAPES;
    public static final int HEIGHT_IMPASSABLE;

    protected WoodPileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(PILE_SIZE, 1).setValue(LIT, false));
    }

    @Override
	public MapCodec<WoodPileBlock> codec() {
		return CODEC;
	}

    @Override
	protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
		return pathComputationType == PathComputationType.LAND ? (Integer)blockState.getValue(PILE_SIZE) < HEIGHT_IMPASSABLE : false;
	}

    @Override
	protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPES[(int) Math.ceil(blockState.getValue(PILE_SIZE)/3.0)];
	}

    @Override
	protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPES[(int) Math.ceil(blockState.getValue(PILE_SIZE)/3.0)];
	}

    @Override
	protected VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return SHAPES[(int) Math.ceil(blockState.getValue(PILE_SIZE)/3.0)];
	}

    @Override
	protected VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPES[(int) Math.ceil(blockState.getValue(PILE_SIZE)/3.0)];
	}

	@Override
	protected boolean useShapeForLightOcclusion(BlockState blockState) {
		return true;
	}

    @Override
	protected float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return blockState.getValue(PILE_SIZE) == MAX_HEIGHT ? 0.2F : 1.0F;
	}

    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
	protected boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
		int i = (Integer)blockState.getValue(PILE_SIZE);
		if (!blockPlaceContext.getItemInHand().is(this.asItem()) || i >= MAX_HEIGHT) {
			return i == 1;
		} else {
			return blockPlaceContext.replacingClickedOnBlock() ? blockPlaceContext.getClickedFace() == Direction.UP : true;
		}
	}

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
		if (blockState.is(this)) {
			int i = (Integer)blockState.getValue(PILE_SIZE);
			return blockState.setValue(PILE_SIZE, Math.min(MAX_HEIGHT, i + 1));
		} else {
			FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
            return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection()).setValue(LIT, false);
		}
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HORIZONTAL_FACING, PILE_SIZE, LIT);
    }

    protected void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, boolean bl) {
        Containers.updateNeighboursAfterDestroy(blockState, serverLevel, blockPos);
    }

    protected FluidState getFluidState(BlockState blockState) {
        return (Boolean)blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
	public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
		if ((Boolean)blockState.getValue(LIT)) {
			if (randomSource.nextInt(10) == 0) {
				level.playLocalSound(
					blockPos.getX() + 0.5,
					blockPos.getY() + 0.5,
					blockPos.getZ() + 0.5,
					SoundEvents.CAMPFIRE_CRACKLE,
					SoundSource.BLOCKS,
					0.5F + randomSource.nextFloat(),
					randomSource.nextFloat() * 0.7F + 0.6F,
					false
				);
			}

			if (randomSource.nextInt(5) == 0) {
				for (int i = 0; i < randomSource.nextInt(1) + 1; i++) {
					level.addParticle(
						ParticleTypes.LAVA,
						blockPos.getX() + 0.5,
						blockPos.getY() + 0.5,
						blockPos.getZ() + 0.5,
						randomSource.nextFloat() / 2.0F,
						5.0E-5,
						randomSource.nextFloat() / 2.0F
					);
				}
			}
		}
	}

    public static void dowse(@Nullable Entity entity, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
		if (levelAccessor.isClientSide()) {
			for (int i = 0; i < 20; i++) {
				makeParticles((Level)levelAccessor, blockPos);
			}
		}

		levelAccessor.gameEvent(entity, GameEvent.BLOCK_CHANGE, blockPos);
	}

    @Override
	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		if (!(Boolean)blockState.getValue(BlockStateProperties.WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
			boolean bl = (Boolean)blockState.getValue(LIT);
			if (bl) {
				if (!levelAccessor.isClientSide()) {
					levelAccessor.playSound(null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
				}

				dowse(null, levelAccessor, blockPos, blockState);
			}

			levelAccessor.setBlock(blockPos, blockState.setValue(WATERLOGGED, true).setValue(LIT, false), 3);
			levelAccessor.scheduleTick(blockPos, fluidState.getType(), fluidState.getType().getTickDelay(levelAccessor));
			return true;
		} else {
			return false;
		}
	}

    public static void makeParticles(Level level, BlockPos blockPos) {
		RandomSource randomSource = level.getRandom();
		SimpleParticleType simpleParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
		level.addAlwaysVisibleParticle(
			simpleParticleType,
			true,
			blockPos.getX() + 0.5 + randomSource.nextDouble() / 3.0 * (randomSource.nextBoolean() ? 1 : -1),
			blockPos.getY() + randomSource.nextDouble() + randomSource.nextDouble(),
			blockPos.getZ() + 0.5 + randomSource.nextDouble() / 3.0 * (randomSource.nextBoolean() ? 1 : -1),
			0.0,
			0.07,
			0.0
		);
		
        level.addParticle(
            ParticleTypes.SMOKE,
            blockPos.getX() + 0.5 + randomSource.nextDouble() / 4.0 * (randomSource.nextBoolean() ? 1 : -1),
            blockPos.getY() + 0.4,
            blockPos.getZ() + 0.5 + randomSource.nextDouble() / 4.0 * (randomSource.nextBoolean() ? 1 : -1),
            0.0,
            0.005,
            0.0
        );
	}

    @Override
	protected BlockState rotate(BlockState blockState, Rotation rotation) {
		return blockState.setValue(HORIZONTAL_FACING, rotation.rotate(blockState.getValue(HORIZONTAL_FACING)));
	}

	@Override
	protected BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(blockState.getValue(HORIZONTAL_FACING)));
	}

    public static boolean canLight(BlockState blockState) {
		return blockState.is(ModBlockTags.WOOD_PILES, blockStateBase -> blockStateBase.hasProperty(WATERLOGGED) && blockStateBase.hasProperty(LIT))
			&& !(Boolean)blockState.getValue(WATERLOGGED)
			&& !(Boolean)blockState.getValue(LIT);
	}
    
    @Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (serverLevel.canSpreadFireAround(blockPos)) {
			int i = randomSource.nextInt(3);
			if (i > 0) {
				BlockPos blockPos2 = blockPos;

				for (int j = 0; j < i; j++) {
					blockPos2 = blockPos2.offset(randomSource.nextInt(3) - 1, 1, randomSource.nextInt(3) - 1);
					if (!serverLevel.isLoaded(blockPos2)) {
						return;
					}

					BlockState blockState2 = serverLevel.getBlockState(blockPos2);
					if (blockState2.isAir()) {
						if (this.hasFlammableNeighbours(serverLevel, blockPos2)) {
							serverLevel.setBlockAndUpdate(blockPos2, BaseFireBlock.getState(serverLevel, blockPos2));
							return;
						}
					} else if (blockState2.blocksMotion()) {
						return;
					}
				}
			} else {
				for (int k = 0; k < 3; k++) {
					BlockPos blockPos3 = blockPos.offset(randomSource.nextInt(3) - 1, 0, randomSource.nextInt(3) - 1);
					if (!serverLevel.isLoaded(blockPos3)) {
						return;
					}

					if (serverLevel.isEmptyBlock(blockPos3.above()) && this.isFlammable(serverLevel, blockPos3)) {
						serverLevel.setBlockAndUpdate(blockPos3.above(), BaseFireBlock.getState(serverLevel, blockPos3));
					}
				}
			}
		}
	}

    private boolean hasFlammableNeighbours(LevelReader levelReader, BlockPos blockPos) {
		for (Direction direction : Direction.values()) {
			if (this.isFlammable(levelReader, blockPos.relative(direction))) {
				return true;
			}
		}

		return false;
	}

    private boolean isFlammable(LevelReader levelReader, BlockPos blockPos) {
		return levelReader.isInsideBuildHeight(blockPos.getY()) && !levelReader.hasChunkAt(blockPos) ? false : levelReader.getBlockState(blockPos).ignitedByLava();
	}

    static {
        MAX_HEIGHT = 12;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
        PILE_SIZE = ModBlockStateProperties.PILE_SIZE;
        LIT = BlockStateProperties.LIT;
        SHAPES = Block.boxes(4, i -> Block.column(16.0, 0.0, i * 4));
        HEIGHT_IMPASSABLE = 7;
    }
}
