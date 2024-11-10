package nz.duncy.first_steps.item.custom;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class IronSourceItem extends Item {
    public IronSourceItem(net.minecraft.item.Item.Settings settings) {
        super(settings);
    }

    @Override
     public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.first_steps.iron_source").formatted(Formatting.DARK_GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
