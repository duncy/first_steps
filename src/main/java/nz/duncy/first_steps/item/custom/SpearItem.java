package nz.duncy.first_steps.item.custom;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.item.entity.SpearEntity;

public class SpearItem extends ToolItem {

    public SpearItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings.component(DataComponentTypes.TOOL, createToolComponent()));
    }

    private static ToolComponent createToolComponent() {
		    return new ToolComponent(
			List.of(), 1.0F, 2
		);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed) {
		return AttributeModifiersComponent.builder()
			.add(
				EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(
					BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)((float)baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE
				),
				AttributeModifierSlot.MAINHAND
			)
			.add(
				EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
				AttributeModifierSlot.MAINHAND
			)
			.build();
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
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerEntity = (PlayerEntity) user;
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i < 10) {
            return;
        }

        if (!isAboutToBreak(stack)) {
            RegistryEntry<SoundEvent> registryEntry = (RegistryEntry<SoundEvent>)EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.TRIDENT_SOUND)
                .orElse(SoundEvents.ITEM_TRIDENT_THROW);
        
            if (!world.isClient) {
                stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));

                

                SpearEntity spearEntity = getEntity(stack, world, playerEntity);
                spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);

                if (playerEntity.getAbilities().creativeMode) {
                    spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }

                world.spawnEntity(spearEntity);
                world.playSoundFromEntity(null, spearEntity, registryEntry.value(), SoundCategory.PLAYERS, 1.0f, 1.0f);

                if (!playerEntity.getAbilities().creativeMode) {
                    playerEntity.getInventory().removeOne(stack);
                }
            }

            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }

    public SpearEntity getEntity(ItemStack stack, World world, PlayerEntity playerEntity) {
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

            return new SpearEntity(playerEntity, world, stack, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (isAboutToBreak(itemStack)) {
            return TypedActionResult.fail(itemStack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    private static boolean isAboutToBreak(ItemStack stack) {
		return stack.getDamage() >= stack.getMaxDamage() - 1;
	}

    @Override
    public int getEnchantability() {
        return 1;
    }
}
