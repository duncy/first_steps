package nz.duncy.first_steps.client.render.item;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class IronSpearEntityRenderer extends SpearEntityRenderer {
    public IronSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new Identifier(FirstSteps.MOD_ID, "textures/entity/iron_spear.png"));
    }
}
