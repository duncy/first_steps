package nz.duncy.first_steps.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.ModItems;

@Environment(EnvType.CLIENT)
public enum KnappingSelection {
    HOE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.hoe_head"), new ItemStack(ModItems.STONE_HEAD_HOE)),
    SHOVEL(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.shovel_head"), new ItemStack(ModItems.STONE_HEAD_SHOVEL)),
    AXE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.axe_head"), new ItemStack(ModItems.STONE_HEAD_AXE)),
    KNIFE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.knife_head"), new ItemStack(ModItems.STONE_HEAD_KNIFE)),
    SPEAR(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.spear_head"), new ItemStack(ModItems.STONE_HEAD_SPEAR)),
    ARROW(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.arrow_head"), new ItemStack(ModItems.STONE_HEAD_ARROW));


    protected static final KnappingSelection[] VALUES = values();
    // private static final int field_32317 = 16;
    // protected static final int field_32316 = 5;
    private final Text text;
    private final ItemStack icon;

    private KnappingSelection(MutableText text, ItemStack icon) {
        this.text = text;
        this.icon = icon;
    }

    void renderIcon(DrawContext context, int x, int y) {
        context.drawItem(this.icon, x, y);
    }

    Text getText() {
        return this.text;
    }


    // GameModeSelectionScreen$GameModeSelection next() {
    // GameModeSelectionScreen$GameModeSelection var10000;
    // switch (1.field_24574[this.ordinal()]) {
    //     case 1:
    //         var10000 = SURVIVAL;
    //         break;
    //     case 2:
    //         var10000 = ADVENTURE;
    //         break;
    //     case 3:
    //         var10000 = SPECTATOR;
    //         break;
    //     case 4:
    //         var10000 = CREATIVE;
    //         break;
    //     default:
    //         throw new IncompatibleClassChangeError();
    // }

    // return var10000;
    // }

    // static Selection of(GameMode gameMode) {
    // Selection var10000;
    // switch (1.field_24575[gameMode.ordinal()]) {
    //     case 1:
    //         var10000 = SPECTATOR;
    //         break;
    //     case 2:
    //         var10000 = SURVIVAL;
    //         break;
    //     case 3:
    //         var10000 = CREATIVE;
    //         break;
    //     case 4:
    //         var10000 = ADVENTURE;
    //         break;
    //     default:
    //         throw new IncompatibleClassChangeError();
    // }

    // return var10000;
    // }
}
