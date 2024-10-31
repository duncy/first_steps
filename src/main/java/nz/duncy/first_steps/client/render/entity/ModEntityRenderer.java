package nz.duncy.first_steps.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearModel;
import nz.duncy.first_steps.client.render.item.BasaltSpearEntityRenderer;
import nz.duncy.first_steps.item.entity.ModItemEntities;

@Environment(EnvType.CLIENT)
public class ModEntityRenderer {
    public static void registerModEntityRenderers() {
        EntityRendererRegistry.register(ModItemEntities.BASALT_SPEAR, BasaltSpearEntityRenderer::new);
    }
}
