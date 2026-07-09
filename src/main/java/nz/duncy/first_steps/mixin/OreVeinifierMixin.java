package nz.duncy.first_steps.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.OreVeinifier;
import nz.duncy.first_steps.world.level.block.ModBlocks;

@Mixin(OreVeinifier.class)
public abstract class OreVeinifierMixin {

    @Inject(method = "create", at = @At("RETURN"), cancellable = true)
    private static void first_steps$injected(CallbackInfoReturnable<NoiseChunk.BlockStateFiller> cir) {
        NoiseChunk.BlockStateFiller original = cir.getReturnValue();

        cir.setReturnValue((context) -> {
            BlockState state = original.calculate(context);

            if (state != null) {
                if (state.is(Blocks.DEEPSLATE_IRON_ORE)) {
                    return ModBlocks.DEEPSLATE_IRON_ORE.defaultBlockState();
                }
                if (state.is(Blocks.RAW_IRON_BLOCK)) {
                    return ModBlocks.RAW_DEEPSLATE_IRON.defaultBlockState();
                }
    
                if (state.is(Blocks.COPPER_ORE)) {
                    return ModBlocks.STONE_COPPER_ORE.defaultBlockState();
                }
                if (state.is(Blocks.RAW_COPPER_BLOCK)) {
                    return ModBlocks.RAW_STONE_COPPER.defaultBlockState();
                }
            }
            return state;
        });
    }
}