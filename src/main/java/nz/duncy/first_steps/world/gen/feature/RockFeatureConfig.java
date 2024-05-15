package nz.duncy.first_steps.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.FeatureConfig;

public record RockFeatureConfig(int number, Identifier blockId) implements FeatureConfig {
    public static final Codec<RockFeatureConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("number").forGetter(RockFeatureConfig::number),
            Identifier.CODEC.fieldOf("blockID").forGetter(RockFeatureConfig::blockId))
        .apply(instance, RockFeatureConfig::new));

}
