package nz.duncy.first_steps.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import nz.duncy.first_steps.block.entity.PottersWheelBlockEntity;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

@Environment(EnvType.CLIENT)
public class PottersWheelBlockEntityRenderer implements BlockEntityRenderer<PottersWheelBlockEntity> {
    private final PottersWheelBlockModel model;
    
    public PottersWheelBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this(context.getLoadedEntityModels());
	}

    public PottersWheelBlockEntityRenderer(LoadedEntityModels models) {
        ModelPart modelPart = models.getModelPart(ModEntityModelLayers.POTTERS_WHEEL);
        this.model = new PottersWheelBlockModel(modelPart);
    }

    @Override
    public void render(PottersWheelBlockEntity pottersWheelBlockEntity, float f, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        float yaw = 270.0F * pottersWheelBlockEntity.getAnimationProgress(pottersWheelBlockEntity, f) * -0.017453292F;

        this.render(yaw, matrices, vertices, light, overlay);
    }
    
    public void render(float yaw, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        matrices.push();

        VertexConsumer vertexConsumer = vertices.getBuffer(RenderLayer.getEntityCutout(PottersWheelBlockModel.POTTERS_WHEEL_TEXTURE));
        this.model.renderBase(matrices, vertexConsumer, light, overlay);
        
        this.model.renderWheel(matrices, vertexConsumer, light, overlay, yaw);
        
        matrices.pop();
    }
}
