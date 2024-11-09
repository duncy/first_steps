package nz.duncy.first_steps.client.render.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import nz.duncy.first_steps.block.custom.RockBlock;

public class RockBlockItem extends BlockItem {

    public RockBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
    }


    public ActionResult useOnBlock(ItemUsageContext context) {
        // Get the player
        PlayerEntity player = context.getPlayer();

        if (player != null && context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof RockBlock) {
            if (!context.getWorld().isClient) {
                player.openHandledScreen((NamedScreenHandlerFactory) context.getWorld().getBlockEntity(context.getBlockPos()) );
            }
            return ActionResult.success(false); // Prevent block placement
        }

        return super.useOnBlock(context);
    }
}
