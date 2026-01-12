package nz.duncy.first_steps.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import nz.duncy.first_steps.FirstSteps;

public class ModItemTags {
    public static final TagKey<Item> IS_ROCK = createTag("is_rock");

    // public static final TagKey<Item> ORES = createTag("ores");

    // public static final TagKey<Item> PADDED_CLOTH_REPAIR = createTag("padded_cloth_repair");
    public static final TagKey<Item> FLINT_REPAIR = createTag("flint_repair");
    public static final TagKey<Item> BASALT_REPAIR = createTag("basalt_repair");
    public static final TagKey<Item> OBSIDIAN_REPAIR = createTag("obsidian_repair");
    // public static final TagKey<Item> BRONZE_REPAIR = createTag("bronze_repair");

    public static final TagKey<Item> TINDER = createTag("tinder");

    // public static final TagKey<Item> BASE_LAYER_ARMOR = createTag("base_layer_armor");
    // public static final TagKey<Item> MID_LAYER_ARMOR = createTag("mid_layer_armor");
    // public static final TagKey<Item> TOP_LAYER_ARMOR = createTag("top_layer_armor");
    // public static final TagKey<Item> SHOULDER_ARMOR = createTag("shoulder_armor");
    // public static final TagKey<Item> HAND_ARMOR = createTag("hand_armor");
    // public static final TagKey<Item> TABARD = createTag("tabard");

    // public static final TagKey<Item> HAND_SUPPORTING_ARMOR = createTag("hand_supporting_armor");
    // public static final TagKey<Item> SHOULDER_SUPPORTING_ARMOR = createTag("shoulder_supporting_armor");

    private static TagKey<Item> createTag(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, name));
    }
}
