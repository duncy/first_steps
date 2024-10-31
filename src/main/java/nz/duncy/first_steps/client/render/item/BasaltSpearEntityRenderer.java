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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearModel;
import nz.duncy.first_steps.item.entity.SpearEntity;

@Environment(EnvType.CLIENT)
public class BasaltSpearEntityRenderer extends EntityRenderer<SpearEntity> {
    private static final Identifier TEXTURE = new Identifier(FirstSteps.MOD_ID, "textures/entity/basalt_spear.png");
    private final SpearModel model;

    public BasaltSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpearModel(context.getPart(ModEntityModelLayers.SPEAR), TEXTURE);
    }

    @Override
    public void render(SpearEntity spearEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(g, spearEntity.prevYaw, spearEntity.getYaw()) - 90.0f));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(g, spearEntity.prevPitch, spearEntity.getPitch()) + 135.0f));
        matrixStack.translate(-0.25f, 0, 0);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, this.model.getLayer(this.getTexture(spearEntity)), false, spearEntity.isEnchanted());
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(spearEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
    
    @Override
    public Identifier getTexture(SpearEntity spearEntity) {
        return TEXTURE;
    }
}
