package nz.duncy.first_steps.world.level.block.entity;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.world.level.block.HorizontalFacingWaterloggedCrackedEntityBlock;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class UnfiredGenericBlockEntity extends UnfiredBlockEntity {
    private static final Map<Block, Block> FIRED_BLOCK_MAP = ImmutableMap.of(
        ModBlocks.UNFIRED_FLOWER_POT, Blocks.FLOWER_POT,
        ModBlocks.UNFIRED_CRUCIBLE, ModBlocks.CRUCIBLE,
        ModBlocks.UNFIRED_CASING_HOE, ModBlocks.CASING_HOE,
        ModBlocks.UNFIRED_CASING_SHOVEL, ModBlocks.CASING_SHOVEL,
        ModBlocks.UNFIRED_CASING_AXE, ModBlocks.CASING_AXE,
        ModBlocks.UNFIRED_CASING_KNIFE, ModBlocks.CASING_KNIFE,
        ModBlocks.UNFIRED_CASING_SPEAR, ModBlocks.CASING_SPEAR,
        ModBlocks.UNFIRED_CASING_PICKAXE, ModBlocks.CASING_PICKAXE,
        ModBlocks.UNFIRED_CASING_SWORD, ModBlocks.CASING_SWORD
    );

    private static final Map<Block, Block> HORIZONTAL_FIRED_BLOCK_MAP = ImmutableMap.of(
        ModBlocks.UNFIRED_INGOT_CAST, ModBlocks.INGOT_CAST
    );

    public UnfiredGenericBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.UNFIRED_GENERIC_BLOCK, blockPos, blockState);
    }

    @Override
    public void fire(Level level, BlockPos blockPos, BlockState blockState) {
        Block firedBlock = blockState.getBlock();
        BlockState firedBlockState;

        if (FIRED_BLOCK_MAP.containsKey(firedBlock)) {
            firedBlockState = firedBlock.defaultBlockState();
        } else {
            firedBlockState = HORIZONTAL_FIRED_BLOCK_MAP.get(blockState.getBlock()).defaultBlockState()
            .setValue(
                HorizontalFacingWaterloggedCrackedEntityBlock.HORIZONTAL_FACING,
                blockState.getValue(HorizontalFacingWaterloggedCrackedEntityBlock.HORIZONTAL_FACING)
            );
        }
        
        level.setBlock(blockPos, firedBlockState, Block.UPDATE_ALL);
    }
}
