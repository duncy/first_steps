package nz.duncy.first_steps.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
    @Inject(method = "generate", at = @At(value = "HEAD", target = "Lnet/minecraft/world/gen/feature/Feature;generate(Lnet/minecraft/world/gen/feature/util/FeatureContext;)Z"), cancellable = true)
    protected void injectGenerateMethod(FeatureContext<OreFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        Block[] preventedOres = {Blocks.COPPER_ORE, Blocks.IRON_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_IRON_ORE};
        for (OreFeatureConfig.Target target : context.getConfig().targets) {
            if (Arrays.asList(preventedOres).contains(target.state.getBlock())) {
                cir.setReturnValue(false);
            }
        }
    }
}
