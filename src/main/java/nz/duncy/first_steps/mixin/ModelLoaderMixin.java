// package nz.duncy.first_steps.mixin;

// import org.spongepowered.asm.mixin.Mixin;
// import net.minecraft.client.render.model.ModelLoader;

// @Mixin(ModelLoader.class)
// public abstract class ModelLoaderMixin {
    // @Shadow
    // protected abstract void loadInventoryVariantItemModel(Identifier id);

    // @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadInventoryVariantItemModel(Lnet/minecraft/util/Identifier;)V", shift = At.Shift.AFTER))
    // public void addModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "stone_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "flint_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "basalt_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "obsidian_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "copper_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "bronze_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "iron_spear_3d"));
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "steel_spear_3d"));
    
    //     this.loadInventoryVariantItemModel(Identifier.of(FirstSteps.MOD_ID, "wooden_tongs_dim"));
    // }
    
// }