package nz.duncy.first_steps.block.custom;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.custom.ModItems;

public class OreRockBlock extends Block {
    private VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 1, 12);

    public OreRockBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (hit.getType() == HitResult.Type.BLOCK) {
                Item heldItem = player.getStackInHand(player.getActiveHand()).getItem();
                if (heldItem instanceof BlockItem) {
                    if (((BlockItem) heldItem).getBlock() instanceof RockBlock || heldItem == Items.FLINT) {

                        ItemStack itemStack = new ItemStack(ModItems.RAW_STONE_COPPER);
                        world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
                        world.breakBlock(pos, false);
                        return ActionResult.SUCCESS;    
                    }                    
                }              
            }              
        }
        return ActionResult.SUCCESS_SERVER;
    }



    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.first_steps.ore_rock_block").formatted(Formatting.GOLD, Formatting.ITALIC));
        super.appendTooltip(stack, context, tooltip, options);
    }
}
