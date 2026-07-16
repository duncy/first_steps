package nz.duncy.first_steps.metallurgy;

import java.util.EnumMap;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import nz.duncy.first_steps.tags.ModItemTags;

public class MetalStorage {
    private final EnumMap<Metal, Integer> capacity = new EnumMap<>(Metal.class);
    private int temperature = 20;

    public MetalStorage() {

    }

    public int getMetalByIndex(int index) {
        return capacity.getOrDefault(Metal.byIndex(index), 0);
    }

    private int getTotalFillage() {
        return this.capacity.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean wouldThisOverfill(ItemStack newItemStack) {
        return this.getTotalFillage() + getItemAmount(newItemStack) > 81;
    }
    
    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int[] getMetalAmounts() {
        return this.capacity.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public void setMetalAmounts(int[] metalAmounts) {
        for (int index = 0; index < metalAmounts.length; index++) {
            int amount = metalAmounts[index];
            Metal metal = Metal.byIndex(index);

            if (amount > 0) {
                this.capacity.put(metal, metalAmounts[index]);
            } else {
                this.capacity.remove(metal);
            } 
        }
    }

    public void addItemAmount(ItemStack itemStack) {
        updateCapacity(itemStack, getItemAmount(itemStack));
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
                if (itemStack.is(metal.getItemTag())) {
                    this.capacity.merge(metal, itemAmount, Integer::sum);
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
