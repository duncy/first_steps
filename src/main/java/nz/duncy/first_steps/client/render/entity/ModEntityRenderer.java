package nz.duncy.first_steps.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import nz.duncy.first_steps.entity.ModEntities;

@Environment(EnvType.CLIENT)
public class ModEntityRenderer {
    public static void registerModEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.STONE_SPEAR, StoneSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLINT_SPEAR, FlintSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BASALT_SPEAR, BasaltSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.OBSIDIAN_SPEAR, ObsidianSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.COPPER_SPEAR, CopperSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BRONZE_SPEAR, BronzeSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.IRON_SPEAR, IronSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMORERS_MANNEQUIN, ArmorersMannequinEntityRenderer::new);
    }
}