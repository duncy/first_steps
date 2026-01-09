package nz.duncy.first_steps.events;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.tags.ModItemTags;

public class ModEventsClient {
    

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering client only events for " + FirstSteps.MOD_ID);

        ItemTooltipCallback.EVENT.register((stack, context, flag, component) -> {
            if (stack.is(ModItemTags.IS_ROCK)) {
                component.add(1, Component.translatable("tooltip.first_steps.rock_block").withStyle(ChatFormatting.GRAY));
            }
        });
    }
}
