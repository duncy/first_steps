package nz.duncy.first_steps.world.item.component;

import java.util.EnumMap;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipProvider;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.Metal;

public record CrucibleContainerContents(int temperature, ItemContainerContents inventory) implements TooltipProvider {
    public static final Codec<CrucibleContainerContents> CODEC = RecordCodecBuilder.create(instance -> 
        instance.group(
            Codec.INT.fieldOf("temperature").forGetter(CrucibleContainerContents::temperature),
            ItemContainerContents.CODEC.fieldOf("inventory").forGetter(CrucibleContainerContents::inventory)
        ).apply(instance, CrucibleContainerContents::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CrucibleContainerContents> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, CrucibleContainerContents::temperature,
        ItemContainerContents.STREAM_CODEC, CrucibleContainerContents::inventory,
        CrucibleContainerContents::new
    );

    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(Component.translatable("tooltip.first_steps.crucible.temperature", this.temperature).withStyle(ChatFormatting.GRAY));

        if (inventory != null) {
            EnumMap<Metal, Integer> capacity = new EnumMap<>(Metal.class);
            
            for (ItemStack stack : this.inventory.nonEmptyItems()) {
                int amount = 0;

                if (stack.is(ModItemTags.ONE_NINTH_INGOT_EQUIVALENT)) amount = 1;
                else if (stack.is(ModItemTags.ONE_INGOT_EQUIVALENT)) amount = 9;
                else if (stack.is(ModItemTags.NINE_INGOTS_EQUIVALENT)) amount = 81;

                if (amount > 0) {
                    for (Metal metal : Metal.values()) {
                        if (stack.is(metal.getItemTag())) {
                            capacity.merge(metal, amount, Integer::sum);
                            break;
                        }
                    }
                }
            }

            if (capacity.size() > 1) { 
                int copperAmount = capacity.getOrDefault(Metal.COPPER, 0);
                int tinAmount = capacity.getOrDefault(Metal.TIN, 0);
    
                int bronzeAmount = Metal.buildBronzeAmount(copperAmount, tinAmount);
    
                if (bronzeAmount > 0) {
                    capacity.remove(Metal.COPPER);
                    capacity.remove(Metal.TIN);
                    capacity.merge(Metal.BRONZE, bronzeAmount, Integer::sum);
                }
            }

            if (capacity.size() > 0) {
                consumer.accept(Component.empty());
            }

            for (Metal metal : capacity.keySet()) {
                int amount = capacity.getOrDefault(metal, 0);
                if (amount > 0) {

                    for (Component component : Metal.getAlloyTooltipComponents(metal, amount)) {
                        consumer.accept(component);
                    }
                }
            }
        }
   }    
}
