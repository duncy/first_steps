package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.FlintSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

public class FlintSpearEntityRenderer extends SpearEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/flint_spear.png");

    public FlintSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FlintSpearEntityModel(context.getPart(ModEntityModelLayers.FLINT_SPEAR)));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
