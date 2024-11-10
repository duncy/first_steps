package nz.duncy.first_steps.screen;

import java.util.List;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ScreenHandlerPropertyUpdateS2CPacket;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import nz.duncy.first_steps.block.entity.RockBlockEntity;

public class KnappingSelectionScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    public KnappingSelectionScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new ArrayPropertyDelegate(1));
	}

    public KnappingSelectionScreenHandler(int syncId, PlayerInventory playerInventory, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.KNAPPING_SELECTION_SCREEN_HANDLER, syncId);
        this.propertyDelegate = arrayPropertyDelegate;



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

    public int getSelection() {
        return this.propertyDelegate.get(0);
    }

    public void setSelection(KnappingSelection selection) {
        this.propertyDelegate.set(0, selection.ordinal());

    }

    public void onClosed(PlayerEntity player) {
        if (getSelection() >= 0) {
            sendContentUpdates();
        }   
        super.onClosed(player);
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
