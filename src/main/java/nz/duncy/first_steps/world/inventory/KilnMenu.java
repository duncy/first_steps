package nz.duncy.first_steps.world.inventory;

import java.util.List;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.recipebook.ServerPlaceRecipe.CraftingMenuAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.item.crafting.KilnRecipe;
import nz.duncy.first_steps.world.item.crafting.ModRecipePropertySet;

public class KilnMenu extends RecipeBookMenu {
    public static final int INGREDIENT_SLOT = 0;
    public static final int RESULT_SLOT = 1;
    public static final int FUEL_SLOT = 2;
    public static final int SLOT_COUNT = 3;
    public static final int DATA_COUNT = 4;
    final Container container;
    private final ContainerData data;
    protected final Level level;
    private final RecipePropertySet acceptedInputs;
    private final RecipeBookType recipeBookType;

    public KilnMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public KilnMenu(int i, Inventory inventory, Container container, ContainerData containerData) {
        super(ModMenuType.KILN_MENU, i);

        this.recipeBookType = RecipeBookType.FURNACE;
        checkContainerSize(container, 3);
        checkContainerDataCount(containerData, 4);
        this.container = container;
        this.data = containerData;
        this.level = inventory.player.level();
        this.acceptedInputs = this.level.recipeAccess().propertySet(ModRecipePropertySet.KILN);
        this.addSlot(new KilnCrucibleSlot(container, INGREDIENT_SLOT,62, 45));
        this.addSlot(new FurnaceResultSlot(inventory.player, container, RESULT_SLOT, 107, 21));
        this.addSlot(new KilnFuelSlot(this, container, FUEL_SLOT, 107, 70));
        this.addStandardInventorySlots(inventory, 8, 95);
        this.addDataSlots(containerData);
   }

    protected boolean isFuel(ItemStack itemStack) {
        return this.level.fuelValues().isFuel(itemStack);
    }

    protected boolean canBurn(ItemStack itemStack) {
        return this.acceptedInputs.test(itemStack);
     }

    @Override
    public void fillCraftSlotsStackedContents(StackedItemContents stackedItemContents) {
        if (this.container instanceof StackedContentsCompatible) {
             ((StackedContentsCompatible) this.container).fillStackedContents(stackedItemContents);
        }
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return this.recipeBookType;
    }

    @Override
    public PostPlaceAction handlePlacement(final boolean useMaxItems, final boolean allowDroppingItemsToClear, final RecipeHolder<?> recipe, final ServerLevel level, final Inventory inventory) {
        final List<Slot> slotsToClear = List.of(this.getSlot(0), this.getSlot(2));
        final CraftingMenuAccess<KilnRecipe> craftingMenuAccess = new CraftingMenuAccess<KilnRecipe>() {
            @Override
            public void fillCraftSlotsStackedContents(final StackedItemContents stackedContents) {
                KilnMenu.this.fillCraftSlotsStackedContents(stackedContents);
            }

            @Override
            public void clearCraftingContent() {
                slotsToClear.forEach(s -> s.set(ItemStack.EMPTY));
            }

            @Override
            public boolean recipeMatches(final RecipeHolder<KilnRecipe> recipe) {
                return ((KilnRecipe)recipe.value()).matches(new SingleRecipeInput(KilnMenu.this.container.getItem(0)), level);
            }
        };

    
        return ServerPlaceRecipe.placeRecipe(craftingMenuAccess, 1, 1, List.of(this.getSlot(0)), slotsToClear, inventory, (RecipeHolder<KilnRecipe>) recipe, useMaxItems, allowDroppingItemsToClear);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();

            if (index == 0 || index == 1 || index == 2) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, index == 1)) {
                    return ItemStack.EMPTY;
                }
                
                if (index == 1) {
                    slot.onQuickCraft(itemStack2, itemStack);
                }
            } 
            
            else {
                if (itemStack2.is(ModItems.CRUCIBLE)) {
                    if (!this.moveItemStackTo(itemStack2, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }else if (this.isFuel(itemStack2)) {
                    if (!this.moveItemStackTo(itemStack2, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (index >= 3 && index < 30) {
                        if (!this.moveItemStackTo(itemStack2, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemStack2, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    public float getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float)this.data.get(0) / (float)i, 0.0F, 1.0F);
    }

    public float getBurnProgress() {
        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }

    public Slot getResultSlot() {
        return (Slot)this.slots.get(2);
    }

    public int getData(int index) {
        return this.data.get(index);
    }
}