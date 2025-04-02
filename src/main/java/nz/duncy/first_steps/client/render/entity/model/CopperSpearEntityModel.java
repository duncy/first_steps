package nz.duncy.first_steps.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class CopperSpearEntityModel extends SpearEntityModel {
    public static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/copper_spear.png");

    public CopperSpearEntityModel(ModelPart root) {
        super(root);
    }    
}
