package nz.duncy.first_steps.world.inventory;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.network.protocol.common.custom.AnvilRecipePacketPayload;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;

public class AnvilMenu extends RecipeSelectionMenu<AnvilRecipe, AnvilMenu> {

    public AnvilMenu(int i, Inventory inventory) {
        super(ModMenuType.ANVIL_SELECTION_MENU, i, inventory);
    }

    public AnvilMenu(int i, Inventory inventory, SingleInputSet<AnvilRecipe> recipes, BlockPos blockPos) {
        super(ModMenuType.ANVIL_SELECTION_MENU, i, inventory, recipes, blockPos);
    }   

    @Override
    protected void sendRecipes(Player player) {
        if (!player.level().isClientSide()) {
            ServerPlayNetworking.send((ServerPlayer) player, new AnvilRecipePacketPayload(getRecipes()));
        }
    }
    
    public void dropHead(int selection, ServerPlayer player) {
        player.closeContainer();
        ServerLevel serverLevel = player.level();
        if (!serverLevel.isClientSide()) {
            if (selection >= 0) {
                ContextMap contextMap = SlotDisplayContext.fromLevel(serverLevel);
                ItemStack itemStack = getRecipes().entries().get(selection).recipe().optionDisplay().resolveForFirstStack(contextMap).copy();
                BlockPos blockPos = getBlockPos();
                serverLevel.addFreshEntity(new ItemEntity(serverLevel, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack));
                serverLevel.destroyBlock(blockPos, false);
            }
        }
    }
}
