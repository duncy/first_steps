package nz.duncy.first_steps.loot.condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.WeatherCheckLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

public record BiomeLootCondition(Set<TagKey<Biome>> biomeTags) implements LootCondition {
    public static final MapCodec<BiomeLootCondition> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
					TagKey.codec(RegistryKeys.BIOME)
                        .listOf()
                        .optionalFieldOf("biome_tags", List.of())
                        .forGetter(bc -> new ArrayList<>(bc.biomeTags))
				)
				.apply(instance, list -> new BiomeLootCondition(new HashSet<>(list)))
	);

    @Override
    public boolean test(LootContext lootContext) {
        ServerWorld serverWorld = lootContext.getWorld();
        Vec3d pos = lootContext.get(LootContextParameters.ORIGIN);

        if (serverWorld == null || pos == null || biomeTags.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos = BlockPos.ofFloored(pos.getX(), pos.getY(), pos.getZ());
            RegistryEntry<Biome> biome = serverWorld.getBiome(blockPos);
            return biomeTags.stream().anyMatch(tag -> biome.isIn(tag));
        }
    }

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.BIOME_CHECK;
    }

    public static BiomeLootCondition.Builder create() {
		return new BiomeLootCondition.Builder();
	}

	public static class Builder implements LootCondition.Builder {
		private Set<TagKey<Biome>> biomeTags = Set.of();

		public BiomeLootCondition.Builder of(TagKey<Biome>... biomeTags) {
			this.biomeTags = Set.of(biomeTags);
			return this;
		}

		public BiomeLootCondition build() {
			return new BiomeLootCondition(this.biomeTags);
		}
	}
}
