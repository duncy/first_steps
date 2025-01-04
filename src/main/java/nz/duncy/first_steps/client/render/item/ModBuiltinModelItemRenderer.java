package nz.duncy.first_steps.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.math.BlockPos;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.PottersWheelBlockEntity;
import nz.duncy.first_steps.block.entity.UnfiredDecoratedPotBlockEntity;
import nz.duncy.first_steps.client.render.entity.model.SpearModel;
import nz.duncy.first_steps.item.custom.SpearItem;

@Environment(EnvType.CLIENT)
public class ModBuiltinModelItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final PottersWheelBlockEntity renderPottersWheel;
    private final UnfiredDecoratedPotBlockEntity renderUnfiredDecoratedPot;
    private SpearModel modelSpear;

    public ModBuiltinModelItemRenderer() {
        this.renderPottersWheel = new PottersWheelBlockEntity(BlockPos.ORIGIN, ModBlocks.POTTERS_WHEEL.getDefaultState());
        this.renderUnfiredDecoratedPot = new UnfiredDecoratedPotBlockEntity(BlockPos.ORIGIN, ModBlocks.UNFIRED_DECORATED_POT.getDefaultState());
    }

    public void reload(ResourceManager manager) {
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Item item = stack.getItem();
        FirstSteps.LOGGER.info(stack.getItem().toString());
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
        
            BlockState blockState = block.getDefaultState();
            Object blockEntity;
            
            if (blockState.isOf(ModBlocks.POTTERS_WHEEL)) {
                blockEntity = this.renderPottersWheel;
            } else if (blockState.isOf(ModBlocks.UNFIRED_DECORATED_POT)) {
                this.renderUnfiredDecoratedPot.readFrom(stack);
				blockEntity = this.renderUnfiredDecoratedPot;
            } else {
                return;
            }

            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity((BlockEntity)blockEntity, matrices, vertexConsumers, light, overlay);
        } else {
            
            if (stack.getItem() instanceof SpearItem) {
                matrices.push();
				matrices.scale(1.0F, -1.0F, -1.0F);
				VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(
					vertexConsumers, RenderLayers.getItemLayer(stack, false), false, stack.hasGlint()
				);
				this.modelSpear.render(matrices, vertexConsumer2, light, overlay);
				matrices.pop();
            }
        }
    }
}
