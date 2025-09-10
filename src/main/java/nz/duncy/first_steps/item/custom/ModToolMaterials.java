package nz.duncy.first_steps.item.custom;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import nz.duncy.first_steps.util.ModTags;

public class ModToolMaterials {
	// incorrectBlocksForDrops,  durability,  speed,  attackDamageBonus,  enchantmentValue, repairItems
	public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 50, 2.0F, 0.0F, 5, ModTags.Items.FLINT_REPAIR);
	public static final ToolMaterial BASALT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 50, 2.0F, 0.0F, 5, ModTags.Items.BASALT_REPAIR);
	public static final ToolMaterial OBSIDIAN = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 50, 2.0F, 0.0F, 5, ModTags.Items.OBSIDIAN_REPAIR);
	public static final ToolMaterial COPPER = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL, 150, 3.0F, 0.5F, 14, ModTags.Items.COPPER_REPAIR);
	public static final ToolMaterial BRONZE = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL, 400, 5.0F, 1.0F, 8, ModTags.Items.BRONZE_REPAIR);
}

