package nz.duncy.first_steps.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class BasaltSpearEntityModel extends SpearEntityModel {
    public static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/basalt_spear.png");

    public BasaltSpearEntityModel(ModelPart root) {
        super(root);
    }    
}
