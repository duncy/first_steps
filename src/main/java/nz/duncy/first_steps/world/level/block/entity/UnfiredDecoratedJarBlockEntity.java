package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.level.block.DecoratedJarBlock;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.UnfiredDecoratedJarBlock;

public class UnfiredDecoratedJarBlockEntity extends UnfiredDecoratedBlockEntity {
    public UnfiredDecoratedJarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.UNFIRED_DECORATED_JAR, blockPos, blockState);
    }

    @Override
	public ItemStack createUnfiredDecoratedItem(PotDecorations potDecorations) {
		ItemStack itemStack = ModItems.UNFIRED_DECORATED_JAR.getDefaultInstance();
        itemStack.set(DataComponents.POT_DECORATIONS, potDecorations);
        return itemStack;
	}

    @Override
    public void firePot(Level level, BlockPos blockPos, BlockState blockState) {
        BlockState firedBlockState = ModBlocks.DECORATED_JAR.defaultBlockState().setValue(DecoratedJarBlock.HORIZONTAL_FACING, blockState.getValue(UnfiredDecoratedJarBlock.HORIZONTAL_FACING));
        
        CompoundTag tag = this.saveWithoutMetadata(level.registryAccess());
        
        level.setBlock(blockPos, firedBlockState, Block.UPDATE_ALL);

        BlockEntity firedBlockEntity = level.getBlockEntity(blockPos);
        if (firedBlockEntity instanceof DecoratedJarBlockEntity decoratedJarBlockEntity) {
            decoratedJarBlockEntity.loadCustomOnly(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), tag));
        }  
    }
}
