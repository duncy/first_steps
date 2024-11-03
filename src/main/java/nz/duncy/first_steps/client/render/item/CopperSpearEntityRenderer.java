package nz.duncy.first_steps.client.render.item;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class CopperSpearEntityRenderer extends SpearEntityRenderer {
    public CopperSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new Identifier(FirstSteps.MOD_ID, "textures/entity/copper_spear.png"));
    }
}
