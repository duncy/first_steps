package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import nz.duncy.first_steps.client.render.block.entity.PottersWheelBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class PottersWheelModelRenderer implements SimpleSpecialModelRenderer {
	private final PottersWheelBlockEntityRenderer blockEntityRenderer;

	public PottersWheelModelRenderer(PottersWheelBlockEntityRenderer blockEntityRenderer) {
		this.blockEntityRenderer = blockEntityRenderer;
	}


	@Override
	public void render(
		ModelTransformationMode modelTransformationMode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, boolean glint
	) {
		this.blockEntityRenderer.render(0.0F, matrices, vertexConsumers, light, overlay);
	}

	@Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<PottersWheelModelRenderer.Unbaked> CODEC = MapCodec.unit(new PottersWheelModelRenderer.Unbaked());

		@Override
		public MapCodec<PottersWheelModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SimpleSpecialModelRenderer bake(LoadedEntityModels entityModels) {
			return new PottersWheelModelRenderer(new PottersWheelBlockEntityRenderer(entityModels));
		}
	}
}
