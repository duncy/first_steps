package nz.duncy.first_steps.util;

import net.minecraft.block.Block;
//import net.minecraft.item.Item;
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

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(FirstSteps.MOD_ID, name));
        }

    }

    public static class Items {
        //private static TagKey<Item> createTag(String name) {
        //    return TagKey.of(RegistryKeys.ITEM, new Identifier(FirstSteps.MOD_ID, name));
        //}

    }
}
