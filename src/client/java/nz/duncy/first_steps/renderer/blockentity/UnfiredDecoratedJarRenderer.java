package nz.duncy.first_steps.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.phys.Vec3;
import nz.duncy.first_steps.model.geom.ModModelLayers;
import nz.duncy.first_steps.renderer.ModSheets;
import nz.duncy.first_steps.renderer.blockentity.state.UnfiredDecoratedJarRenderState;
import nz.duncy.first_steps.world.level.block.entity.UnfiredDecoratedJarBlockEntity;

import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

public class UnfiredDecoratedJarRenderer implements BlockEntityRenderer<UnfiredDecoratedJarBlockEntity, UnfiredDecoratedJarRenderState> {
    private final MaterialSet materials;
    private static final String NECK = "neck";
    private static final String FRONT = "front";
    private static final String BACK = "back";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private final ModelPart neck;
    private final ModelPart frontSide;
    private final ModelPart backSide;
    private final ModelPart leftSide;
    private final ModelPart rightSide;
    private final ModelPart top;
    private final ModelPart bottom;

    public UnfiredDecoratedJarRenderer(BlockEntityRendererProvider.Context context) {
        this(context.entityModelSet(), context.materials());
    }

    public UnfiredDecoratedJarRenderer(SpecialModelRenderer.BakingContext bakingContext) {
        this(bakingContext.entityModelSet(), bakingContext.materials());
    }

    public UnfiredDecoratedJarRenderer(EntityModelSet entityModelSet, MaterialSet materialSet) {
        this.materials = materialSet;
        ModelPart modelPart = entityModelSet.bakeLayer(ModModelLayers.UNFIRED_DECORATED_JAR_BASE);
        this.neck = modelPart.getChild(NECK);
        this.top = modelPart.getChild(TOP);
        this.bottom = modelPart.getChild(BOTTOM);
        ModelPart modelPart2 = entityModelSet.bakeLayer(ModModelLayers.UNFIRED_DECORATED_JAR_SIDES);
        this.frontSide = modelPart2.getChild(FRONT);
        this.backSide = modelPart2.getChild(BACK);
        this.leftSide = modelPart2.getChild(LEFT);
        this.rightSide = modelPart2.getChild(RIGHT);
    }

    public static LayerDefinition createBaseLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(NECK, CubeListBuilder.create()
            .texOffs(0, 0).addBox(2.0F, 22.0F, 2.0F, 12.0F, 1.0F, 12.0F)
            .texOffs(0, 0).addBox(2.0F, 19.0F, 2.0F, 12.0F, 2.0F, 12.0F)
            .texOffs(2, 4).addBox(3.0F, 21.0F, 3.0F, 10.0F, 1.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 37.0F, 16.0F, 3.1415927F, 0.0F, 0.0F));
        CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(-14, 16).addBox(0.0F, 0.0F, 0.0F, 14.0F, 0.0F, 14.0F);
        partDefinition.addOrReplaceChild(TOP, cubeListBuilder, PartPose.offsetAndRotation(1.0F, 14.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild(BOTTOM, cubeListBuilder, PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public static LayerDefinition createSidesLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, 2.0F, 0.0F, 14.0F, 14.0F, 0.0F, EnumSet.of(Direction.NORTH));
        partDefinition.addOrReplaceChild(BACK, cubeListBuilder, PartPose.offsetAndRotation(15.0F, 16.0F, 1.0F, 0.0F, 0.0F, 3.1415927F));
        partDefinition.addOrReplaceChild(LEFT, cubeListBuilder, PartPose.offsetAndRotation(1.0F, 16.0F, 1.0F, 0.0F, -1.5707964F, 3.1415927F));
        partDefinition.addOrReplaceChild(RIGHT, cubeListBuilder, PartPose.offsetAndRotation(15.0F, 16.0F, 15.0F, 0.0F, 1.5707964F, 3.1415927F));
        partDefinition.addOrReplaceChild(FRONT, cubeListBuilder, PartPose.offsetAndRotation(1.0F, 16.0F, 15.0F, 3.1415927F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    private static Material getSideMaterial(Optional<Item> optional) {
        if (optional.isPresent()) {
            Material material = ModSheets.getUnfiredDecoratedPotMaterial(DecoratedPotPatterns.getPatternFromItem((Item)optional.get()));
            if (material != null) {
                return material;
            }
        }

        return ModSheets.UNFIRED_DECORATED_POT_SIDE;
    }

    public UnfiredDecoratedJarRenderState createRenderState() {
        return new UnfiredDecoratedJarRenderState();
    }

    public void extractRenderState(UnfiredDecoratedJarBlockEntity decoratedJarBlockEntity, UnfiredDecoratedJarRenderState decoratedJarRenderState, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(decoratedJarBlockEntity, decoratedJarRenderState, f, vec3, crumblingOverlay);
        decoratedJarRenderState.decorations = decoratedJarBlockEntity.getDecorations();
        decoratedJarRenderState.direction = decoratedJarBlockEntity.getDirection();
    }

    public void submit(UnfiredDecoratedJarRenderState decoratedJarRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        Direction direction = decoratedJarRenderState.direction;
        poseStack.translate(0.5, 0.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - direction.toYRot()));
        poseStack.translate(-0.5, 0.0, -0.5);

        this.submit(poseStack, submitNodeCollector, decoratedJarRenderState.lightCoords, OverlayTexture.NO_OVERLAY, decoratedJarRenderState.decorations, 0);
        poseStack.popPose();
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int j, PotDecorations potDecorations, int k) {
        RenderType renderType = ModSheets.UNFIRED_DECORATED_JAR_BASE.renderType(RenderTypes::entitySolid);
        TextureAtlasSprite textureAtlasSprite = this.materials.get(ModSheets.UNFIRED_DECORATED_JAR_BASE);
        submitNodeCollector.submitModelPart(this.neck, poseStack, renderType, i, j, textureAtlasSprite, false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        submitNodeCollector.submitModelPart(this.top, poseStack, renderType, i, j, textureAtlasSprite, false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        submitNodeCollector.submitModelPart(this.bottom, poseStack, renderType, i, j, textureAtlasSprite, false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        Material material = getSideMaterial(potDecorations.front());
        submitNodeCollector.submitModelPart(this.frontSide, poseStack, material.renderType(RenderTypes::entitySolid), i, j, this.materials.get(material), false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        Material material2 = getSideMaterial(potDecorations.back());
        submitNodeCollector.submitModelPart(this.backSide, poseStack, material2.renderType(RenderTypes::entitySolid), i, j, this.materials.get(material2), false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        Material material3 = getSideMaterial(potDecorations.left());
        submitNodeCollector.submitModelPart(this.leftSide, poseStack, material3.renderType(RenderTypes::entitySolid), i, j, this.materials.get(material3), false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
        Material material4 = getSideMaterial(potDecorations.right());
        submitNodeCollector.submitModelPart(this.rightSide, poseStack, material4.renderType(RenderTypes::entitySolid), i, j, this.materials.get(material4), false, false, -1, (ModelFeatureRenderer.CrumblingOverlay)null, k);
    }

    public void getExtents(Consumer<Vector3fc> consumer) {
        PoseStack poseStack = new PoseStack();
        this.neck.getExtentsForGui(poseStack, consumer);
        this.top.getExtentsForGui(poseStack, consumer);
        this.bottom.getExtentsForGui(poseStack, consumer);
    }
}
