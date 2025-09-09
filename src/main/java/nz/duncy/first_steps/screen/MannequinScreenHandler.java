package nz.duncy.first_steps.screen;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldEvents;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.screen.slot.MannequinSlot;
import nz.duncy.first_steps.util.ModTags;

public class MannequinScreenHandler extends ScreenHandler {
	private static final Identifier SLOT_BACKGROUND = Identifier.of(FirstSteps.MOD_ID, "container/slot");
    protected final ScreenHandlerContext context;
	protected final PlayerEntity player;
	private final Inventory input;
    protected final CraftingResultInventory output = new CraftingResultInventory() {
		@Override
		public void markDirty() {
			MannequinScreenHandler.this.onContentChanged(this);
		}
	};
	private static final int SLOT_COUNT = 7;
	private static final List<TagKey<Item>> slotExpectationOrder = List.of(
		ModTags.Items.SHOULDER_ARMOR,
		ModTags.Items.TOP_LAYER_ARMOR,
		ModTags.Items.SHOULDER_ARMOR,
		ModTags.Items.HAND_ARMOR,
		ModTags.Items.BASE_LAYER_ARMOR,
		ModTags.Items.HAND_ARMOR,
		ModTags.Items.MID_LAYER_ARMOR
	); 

    public MannequinScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(ModScreenHandlers.MANNEQUIN_SCREEN_HANDLER, syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

    public MannequinScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId);
        this.context = context;
		this.player = playerInventory.player;
		this.input = this.createInputInventory(SLOT_COUNT);
        this.createSlots();
		this.addPlayerSlots(playerInventory, 8, 84);
    }

	private void createSlots() {
		for (int i = 0; i < MannequinSlot.BASE_LAYER_SLOT_INDEX; i++) {
			this.addSlot(new MannequinSlot(this.input, i, (62 + (18 * (i % 3))), (18 + (18 * (int) Math.ceil(i/3))), slotExpectationOrder.get(i), SLOT_BACKGROUND));
		}

		this.addSlot(new MannequinSlot(this.input, MannequinSlot.BASE_LAYER_SLOT_INDEX, (62 + (18 * (MannequinSlot.BASE_LAYER_SLOT_INDEX % 3))), (18 + (18 * (int) Math.ceil(MannequinSlot.BASE_LAYER_SLOT_INDEX/3))), slotExpectationOrder.get(MannequinSlot.BASE_LAYER_SLOT_INDEX), null));

		for (int i = MannequinSlot.BASE_LAYER_SLOT_INDEX + 1; i < SLOT_COUNT - 1; i++) {
			this.addSlot(new MannequinSlot(this.input, i, (62 + (18 * (i % 3))), (18 + (18 * (int) Math.ceil(i/3))), slotExpectationOrder.get(i), SLOT_BACKGROUND));
		}

		this.addSlot(new MannequinSlot(this.input, SLOT_COUNT - 1, (62 + (18 * (SLOT_COUNT % 3))), (18 + (18 * (int) Math.ceil(SLOT_COUNT/3))), slotExpectationOrder.get(SLOT_COUNT - 1), null));

		enableSlot(MannequinSlot.BASE_LAYER_SLOT_INDEX);		
	}    

	private void enableSlot(int index) {
		((MannequinSlot) this.slots.get(index)).setEnabled();
	}

	private void disableSlot(int index) {
		((MannequinSlot) this.slots.get(index)).setDisabled();
	}

    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
		return true;
	}

    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
		stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
		this.output.unlockLastRecipe(player, this.getInputStacks());
		this.decrementStack(0);
		this.decrementStack(1);
		this.decrementStack(2);
		this.context.run((world, pos) -> world.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, pos, 0));
	}

	protected boolean canUse(BlockState state) {
        // return state.isOf(ModBlocks.ARMOURERS_MANNEQUIN);
		return true;
    }

    private List<ItemStack> getInputStacks() {
		return List.of(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
	}

	private SmithingRecipeInput createRecipeInput() {
		return new SmithingRecipeInput(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
	}

	private void decrementStack(int slot) {
		ItemStack itemStack = this.input.getStack(slot);
		if (!itemStack.isEmpty()) {
			itemStack.decrement(1);
			this.input.setStack(slot, itemStack);
		}
	}

    private SimpleInventory createInputInventory(int size) {
		return new SimpleInventory(size) {
			@Override
			public void markDirty() {
				super.markDirty();
				MannequinScreenHandler.this.onContentChanged(this);
			}
		};
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		super.onContentChanged(inventory);
		if (inventory == this.input) {
			// Check if base armor present, if so, enable mid and top layer slots if top layer not already present
			if (inventory.getStack(MannequinSlot.BASE_LAYER_SLOT_INDEX).isIn(ModTags.Items.BASE_LAYER_ARMOR)) {
				enableSlot(MannequinSlot.MID_LAYER_SLOT_INDEX);
				enableSlot(MannequinSlot.TOP_LAYER_SLOT_INDEX);
				if (inventory.getStack(MannequinSlot.TOP_LAYER_SLOT_INDEX).isIn(ModTags.Items.TOP_LAYER_ARMOR)) {
					if (inventory.getStack(MannequinSlot.TOP_LAYER_SLOT_INDEX).isIn(ModTags.Items.SHOULDER_SUPPORTING_ARMOR)) {
						enableSlot(MannequinSlot.LEFT_SHOULDER_SLOT_INDEX);
						enableSlot(MannequinSlot.RIGHT_SHOULDER_SLOT_INDEX);
					} else {
						disableSlot(MannequinSlot.LEFT_SHOULDER_SLOT_INDEX);
						disableSlot(MannequinSlot.RIGHT_SHOULDER_SLOT_INDEX);
					}
					if (inventory.getStack(MannequinSlot.TOP_LAYER_SLOT_INDEX).isIn(ModTags.Items.HAND_SUPPORTING_ARMOR)) {
						enableSlot(MannequinSlot.LEFT_HAND_SLOT_INDEX);
						enableSlot(MannequinSlot.RIGHT_HAND_SLOT_INDEX);
					} else {
						disableSlot(MannequinSlot.LEFT_HAND_SLOT_INDEX);
						disableSlot(MannequinSlot.RIGHT_HAND_SLOT_INDEX);
					}
				} else {
					disableSlot(MannequinSlot.LEFT_SHOULDER_SLOT_INDEX);
					disableSlot(MannequinSlot.RIGHT_SHOULDER_SLOT_INDEX);
					disableSlot(MannequinSlot.LEFT_HAND_SLOT_INDEX);
					disableSlot(MannequinSlot.RIGHT_HAND_SLOT_INDEX);
				}
			} else {
				disableSlot(MannequinSlot.MID_LAYER_SLOT_INDEX);
				disableSlot(MannequinSlot.TOP_LAYER_SLOT_INDEX);
				disableSlot(MannequinSlot.LEFT_SHOULDER_SLOT_INDEX);
				disableSlot(MannequinSlot.RIGHT_SHOULDER_SLOT_INDEX);
				disableSlot(MannequinSlot.LEFT_HAND_SLOT_INDEX);
				disableSlot(MannequinSlot.RIGHT_HAND_SLOT_INDEX);
			}
		}
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.context.get((world, pos) -> !this.canUse(world.getBlockState(pos)) ? false : player.canInteractWithBlockAt(pos, 4.0), true);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotIndex) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotIndex);
		if (slot != null && slot.hasStack()) {
			ItemStack slotItemStack = slot.getStack();
			itemStack = slotItemStack.copy();
			int i = this.getPlayerInventoryStartIndex();
			int j = this.getPlayerHotbarEndIndex();
			if (slotIndex == getMannequinEndIndex()) {
				if (!this.insertItem(slotItemStack, i, j, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickTransfer(slotItemStack, itemStack);
			} else if (slotIndex >= 0 && slotIndex < getMannequinEndIndex()) {
				if (!this.insertItem(slotItemStack, i, j, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.isValidIngredient(slotItemStack) && slotIndex >= this.getPlayerInventoryStartIndex() && slotIndex < this.getPlayerHotbarEndIndex()) {
				if (!this.insertItem(slotItemStack, 0, getMannequinEndIndex(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotIndex >= this.getPlayerInventoryStartIndex() && slotIndex < this.getPlayerInventoryEndIndex()) {
				if (!this.insertItem(slotItemStack, this.getPlayerHotbarStartIndex(), this.getPlayerHotbarEndIndex(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotIndex >= this.getPlayerHotbarStartIndex()
				&& slotIndex < this.getPlayerHotbarEndIndex()
				&& !this.insertItem(slotItemStack, this.getPlayerInventoryStartIndex(), this.getPlayerInventoryEndIndex(), false)) {
				return ItemStack.EMPTY;
			}

			if (slotItemStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (slotItemStack.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, slotItemStack);
		}

		return itemStack;
	}

	protected boolean isValidIngredient(ItemStack stack) {
		return true;
	}

	private int getMannequinEndIndex() {
		return SLOT_COUNT - 1;
	}

	private int getPlayerInventoryStartIndex() {
		return SLOT_COUNT;
	}

	private int getPlayerInventoryEndIndex() {
		return this.getPlayerInventoryStartIndex() + 27;
	}

	private int getPlayerHotbarStartIndex() {
		return this.getPlayerInventoryEndIndex();
	}

	private int getPlayerHotbarEndIndex() {
		return this.getPlayerHotbarStartIndex() + 9;
	}

}
