package nz.duncy.first_steps.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import nz.duncy.first_steps.FirstSteps;

@Environment(EnvType.CLIENT)
public class PottersWheelModel extends Model {
    private static final String LID = "lid";
    private static final String BASE = "base";
    private static final String ROD = "rod";
    private static final String STAND_LEG_1 = "stand_leg_1";
    private static final String STAND_LEG_2 = "stand_leg_2";
    private static final String STAND_LEG_3 = "stand_leg_3";
    private static final String STAND_LEG_4 = "stand_leg_4";
    private static final String STAND_LID = "stand_lid";

    private final ModelPart base;
    private final ModelPart lid;
    private final ModelPart rod;
    private final ModelPart stand_leg_1;
    private final ModelPart stand_leg_2;
    private final ModelPart stand_leg_3;
    private final ModelPart stand_leg_4;
    private final ModelPart stand_lid;

    public static final Identifier POTTERS_WHEEL_TEXTURE = Identifier.of(FirstSteps.MOD_ID, "textures/block/potters_wheel.png");



    public PottersWheelModel(ModelPart modelPart) {
        super(RenderLayer::getEntitySolid);
        this.lid = modelPart.getChild(LID);
        this.base = modelPart.getChild(BASE);
        this.rod = modelPart.getChild(ROD);
        this.stand_leg_1 = modelPart.getChild(STAND_LEG_1);
        this.stand_leg_2 = modelPart.getChild(STAND_LEG_2);
        this.stand_leg_3 = modelPart.getChild(STAND_LEG_3);
        this.stand_leg_4 = modelPart.getChild(STAND_LEG_4);
        this.stand_lid = modelPart.getChild(STAND_LID);
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(LID, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 13.0F, -8.0F, 16.0F, 3.0F, 16.0F), ModelTransform.NONE);
        modelPartData.addChild(BASE, ModelPartBuilder.create().uv(0, 19).cuboid(4.0F, 0.0F, 4.0F, 8.0F, 1.0F, 8.0F), ModelTransform.NONE);
        modelPartData.addChild(ROD, ModelPartBuilder.create().uv(32, 19).cuboid(-1.0F, 1.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild(STAND_LEG_1, ModelPartBuilder.create().uv(32, 19).cuboid(2.0F, 0.0F, 2.0F, 2.0F, 10.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild(STAND_LEG_2, ModelPartBuilder.create().uv(32, 19).cuboid(2.0F, 0.0F, 12.0F, 2.0F, 10.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild(STAND_LEG_3, ModelPartBuilder.create().uv(32, 19).cuboid(12.0F, 0.0F, 12.0F, 2.0F, 10.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild(STAND_LEG_4, ModelPartBuilder.create().uv(32, 19).cuboid(12.0F, 0.0F, 2.0F, 2.0F, 10.0F, 2.0F), ModelTransform.NONE);
        modelPartData.addChild(STAND_LID, ModelPartBuilder.create().uv(0, 33).cuboid(2.0F, 10.0F, 2.0F, 12.0F, 2.0F, 12.0F), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void renderBase(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        this.base.render(matrices, vertexConsumer, light, overlay);
        this.stand_leg_1.render(matrices, vertexConsumer, light, overlay);
        this.stand_leg_2.render(matrices, vertexConsumer, light, overlay);
        this.stand_leg_3.render(matrices, vertexConsumer, light, overlay);
        this.stand_leg_4.render(matrices, vertexConsumer, light, overlay);
        this.stand_lid.render(matrices, vertexConsumer, light, overlay);
    }

    public void renderWheel(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float yaw) {
        Direction direction = Direction.UP;

        matrices.translate(0.5F, 0.0F, 0.5F);
        matrices.scale(0.9995F, 0.9995F, 0.9995F);
        matrices.multiply(direction.getRotationQuaternion());

        this.lid.yaw = yaw;
        this.rod.yaw = yaw;

        this.lid.render(matrices, vertexConsumer, light, overlay);
        this.rod.render(matrices, vertexConsumer, light, overlay);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.renderBase(matrices, vertices, light, overlay);
        this.renderWheel(matrices, vertices, light, overlay, 0.0F);
    }
 
}
