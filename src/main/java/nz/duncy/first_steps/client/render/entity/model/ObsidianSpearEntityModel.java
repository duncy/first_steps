package nz.duncy.first_steps.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ObsidianSpearEntityModel extends SpearEntityModel {
    public static final Identifier TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/entity/obsidian_spear.png");

    public ObsidianSpearEntityModel(ModelPart root) {
        super(root);
    }    
}
