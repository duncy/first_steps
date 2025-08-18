// package nz.duncy.first_steps.screen;

// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.minecraft.client.gui.DrawContext;
// import net.minecraft.item.ItemStack;
// import net.minecraft.text.MutableText;
// import net.minecraft.text.Text;
// import nz.duncy.first_steps.FirstSteps;
// import nz.duncy.first_steps.item.ModItems;

// @Environment(EnvType.CLIENT)
// public enum KnappingSelection {
//     HOE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.hoe_head"), new ItemStack(ModItems.STONE_HEAD_HOE)),
//     SHOVEL(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.shovel_head"), new ItemStack(ModItems.STONE_HEAD_SHOVEL)),
//     AXE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.axe_head"), new ItemStack(ModItems.STONE_HEAD_AXE)),
//     KNIFE(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.knife_head"), new ItemStack(ModItems.STONE_HEAD_KNIFE)),
//     SPEAR(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.spear_head"), new ItemStack(ModItems.STONE_HEAD_SPEAR)),
//     ARROW(Text.translatable("screen." + FirstSteps.MOD_ID + ".knapping.arrow_head"), new ItemStack(ModItems.STONE_HEAD_ARROW));


//     protected static final KnappingSelection[] VALUES = values();
//     private final Text text;
//     private final ItemStack icon;

//     private KnappingSelection(MutableText text, ItemStack icon) {
//         this.text = text;
//         this.icon = icon;
//     }

//     void renderIcon(DrawContext context, int x, int y) {
//         context.drawItem(this.icon, x, y);
//     }

//     Text getText() {
//         return this.text;
//     }
// }
