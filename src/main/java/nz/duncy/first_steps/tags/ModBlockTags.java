package nz.duncy.first_steps.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import nz.duncy.first_steps.FirstSteps;

public class ModBlockTags {
        public static final TagKey<Block> NEEDS_BRONZE_TOOL = 
            createTag("needs_bronze_tool");

	    public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = 
            createTag("incorrect_for_bronze_tool");
	

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, name));
        }
}
