package nz.duncy.first_steps.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class FlintSpearEntityModel extends SpearEntityModel {
    public static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/flint_spear.png");

    public FlintSpearEntityModel(ModelPart root) {
        super(root);
    }    
}
