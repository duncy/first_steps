package nz.duncy.first_steps.util.events;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.custom.RockBlock;

public class ModEvents {

    public static void registerModEvents() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isOf(Items.FLINT)) {
                    BlockHitResult hit = (BlockHitResult) player.raycast(5.0, 0f, false);
                    if (!(world.getBlockState(hit.getBlockPos()).getBlock() instanceof RockBlock)) {
                        ItemPlacementContext context = new ItemPlacementContext(player, hand, itemStack, hit);
                        return ((BlockItem) ModBlocks.FLINT_ROCK.asItem()).place(context);
                    }  
                }
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        });

        ItemTooltipCallback.EVENT.register((stack, context, type, text) -> {
            if (stack.getItem() == Items.CLAY_BALL) {
                text.add(1, Text.translatable("tooltip.first_steps.clay").formatted(Formatting.GRAY));
            } else if (stack.getItem() == Items.FLINT) {
                text.add(1, Text.translatable("tooltip.first_steps.rock_block").formatted(Formatting.GRAY));
            }
        });
    }
    
}
