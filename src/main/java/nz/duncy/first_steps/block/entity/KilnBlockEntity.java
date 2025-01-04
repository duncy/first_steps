package nz.duncy.first_steps.block.entity;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.custom.KilnBlock;
import nz.duncy.first_steps.component.ModDataComponentTypes;
import nz.duncy.first_steps.recipe.KilningRecipe;
import nz.duncy.first_steps.recipe.ModRecipes;
import nz.duncy.first_steps.screen.KilnScreenHandler;

public class KilnBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int KILN_FUEL_INPUT_SLOT = 0; // Fuel input
    private static final int KILN_INPUT_SLOT = 1; // Kiln top input
    private static final int KILN_FUEL_OUTPUT_SLOT = 2; // Fuel output

    // private ItemStack currentFuel;

    protected final PropertyDelegate propertyDelegate;

    private int burnTime;
    private int fuelTime;

    private int temperature;
    private int maxTemperature;
    private static final int minTemperature = 20;

    private final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, KilningRecipe> matchGetter;

    

    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN_BLOCK_ENTITY, pos, state);

        this.temperature = 20;

        this.matchGetter = ServerRecipeManager.createCachedMatchGetter(ModRecipes.KILNING_TYPE);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KilnBlockEntity.this.burnTime;
                    case 1 -> KilnBlockEntity.this.fuelTime;
                    case 2 -> KilnBlockEntity.this.temperature;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> KilnBlockEntity.this.burnTime = value;
                    case 1 -> KilnBlockEntity.this.fuelTime = value;
                    case 2 -> KilnBlockEntity.this.temperature = value;
                };
            }

            @Override
            public int size() {
                return 3;
            }
        };
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
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("kiln.burnTime", this.burnTime);
        nbt.putInt("kiln.temperature", this.temperature);
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        this.burnTime = nbt.getInt("kiln.burnTime");
        this.fuelTime = this.getFuelTime(world.getFuelRegistry(), (ItemStack)this.inventory.get(KILN_FUEL_INPUT_SLOT));
        this.maxTemperature = this.getMaxTemperature((ItemStack)this.inventory.get(KILN_FUEL_INPUT_SLOT));
        this.temperature = nbt.getInt("kiln.temperature");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new KilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public void tick(ServerWorld serverWorld, BlockPos pos, BlockState state, KilnBlockEntity blockEntity) {
        if (world.isClient()) {
            return;
        }

        boolean burning = blockEntity.isBurning();
        boolean dirty = false;

        ItemStack fuelItemstack = blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT);

        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
            if (blockEntity.temperature < blockEntity.maxTemperature) {
                ++blockEntity.temperature;
            }
            if (blockEntity.burnTime == 0) {
                FirstSteps.LOGGER.info("burn over, checking recipe");
                if (hasWasteProductRecipe(serverWorld)) {
                    FirstSteps.LOGGER.info("has recipe");
                    craftWasteProduct(serverWorld);
                }
                FirstSteps.LOGGER.info("done");
            }
        } else if (blockEntity.temperature > minTemperature){
            --blockEntity.temperature;
        }

        // Kiln is burning OR
        // Fuel slot is not empty and output slot empty or can receive new items
        if (blockEntity.isBurning() || (!isFuelSlotEmpty(blockEntity) && isOutputSlotEmptyOrReceivable())) {
            
            if (!blockEntity.isBurning()) {
                blockEntity.burnTime = getFuelTime(world.getFuelRegistry(), fuelItemstack); // Get fuel burn time
                blockEntity.fuelTime = blockEntity.burnTime;
                blockEntity.maxTemperature = getMaxTemperature(fuelItemstack);
                if (blockEntity.isBurning()) {
                    dirty = true;
                    if (!isFuelSlotEmpty(blockEntity)) {
                        ItemStack remainderFuelItemStack = fuelItemstack.getItem().getRecipeRemainder();
                        // this.currentFuel = new ItemStack(fuelItemstack.getItem(), 1);
                        fuelItemstack.decrement(1); // Consume fuel
                        if (fuelItemstack.isEmpty()) {
                            // Add the empty bucket back to the fuel slot
                            blockEntity.inventory.set(KILN_FUEL_INPUT_SLOT, remainderFuelItemStack == null ? ItemStack.EMPTY : remainderFuelItemStack); 
                        }
                    }
                }
            }
        }

        if (burning != blockEntity.isBurning()) {
            dirty = true;
            state = (BlockState)state.with(KilnBlock.LIT, blockEntity.isBurning());
            world.setBlockState(pos, state, 3);
        }

        if (!isTopInputSlotEmpty(blockEntity)) {
            ItemStack crucibleItemStack = blockEntity.inventory.get(KILN_INPUT_SLOT);
            int crucibleTemperature  = crucibleItemStack.getOrDefault(ModDataComponentTypes.TEMPERATURE, 20);

            if (crucibleTemperature < blockEntity.temperature) {
                crucibleTemperature++;
            } else if (crucibleTemperature > blockEntity.temperature) {
                crucibleTemperature--;
            }

            dirty = true;
            crucibleItemStack.set(ModDataComponentTypes.TEMPERATURE, crucibleTemperature);

        }
        
        if (dirty) {
            markDirty(world, pos, state);
        }
    }

    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
		return fuelRegistry.getFuelTicks(stack);
	}

    private boolean isFuelSlotEmpty(KilnBlockEntity blockEntity) {
        return blockEntity.inventory.get(KILN_FUEL_INPUT_SLOT).isEmpty();
    }

    public boolean isTopInputSlotEmpty(KilnBlockEntity blockEntity) {
        return blockEntity.inventory.get(KILN_INPUT_SLOT).isEmpty();
    }

    private boolean hasWasteProductRecipe(ServerWorld serverWorld) {
        Optional<RecipeEntry<KilningRecipe>> recipe = getWasteProductRecipe(serverWorld);
        FirstSteps.LOGGER.info(String.valueOf(recipe));
        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))
                && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeEntry<KilningRecipe>> getWasteProductRecipe(ServerWorld serverWorld) {
        return this.matchGetter.getFirstMatch(new SingleStackRecipeInput(this.inventory.getFirst()), serverWorld);
    }

    private void craftWasteProduct(ServerWorld serverWorld) {
        Optional<RecipeEntry<KilningRecipe>> recipe = getWasteProductRecipe(serverWorld);

        // this.removeStack(KILN_FUEL_INPUT_SLOT, 1);

        this.setStack(KILN_FUEL_OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
                getStack(KILN_FUEL_OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(KILN_FUEL_OUTPUT_SLOT).getItem() == item || this.getStack(KILN_FUEL_OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(KILN_FUEL_OUTPUT_SLOT).getCount() + result.getCount() <= getStack(KILN_FUEL_OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(KILN_FUEL_OUTPUT_SLOT).isEmpty() || this.getStack(KILN_FUEL_OUTPUT_SLOT).getCount() < this.getStack(KILN_FUEL_OUTPUT_SLOT).getMaxCount();
    }

    private boolean isBurning() {
        return this.burnTime > 0;
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
