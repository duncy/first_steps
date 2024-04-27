package nz.duncy.first_steps.block.entity.renderer;

import java.util.BitSet;
import java.util.function.Function;

import org.joml.Math;
import org.joml.Matrix4f;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import nz.duncy.first_steps.block.custom.RockBlock;
import nz.duncy.first_steps.block.entity.RockBlockEntity;

public class RockBlockEntityRenderer implements BlockEntityRenderer<RockBlockEntity> {
    private final Function<Identifier, Sprite> ATLAS = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
    private Sprite SPRITE = null;
    private float MIN_U;
    private float MIN_V;
    private float MAX_U;
    private float MAX_V;
    private float PIXEL_U;
    private float PIXEL_V;
    private float START_U;
    private float START_V;
    private float END_U;
    private float END_V;

    public RockBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    private void setTextureData(RockBlockEntity entity) {
        SPRITE = ATLAS.apply(entity.getTextureId());
        MIN_U = SPRITE.getMinU();
        MIN_V = SPRITE.getMinV();
        MAX_U = SPRITE.getMaxU();
        MAX_V = SPRITE.getMaxV();
        PIXEL_U = (MAX_U - MIN_U) / 16;
        PIXEL_V = (MAX_V - MIN_V) / 16;
        START_U = MIN_U + PIXEL_U * 4;
        START_V = MIN_V + PIXEL_V * 4;
        END_U = MAX_U - PIXEL_U * 4;
        END_V = MAX_V - PIXEL_V * 4;
    }

    @Override
    public void render(RockBlockEntity entity, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay) {
            setTextureData(entity);

            BitSet inactiveVoxels = entity.getKnappedVoxels();

            MinecraftClient client = MinecraftClient.getInstance();
            HitResult hit = client.crosshairTarget;
            BlockPos pos = entity.getPos();
            int voxelPos = -1;
            if (hit.getType() == HitResult.Type.BLOCK) {
                Item heldItem = client.player.getInventory().getMainHandStack().getItem();
                if (heldItem instanceof BlockItem) {
                    if (((BlockItem) heldItem).getBlock() instanceof RockBlock) {
                        // Get the coordinates of the clicked voxel within the block
                        double hitX = hit.getPos().getX() - pos.getX() - 0.25;
                        double hitZ = hit.getPos().getZ() - pos.getZ() - 0.25;

                        voxelPos = (int) (Math.floor(hitX / 0.0625) + (Math.floor(hitZ / 0.0625) * 8));
                    }

                }

            }

            matrices.push();

            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
            Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
    
            for (int i = 0; i < 64; i++) {
                if (!inactiveVoxels.get(i)) {
                    int x = i % 8;
                    int z = (int) Math.floor(i / 8);
                    
                    float iStartU = START_U + PIXEL_U * x;
                    float iStartV = START_V + PIXEL_V * z;
                    float iEndU = END_U - PIXEL_U * (7 - x);
                    float iEndV = END_V - PIXEL_V * (7 - z);

                    float startX = 0.25f + x * 0.0625f;
                    float startZ = 0.25f + z * 0.0625f;
                    float endX = 0.75f - (7 - x) * 0.0625f;
                    float endZ = 0.75f - (7 - z) * 0.0625f;
                   
                    float highlight = 1f;

                    // Make sure the highlight voxel is only rendered on the block being targetted
                    if (i == voxelPos && Math.floor(hit.getPos().getX()) == pos.getX() && Math.floor(hit.getPos().getY()) == pos.getY() && Math.floor(hit.getPos().getZ()) == pos.getZ()) {
                        highlight = entity.edgeVoxel(i) ? 0.5f : 1f;
                    }
                    

                    renderTop(buffer, positionMatrix, iStartU, iStartV, iEndU, iEndV, light, overlay, startX, startZ, endX, endZ, highlight);
                    renderNorth(buffer, positionMatrix, iStartU, iStartV, iEndU, iEndV, light, overlay, startX, startZ, endX, highlight);
                    renderEast(buffer, positionMatrix, iStartU, iStartV, iEndU, iEndV, light, overlay, startX, startZ, endZ, highlight);
                    renderSouth(buffer, positionMatrix, iStartU, iStartV, iEndU, iEndV, light, overlay, startX, endX, endZ, highlight);
                    renderWest(buffer, positionMatrix, iStartU, iStartV, iEndU, iEndV, light, overlay, startZ, endX, endZ, highlight);
                }
            }
            matrices.pop(); 
    }

    private void renderNorth(VertexConsumer buffer, Matrix4f positionMatrix, float startU, float startV, float endU, float endV, int light, int overlay, float startX, float startZ, float endX, float highlight) {
        buffer.vertex(positionMatrix, startX, 0f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0.0625f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
    }

    private void renderEast(VertexConsumer buffer, Matrix4f positionMatrix, float startU, float startV, float endU, float endV, int light, int overlay, float startX, float startZ, float endZ, float highlight) {
        buffer.vertex(positionMatrix, startX, 0f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
    }

    private void renderSouth(VertexConsumer buffer, Matrix4f positionMatrix, float startU, float startV, float endU, float endV, int light, int overlay, float startX, float endX, float endZ, float highlight) {
        buffer.vertex(positionMatrix, endX, 0f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0.0625f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
    }

    private void renderWest(VertexConsumer buffer, Matrix4f positionMatrix, float startU, float startV, float endU, float endV, int light, int overlay, float startZ, float endX, float endZ, float highlight) {
        buffer.vertex(positionMatrix, endX, 0f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0.0625f, startZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0.0625f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(startU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0f, endZ).color(0.5f, 0.5f, 0.5f, highlight).texture(endU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
    }

    private void renderTop(VertexConsumer buffer, Matrix4f positionMatrix, float startU, float startV, float endU, float endV, int light, int overlay, float startX, float startZ, float endX, float endZ, float highlight) {
        buffer.vertex(positionMatrix, endX, 0.0625f, startZ).color(1f, 1f, 1f, highlight).texture(endU, startV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, startZ).color(1f, 1f, 1f, highlight).texture(startU, startV).light(light).overlay(overlay).normal(1f, 1f,1f).next();
        buffer.vertex(positionMatrix, startX, 0.0625f, endZ).color(1f, 1f, 1f, highlight).texture(startU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
        buffer.vertex(positionMatrix, endX, 0.0625f, endZ).color(1f, 1f, 1f, highlight).texture(endU, endV).light(light).overlay(overlay).normal(1f, 1f, 1f).next();
    }
}
