package nz.duncy.first_steps.item.entity.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.model.ModEntityModelLayers;
import nz.duncy.first_steps.model.SpearModel;

public class ModEntityRenderer {
    public static void registerSpearEntities() {
        EntityRendererRegistry.register(ModItemEntities.BASALT_SPEAR, BasaltSpearEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.SPEAR, SpearModel::getTexturedModelData);
    }
}
