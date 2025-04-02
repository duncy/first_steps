package nz.duncy.first_steps.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class BronzeSpearEntityModel extends SpearEntityModel {
    public static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/bronze_spear.png");

    public BronzeSpearEntityModel(ModelPart root) {
        super(root);
    }    
}
