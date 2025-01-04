package nz.duncy.first_steps.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.item.entity.SpearEntity;

public class SpearItem extends Item implements ProjectileItem {

    public SpearItem(ToolMaterial toolMaterial, float attackDamage, Item.Settings settings) {
        super(toolMaterial.applySwordSettings(settings, attackDamage, -2.9F));
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 72000;
	}

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
			int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
			if (i < 10) {
				return false;
			} else {
				float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, playerEntity);
				if (f > 0.0F && !playerEntity.isTouchingWaterOrRain()) {
					return false;
				} else if (stack.willBreakNextUse()) {
					return false;
				} else {
					RegistryEntry<SoundEvent> registryEntry = (RegistryEntry<SoundEvent>)EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND)
						.orElse(SoundEvents.ITEM_TRIDENT_THROW);
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
					if (world instanceof ServerWorld serverWorld) {
						stack.damage(1, playerEntity);
						if (f == 0.0F) {
							TridentEntity tridentEntity = ProjectileEntity.spawnWithVelocity(TridentEntity::new, serverWorld, stack, playerEntity, 0.0F, 2.5F, 1.0F);
							if (playerEntity.isInCreativeMode()) {
								tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
							} else {
								playerEntity.getInventory().removeOne(stack);
							}

							world.playSoundFromEntity(null, tridentEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
							return true;
						}
					}
                    return false;
				}
			}
		} else {
			return false;
		}
    }

    public SpearEntity getEntity(World world, Position pos, ItemStack stack, Direction direction) {
        EntityType<SpearEntity> type;

            switch (stack.getItem().toString()) {
                case "stone_spear":
                    type = ModItemEntities.STONE_SPEAR;
                    break;
                case "flint_spear":
                    type = ModItemEntities.FLINT_SPEAR;
                    break;
                case "basalt_spear":
                    type = ModItemEntities.BASALT_SPEAR;
                    break;
                case "obsidian_spear":
                    type = ModItemEntities.OBSIDIAN_SPEAR;
                    break;
                case "copper_spear":
                    type = ModItemEntities.COPPER_SPEAR;
                    break;
                case "bronze_spear":
                    type = ModItemEntities.BRONZE_SPEAR;
                    break;
                case "iron_spear":
                    type = ModItemEntities.IRON_SPEAR;
                    break;
                case "steel_spear":
                    type = ModItemEntities.STEEL_SPEAR;
                    break;
                default:
                    type = ModItemEntities.STONE_SPEAR;
                    break;
            }

            return new SpearEntity(type, world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.willBreakNextUse()) {
			return ActionResult.FAIL;
		} else {
			user.setCurrentHand(hand);
			return ActionResult.CONSUME;
		}
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }
    
    @Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		SpearEntity spearEntity = getEntity(world, pos, stack, direction);
		spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		return spearEntity;
	}
}
