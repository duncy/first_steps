package nz.duncy.first_steps.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.util.math.EulerAngle;
import nz.duncy.first_steps.entity.decoration.ArmorersMannequinEntity;

@Environment(EnvType.CLIENT)
public class ArmorersMannequinEntityRenderState extends BipedEntityRenderState {
	public float yaw;
	public float timeSinceLastHit;
	public boolean showArms = true;
	public boolean showBasePlate = true;
	public EulerAngle headRotation = ArmorersMannequinEntity.HEAD_ROTATION;
	public EulerAngle bodyRotation = ArmorersMannequinEntity.BODY_ROTATION;
	public EulerAngle leftArmRotation = ArmorersMannequinEntity.LEFT_ARM_ROTATION;
	public EulerAngle rightArmRotation = ArmorersMannequinEntity.RIGHT_ARM_ROTATION;
	public EulerAngle leftLegRotation = ArmorersMannequinEntity.LEFT_LEG_ROTATION;
	public EulerAngle rightLegRotation = ArmorersMannequinEntity.RIGHT_LEG_ROTATION;
}
