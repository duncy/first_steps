package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.CopperSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

public class CopperSpearEntityRenderer extends SpearEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/copper_spear.png");

    public CopperSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CopperSpearEntityModel(context.getPart(ModEntityModelLayers.COPPER_SPEAR)));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
