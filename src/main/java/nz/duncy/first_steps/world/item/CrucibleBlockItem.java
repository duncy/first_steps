package nz.duncy.first_steps.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import nz.duncy.first_steps.metallurgy.MetalStorage;
import nz.duncy.first_steps.metallurgy.TemperatureStorage;
import nz.duncy.first_steps.world.item.component.CrucibleContainerContents;
import nz.duncy.first_steps.world.item.component.ModDataComponents;
import nz.duncy.first_steps.world.level.block.IngotCastBlock;
import nz.duncy.first_steps.world.level.block.entity.IngotCastBlockEntity;

public class CrucibleBlockItem extends BlockItem {
    public CrucibleBlockItem(Block block, Properties properties) {
        super(block, properties.component(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS, new CrucibleContainerContents(20, null)));
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level world = useOnContext.getLevel();
        BlockPos pos = useOnContext.getClickedPos();
        ItemStack stack = useOnContext.getItemInHand();

        if (player != null) {
            BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof IngotCastBlock) {

                IngotCastBlockEntity blockEntity = (IngotCastBlockEntity) world.getBlockEntity(pos);

                CrucibleContainerContents contents = stack.get(ModDataComponents.CRUCIBLE_CONTAINER_CONTENTS);

                if (contents != null && blockEntity != null) {
                    if (contents.inventory() != null) {
                        MetalStorage metalStorage = new MetalStorage();
                        TemperatureStorage temperatureStorage = new TemperatureStorage(contents.temperature());

                        NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(9, ItemStack.EMPTY);
                        contents.inventory().copyInto(inventoryStacks);
                        metalStorage.buildCapacity(inventoryStacks);

                        if (blockEntity.pour(metalStorage) == true) {
                            world.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1f, 1f);
                            return InteractionResult.SUCCESS_SERVER;
                        } else {
                            return InteractionResult.PASS;
                        }
                    }
                }
            }
        }

        return super.useOn(useOnContext);
    }
}
