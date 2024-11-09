package nz.duncy.first_steps.client.render.item;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class BronzeSpearEntityRenderer extends SpearEntityRenderer {
    public BronzeSpearEntityRenderer(EntityRendererFactory.Context context) {
        super(context, Identifier.of(FirstSteps.MOD_ID, "textures/entity/bronze_spear.png"));
    }
}
