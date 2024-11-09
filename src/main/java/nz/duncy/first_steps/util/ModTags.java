package nz.duncy.first_steps.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModTags {
    public static class Blocks {
        
        public static final TagKey<Block> NEEDS_STONE_ROCK = 
            createTag("needs_stone_rock");
        
        public static final TagKey<Block> NEEDS_OBSIDIAN_ROCK = 
            createTag("needs_obsidian_rock");

        public static final TagKey<Block> NEEDS_COPPER_TOOL  = 
            createTag("needs_copper_tool");

        public static final TagKey<Block> NEEDS_BRONZE_TOOL = 
            createTag("needs_bronze_tool");

        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = 
            createTag("incorrect_for_copper_tool");

	    public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = 
            createTag("incorrect_for_bronze_tool");
	

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(FirstSteps.MOD_ID, name));
        }

    }

    public static class Items {

        public static final TagKey<Item> ORES = createTag("ores");

        private static TagKey<Item> createTag(String name) {
           return TagKey.of(RegistryKeys.ITEM, Identifier.of(FirstSteps.MOD_ID, name));
        }

    }

    public static final String INCORRECT_FOR_BRONZE_TOOL = null;
}
