package nz.duncy.first_steps.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import java.util.Objects;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.PotDecorations;
import nz.duncy.first_steps.renderer.blockentity.UnfiredDecoratedPotRenderer;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public class UnfiredDecoratedPotSpecialRenderer implements SpecialModelRenderer<PotDecorations> {
    private final UnfiredDecoratedPotRenderer unfiredDecoratedPotRenderer;

    public UnfiredDecoratedPotSpecialRenderer(UnfiredDecoratedPotRenderer unfiredDecoratedPotRenderer) {
        this.unfiredDecoratedPotRenderer = unfiredDecoratedPotRenderer;
    }

    public @Nullable PotDecorations extractArgument(ItemStack itemStack) {
        return (PotDecorations)itemStack.get(DataComponents.POT_DECORATIONS);
    }

    public void submit(@Nullable PotDecorations potDecorations, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, boolean bl, int k) {
        this.unfiredDecoratedPotRenderer.submit(poseStack, submitNodeCollector, i, j, (PotDecorations)Objects.requireNonNullElse(potDecorations, PotDecorations.EMPTY), k);
    }

    public void getExtents(Consumer<Vector3fc> consumer) {
        this.unfiredDecoratedPotRenderer.getExtents(consumer);
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        public Unbaked() {
        }

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext bakingContext) {
            return new UnfiredDecoratedPotSpecialRenderer(new UnfiredDecoratedPotRenderer(bakingContext));
        }
    }
}
