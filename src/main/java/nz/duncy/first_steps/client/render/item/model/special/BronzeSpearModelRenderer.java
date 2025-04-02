package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.BronzeSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;

public class BronzeSpearModelRenderer extends SpearModelRenderer {

    public BronzeSpearModelRenderer(SpearEntityModel model) {
        super(model);
    }

	@Override
	public Identifier getTexture() {
		return BronzeSpearEntityModel.TEXTURE;
	}
    
    @Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<BronzeSpearModelRenderer.Unbaked> CODEC = MapCodec.unit(new BronzeSpearModelRenderer.Unbaked());

		@Override
		public MapCodec<BronzeSpearModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new BronzeSpearModelRenderer(new SpearEntityModel(entityModels.getModelPart(ModEntityModelLayers.BRONZE_SPEAR)));
		}
	}
}
