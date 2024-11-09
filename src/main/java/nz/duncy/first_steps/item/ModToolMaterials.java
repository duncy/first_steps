package nz.duncy.first_steps.item;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.util.ModTags;

public enum ModToolMaterials implements ToolMaterial {
    FLINT(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, () -> Ingredient.ofItems(ModBlocks.FLINT_ROCK)),
    BASALT(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, () -> Ingredient.ofItems(ModBlocks.BASALT_ROCK)),
    OBSIDIAN(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, () -> Ingredient.ofItems(ModBlocks.OBSIDIAN_ROCK)),
    COPPER(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL, 160, 4.5F, 2.0F, 14, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
	BRONZE(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL, 200, 5.0F, 2.0F, 8, () -> Ingredient.ofItems(ModItems.BRONZE_INGOT));
    // STEEL(MiningLevels.DIAMOND, 1561, 8.0f, 3.0f, 10, () -> Ingredient.ofItems(ModItems.STEEL_INGOT));

    private final TagKey<Block> inverseTag;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterials(
		final TagKey<Block> inverseTag,
		final int itemDurability,
		final float miningSpeed,
		final float attackDamage,
		final int enchantability,
		final Supplier<Ingredient> repairIngredient) {
		this.inverseTag = inverseTag;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = Suppliers.memoize(repairIngredient::get);
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
	public TagKey<Block> getInverseTag() {
		return this.inverseTag;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return (Ingredient)this.repairIngredient.get();
	}
}
