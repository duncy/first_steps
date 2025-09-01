package nz.duncy.first_steps.item.custom;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import nz.duncy.first_steps.util.ModTags;

public class ModToolMaterials {
	public static final ToolMaterial FLINT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModTags.Items.FLINT_REPAIR);
	public static final ToolMaterial BASALT = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModTags.Items.BASALT_REPAIR);
	public static final ToolMaterial OBSIDIAN = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.0F, 1.0F, 5, ModTags.Items.OBSIDIAN_REPAIR);
	public static final ToolMaterial COPPER = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL, 160, 4.5F, 2.0F, 14, ModTags.Items.COPPER_REPAIR);
	public static final ToolMaterial BRONZE = new ToolMaterial(ModTags.Blocks.INCORRECT_FOR_BRONZE_TOOL, 200, 5.0F, 2.0F, 8, ModTags.Items.BRONZE_REPAIR);
}

