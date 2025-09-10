package nz.duncy.first_steps.client.render.entity.model;

import java.util.Set;

import com.google.common.collect.Sets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.block.entity.PottersWheelBlockModel;
import nz.duncy.first_steps.client.render.block.entity.UnfiredDecoratedPotBlockEntityRenderer;
import nz.duncy.first_steps.client.render.entity.ArmorersMannequinEntityRenderer;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayers {

    private static final String MAIN = "main";
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();
    public static final EntityModelLayer STONE_SPEAR = registerMain("stpne_spear");
    public static final EntityModelLayer FLINT_SPEAR = registerMain("flint_spear");
    public static final EntityModelLayer BASALT_SPEAR = registerMain("basalt_spear");
    public static final EntityModelLayer OBSIDIAN_SPEAR = registerMain("obsidian_spear");
    public static final EntityModelLayer COPPER_SPEAR = registerMain("copper_spear");
    public static final EntityModelLayer BRONZE_SPEAR = registerMain("bronze_spear");
    public static final EntityModelLayer IRON_SPEAR = registerMain("iron_spear");
    public static final EntityModelLayer POTTERS_WHEEL = registerMain("potters_wheel");
    public static final EntityModelLayer UNFIRED_DECORATED_POT_BASE = registerMain("unfired_decorated_pot_base");
    public static final EntityModelLayer UNFIRED_DECORATED_POT_SIDES = registerMain("unfired_decorated_pot_sides");
    public static final EntityModelLayer ARMORERS_MANNEQUIN = registerMain("armorers_mannequin");


    private static EntityModelLayer registerMain(String id) {
        return register(id, MAIN);
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        }
        return entityModelLayer;
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Identifier.of(FirstSteps.MOD_ID, id), layer);
    }

    public static void registerEntityModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.STONE_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.FLINT_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BASALT_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.OBSIDIAN_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.COPPER_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.BRONZE_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.IRON_SPEAR, SpearEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.POTTERS_WHEEL, PottersWheelBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.UNFIRED_DECORATED_POT_BASE, UnfiredDecoratedPotBlockEntityRenderer::getTopBottomNeckTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.UNFIRED_DECORATED_POT_SIDES, UnfiredDecoratedPotBlockEntityRenderer::getSidesTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.ARMORERS_MANNEQUIN, ArmorersMannequinEntityModel::getTexturedModelData);
    }
}