package nz.duncy.first_steps.client.render.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.math.BlockPos;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.PottersWheelBlockEntity;
import nz.duncy.first_steps.block.entity.UnfiredDecoratedPotBlockEntity;

@Environment(EnvType.CLIENT)
public class ModBuiltinModelItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private final PottersWheelBlockEntity renderPottersWheel;
    private final UnfiredDecoratedPotBlockEntity renderUnfiredDecoratedPot;

    public ModBuiltinModelItemRenderer() {
        this.renderPottersWheel = new PottersWheelBlockEntity(BlockPos.ORIGIN, ModBlocks.POTTERS_WHEEL.getDefaultState());
        this.renderUnfiredDecoratedPot = new UnfiredDecoratedPotBlockEntity(BlockPos.ORIGIN, ModBlocks.UNFIRED_DECORATED_POT.getDefaultState());
    }

    public void reload(ResourceManager manager) {
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
        
            BlockState blockState = block.getDefaultState();
            Object blockEntity;
            
            if (blockState.isOf(ModBlocks.POTTERS_WHEEL)) {
                blockEntity = this.renderPottersWheel;
            } else if (blockState.isOf(ModBlocks.UNFIRED_DECORATED_POT)) {
                this.renderUnfiredDecoratedPot.readNbtFromStack(stack);
                blockEntity = this.renderUnfiredDecoratedPot;
            } else {
                return;
            }

            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity((BlockEntity)blockEntity, matrices, vertexConsumers, light, overlay);
        }
    }
}
