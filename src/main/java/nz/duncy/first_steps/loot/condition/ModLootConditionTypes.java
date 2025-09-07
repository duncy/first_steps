package nz.duncy.first_steps.loot.condition;

import com.mojang.serialization.MapCodec;

import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModLootConditionTypes {

    public static final LootConditionType BIOME_CHECK = register("biome_check", BiomeLootCondition.CODEC);

    private static LootConditionType register(String id, MapCodec<? extends LootCondition> codec) {
		return Registry.register(Registries.LOOT_CONDITION_TYPE, Identifier.of(FirstSteps.MOD_ID, id), new LootConditionType(codec));
	}
    
}
