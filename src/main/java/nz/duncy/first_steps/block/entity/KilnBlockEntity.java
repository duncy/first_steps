package nz.duncy.first_steps.block.entity;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
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
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.KilnBlock;
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

    private int litTimeRemaining;
	private int litTotalTime;
    private Item currentFuel;


    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
        public int get(int index) {
            return switch (index) {
                case 0 -> KilnBlockEntity.this.litTimeRemaining;
                case 1 -> KilnBlockEntity.this.litTotalTime;
                case 2 -> KilnBlockEntity.this.temperature;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> KilnBlockEntity.this.litTimeRemaining = value;
                case 1 -> KilnBlockEntity.this.litTotalTime = value;
                case 2 -> KilnBlockEntity.this.temperature = value;
            };
        }

		@Override
		public int size() {
			return 3;
		}
	};

    private final Reference2IntOpenHashMap<RegistryKey<Recipe<?>>> recipesUsed = new Reference2IntOpenHashMap<>();
	private final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, KilningRecipe> matchGetter;

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY, pos, state);

        this.temperature = 20;

        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.KILNING_TYPE);

        this.currentFuel = Items.AIR;

        
        
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
		this.litTimeRemaining = nbt.getShort("lit_time_remaining");
		this.litTotalTime = nbt.getShort("lit_total_time");
        this.temperature = nbt.getShort("temperature");
		NbtCompound nbtCompound = nbt.getCompound("RecipesUsed");
        this.currentFuel = Item.byRawId(nbt.getInt("current_fuel"));

		for (String string : nbtCompound.getKeys()) {
			this.recipesUsed.put(RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(string)), nbtCompound.getInt(string));
		}

        this.maxTemperature = this.getMaxTemperature((ItemStack)this.inventory.get(KILN_FUEL_INPUT_SLOT));
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
		super.writeNbt(nbt, registries);
		nbt.putShort("lit_time_remaining", (short)this.litTimeRemaining);
		nbt.putShort("lit_total_time", (short)this.litTotalTime);
        nbt.putShort("temperature", (short) this.temperature);
        nbt.putInt("current_fuel", Item.getRawId(this.currentFuel));
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

    public static void tick(ServerWorld world, BlockPos pos, BlockState state, KilnBlockEntity blockEntity) {
        boolean burning = blockEntity.isBurning();
        boolean dirty = false;

        ItemStack fuelItemstack = blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT);

        if (blockEntity.isBurning()) {
            blockEntity.litTimeRemaining--;

            if (blockEntity.temperature < blockEntity.maxTemperature) {
                ++blockEntity.temperature;
            }

            if (blockEntity.litTimeRemaining <= 0) {
                // Burning is over, produce waste product
                SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(new ItemStack(blockEntity.currentFuel));
                RecipeEntry<KilningRecipe> recipeEntry = blockEntity.matchGetter.getFirstMatch(singleStackRecipeInput, world).orElse(null);

                int i = blockEntity.getMaxCountPerStack();

                if (canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                    if (craftRecipe(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }
                }
            }

        } else {
            if (blockEntity.temperature > minTemperature) {
                --blockEntity.temperature;
            }
            if (!fuelItemstack.isEmpty()) {
                
                SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(fuelItemstack);
                RecipeEntry<KilningRecipe> recipeEntry = blockEntity.matchGetter.getFirstMatch(singleStackRecipeInput, world).orElse(null);

                int i = blockEntity.getMaxCountPerStack();

                if (canAcceptRecipeOutput(world.getRegistryManager(), recipeEntry, singleStackRecipeInput, blockEntity.inventory, i)) {
                    blockEntity.litTimeRemaining = blockEntity.getFuelTime(world.getFuelRegistry(), fuelItemstack);
                    blockEntity.litTotalTime = blockEntity.litTimeRemaining;

                    if (blockEntity.litTimeRemaining > 0) {
                        blockEntity.maxTemperature = blockEntity.getMaxTemperature(fuelItemstack);

                        blockEntity.currentFuel = fuelItemstack.getItem();

                        fuelItemstack.decrement(1);
                    }
                
                    dirty = true;
                    
                    if (fuelItemstack.isEmpty()) {
                        blockEntity.inventory.set(KILN_FUEL_INPUT_SLOT, blockEntity.currentFuel.getRecipeRemainder());
                    }
                }
            }
        }

        if (burning != blockEntity.isBurning()) {
            dirty = true;
            state = state.with(KilnBlock.LIT, Boolean.valueOf(blockEntity.isBurning()));
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }

        if (dirty) {
            markDirty(world, pos, state);
        }
    }

	public void setLastRecipe(@Nullable RecipeEntry<KilningRecipe> recipe) {
		if (recipe != null) {
			RegistryKey<Recipe<?>> registryKey = recipe.id();
			this.recipesUsed.addTo(registryKey, 1);
		}
	}

    private static boolean craftRecipe(
		DynamicRegistryManager dynamicRegistryManager,
		@Nullable RecipeEntry<KilningRecipe> recipe,
		SingleStackRecipeInput input,
		DefaultedList<ItemStack> inventory,
		int maxCount
	) {
		if (recipe != null && canAcceptRecipeOutput(dynamicRegistryManager, recipe, input, inventory, maxCount)) {
			ItemStack resultStack = recipe.value().craft(input, dynamicRegistryManager);
			ItemStack outputStack = inventory.get(KILN_FUEL_OUTPUT_SLOT);
			if (outputStack.isEmpty()) {
				inventory.set(KILN_FUEL_OUTPUT_SLOT, resultStack.copy());
			} else if (ItemStack.areItemsAndComponentsEqual(outputStack, resultStack)) {
				outputStack.increment(1);
			}

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
		if (recipe != null) {
			ItemStack resultStack = recipe.value().craft(input, dynamicRegistryManager);
			if (resultStack.isEmpty()) {
				return false;
			} else {
				ItemStack outputStack = inventory.get(KILN_FUEL_OUTPUT_SLOT);
				if (outputStack.isEmpty()) {
					return true;
				} else if (!ItemStack.areItemsAndComponentsEqual(outputStack, resultStack)) {
					return false;
				} else {
					return outputStack.getCount() < maxCount && outputStack.getCount() < outputStack.getMaxCount() ? true : outputStack.getCount() < resultStack.getMaxCount();
				}
			}
		} else {
			return true;
		}
	}


    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
		return fuelRegistry.getFuelTicks(stack);
	}

    public boolean isTopInputSlotEmpty(KilnBlockEntity blockEntity) {
        return blockEntity.inventory.get(KILN_INPUT_SLOT).isEmpty();
    }

    private boolean isBurning() {
		return this.litTimeRemaining > 0;
	}

    private int getMaxTemperature(ItemStack itemStack) {
        return switch (itemStack.getRegistryEntry().getIdAsString()) {
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
