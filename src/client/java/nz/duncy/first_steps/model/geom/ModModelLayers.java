package nz.duncy.first_steps.model.geom;

import java.util.Set;

import com.google.common.collect.Sets;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.model.PottersWheelModel;
import nz.duncy.first_steps.renderer.blockentity.DecoratedJarRenderer;
import nz.duncy.first_steps.renderer.blockentity.UnfiredDecoratedJarRenderer;
import nz.duncy.first_steps.renderer.blockentity.UnfiredDecoratedPotRenderer;

public class ModModelLayers {
    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
    public static final ModelLayerLocation DECORATED_JAR_BASE = register("decorated_jar_base");
    public static final ModelLayerLocation DECORATED_JAR_SIDES = register("decorated_jar_sides");
    public static final ModelLayerLocation UNFIRED_DECORATED_POT_BASE = register("unfired_decorated_pot_base");
    public static final ModelLayerLocation UNFIRED_DECORATED_POT_SIDES = register("unfired_decorated_pot_sides");
    public static final ModelLayerLocation UNFIRED_DECORATED_JAR_BASE = register("unfired_decorated_jar_base");
    public static final ModelLayerLocation UNFIRED_DECORATED_JAR_SIDES = register("unfired_decorated_jar_sides");
    public static final ModelLayerLocation POTTERS_WHEEL = register("potters_wheel");

    private static ModelLayerLocation register(String string) {
        return register(string, "main");
    }

    private static ModelLayerLocation register(String string, String string2) {
        ModelLayerLocation modelLayerLocation = createLocation(string, string2);
        if (!ALL_MODELS.add(modelLayerLocation)) {
           throw new IllegalStateException("Duplicate registration for " + String.valueOf(modelLayerLocation));
        } else {
           return modelLayerLocation;
        }
    }

    private static ModelLayerLocation createLocation(String string, String string2) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string), string2);
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering model layers for " + FirstSteps.MOD_ID);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DECORATED_JAR_BASE, DecoratedJarRenderer::createBaseLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DECORATED_JAR_SIDES, DecoratedJarRenderer::createSidesLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.UNFIRED_DECORATED_POT_BASE, UnfiredDecoratedPotRenderer::createBaseLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.UNFIRED_DECORATED_POT_SIDES, UnfiredDecoratedPotRenderer::createSidesLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.UNFIRED_DECORATED_JAR_BASE, UnfiredDecoratedJarRenderer::createBaseLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.UNFIRED_DECORATED_JAR_SIDES, UnfiredDecoratedJarRenderer::createSidesLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.POTTERS_WHEEL, PottersWheelModel::getLayer);
    }
}
