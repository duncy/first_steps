package nz.duncy.first_steps.world.gen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import nz.duncy.first_steps.FirstSteps;

public class RockFeature extends Feature<RockFeatureConfig> {
    public RockFeature(Codec<RockFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<RockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();

        // Origin of feature generation
        BlockPos origin = context.getOrigin();

        RockFeatureConfig config = context.getConfig();

        int number = config.number();

        Identifier blockId = config.blockId();

        BlockState blockState = Registries.BLOCK.get(blockId).getDefaultState();

        // Ensure block id is valid
        if (blockState == null) throw new IllegalStateException(blockId + " could not be parsed to a valid block identifier");    
    
        // Find world surface
        BlockPos testPos = new BlockPos(origin);
        for (int y = 0; y < world.getHeight(); y++) {
            testPos = testPos.up();
            // Tag name dirt includes grass, mud, podzol etc so will continue checking until previous was but next is air
            if (world.getBlockState(testPos).isIn(BlockTags.DIRT)) {
                if (world.getBlockState(testPos.up()).isOf(Blocks.AIR)) {
                    for (int i = 0; i < number; i++) {
                        world.setBlockState(testPos, blockState, 0x10);
                        FirstSteps.LOGGER.info("placing rock at" + testPos);
                        testPos = testPos.up();

                        // Ensure not exceeding world limit
                        if (testPos.getY() >= world.getTopY()) break;
                    }
                    return true;
                }
            }
        }
        // No valid surface to place the rock
        return false;
    }

}