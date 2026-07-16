package nz.duncy.first_steps.metallurgy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.ModItems;

public enum Metal {
    TIN("tooltip.first_steps.crucible.tin", 0xFFFFF9e9, ChatFormatting.WHITE, ModItems.RAW_TIN, ModItemTags.TIN_SOURCE),
    COPPER("tooltip.first_steps.crucible.copper", 0xFFFFA17F, ChatFormatting.GOLD, Items.RAW_COPPER, ModItemTags.COPPER_SOURCE),
    BRONZE("tooltip.first_steps.crucible.bronze", 0xFFFAF1C8, ChatFormatting.YELLOW, ModItems.RAW_BRONZE, ModItemTags.BRONZE_SOURCE),
    IRON("tooltip.first_steps.crucible.iron", 0xFFD5D5D5, ChatFormatting.GRAY, Items.RAW_IRON, ModItemTags.IRON_SOURCE),
    GOLD("tooltip.first_steps.crucible.gold", 0xFFFFF79D, ChatFormatting.YELLOW, Items.RAW_GOLD, ModItemTags.GOLD_SOURCE),
    NETHERITE("tooltip.first_steps.crucible.netherite", 0xFF4C4143, ChatFormatting.BLACK, Items.NETHERITE_SCRAP, ModItemTags.NETHERITE_SOURCE);


    private final String name;
    private final int color;
    private final ChatFormatting textStyle;
    private final Item itemIcon;
    private final TagKey<Item> itemTag;

    Metal(String name, int color, ChatFormatting textStyle, Item itemIcon, TagKey<Item> itemTag) {
        this.name = name;
        this.color = color;
        this.textStyle = textStyle;
        this.itemIcon = itemIcon;
        this.itemTag = itemTag;
    }

    public int getColor() {
        return this.color;
    }

    public Component getDisplayName() {
        return Component.translatable(this.name).withStyle(this.textStyle);
    }

    public Item getItemIcon() {
        return this.itemIcon;
    }

    public TagKey<Item> getItemTag() {
        return this.itemTag;
    }

    public static Metal byIndex(int index) {
        return values()[index];
    }

    public static List<Component> getAlloyTooltipComponents(int index, int amount) {
        return getAlloyTooltipComponents(byIndex(index), amount);
    }

    public static List<Component> getAlloyTooltipComponents(Metal metal, int amount) {
        List<Component> tooltipComponents = new ArrayList<>(); 

        tooltipComponents.add(metal.getDisplayName());

        if (amount == 81) tooltipComponents.add(Component.translatable("tooltip.first_steps.crucible.quantity_block.one").withStyle(net.minecraft.ChatFormatting.GRAY));
        else {
            int ingots = amount / 9;
            if (ingots > 1) tooltipComponents.add(Component.translatable("tooltip.first_steps.crucible.quantity_ingot.many", ingots).withStyle(net.minecraft.ChatFormatting.GRAY));
            else if (ingots == 1) tooltipComponents.add(Component.translatable("tooltip.first_steps.crucible.quantity_ingot.one").withStyle(net.minecraft.ChatFormatting.GRAY));

            int nuggets = amount % 9;
            if (nuggets > 1) tooltipComponents.add(Component.translatable("tooltip.first_steps.crucible.quantity_nugget.many", nuggets).withStyle(net.minecraft.ChatFormatting.GRAY));
            else if (nuggets == 1) tooltipComponents.add(Component.translatable("tooltip.first_steps.crucible.quantity_nugget.one").withStyle(net.minecraft.ChatFormatting.GRAY));
        } 

        return tooltipComponents;
    }

    public static int buildBronzeAmount(int copperAmount, int tinAmount) {
        if (copperAmount > 0 && tinAmount > 0) {
            if (copperAmount / tinAmount == 8) {
                return copperAmount + tinAmount;
            }
        }
        return 0;
    }
}
