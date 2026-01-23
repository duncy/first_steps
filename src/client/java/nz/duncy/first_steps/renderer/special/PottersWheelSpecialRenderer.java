package nz.duncy.first_steps.renderer.special;

import java.util.function.Consumer;

import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import nz.duncy.first_steps.renderer.ModSheets;
import nz.duncy.first_steps.renderer.blockentity.PottersWheelRenderer;

public class PottersWheelSpecialRenderer implements NoDataSpecialModelRenderer {
    private PottersWheelRenderer pottersWheelRenderer;
    private final Direction DIRECTION = Direction.UP;

    public PottersWheelSpecialRenderer(PottersWheelRenderer pottersWheelRenderer) {
        this.pottersWheelRenderer = pottersWheelRenderer;
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        this.pottersWheelRenderer.getExtents(DIRECTION, consumer);
    }

    @Override
    public void submit(ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl, int k) {
        Material material = ModSheets.POTTERS_WHEEL;
        this.pottersWheelRenderer.submit(poseStack, submitNodeCollector, i, j, DIRECTION, 0.0F, null, material,  k);
    }

    public static record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        public Unbaked() {
        }

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext bakingContext) {
            return new PottersWheelSpecialRenderer(new PottersWheelRenderer(bakingContext));
        }
    }
}
