package nz.duncy.first_steps.block.custom;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.screen.MannequinScreenHandler;
import nz.duncy.first_steps.screen.ModScreenHandlers;
import nz.duncy.first_steps.stat.ModStats;

public class MannequinBlock extends CraftingTableBlock {
    	public static final MapCodec<MannequinBlock> CODEC = createCodec(MannequinBlock::new);
	private static final Text SCREEN_TITLE = Text.translatable("container." + FirstSteps.MOD_ID + ".mannequin");

	@Override
	public MapCodec<MannequinBlock> getCodec() {
		return CODEC;
	}

	public MannequinBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory(
			(syncId, inventory, player) -> new MannequinScreenHandler(ModScreenHandlers.MANNEQUIN_SCREEN_HANDLER, syncId, inventory, ScreenHandlerContext.create(world, pos)), SCREEN_TITLE
		);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient) {
			player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			player.incrementStat(ModStats.INTERACT_WITH_MANNEQUIN);
		}

		return ActionResult.SUCCESS;
	}
}
