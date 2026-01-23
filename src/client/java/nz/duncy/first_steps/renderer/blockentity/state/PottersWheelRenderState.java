package nz.duncy.first_steps.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class PottersWheelRenderState extends BlockEntityRenderState {
    public Direction direction;
    public float progress;
 
    public PottersWheelRenderState() {
       this.direction = Direction.UP;
    }
 }
 
