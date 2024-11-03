package nz.duncy.first_steps.client.render.item;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class SteelSpearEntityRenderer extends SpearEntityRenderer {
    public SteelSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new Identifier(FirstSteps.MOD_ID, "textures/entity/steel_spear.png"));
    }
}
