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
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipePropertySet;
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
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.screen.slot.MannequinSlotsManager;

public class MannequinScreenHandler extends ScreenHandler {
    protected final ScreenHandlerContext context;
	protected final PlayerEntity player;
	protected final Inventory input;
    protected final CraftingResultInventory output = new CraftingResultInventory() {
		@Override
		public void markDirty() {
			MannequinScreenHandler.this.onContentChanged(this);
		}
	};
    private final World world;
	private final int slotCount;

    public MannequinScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(ModScreenHandlers.MANNEQUIN_SCREEN_HANDLER, syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

    public MannequinScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId);
        this.context = context;
		this.player = playerInventory.player;
        this.world = this.player.getWorld();
        MannequinSlotsManager mannequinSlotsManager = createMannequinSlotsManager(this.world.getRecipeManager());
		this.input = this.createInputInventory(mannequinSlotsManager.getInputSlotCount());
		this.slotCount = mannequinSlotsManager.getInputSlotCount();
		this.addInputSlots(mannequinSlotsManager);
		this.addPlayerSlots(playerInventory, 8, 84);
    }

    private static MannequinSlotsManager createMannequinSlotsManager(RecipeManager recipeManager) {
		RecipePropertySet recipePropertySet = recipeManager.getPropertySet(RecipePropertySet.SMITHING_BASE);
		RecipePropertySet recipePropertySet2 = recipeManager.getPropertySet(RecipePropertySet.SMITHING_TEMPLATE);
		RecipePropertySet recipePropertySet3 = recipeManager.getPropertySet(RecipePropertySet.SMITHING_ADDITION);
		return MannequinSlotsManager.builder()
			.input(0, 62, 18, recipePropertySet2::canUse)
			.input(1, 80, 18, recipePropertySet::canUse)
			.input(2, 98, 18, recipePropertySet3::canUse)
			.input(3, 62,36, recipePropertySet2::canUse)
			.input(4, 80, 36, recipePropertySet::canUse)
			.input(5, 98, 36, recipePropertySet3::canUse)
			.input(6, 62, 54, recipePropertySet2::canUse)
			.input(7, 80, 54, recipePropertySet::canUse)
			.input(8, 98, 54, recipePropertySet::canUse)
			.build();
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

    private void addInputSlots(MannequinSlotsManager mannequinSlotsManager) {
		for (final MannequinSlotsManager.MannequinSlot mannequinSlot : mannequinSlotsManager.getInputSlots()) {
			this.addSlot(new Slot(this.input, mannequinSlot.slotId(), mannequinSlot.x(), mannequinSlot.y()) {
				@Override
				public boolean canInsert(ItemStack stack) {
					return mannequinSlot.mayPlace().test(stack);
				}
			});
		}
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
			boolean bl = this.getSlot(0).hasStack() && this.getSlot(1).hasStack() && this.getSlot(2).hasStack() && !this.getSlot(this.getInputSlotCount()).hasStack();
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
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getStack();
			itemStack = itemStack2.copy();
			int i = this.getPlayerInventoryStartIndex();
			int j = this.getPlayerHotbarEndIndex();
			if (slot == this.getInputSlotCount()) {
				if (!this.insertItem(itemStack2, i, j, true)) {
					return ItemStack.EMPTY;
				}

				slot2.onQuickTransfer(itemStack2, itemStack);
			} else if (slot >= 0 && slot < this.getInputSlotCount()) {
				if (!this.insertItem(itemStack2, i, j, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.isValidIngredient(itemStack2) && slot >= this.getPlayerInventoryStartIndex() && slot < this.getPlayerHotbarEndIndex()) {
				if (!this.insertItem(itemStack2, 0, this.getInputSlotCount(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (slot >= this.getPlayerInventoryStartIndex() && slot < this.getPlayerInventoryEndIndex()) {
				if (!this.insertItem(itemStack2, this.getPlayerHotbarStartIndex(), this.getPlayerHotbarEndIndex(), false)) {
					return ItemStack.EMPTY;
				}
			} else if (slot >= this.getPlayerHotbarStartIndex()
				&& slot < this.getPlayerHotbarEndIndex()
				&& !this.insertItem(itemStack2, this.getPlayerInventoryStartIndex(), this.getPlayerInventoryEndIndex(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot2.setStack(ItemStack.EMPTY);
			} else {
				slot2.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot2.onTakeItem(player, itemStack2);
		}

		return itemStack;
	}

	protected boolean isValidIngredient(ItemStack stack) {
		return true;
	}

	private int getInputSlotCount() {
		return this.slotCount;
	}

	private int getPlayerInventoryStartIndex() {
		return this.getInputSlotCount() + 1;
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
