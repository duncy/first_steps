package nz.duncy.first_steps.item.custom;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class CopperSourceItem extends Item {
    public CopperSourceItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, net.minecraft.item.Item.TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.first_steps.copper_source").formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
