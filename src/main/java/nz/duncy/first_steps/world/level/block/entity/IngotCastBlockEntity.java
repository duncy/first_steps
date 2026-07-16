package nz.duncy.first_steps.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IngotCastBlockEntity extends BlockEntity {

    public IngotCastBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.INGOT_CAST, blockPos, blockState);
    }
    
}
