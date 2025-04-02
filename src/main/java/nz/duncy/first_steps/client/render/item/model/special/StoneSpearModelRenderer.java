package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.BasaltSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.StoneSpearEntityModel;

public class StoneSpearModelRenderer extends SpearModelRenderer {

    public StoneSpearModelRenderer(SpearEntityModel model) {
        super(model);
    }

	@Override
	public Identifier getTexture() {
		return StoneSpearEntityModel.TEXTURE;
	}
    
    @Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<StoneSpearModelRenderer.Unbaked> CODEC = MapCodec.unit(new StoneSpearModelRenderer.Unbaked());

		@Override
		public MapCodec<StoneSpearModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new StoneSpearModelRenderer(new SpearEntityModel(entityModels.getModelPart(ModEntityModelLayers.STONE_SPEAR)));
		}
	}
}
