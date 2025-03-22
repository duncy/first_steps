// package nz.duncy.first_steps.client.render.item;

// import org.joml.Quaternionf;

// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
// import net.minecraft.block.BlockState;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.render.RenderLayer;
// import net.minecraft.client.render.VertexConsumer;
// import net.minecraft.client.render.VertexConsumerProvider;
// import net.minecraft.client.render.model.BakedModel;
// import net.minecraft.client.render.model.json.ModelTransformationMode;
// import net.minecraft.client.util.ModelIdentifier;
// import net.minecraft.client.util.math.MatrixStack;
// import net.minecraft.item.ItemStack;
// import net.minecraft.util.Identifier;
// import nz.duncy.first_steps.FirstSteps;
// import nz.duncy.first_steps.block.ModBlocks;
// import nz.duncy.first_steps.item.custom.TongItem;

// @Environment(EnvType.CLIENT)
// public class TongItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
//     private static final BlockState Cruciblestate = ModBlocks.FIRED_CRUCIBLE.getDefaultState();

//     @Override
//     public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices,
//         VertexConsumerProvider vertexConsumers, int light, int overlay) {

//         MinecraftClient client = MinecraftClient.getInstance();
//         BakedModel tongModel = client.getBakedModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "wooden_tongs")));
//         VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getCutout());

//         matrices.push();

//         matrices.translate(-0.5f, -0.125f, 0.25f);
//         // matrices.scale(0.75f, 0.75f, 0.75f);
//         matrices.multiply(new Quaternionf(-0.653f, -0.271f, -0.271f, 0.653f));
        
//         // matrices.multiply(new Quaternionf(-0.5f, 0.5f, -0.5f, 0.5f));
//         MatrixStack.Entry entry = matrices.peek();
//         tongModel.getQuads(null, null, client.world.random).forEach(quad -> {
//             consumer.quad(entry, quad, 1F, 1F, 1F, 0.0F, light, overlay);
//         });
        
//         matrices.pop();

//         if (!TongItem.isEmpty(stack)) {
//             matrices.push();
//             matrices.translate(0.035f, 0.25f, -0.2f);
//             matrices.scale(0.35f, 0.35f, 0.35f);
//             client.getBlockRenderManager().renderBlockAsEntity(Cruciblestate, matrices, vertexConsumers, light, overlay);
//             matrices.pop();
//         }
//     }
// }
