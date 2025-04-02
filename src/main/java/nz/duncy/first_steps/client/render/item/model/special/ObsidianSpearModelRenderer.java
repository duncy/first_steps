package nz.duncy.first_steps.client.render.item.model.special;

import com.mojang.serialization.MapCodec;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.client.render.entity.model.ObsidianSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;
import nz.duncy.first_steps.client.render.entity.model.SpearEntityModel;

public class ObsidianSpearModelRenderer extends SpearModelRenderer {

    public ObsidianSpearModelRenderer(SpearEntityModel model) {
        super(model);
    }

	@Override
	public Identifier getTexture() {
		return ObsidianSpearEntityModel.TEXTURE;
	}
    
    @Environment(EnvType.CLIENT)
	public static record Unbaked() implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<ObsidianSpearModelRenderer.Unbaked> CODEC = MapCodec.unit(new ObsidianSpearModelRenderer.Unbaked());

		@Override
		public MapCodec<ObsidianSpearModelRenderer.Unbaked> getCodec() {
			return CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(LoadedEntityModels entityModels) {
			return new ObsidianSpearModelRenderer(new SpearEntityModel(entityModels.getModelPart(ModEntityModelLayers.OBSIDIAN_SPEAR)));
		}
	}
}
