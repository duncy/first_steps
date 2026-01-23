package nz.duncy.first_steps.world.level.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UnlitTorchBlock extends BaseTorchBlock {
    private static final MapCodec<UnlitTorchBlock> CODEC = simpleCodec(UnlitTorchBlock::new);

    protected UnlitTorchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseTorchBlock> codec() {
        return CODEC;
    }

    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        Item item = itemStack.getItem();
        if (item == Items.FLINT_AND_STEEL) {
            level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(blockPos, Blocks.TORCH.defaultBlockState(), UPDATE_ALL);
            itemStack.hurtAndBreak(1, player, interactionHand.asEquipmentSlot());
            return InteractionResult.SUCCESS;
        } else if (item == Items.FIRE_CHARGE) {
            level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(blockPos, Blocks.TORCH.defaultBlockState(), UPDATE_ALL);
            itemStack.consume(1, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }  
}
