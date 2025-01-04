package nz.duncy.first_steps.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.item.ModItems;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.FIXED && renderMode != ModelTransformationMode.GROUND) {
            if (stack.isOf(ModItems.STONE_SPEAR)) {
                FirstSteps.LOGGER.info(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "item/stone_spear_3d")).toString());
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "stone_spear_3d")));
            } else if (stack.isOf(ModItems.FLINT_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "flint_spear_3d")));
            } else if (stack.isOf(ModItems.BASALT_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "basalt_spear_3d")));
            } else if (stack.isOf(ModItems.OBSIDIAN_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "obsidian_spear_3d")));
            } else if (stack.isOf(ModItems.COPPER_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "copper_spear_3d")));
            } else if (stack.isOf(ModItems.BRONZE_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "bronze_spear_3d")));
            } else if (stack.isOf(ModItems.IRON_SPEAR)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "iron_spear_3d")));
            // } else if (stack.isOf(ModItems.STEEL_SPEAR)) {
            //     return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier.of(Identifier.of(FirstSteps.MOD_ID, "steel_spear_3d"));
            } else if (stack.isOf(ModItems.WOODEN_TONGS)) {
                return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(FirstSteps.MOD_ID, "wooden_tongs_dim")));
            } 
        }
        
        return value;
    }
}
