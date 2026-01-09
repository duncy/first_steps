package nz.duncy.first_steps.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.world.level.block.RockBlock;

public class RockBlockItem extends BlockItem {

    public RockBlockItem(Block block, Properties properties) {
        super(block, properties);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level world = useOnContext.getLevel();
        BlockPos pos = useOnContext.getClickedPos();
        ItemStack stack = useOnContext.getItemInHand();

        if (player != null && player.isCrouching()) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == this.getBlock() &&
                state.getValue(RockBlock.ROCKS) < RockBlock.MAX_ROCKS) {

                BlockState newState = state.setValue(RockBlock.ROCKS, state.getValue(RockBlock.ROCKS) + 1);
                world.setBlock(pos, newState, 3);
                world.playSound(null, pos, state.getSoundType().getPlaceSound(),
                    SoundSource.BLOCKS, 1f, 1f);

                if (!player.getAbilities().instabuild) stack.shrink(1);

                return InteractionResult.SUCCESS_SERVER;
            }
        }

        return super.useOn(useOnContext);
    }
}
