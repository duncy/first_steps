package nz.duncy.first_steps.screen;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.display.CuttingRecipeDisplay.Grouping;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import nz.duncy.first_steps.block.entity.RockBlockEntity;
import nz.duncy.first_steps.network.packet.KnappingRecipePayload;

public class KnappingSelectionScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public final Grouping<StonecuttingRecipe> recipes;
    private final RockBlockEntity blockEntity;

    public KnappingSelectionScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new ArrayPropertyDelegate(1), null, null);
	}

    public KnappingSelectionScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate arrayPropertyDelegate, Grouping<StonecuttingRecipe> recipes, RockBlockEntity blockEntity) {
        super(ModScreenHandlers.KNAPPING_SELECTION_SCREEN_HANDLER, syncId);
        this.propertyDelegate = arrayPropertyDelegate;
        this.recipes = recipes; 
        this.blockEntity = blockEntity;

        sendRecipes(playerInventory.player);
        

        // this.addSlot(new KilnFuelSlot(this, inventory, 0, 44, 50)); // Fuel input
        // this.addSlot(new KilnTopSlot(inventory, 1, 80, 21)); // Top input
        // this.addSlot(new KilnOutputSlot(playerInventory.player, inventory, 2, 116, 50)); // Fuel output
        // for (int i = 0; i < 9; i++) {
        //     this.addSlot(new CrucibleSlot(this, inventory, i, 24 + (22 * (i % 3)), 17 + (18 * ((int) Math.ceil(i/3)))));
        // }

        // addPlayerInventory(playerInventory);
        // addPlayerHotbar(playerInventory);

        // addProperties(arrayPropertyDelegate);
    }

    private void sendRecipes(PlayerEntity player) {
        if (!player.getWorld().isClient()) {
            ServerPlayNetworking.send((ServerPlayerEntity) player, new KnappingRecipePayload(getAvailableRecipes()));
        }
    }

    public Grouping<StonecuttingRecipe> getAvailableRecipes() {
		return this.recipes;
	}

	public int getAvailableRecipeCount() {
		return this.recipes.size();
	}

    public int getSelection() {
        return this.propertyDelegate.get(0);
    }

    public void setSelection(int index) {
        this.propertyDelegate.set(0, index);

    }

    public void dropHead(int selection, ServerPlayerEntity player) {
        player.closeHandledScreen();
        if (!this.blockEntity.getWorld().isClient) {
            if (selection >= 0) {
                this.blockEntity.dropHead(this.recipes, selection);
            }
        }
    }

    
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }
    
}
