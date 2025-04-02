package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.StoneSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

public class StoneSpearEntityRenderer extends SpearEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/stone_spear.png");

    public StoneSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new StoneSpearEntityModel(context.getPart(ModEntityModelLayers.STONE_SPEAR)));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
