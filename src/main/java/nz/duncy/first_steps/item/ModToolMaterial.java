package nz.duncy.first_steps.item;

import java.util.function.Supplier;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import nz.duncy.first_steps.block.ModBlocks;

public enum ModToolMaterial implements ToolMaterial {
    FLINT(MiningLevels.STONE, 131, 4.0f, 1.0f, 5, () -> Ingredient.ofItems(ModBlocks.FLINT_ROCK)),
    BASALT(MiningLevels.STONE, 131, 4.0f, 1.0f, 5, () -> Ingredient.ofItems(ModBlocks.BASALT_ROCK)),
    OBSIDIAN(MiningLevels.STONE, 131, 4.0f, 1.0f, 5, () -> Ingredient.ofItems(ModBlocks.OBSIDIAN_ROCK)),
    COPPER(MiningLevels.STONE, 175, 5.0f, 2.0f, 14, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
    BRONZE(MiningLevels.IRON, 225, 5.0f, 2.0f, 14, () -> Ingredient.ofItems(ModItems.BRONZE_INGOT)),
    STEEL(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 10, () -> Ingredient.ofItems(ModItems.STEEL_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
