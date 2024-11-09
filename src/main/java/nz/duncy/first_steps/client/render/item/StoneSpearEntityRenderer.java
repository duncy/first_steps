package nz.duncy.first_steps.client.render.item;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class StoneSpearEntityRenderer extends SpearEntityRenderer {
    public StoneSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, Identifier.of(FirstSteps.MOD_ID, "textures/entity/stone_spear.png"));
    }
}
