package nz.duncy.first_steps.world.item;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class UnlitTorchBlockItem extends StandingAndWallBlockItem {
    public UnlitTorchBlockItem(Block block, Block wallBlock, Direction direction, Properties properties) {
        super(block, wallBlock, direction, properties);
    }
    
    public InteractionResult useOn(UseOnContext useOnContext) {
        Block block = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos()).getBlock();
        if (block == Blocks.CAMPFIRE || block == Blocks.FIRE) {
            useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), new ItemStack(Items.TORCH, useOnContext.getItemInHand().getCount()));
            return InteractionResult.SUCCESS;
        } else if (block == Blocks.SOUL_CAMPFIRE || block == Blocks.SOUL_FIRE) {
            useOnContext.getPlayer().setItemInHand(useOnContext.getHand(), new ItemStack(Items.SOUL_TORCH, useOnContext.getItemInHand().getCount()));
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(useOnContext);
        }
    }
}
