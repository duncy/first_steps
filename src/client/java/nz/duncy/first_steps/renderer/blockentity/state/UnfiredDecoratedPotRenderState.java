// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package nz.duncy.first_steps.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.PotDecorations;

public class UnfiredDecoratedPotRenderState extends BlockEntityRenderState {
   public float yRot;
   public PotDecorations decorations;
   public Direction direction;

   public UnfiredDecoratedPotRenderState() {
      this.decorations = PotDecorations.EMPTY;
      this.direction = Direction.NORTH;
   }
}
