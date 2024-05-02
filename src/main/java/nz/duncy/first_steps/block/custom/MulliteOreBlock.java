package nz.duncy.first_steps.block.custom;

import net.minecraft.block.Block;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public class MulliteOreBlock extends Block {
    public static DirectionProperty FACING = DirectionProperty.of("facing",
            Direction.UP,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.DOWN);

    public MulliteOreBlock(Settings settings) {
        super(settings);
    }

   // @Nullable
   // @Override
   // public BlockState getPlacementState(ItemPlacementContext ctx) {
   //     return getRandomBlockState();
   // }

   // public BlockState getRandomBlockState() {
   //     return this.getDefaultState().with(FACING, getRandomDirection());
   // }
}


