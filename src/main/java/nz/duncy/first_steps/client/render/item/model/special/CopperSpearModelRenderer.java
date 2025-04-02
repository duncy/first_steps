package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.CopperSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;

public class CopperSpearModelRenderer extends SpearModelRenderer {

    public CopperSpearModelRenderer(SpearEntityModel model) {
        super(model);
    }

	@Override
	public Identifier getTexture() {
		return CopperSpearEntityModel.TEXTURE;
	}
    
    @Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<CopperSpearModelRenderer.Unbaked> CODEC = MapCodec.unit(new CopperSpearModelRenderer.Unbaked());

		@Override
		public MapCodec<CopperSpearModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new CopperSpearModelRenderer(new SpearEntityModel(entityModels.getModelPart(ModEntityModelLayers.COPPER_SPEAR)));
		}
	}
}
