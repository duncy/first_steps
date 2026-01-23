package nz.duncy.first_steps.gui.screens.inventory;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.network.cache.ClientPottersWheelState;
import nz.duncy.first_steps.network.protocol.common.custom.PottersWheelSelectionPacketPayload;
import nz.duncy.first_steps.world.inventory.PottersWheelMenu;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;

public class PottersWheelScreen extends RecipeSelectionScreen<PottersWheelRecipe, PottersWheelMenu> {
    
    public PottersWheelScreen(PottersWheelMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        if (ClientPottersWheelState.pendingRecipes != this.recipes) {
            updateRecipes(ClientPottersWheelState.pendingRecipes);
            ClientPottersWheelState.pendingRecipes = SingleInputSet.empty();
        }
    }

    @Override
    protected void sendSelection(int selection) {
        if (this.minecraft.level.isClientSide()) {
            PottersWheelSelectionPacketPayload payload = new PottersWheelSelectionPacketPayload(selection);
            ClientPlayNetworking.send(payload);
        }
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen." + FirstSteps.MOD_ID + ".potters_wheel.switcher");
    }
}
