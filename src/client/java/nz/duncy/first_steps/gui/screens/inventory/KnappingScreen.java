package nz.duncy.first_steps.gui.screens.inventory;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.network.cache.ClientKnappingState;
import nz.duncy.first_steps.network.protocol.common.custom.KnappingSelectionPacketPayload;
import nz.duncy.first_steps.world.inventory.KnappingMenu;

public class KnappingScreen extends RecipeSelectionScreen<StonecutterRecipe, KnappingMenu> {

    public KnappingScreen(KnappingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        if (ClientKnappingState.pendingRecipes != this.recipes) {
            updateRecipes(ClientKnappingState.pendingRecipes);
            ClientKnappingState.pendingRecipes = SingleInputSet.empty();
        }
    }

    @Override
    protected void sendSelection(int selection) {
        if (this.minecraft.level.isClientSide()) {
            KnappingSelectionPacketPayload payload = new KnappingSelectionPacketPayload(selection);
            ClientPlayNetworking.send(payload);
        }
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen." + FirstSteps.MOD_ID + ".knapping.switcher");
    }
}
