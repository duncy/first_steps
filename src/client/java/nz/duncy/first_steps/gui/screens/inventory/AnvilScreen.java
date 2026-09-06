package nz.duncy.first_steps.gui.screens.inventory;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.network.cache.ClientAnvilState;
import nz.duncy.first_steps.network.protocol.common.custom.AnvilSelectionPacketPayload;
import nz.duncy.first_steps.world.inventory.AnvilMenu;
import nz.duncy.first_steps.world.item.crafting.AnvilRecipe;

public class AnvilScreen extends RecipeSelectionScreen<AnvilRecipe, AnvilMenu> {
    
    public AnvilScreen(AnvilMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        if (ClientAnvilState.pendingRecipes != this.recipes) {
            updateRecipes(ClientAnvilState.pendingRecipes);
            ClientAnvilState.pendingRecipes = SingleInputSet.empty();
        }
    }

    @Override
    protected void sendSelection(int selection) {
        if (this.minecraft.level.isClientSide()) {
            AnvilSelectionPacketPayload payload = new AnvilSelectionPacketPayload(selection);
            ClientPlayNetworking.send(payload);
        }
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen." + FirstSteps.MOD_ID + ".tool_head.switcher");
    }
}
