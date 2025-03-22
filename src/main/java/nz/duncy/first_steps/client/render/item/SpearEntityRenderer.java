package nz.duncy.first_steps.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearModel;
import nz.duncy.first_steps.client.render.entity.state.SpearEntityState;
import nz.duncy.first_steps.item.entity.SpearEntity;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity, SpearEntityState> {
    private final Identifier TEXTURE;
    private final SpearModel model;

    public SpearEntityRenderer(EntityRendererFactory.Context context, Identifier texture) {
        super(context);
        this.TEXTURE = texture;
        this.model = new SpearModel(context.getPart(ModEntityModelLayers.SPEAR), this.TEXTURE);
    }

    @Override
    public void render(SpearEntityState spearEntityState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(spearEntityState.yaw - 90.0F));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(spearEntityState.pitch + 90.0F));
		VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
			vertexConsumerProvider, this.model.getLayer(TEXTURE), false, spearEntityState.enchanted
		);
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		matrixStack.pop();
		super.render(spearEntityState, matrixStack, vertexConsumerProvider, i);
    }
    
    public SpearEntityState createRenderState() {
		return new SpearEntityState();
	}

	public void updateRenderState(SpearEntity spearEntity, SpearEntityState spearEntityState, float f) {
		super.updateRenderState(spearEntity, spearEntityState, f);
		spearEntityState.yaw = spearEntity.getLerpedYaw(f);
		spearEntityState.pitch = spearEntity.getLerpedPitch(f);
		spearEntityState.enchanted = spearEntity.isEnchanted();
	}
}
