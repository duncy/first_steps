package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.IronSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;

public class IronSpearModelRenderer extends SpearModelRenderer {

    public IronSpearModelRenderer(SpearEntityModel model) {
        super(model);
    }

	@Override
	public Identifier getTexture() {
		return IronSpearEntityModel.TEXTURE;
	}
    
    @Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<IronSpearModelRenderer.Unbaked> CODEC = MapCodec.unit(new IronSpearModelRenderer.Unbaked());

		@Override
		public MapCodec<IronSpearModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new IronSpearModelRenderer(new SpearEntityModel(entityModels.getModelPart(ModEntityModelLayers.IRON_SPEAR)));
		}
	}
}
