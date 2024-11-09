package nz.duncy.first_steps.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.item.entity.SpearEntity;

public class SpearItem extends ToolItem implements Vanishable {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final float attackDamage;

    public SpearItem(ToolMaterial toolMaterial, int attackDamage, Item.Settings settings) {
        super(toolMaterial, settings);
        this.attackDamage = (float)attackDamage + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.9f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
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
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerEntity = (PlayerEntity) user;
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if (i < 10) {
            return;
        }
        
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));

            

            SpearEntity spearEntity = getEntity(stack, world, playerEntity);
            spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, 2.5f, 1.0f);

            if (playerEntity.getAbilities().creativeMode) {
                spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }

            world.spawnEntity(spearEntity);
            world.playSoundFromEntity(null, spearEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);

            if (!playerEntity.getAbilities().creativeMode) {
                playerEntity.getInventory().removeOne(stack);
            }
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
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
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
    
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if ((double)state.getHardness(world, pos) != 0.0) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }
}
