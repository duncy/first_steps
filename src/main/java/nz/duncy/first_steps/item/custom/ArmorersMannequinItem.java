package nz.duncy.first_steps.item.custom;

import java.util.function.Consumer;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import nz.duncy.first_steps.entity.ModEntities;
import nz.duncy.first_steps.entity.decoration.ArmorersMannequinEntity;

public class ArmorersMannequinItem extends Item {
    
    public ArmorersMannequinItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		Direction direction = context.getSide();
		if (direction == Direction.DOWN) {
			return ActionResult.FAIL;
		} else {
			World world = context.getWorld();
			ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
			BlockPos blockPos = itemPlacementContext.getBlockPos();
			ItemStack itemStack = context.getStack();
			Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
			Box box = ModEntities.ARMORERS_MANNEQUIN.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			if (world.isSpaceEmpty(null, box) && world.getOtherEntities(null, box).isEmpty()) {
				if (world instanceof ServerWorld serverWorld) {
					Consumer<ArmorersMannequinEntity> consumer = EntityType.copier(serverWorld, itemStack, context.getPlayer());
					ArmorersMannequinEntity armorersMannequinEntity = ModEntities.ARMORERS_MANNEQUIN.create(serverWorld, consumer, blockPos, SpawnReason.SPAWN_ITEM_USE, true, true);
					if (armorersMannequinEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					armorersMannequinEntity.refreshPositionAndAngles(armorersMannequinEntity.getX(), armorersMannequinEntity.getY(), armorersMannequinEntity.getZ(), f, 0.0F);
					serverWorld.spawnEntityAndPassengers(armorersMannequinEntity);
					world.playSound(
						null, armorersMannequinEntity.getX(), armorersMannequinEntity.getY(), armorersMannequinEntity.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F
					);
					armorersMannequinEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
				}

				itemStack.decrement(1);
				return ActionResult.SUCCESS;
			} else {
				return ActionResult.FAIL;
			}
		}
	}
}
