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

        public static final TagKey<Item> PADDED_CLOTH_REPAIR = createTag("padded_cloth_repair");
        public static final TagKey<Item> FLINT_REPAIR = createTag("flint_repair");
        public static final TagKey<Item> BASALT_REPAIR = createTag("basalt_repair");
        public static final TagKey<Item> OBSIDIAN_REPAIR = createTag("obsidian_repair");
        public static final TagKey<Item> COPPER_REPAIR = createTag("copper_repair");
        public static final TagKey<Item> BRONZE_REPAIR = createTag("bronze_repair");

        public static final TagKey<Item> TINDER = createTag("tinder");

        public static final TagKey<Item> BASE_LAYER_ARMOR = createTag("base_layer_armor");
        public static final TagKey<Item> MID_LAYER_ARMOR = createTag("mid_layer_armor");
        public static final TagKey<Item> TOP_LAYER_ARMOR = createTag("top_layer_armor");
        public static final TagKey<Item> SHOULDER_ARMOR = createTag("shoulder_armor");
        public static final TagKey<Item> HAND_ARMOR = createTag("hand_armor");
        public static final TagKey<Item> TABARD = createTag("tabard");

        public static final TagKey<Item> HAND_SUPPORTING_ARMOR = createTag("hand_supporting_armor");
        public static final TagKey<Item> SHOULDER_SUPPORTING_ARMOR = createTag("shoulder_supporting_armor");

        private static TagKey<Item> createTag(String name) {
           return TagKey.of(RegistryKeys.ITEM, Identifier.of(FirstSteps.MOD_ID, name));
        }

    }

    public static final String INCORRECT_FOR_BRONZE_TOOL = null;
}
