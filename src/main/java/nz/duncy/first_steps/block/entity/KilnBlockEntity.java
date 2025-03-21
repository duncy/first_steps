package nz.duncy.first_steps.block.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.KilnBlock;
import nz.duncy.first_steps.component.ModDataComponentTypes;
import nz.duncy.first_steps.recipe.KilningRecipe;
import nz.duncy.first_steps.recipe.ModRecipes;
import nz.duncy.first_steps.screen.KilnScreenHandler;

public class KilnBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int KILN_FUEL_INPUT_SLOT = 0; // Fuel input
    private static final int KILN_INPUT_SLOT = 1; // Kiln top input
    private static final int KILN_FUEL_OUTPUT_SLOT = 2; // Fuel output

    private int temperature;
    private int maxTemperature;
    private static final int minTemperature = 20;

    int litTimeRemaining;
	int litTotalTime;
	int cookingTimeSpent;
	int cookingTotalTime;

    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
        public int get(int index) {
            return switch (index) {
                case 0 -> KilnBlockEntity.this.litTimeRemaining;
                case 1 -> KilnBlockEntity.this.litTotalTime;
                case 2 -> KilnBlockEntity.this.cookingTimeSpent;
                case 3 -> KilnBlockEntity.this.cookingTotalTime;
                case 4 -> KilnBlockEntity.this.temperature;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> KilnBlockEntity.this.litTimeRemaining = value;
                case 1 -> KilnBlockEntity.this.litTotalTime = value;
                case 2 -> KilnBlockEntity.this.cookingTimeSpent = value;
                case 3 -> KilnBlockEntity.this.cookingTotalTime = value;
                case 4 -> KilnBlockEntity.this.temperature = value;
            };
        }

		@Override
		public int size() {
			return 5;
		}
	};

    private final Reference2IntOpenHashMap<RegistryKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
	private final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, KilningRecipe> matchGetter;

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY, pos, state);

        this.temperature = 20;

        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.KILNING_TYPE);

        
        
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + FirstSteps.MOD_ID + ".kiln");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.readNbt(nbt, registries);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, this.inventory, registries);
		this.cookingTimeSpent = nbt.getShort("cooking_time_spent");
		this.cookingTotalTime = nbt.getShort("cooking_total_time");
		this.litTimeRemaining = nbt.getShort("lit_time_remaining");
		this.litTotalTime = nbt.getShort("lit_total_time");
        this.temperature = nbt.getShort("temperature");
		NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");

		for (String string : nbtCompound.getKeys()) {
			this.recipesUsed.put(RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(string)), nbtCompound.getInt(string));
		}

        this.maxTemperature = this.getMaxTemperature((ItemStack)this.inventory.get(KILN_FUEL_INPUT_SLOT));
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.writeNbt(nbt, registries);
		nbt.putShort("cooking_time_spent", (short)this.cookingTimeSpent);
		nbt.putShort("cooking_total_time", (short)this.cookingTotalTime);
		nbt.putShort("lit_time_remaining", (short)this.litTimeRemaining);
		nbt.putShort("lit_total_time", (short)this.litTotalTime);
        nbt.putShort("temperature", (short) this.temperature);
		Inventories.writeNbt(nbt, this.inventory, registries);
		NbtCompound nbtCompound = new NbtCompound();
		this.recipesUsed.forEach((recipeKey, count) -> nbtCompound.putInt(recipeKey.getValue().toString(), count));
		nbt.put("RecipesUsed", nbtCompound);
	}

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    // public void tick(ServerWorld serverWorld, BlockPos pos, BlockState state, KilnBlockEntity blockEntity) {
    //     if (world.isClient()) {
    //         return;
    //     }

    //     boolean burning = blockEntity.isBurning();
    //     boolean dirty = false;

    //     ItemStack fuelItemstack = blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT);

    //     if (blockEntity.isBurning()) {
    //         --blockEntity.burnTime;
    //         if (blockEntity.temperature < blockEntity.maxTemperature) {
    //             ++blockEntity.temperature;
    //         }
    //         if (blockEntity.burnTime == 0) {
    //             FirstSteps.LOGGER.info("burn over, checking recipe");
    //             if (hasWasteProductRecipe(serverWorld)) {
    //                 FirstSteps.LOGGER.info("has recipe");
    //                 craftWasteProduct(serverWorld);
    //             }
    //             FirstSteps.LOGGER.info("done");
    //         }
    //     } else if (blockEntity.temperature > minTemperature){
    //         --blockEntity.temperature;
    //     }

    //     // Kiln is burning OR
    //     // Fuel slot is not empty and output slot empty or can receive new items
    //     if (blockEntity.isBurning() || (!isFuelSlotEmpty(blockEntity) && isOutputSlotEmptyOrReceivable())) {
            
    //         if (!blockEntity.isBurning()) {
    //             blockEntity.burnTime = getFuelTime(world.getFuelRegistry(), fuelItemstack); // Get fuel burn time
    //             blockEntity.fuelTime = blockEntity.burnTime;
    //             blockEntity.maxTemperature = getMaxTemperature(fuelItemstack);
    //             if (blockEntity.isBurning()) {
    //                 dirty = true;
    //                 if (!isFuelSlotEmpty(blockEntity)) {
    //                     ItemStack remainderFuelItemStack = fuelItemstack.getItem().getRecipeRemainder();
    //                     // this.currentFuel = new ItemStack(fuelItemstack.getItem(), 1);
    //                     fuelItemstack.decrement(1); // Consume fuel
    //                     if (fuelItemstack.isEmpty()) {
    //                         // Add the empty bucket back to the fuel slot
    //                         blockEntity.inventory.set(KILN_FUEL_INPUT_SLOT, remainderFuelItemStack == null ? ItemStack.EMPTY : remainderFuelItemStack); 
    //                     }
    //                 }
    //             }
    //         }
    //     }

    //     if (burning != blockEntity.isBurning()) {
    //         dirty = true;
    //         state = (BlockState)state.with(KilnBlock.LIT, blockEntity.isBurning());
    //         world.setBlockState(pos, state, 3);
    //     }

    //     if (!isTopInputSlotEmpty(blockEntity)) {
    //         ItemStack crucibleItemStack = blockEntity.inventory.get(KILN_INPUT_SLOT);
    //         int crucibleTemperature  = crucibleItemStack.getOrDefault(ModDataComponentTypes.TEMPERATURE, 20);

    //         if (crucibleTemperature < blockEntity.temperature) {
    //             crucibleTemperature++;
    //         } else if (crucibleTemperature > blockEntity.temperature) {
    //             crucibleTemperature--;
    //         }

    //         dirty = true;
    //         crucibleItemStack.set(ModDataComponentTypes.TEMPERATURE, crucibleTemperature);

    //     }
        
    //     if (dirty) {
    //         markDirty(world, pos, state);
    //     }
    // }

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, KilnBlockEntity blockEntity) {
        boolean bl = blockEntity.isBurning();
        boolean bl2 = false;
        if (blockEntity.isBurning()) {
            blockEntity.litTimeRemaining--;
        }

        ItemStack itemStack = blockEntity.inventory.get(KILN_FUEL_OUTPUT_SLOT);
        ItemStack itemStack2 = blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT);
        boolean bl3 = !itemStack2.isEmpty();
        boolean bl4 = !itemStack.isEmpty();
        if (blockEntity.isBurning() || bl4 && bl3) {
            SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(itemStack2);
            RecipeEntry<KilningRecipe> recipeEntry;
            if (bl3) {
                recipeEntry = blockEntity.matchGetter.getFirstMatch(singleStackRecipeInput, world).orElse(null);
            } else {
                recipeEntry = null;
            }

            int i = blockEntity.getMaxCountPerStack();
            if (!blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                blockEntity.litTimeRemaining = blockEntity.getFuelTime(world.getFuelRegistry(), itemStack);
                blockEntity.litTotalTime = blockEntity.litTimeRemaining;
                if (blockEntity.isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            blockEntity.inventory.set(1, item.getRecipeRemainder());
                        }
                    }
                }
            }

            if (blockEntity.isBurning() && canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                blockEntity.cookingTimeSpent++;
                if (blockEntity.cookingTimeSpent == blockEntity.cookingTotalTime) {
                    blockEntity.cookingTimeSpent = 0;
                    blockEntity.cookingTotalTime = getCookTime(world, blockEntity);
                    if (craftRecipe(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }

                    bl2 = true;
                }
            } else {
                blockEntity.cookingTimeSpent = 0;
            }
        } else if (!blockEntity.isBurning() && blockEntity.cookingTimeSpent > 0) {
            blockEntity.cookingTimeSpent = MathHelper.clamp(blockEntity.cookingTimeSpent - 2, 0, blockEntity.cookingTotalTime);
        }

        if (bl != blockEntity.isBurning()) {
            bl2 = true;
            state = state.with(KilnBlock.LIT, Boolean.valueOf(blockEntity.isBurning()));
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }

        if (bl2) {
            markDirty(world, pos, state);
        }
    }

    private static int getCookTime(ServerWorld world, KilnBlockEntity kiln) {
		SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(kiln.getStack(KILN_FUEL_INPUT_SLOT));
		return (Integer)kiln.matchGetter
			.getFirstMatch(singleStackRecipeInput, world)
			.map(recipe -> ((KilningRecipe)recipe.value()).getCookingTime())
			.orElse(200);
	}

    private static boolean craftRecipe(
		DynamicRegistryManager dynamicRegistryManager,
		@Nullable RecipeEntry<KilningRecipe> recipe,
		SingleStackRecipeInput input,
		DefaultedList<ItemStack> inventory,
		int maxCount
	) {
		if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
			ItemStack itemStack = inventory.get(KILN_FUEL_INPUT_SLOT);
			ItemStack itemStack2 = recipe.value().craft(input, dynamicRegistryManager);
			ItemStack itemStack3 = inventory.get(KILN_FUEL_OUTPUT_SLOT);
			if (itemStack3.isEmpty()) {
				inventory.set(KILN_FUEL_OUTPUT_SLOT, itemStack2.copy());
			} else if (ItemStack.areItemsAndComponentsEqual(itemStack3, itemStack2)) {
				itemStack3.increment(1);
			}


			itemStack.decrement(1);
			return true;
		} else {
			return false;
		}
	}

    private static boolean canAcceptRecipeOutput(
		DynamicRegistryManager dynamicRegistryManager,
		@Nullable RecipeEntry<KilningRecipe> recipe,
		SingleStackRecipeInput input,
		DefaultedList<ItemStack> inventory,
		int maxCount
	) {
		if (!inventory.get(0).isEmpty() && recipe != null) {
			ItemStack itemStack = recipe.value().craft(input, dynamicRegistryManager);
			if (itemStack.isEmpty()) {
				return false;
			} else {
				ItemStack itemStack2 = inventory.get(2);
				if (itemStack2.isEmpty()) {
					return true;
				} else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
					return false;
				} else {
					return itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxCount() ? true : itemStack2.getCount() < itemStack.getMaxCount();
				}
			}
		} else {
			return false;
		}
	}
    
    private static boolean canAcceptRecipeOutput(
		DynamicRegistryManager dynamicRegistryManager,
		@Nullable KilningRecipe recipe,
		SingleStackRecipeInput input,
		DefaultedList<ItemStack> inventory,
		int maxCount
	) {
		if (!inventory.get(0).isEmpty() && recipe != null) {
			ItemStack itemStack = recipe.value().craft(input, dynamicRegistryManager);
			if (itemStack.isEmpty()) {
				return false;
			} else {
				ItemStack itemStack2 = inventory.get(2);
				if (itemStack2.isEmpty()) {
					return true;
				} else if (!ItemStack.areItemsAndComponentsEqual(itemStack2, itemStack)) {
					return false;
				} else {
					return itemStack2.getCount() < maxCount && itemStack2.getCount() < itemStack2.getMaxCount() ? true : itemStack2.getCount() < itemStack.getMaxCount();
				}
			}
		} else {
			return false;
		}
	}


    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
		return fuelRegistry.getFuelTicks(stack);
	}

    // private boolean isFuelSlotEmpty(KilnBlockEntity blockEntity) {
    //     return blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT).isEmpty();
    // }

    public boolean isTopInputSlotEmpty(KilnBlockEntity blockEntity) {
        return blockEntity.inventory.get(KILN_INPUT_SLOT).isEmpty();
    }

    // private boolean hasWasteProductRecipe(ServerWorld serverWorld) {
    //     Optional<RecipeEntry<KilningRecipe>> recipe = getWasteProductRecipe(serverWorld);
    //     FirstSteps.LOGGER.info(String.valueOf(recipe));
    //     return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))
    //             && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    // }

    // private Optional<RecipeEntry<KilningRecipe>> getWasteProductRecipe(ServerWorld serverWorld) {
    //     return this.matchGetter.getFirstMatch(new SingleStackRecipeInput(this.inventory.getFirst()), serverWorld);
    // }

    // private void craftWasteProduct(ServerWorld serverWorld) {
    //     Optional<RecipeEntry<KilningRecipe>> recipe = getWasteProductRecipe(serverWorld);

    //     // this.removeStack(KILN_FUEL_INPUT_SLOT, 1);

    //     this.setStack(KILN_FUEL_OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
    //             getStack(KILN_FUEL_OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));
    // }

    // private boolean canInsertItemIntoOutputSlot(Item item) {
    //     return this.getStack(KILN_FUEL_OUTPUT_SLOT).getItem() == item || this.getStack(KILN_FUEL_OUTPUT_SLOT).isEmpty();
    // }

    // private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
    //     return this.getStack(KILN_FUEL_OUTPUT_SLOT).getCount() + result.getCount() <= getStack(KILN_FUEL_OUTPUT_SLOT).getMaxCount();
    // }

    // private boolean isOutputSlotEmptyOrReceivable() {
    //     return this.getStack(KILN_FUEL_OUTPUT_SLOT).isEmpty() || this.getStack(KILN_FUEL_OUTPUT_SLOT).getCount() < this.getStack(KILN_FUEL_OUTPUT_SLOT).getMaxCount();
    // }

    private boolean isBurning() {
		return this.litTimeRemaining > 0;
	}

    private int getMaxTemperature(ItemStack itemStack) {
        return switch (itemStack.getRegistryEntry().toString()) {
            case "minecraft:lava_bucket" -> 1200;
            case "minecraft:coal" -> 1600;
            case "minecraft:coal_block" -> 1600;
            case "minecraft:charcoal" -> 1200;
            case "minecraft:dried_kelp_block" -> 50;
            case "minecraft:blaze_rod" -> 1200;
            default -> 950;
        };
    }

    public void removeCrucible() {
        this.inventory.set(KILN_INPUT_SLOT, ItemStack.EMPTY);
    }

    public ItemStack getCrucible() {
        return this.inventory.get(KILN_INPUT_SLOT);
    }
}
