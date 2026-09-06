package nz.duncy.first_steps.metallurgy;

import java.util.EnumMap;
import java.util.Optional;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.tags.ModItemTags;

public class MetalStorage {
    private final EnumMap<Metal, Integer> capacity = new EnumMap<>(Metal.class);

    public void sendMetal(MetalStorage targetMetalStorage) {
        
        for (Metal metal : this.capacity.keySet()) {
            int amount = this.capacity.get(metal);
            int remainder = targetMetalStorage.recieveMetal(metal, amount);
            sumMetalAmount(metal, remainder - amount);
            break;
        }
    }

    public int recieveMetal(Metal metal, int amount) {
        int remainder = 0;

        if (this.capacity.containsKey(metal) || this.capacity.isEmpty()) {
            if (wouldThisOverfill(amount)) {
                remainder = this.getTotalFillage() + amount % 81;
            }

            sumMetalAmount(metal, amount - remainder);
        }

        return remainder;
    }

    private void sumMetalAmount(Metal metal, int amount) {
        if (this.capacity.getOrDefault(metal, 0) - amount == 0 ) {
            this.capacity.remove(metal);
        } else {
            this.capacity.merge(metal, amount, Integer::sum);
        }
    }

    public int getMetalByIndex(int index) {
        return capacity.getOrDefault(Metal.byIndex(index), 0);
    }

    private int getTotalFillage() {
        return this.capacity.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean wouldThisOverfill(ItemStack newItemStack) {
        return wouldThisOverfill(getItemAmount(newItemStack));
    }

    public boolean wouldThisOverfill(int amount) {
        return this.getTotalFillage() + amount > 81;
    }

    public void addItemAmount(ItemStack itemStack) {
        updateCapacity(itemStack, getItemAmount(itemStack));
    }

    public Optional<Metal> getMetal() {
        return this.capacity.keySet().stream().findFirst();
    }

    public int getItemAmount(ItemStack itemStack) {
        int amount = 0;
        if (itemStack.is(ModItemTags.ONE_NINTH_INGOT_EQUIVALENT)) amount = 1;
        else if (itemStack.is(ModItemTags.ONE_INGOT_EQUIVALENT)) amount = 9;
        else if (itemStack.is(ModItemTags.NINE_INGOTS_EQUIVALENT)) amount = 81;

        return amount;
    }

    private void updateCapacity(ItemStack itemStack, int itemAmount) {
        if (itemAmount != 0){
            for (Metal metal : Metal.values()) {
                if (metal == Metal.NONE) continue;
                if (itemStack.is(metal.getItemTag())) {
                    sumMetalAmount(metal, itemAmount);
                    break;
                }
            }
        }
    }

    public void buildCapacity(NonNullList<ItemStack> itemStacks) {
        this.capacity.clear();

        for (ItemStack itemStack : itemStacks) {
            if (!itemStack.isEmpty()) {
                this.addItemAmount(itemStack);
            }
        }
        
        this.buildAlloys();
    }

    private void buildAlloys() {
        if (this.capacity.size() > 1) { 
            int copperAmount = this.capacity.getOrDefault(Metal.COPPER, 0);
            int tinAmount = this.capacity.getOrDefault(Metal.TIN, 0);

            int bronzeAmount = Metal.buildBronzeAmount(copperAmount, tinAmount);

            if (bronzeAmount > 0) {
                this.capacity.remove(Metal.COPPER);
                this.capacity.remove(Metal.TIN);
                this.capacity.merge(Metal.BRONZE, bronzeAmount, Integer::sum);
                return;
            }

            this.capacity.clear();
        }
    }
}
