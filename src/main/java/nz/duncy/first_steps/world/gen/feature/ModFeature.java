package nz.duncy.first_steps.world.gen.feature;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import nz.duncy.first_steps.FirstSteps;

public class ModFeature {
    public static final Identifier ROCK_FEATURE_ID = Identifier.of(FirstSteps.MOD_ID, "rock_feature");
    public static final RockFeature ROCK_FEATURE = new RockFeature(RockFeatureConfig.CODEC);

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Identifier feature_id, F feature) {
        Registry.register(Registries.FEATURE, feature_id, feature);
    }

    public static void registerFeatures() {
        FirstSteps.LOGGER.info("Registering worldgen features for " + FirstSteps.MOD_ID);
        register(ROCK_FEATURE_ID, ROCK_FEATURE);
    }
}
