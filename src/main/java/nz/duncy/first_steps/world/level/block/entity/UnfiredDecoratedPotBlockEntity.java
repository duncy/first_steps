package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.UnfiredDecoratedPotBlock;

public class UnfiredDecoratedPotBlockEntity extends UnfiredDecoratedBlockEntity {
    public UnfiredDecoratedPotBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.UNFIRED_DECORATED_POT, blockPos, blockState);
    }

    @Override
	public ItemStack createUnfiredDecoratedItem(PotDecorations potDecorations) {
		ItemStack itemStack = ModItems.UNFIRED_DECORATED_POT.getDefaultInstance();
        itemStack.set(DataComponents.POT_DECORATIONS, potDecorations);
        return itemStack;
	}

    @Override
    public void firePot(Level level, BlockPos blockPos, BlockState blockState) {
        BlockState firedBlockState = Blocks.DECORATED_POT.defaultBlockState().setValue(DecoratedPotBlock.HORIZONTAL_FACING, blockState.getValue(UnfiredDecoratedPotBlock.HORIZONTAL_FACING));
        
        CompoundTag tag = this.saveWithoutMetadata(level.registryAccess());
        
        level.setBlock(blockPos, firedBlockState, Block.UPDATE_ALL);

        BlockEntity firedBlockEntity = level.getBlockEntity(blockPos);
        if (firedBlockEntity instanceof DecoratedPotBlockEntity decoratedPotBlockEntity) {
            decoratedPotBlockEntity.loadCustomOnly(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), tag));
        }  
    }
}