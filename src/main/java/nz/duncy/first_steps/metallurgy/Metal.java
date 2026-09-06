package nz.duncy.first_steps.metallurgy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.tags.ModItemTags;
import nz.duncy.first_steps.world.item.ModItems;

public enum Metal implements StringRepresentable {
    NONE("none", 0xFFFFFF, ChatFormatting.WHITE, Items.AIR, Items.AIR, null),
    TIN("tin", 0xFFFFF9e9, ChatFormatting.WHITE, ModItems.RAW_TIN, ModItems.TIN_INGOT, ModItemTags.TIN_SOURCE),
    COPPER("copper", 0xFFFFA17F, ChatFormatting.GOLD, Items.RAW_COPPER, Items.COPPER_INGOT, ModItemTags.COPPER_SOURCE),
    BRONZE("bronze", 0xFFFAF1C8, ChatFormatting.YELLOW, ModItems.RAW_BRONZE, ModItems.TIN_INGOT, ModItemTags.BRONZE_SOURCE),
    IRON("iron", 0xFFD5D5D5, ChatFormatting.GRAY, Items.RAW_IRON, Items.IRON_INGOT, ModItemTags.IRON_SOURCE),
    GOLD("gold", 0xFFFFF79D, ChatFormatting.YELLOW, Items.RAW_GOLD, Items.GOLD_INGOT, ModItemTags.GOLD_SOURCE),
    NETHERITE("netherite", 0xFF4C4143, ChatFormatting.BLACK, Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT, ModItemTags.NETHERITE_SOURCE);

    private final String name;
    private final int color;
    private final ChatFormatting textStyle;
    private final Item rawItem;
    private final Item ingotItem;
    private final TagKey<Item> itemTag;

    Metal(String name, int color, ChatFormatting textStyle, Item rawItem, Item ingotItem, TagKey<Item> itemTag) {
        this.name = name;
        this.color = color;
        this.textStyle = textStyle;
        this.rawItem = rawItem;
        this.ingotItem = ingotItem;
        this.itemTag = itemTag;
    }

    public int getColor() {
        return this.color;
    }

    public Component getDisplayName() {
        return Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible." + this.name).withStyle(this.textStyle);
    }

    public Item getRawItem() {
        return this.rawItem;
    }

    public Item getIngotItem() {
        return this.ingotItem;
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

        if (amount == 81) tooltipComponents.add(Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible.quantity_block.one").withStyle(net.minecraft.ChatFormatting.GRAY));
        else {
            int ingots = amount / 9;
            if (ingots > 1) tooltipComponents.add(Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible.quantity_ingot.many", ingots).withStyle(net.minecraft.ChatFormatting.GRAY));
            else if (ingots == 1) tooltipComponents.add(Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible.quantity_ingot.one").withStyle(net.minecraft.ChatFormatting.GRAY));

            int nuggets = amount % 9;
            if (nuggets > 1) tooltipComponents.add(Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible.quantity_nugget.many", nuggets).withStyle(net.minecraft.ChatFormatting.GRAY));
            else if (nuggets == 1) tooltipComponents.add(Component.translatable("tooltip." + FirstSteps.MOD_ID + ".crucible.quantity_nugget.one").withStyle(net.minecraft.ChatFormatting.GRAY));
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

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
