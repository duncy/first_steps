package nz.duncy.first_steps.client.render.item.model.special;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;

public class SpearModelRenderer implements SimpleSpecialModelRenderer {
    private final SpearEntityModel model;

    public SpearModelRenderer(SpearEntityModel model) {
		this.model = model;
	}

    @Override
	public void render(
		ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint
	) {
		matrices.push();
		matrices.scale(1.0F, -1.0F, -1.0F);
		VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture()), false, glint);
		this.model.render(matrices, vertexConsumer, light, overlay);
		matrices.pop();
	}

	public Identifier getTexture() {
		return null;
	}
    
}
