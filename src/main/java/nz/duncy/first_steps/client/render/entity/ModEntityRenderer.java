package nz.duncy.first_steps.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import nz.duncy.first_steps.client.render.item.BasaltSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.BronzeSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.CopperSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.FlintSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.IronSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.ObsidianSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.SteelSpearEntityRenderer;
import nz.duncy.first_steps.client.render.item.StoneSpearEntityRenderer;
import nz.duncy.first_steps.item.entity.ModItemEntities;

@Environment(EnvType.CLIENT)
public class ModEntityRenderer {
    public static void registerModEntityRenderers() {
        EntityRendererRegistry.register(ModItemEntities.STONE_SPEAR, StoneSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.FLINT_SPEAR, FlintSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.BASALT_SPEAR, BasaltSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.OBSIDIAN_SPEAR, ObsidianSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.COPPER_SPEAR, CopperSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.BRONZE_SPEAR, BronzeSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.IRON_SPEAR, IronSpearEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.STEEL_SPEAR, SteelSpearEntityRenderer::new);
    }
}