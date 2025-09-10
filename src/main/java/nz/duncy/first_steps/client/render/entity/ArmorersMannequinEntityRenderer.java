package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import nz.duncy.first_steps.client.render.entity.model.ArmorersMannequinEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.state.ArmorersMannequinEntityRenderState;
import nz.duncy.first_steps.entity.decoration.ArmorersMannequinEntity;

public class ArmorersMannequinEntityRenderer extends LivingEntityRenderer<ArmorersMannequinEntity, ArmorersMannequinEntityRenderState, ArmorersMannequinEntityModel> {
    public static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/armorstand/wood.png");

    public ArmorersMannequinEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new ArmorersMannequinEntityModel(context.getPart(ModEntityModelLayers.ARMORERS_MANNEQUIN)), 0.0F);
	}


    @Override
    public Identifier getTexture(ArmorersMannequinEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public ArmorersMannequinEntityRenderState createRenderState() {
        return new ArmorersMannequinEntityRenderState();
    }

    public void updateRenderState(ArmorersMannequinEntity armorersMannequinEntity, ArmorersMannequinEntityRenderState armorersMannequinEntityRenderState, float f) {
		BipedEntityRenderer.updateBipedRenderState(armorersMannequinEntity, armorersMannequinEntityRenderState, f, this.itemModelResolver);
		armorersMannequinEntityRenderState.yaw = MathHelper.lerpAngleDegrees(f, armorersMannequinEntity.prevYaw, armorersMannequinEntity.getYaw());
		armorersMannequinEntityRenderState.timeSinceLastHit = (float)(armorersMannequinEntity.getWorld().getTime() - armorersMannequinEntity.lastHitTime) + f;
	}

	public void render(ArmorersMannequinEntityRenderState armorersMannequinEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(armorersMannequinEntityRenderState, matrixStack, vertexConsumerProvider, i);
	}

	protected void setupTransforms(ArmorersMannequinEntityRenderState armorersMannequinEntityRenderState, MatrixStack matrixStack, float f, float g) {
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - f));
		if (armorersMannequinEntityRenderState.timeSinceLastHit < 5.0F) {
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(armorersMannequinEntityRenderState.timeSinceLastHit / 1.5F * (float) Math.PI) * 3.0F));
		}
	}

	protected boolean hasLabel(ArmorStandEntity armorStandEntity, double d) {
		return armorStandEntity.isCustomNameVisible();
	}
}
