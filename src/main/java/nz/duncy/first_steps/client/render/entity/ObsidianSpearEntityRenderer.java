package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.ObsidianSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

public class ObsidianSpearEntityRenderer extends SpearEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/obsidian_spear.png");

    public ObsidianSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ObsidianSpearEntityModel(context.getPart(ModEntityModelLayers.OBSIDIAN_SPEAR)));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
