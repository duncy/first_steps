package nz.duncy.first_steps.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.client.render.entity.model.BasaltSpearEntityModel;
import nz.duncy.first_steps.client.render.entity.model.ModEntityModelLayers;

public class BasaltSpearEntityRenderer extends SpearEntityRenderer {
    private static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/basalt_spear.png");

    public BasaltSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BasaltSpearEntityModel(context.getPart(ModEntityModelLayers.BASALT_SPEAR)));
    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
