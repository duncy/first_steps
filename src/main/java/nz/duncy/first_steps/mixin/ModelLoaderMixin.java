package nz.duncy.first_steps.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import nz.duncy.first_steps.FirstSteps;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        // this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "stone_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "flint_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "basalt_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "obsidian_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "copper_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "bronze_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "iron_spear_3d", "inventory"));
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "steel_spear_3d", "inventory"));
        
        this.addModel(new ModelIdentifier(FirstSteps.MOD_ID, "wooden_tongs_dim", "inventory"));
    }
    
}