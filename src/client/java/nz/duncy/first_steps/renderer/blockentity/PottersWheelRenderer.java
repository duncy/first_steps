package nz.duncy.first_steps.renderer.blockentity;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import nz.duncy.first_steps.model.PottersWheelModel;
import nz.duncy.first_steps.model.geom.ModModelLayers;
import nz.duncy.first_steps.renderer.ModSheets;
import nz.duncy.first_steps.renderer.blockentity.state.PottersWheelRenderState;
import nz.duncy.first_steps.world.level.block.entity.PottersWheelBlockEntity;

public class PottersWheelRenderer implements BlockEntityRenderer<PottersWheelBlockEntity, PottersWheelRenderState> {
    private final MaterialSet materials;
    private final PottersWheelModel model;
    
    public PottersWheelRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.materials());
    }

    public PottersWheelRenderer(SpecialModelRenderer.BakingContext bakingContext) {
        this(bakingContext.entityModelSet(), bakingContext.materials());
    }

    public PottersWheelRenderer(EntityModelSet entityModelSet, MaterialSet materialSet) {
        this.materials = materialSet;
        this.model = new PottersWheelModel(entityModelSet.bakeLayer(ModModelLayers.POTTERS_WHEEL));
    }

    public PottersWheelRenderState createRenderState() {
        return new PottersWheelRenderState();
    }

    public void extractRenderState(PottersWheelBlockEntity pottersWheelBlockEntity, PottersWheelRenderState pottersWheelRenderState, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(pottersWheelBlockEntity, pottersWheelRenderState, crumblingOverlay);
        pottersWheelRenderState.progress = pottersWheelBlockEntity.getProgress(f);
    }

    @Override
    public void submit(PottersWheelRenderState pottersWheelRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Material material = ModSheets.POTTERS_WHEEL;
        this.submit(poseStack, submitNodeCollector, pottersWheelRenderState.lightCoords, OverlayTexture.NO_OVERLAY, pottersWheelRenderState.direction, pottersWheelRenderState.progress, pottersWheelRenderState.breakProgress, material, 0);
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int noOverlay, Direction direction, float progress, CrumblingOverlay breakProgress, Material material, int i) {
        poseStack.pushPose();
        this.prepareModel(poseStack, progress, direction);
        submitNodeCollector.submitModel(this.model, progress, poseStack, material.renderType(this.model::renderType), lightCoords, noOverlay, -1, this.materials.get(material), i, breakProgress);
        poseStack.popPose();
    }

    private void prepareModel(PoseStack poseStack, float progress, Direction direction) {
        // poseStack.translate(0.5F, 0.5F, 0.5F);
        // poseStack.scale(0.9995F, 0.9995F, 0.9995F);
        // poseStack.mulPose(direction.getRotation());
        // poseStack.translate(0.0F, -1.5F, 0.0F);
        this.model.setupAnim(progress);
    }

    public void getExtents(Direction direction, Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        this.prepareModel(poseStack, 0.0F, direction);
        this.model.root().getExtentsForGui(poseStack, consumer);
    }
}
