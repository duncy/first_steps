package nz.duncy.first_steps.screen;

import java.util.List;
import java.util.Optional;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.item.custom.MannequinItem;
import nz.duncy.first_steps.screen.slot.MannequinSlot;

public class MannequinScreenHandler extends ScreenHandler {
    protected final ScreenHandlerContext context;
	protected final PlayerEntity player;
	private final Inventory input;
    protected final CraftingResultInventory output = new CraftingResultInventory() {
		@Override
		public void markDirty() {
			MannequinScreenHandler.this.onContentChanged(this);
		}
	};
    private final World world;
	private static final int SLOT_COUNT = 7;

    public MannequinScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(ModScreenHandlers.MANNEQUIN_SCREEN_HANDLER, syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

    public MannequinScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId);
        this.context = context;
		this.player = playerInventory.player;
        this.world = this.player.getWorld();
		this.input = this.createInputInventory(SLOT_COUNT);
        this.createSlots();
		this.addPlayerSlots(playerInventory, 8, 84);
    }

	private void createSlots() {
		for (int i = 0; i < SLOT_COUNT - 1; i++) {
			this.addSlot(new MannequinSlot(this.input, i, (62 + (18 * (i % 3))), (18 + (18 * (int) Math.ceil(i/3))), null));
		}

		this.addSlot(new MannequinSlot(this.input, SLOT_COUNT - 1, (62 + (18 * (SLOT_COUNT % 3))), (18 + (18 * (int) Math.ceil(SLOT_COUNT/3))), null));

		enableSlot(MannequinSlot.BASE_LAYER_SLOT_INDEX);		
	}    

	private void enableSlot(int index) {
		((MannequinSlot) this.slots.get(index)).setEnabled();
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
        return state.isOf(ModBlocks.ARMOURERS_MANNEQUIN);
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

	public void updateResult() {
		SmithingRecipeInput smithingRecipeInput = this.createRecipeInput();
		Optional<RecipeEntry<SmithingRecipe>> optional;
		if (this.world instanceof ServerWorld serverWorld) {
			optional = serverWorld.getRecipeManager().getFirstMatch(RecipeType.SMITHING, smithingRecipeInput, serverWorld);
		} else {
			optional = Optional.empty();
		}

		optional.ifPresentOrElse(recipe -> {
			ItemStack itemStack = ((SmithingRecipe)recipe.value()).craft(smithingRecipeInput, this.world.getRegistryManager());
			this.output.setLastRecipe(recipe);
			this.output.setStack(0, itemStack);
		}, () -> {
			this.output.setLastRecipe(null);
			this.output.setStack(0, ItemStack.EMPTY);
		});
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
			this.updateResult();
		}
        if (this.world instanceof ServerWorld) {
			boolean bl = this.getSlot(0).hasStack() && this.getSlot(1).hasStack() && this.getSlot(2).hasStack() && !this.getSlot(SLOT_COUNT).hasStack();
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
		FirstSteps.LOGGER.info(String.valueOf(slotIndex) + " " + this.slots.get(slotIndex).toString());
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
