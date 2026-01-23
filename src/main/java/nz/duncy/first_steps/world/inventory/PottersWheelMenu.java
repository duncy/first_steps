package nz.duncy.first_steps.world.inventory;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.crafting.SelectableRecipe.SingleInputSet;
import nz.duncy.first_steps.network.protocol.common.custom.PottersWheelRecipePayload;
import nz.duncy.first_steps.world.item.crafting.PottersWheelRecipe;
import nz.duncy.first_steps.world.level.block.entity.PottersWheelBlockEntity;

public class PottersWheelMenu extends RecipeSelectionMenu<PottersWheelRecipe, PottersWheelMenu> {

    public PottersWheelMenu(int i, Inventory inventory) {
        super(ModMenuType.POTTERS_WHEEL_SELECTION_MENU, i, inventory);
    }

    public PottersWheelMenu(int i, Inventory inventory, SingleInputSet<PottersWheelRecipe> recipes, BlockPos blockPos) {
        super(ModMenuType.POTTERS_WHEEL_SELECTION_MENU, i, inventory, recipes, blockPos);
    }   

    @Override
    protected void sendRecipes(Player player) {
        if (!player.level().isClientSide()) {
            ServerPlayNetworking.send((ServerPlayer) player, new PottersWheelRecipePayload(getRecipes()));
        }
    }
    
    public void shapeClay(int selection, ServerPlayer player) {
        player.closeContainer();
        ServerLevel serverLevel = player.level();
        if (!serverLevel.isClientSide()) {
            if (selection >= 0) {
                ContextMap contextMap = SlotDisplayContext.fromLevel(serverLevel);
                ItemStack itemStack = getRecipes().entries().get(selection).recipe().optionDisplay().resolveForFirstStack(contextMap).copy();
                BlockPos blockPos = getBlockPos();
                if (itemStack.getItem() instanceof BlockItem blockItem) {
                    BlockPos aboveBlockPos = blockPos.above();
                    serverLevel.setBlock(aboveBlockPos, blockItem.getBlock().defaultBlockState(), Block.UPDATE_ALL);
                    serverLevel.sendParticles(ParticleTypes.POOF, true, true, aboveBlockPos.getX(), aboveBlockPos.getY(), aboveBlockPos.getZ(), 5, 0.0, 0.0, 0.0, 0.0);
                    serverLevel.playSound(
                                null,
                                aboveBlockPos,
                                SoundEvents.MUD_HIT,
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                            );
                    if (serverLevel.getBlockEntity(blockPos) instanceof PottersWheelBlockEntity pottersWheelBlockEntity) {
                        if (!pottersWheelBlockEntity.isSpinning()) {
                            pottersWheelBlockEntity.startSpinning();
                        }
                    }
                }
            }
        }
    }
}
