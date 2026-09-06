package nz.duncy.first_steps.world.level.block.entity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.metallurgy.Metal;
import nz.duncy.first_steps.metallurgy.MetalStorage;
import nz.duncy.first_steps.metallurgy.TemperatureStorage;
import nz.duncy.first_steps.world.level.block.IngotCastBlock;

public class IngotCastBlockEntity extends BlockEntity {
    private MetalStorage metalStorage;
    private TemperatureStorage temperatureStorage;

    public IngotCastBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.INGOT_CAST, blockPos, blockState);

        metalStorage = new MetalStorage();
        temperatureStorage = new TemperatureStorage();
    }
    

    public boolean pour(MetalStorage crucibleMetalStorage) {
        if (!(this.level == null || this.level.isClientSide())) {
            BlockState blockState = this.getBlockState();

            if (blockState.hasProperty(IngotCastBlock.METAL) && blockState.getValue(IngotCastBlock.METAL) == Metal.NONE) {
                crucibleMetalStorage.sendMetal(this.metalStorage);

                Optional<Metal> metalOption = this.metalStorage.getMetal();
                FirstSteps.LOGGER.info(metalOption.toString());
                if (metalOption.isPresent()) {
                    Metal metal = metalOption.get();
                    FirstSteps.LOGGER.info(metal.getSerializedName());
                    this.level.setBlock(this.worldPosition, blockState.setValue(IngotCastBlock.METAL, metal), Block.UPDATE_ALL);

                    this.setChanged();
                    return true;
                }
            }
        }

        return false;
    }
}
