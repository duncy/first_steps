package nz.duncy.first_steps.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class PottersWheelModel extends Model<Float>{
    private static final String LID = "lid";
    private static final String BASE = "base";
    private static final String ROD = "rod";
    private static final String STAND_LEG_1 = "stand_leg_1";
    private static final String STAND_LEG_2 = "stand_leg_2";
    private static final String STAND_LEG_3 = "stand_leg_3";
    private static final String STAND_LEG_4 = "stand_leg_4";
    private static final String STAND_LID = "stand_lid";
    private final ModelPart lid;
    private final ModelPart rod;

    public PottersWheelModel(ModelPart modelPart) {
        super(modelPart, RenderTypes::entitySolid);
        this.lid = modelPart.getChild(LID);
        this.rod = modelPart.getChild(ROD);
    }

    public static LayerDefinition getLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 13.0F, -8.0F, 16.0F, 3.0F, 16.0F), PartPose.offset(8.0F, 0.0F, 8.0F));
        partDefinition.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 19).addBox(4.0F, 0.0F, 4.0F, 8.0F, 1.0F, 8.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild(ROD, CubeListBuilder.create().texOffs(32, 19).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(8.0F, 0.0F, 8.0F));
        partDefinition.addOrReplaceChild(STAND_LEG_1, CubeListBuilder.create().texOffs(32, 19).addBox(2.0F, 0.0F, 2.0F, 2.0F, 10.0F, 2.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild(STAND_LEG_2, CubeListBuilder.create().texOffs(32, 19).addBox(2.0F, 0.0F, 12.0F, 2.0F, 10.0F, 2.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild(STAND_LEG_3, CubeListBuilder.create().texOffs(32, 19).addBox(12.0F, 0.0F, 12.0F, 2.0F, 10.0F, 2.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild(STAND_LEG_4, CubeListBuilder.create().texOffs(32, 19).addBox(12.0F, 0.0F, 2.0F, 2.0F, 10.0F, 2.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild(STAND_LID, CubeListBuilder.create().texOffs(0, 33).addBox(2.0F, 10.0F, 2.0F, 12.0F, 2.0F, 12.0F), PartPose.ZERO);

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public void setupAnim(Float progress) {
        super.setupAnim(progress);
        this.lid.yRot = 270.0F * progress * -0.017453292F;
        this.rod.yRot = 270.0F * progress * -0.017453292F;
    }
}

