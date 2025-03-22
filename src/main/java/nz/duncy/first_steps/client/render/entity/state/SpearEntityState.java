package nz.duncy.first_steps.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class SpearEntityState extends EntityRenderState {
	public float pitch;
	public float yaw;
	public boolean enchanted;
}
