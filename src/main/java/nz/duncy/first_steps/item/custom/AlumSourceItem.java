package nz.duncy.first_steps.item.custom;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class AlumSourceItem extends Item {
    public AlumSourceItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.first_steps.alum_source").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
