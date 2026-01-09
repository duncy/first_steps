package nz.duncy.first_steps.world.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import nz.duncy.first_steps.tags.ModItemTags;

public class ModToolMaterial {
    // incorrectBlocksForDrops,  durability,  speed,  attackDamageBonus,  enchantmentValue, repairItems
	public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModItemTags.FLINT_REPAIR);
	public static final ToolMaterial BASALT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModItemTags.BASALT_REPAIR);
	public static final ToolMaterial OBSIDIAN = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModItemTags.OBSIDIAN_REPAIR);
	// public static final ToolMaterial BRONZE = new ToolMaterial(ModBlockTags.INCORRECT_FOR_BRONZE_TOOL, 400, 5.0F, 1.0F, 8, ModItemTags.BRONZE_REPAIR);
}
