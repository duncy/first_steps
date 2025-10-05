package nz.duncy.first_steps.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import nz.duncy.first_steps.client.render.entity.state.ArmorersMannequinEntityRenderState;

@Environment(EnvType.CLIENT)
public class ArmorersMannequinEntityModel extends BipedEntityModel<ArmorersMannequinEntityRenderState> {
    private static final String SHOULDER_STICK = "shoulder_stick";
    private static final String RIGHT_BODY_STICK = "right_body_stick";
	private static final String LEFT_BODY_STICK = "left_body_stick";
	private static final String WAIST_STICK = "waist_stick";
	private static final String BASE_PLATE = "base_plate";
    private final ModelPart shoulderStick;
	private final ModelPart rightBodyStick;
	private final ModelPart leftBodyStick;
	private final ModelPart waistStick;

    public ArmorersMannequinEntityModel(ModelPart modelPart) {
		super(modelPart);
        this.shoulderStick = modelPart.getChild(SHOULDER_STICK);
		this.rightBodyStick = modelPart.getChild(RIGHT_BODY_STICK);
		this.leftBodyStick = modelPart.getChild(LEFT_BODY_STICK);
		this.waistStick = modelPart.getChild(WAIST_STICK);
		this.hat.visible = false;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild(
			EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(32, 47).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F), 
            ModelTransform.pivot(0.0F, 1.0F, 0.0F)
		);

        modelPartData.addChild(
            SHOULDER_STICK, ModelPartBuilder.create().uv(0, 26).cuboid(-6.0F, 0.0F, -1.5F, 12.0F, 3.0F, 3.0F), ModelTransform.NONE
        );

		modelPartData.addChild(
            EntityModelPartNames.BODY, ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F), ModelTransform.NONE
        );

		modelPartData.addChild(
			EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(32, 16).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
            ModelTransform.pivot(-5.0F, 2.0F, 0.0F)
		);

		modelPartData.addChild(
			EntityModelPartNames.LEFT_ARM,
			ModelPartBuilder.create().uv(32, 16).mirrored().cuboid(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
			ModelTransform.pivot(5.0F, 2.0F, 0.0F)
		);

		modelPartData.addChild(
			EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(32, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F), ModelTransform.pivot(-1.9F, 12.0F, 0.0F)
		);

		modelPartData.addChild(
			EntityModelPartNames.LEFT_LEG,
			ModelPartBuilder.create().uv(32, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F),
			ModelTransform.pivot(1.9F, 12.0F, 0.0F)
		);

		modelPartData.addChild(
            RIGHT_BODY_STICK, ModelPartBuilder.create().uv(16, 0).cuboid(-3.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), ModelTransform.NONE
            );

		modelPartData.addChild(
            LEFT_BODY_STICK, ModelPartBuilder.create().uv(16, 0).mirrored().cuboid(1.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), ModelTransform.NONE
        );

		modelPartData.addChild(
            WAIST_STICK, ModelPartBuilder.create().uv(0, 48).cuboid(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F), ModelTransform.NONE
        );

		modelPartData.addChild(
			BASE_PLATE, ModelPartBuilder.create().uv(0, 32).cuboid(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F), 
            ModelTransform.pivot(0.0F, 12.0F, 0.0F)
		);
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(ArmorersMannequinEntityRenderState armorersMannequinEntityRenderState) {
        super.setAngles(armorersMannequinEntityRenderState);
		this.head.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.headRotation.getPitch();
		this.head.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.headRotation.getYaw();
		this.head.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.headRotation.getRoll();

		this.body.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getPitch();
		this.body.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getYaw();
		this.body.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getRoll();

		this.leftArm.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftArmRotation.getPitch();
		this.leftArm.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftArmRotation.getYaw();
		this.leftArm.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftArmRotation.getRoll();
        this.leftArm.visible = armorersMannequinEntityRenderState.showArms;

		this.rightArm.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightArmRotation.getPitch();
		this.rightArm.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightArmRotation.getYaw();
		this.rightArm.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightArmRotation.getRoll();
        this.rightArm.visible = armorersMannequinEntityRenderState.showArms;

		this.leftLeg.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftLegRotation.getPitch();
		this.leftLeg.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftLegRotation.getYaw();
		this.leftLeg.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.leftLegRotation.getRoll();

		this.rightLeg.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightLegRotation.getPitch();
		this.rightLeg.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightLegRotation.getYaw();
		this.rightLeg.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.rightLegRotation.getRoll();
		
		this.rightBodyStick.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getPitch();
		this.rightBodyStick.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getYaw();
		this.rightBodyStick.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getRoll();

		this.leftBodyStick.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getPitch();
		this.leftBodyStick.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getYaw();
		this.leftBodyStick.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getRoll();

		this.waistStick.pitch = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getPitch();
		this.waistStick.yaw = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getYaw();
		this.waistStick.roll = (float) (Math.PI / 180.0) * armorersMannequinEntityRenderState.bodyRotation.getRoll();
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		ModelPart modelPart = this.getArm(arm);
		boolean bl = modelPart.visible;
		modelPart.visible = true;
		super.setArmAngle(arm, matrices);
		modelPart.visible = bl;
	}



}
