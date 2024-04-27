package nz.duncy.first_steps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.block.entity.renderer.RockBlockEntityRenderer;
//import nz.duncy.first_steps.models.FirstStepsModelLoadingPlugin;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.item.entity.SpearEntityRenderer;

public class FirstStepsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.ROCK_BLOCK_ENTITY, RockBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModItemEntities.BLACKSTONE_SPEAR, SpearEntityRenderer::new);
        //ModelLoadingPlugin.register(new FirstStepsModelLoadingPlugin());
    }

}
