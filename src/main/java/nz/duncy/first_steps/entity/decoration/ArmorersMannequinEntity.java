package nz.duncy.first_steps.entity.decoration;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import nz.duncy.first_steps.entity.ModEntities;
import nz.duncy.first_steps.item.custom.ModItems;

public class ArmorersMannequinEntity extends LivingEntity {
    private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = entity -> {
		if (entity instanceof AbstractMinecartEntity abstractMinecartEntity && abstractMinecartEntity.isRideable()) {
			return true;
		}

		return false;
	};
    private boolean invisible;
	public long lastHitTime;
    public static final EulerAngle HEAD_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
	public static final EulerAngle BODY_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
	public static final EulerAngle LEFT_ARM_ROTATION = new EulerAngle(-10.0F, 0.0F, -10.0F);
	public static final EulerAngle RIGHT_ARM_ROTATION = new EulerAngle(-15.0F, 0.0F, 10.0F);
	public static final EulerAngle LEFT_LEG_ROTATION = new EulerAngle(-1.0F, 0.0F, -1.0F);
	public static final EulerAngle RIGHT_LEG_ROTATION = new EulerAngle(1.0F, 0.0F, 1.0F);

    public ArmorersMannequinEntity(EntityType<? extends ArmorersMannequinEntity> entityType, World world) {
        super(entityType, world);
    }

    public ArmorersMannequinEntity(World world, double x, double y, double z) {
		this(ModEntities.ARMORERS_MANNEQUIN, world);
		this.setPosition(x, y, z);
	}

    public static DefaultAttributeContainer.Builder createArmorersMannequinAttributes() {
		return createLivingAttributes().add(EntityAttributes.STEP_HEIGHT, 0.0);
	}

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return List.of();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

	@Override
	public void calculateDimensions() {
		double d = this.getX();
		double e = this.getY();
		double f = this.getZ();
		super.calculateDimensions();
		this.setPosition(d, e, f);
	}

	private boolean canClip() {
		return !this.hasNoGravity();
	}

	@Override
	public boolean canMoveVoluntarily() {
		return super.canMoveVoluntarily() && this.canClip();
	}

	@Override
	public Iterable<ItemStack> getHandItems() {
		return List.of();
	}

	@Override
	public boolean canUseSlot(EquipmentSlot slot) {
		return false;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		NbtList nbtList = new NbtList();


		nbt.put("ArmorItems", nbtList);
		NbtList nbtList2 = new NbtList();


		nbt.put("HandItems", nbtList2);
		nbt.putBoolean("Invisible", this.isInvisible());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("ArmorItems", NbtElement.LIST_TYPE)) {
			NbtList nbtList = nbt.getList("ArmorItems", NbtElement.COMPOUND_TYPE);

		}

		if (nbt.contains("HandItems", NbtElement.LIST_TYPE)) {
			NbtList nbtList = nbt.getList("HandItems", NbtElement.COMPOUND_TYPE);

		}

		this.setInvisible(nbt.getBoolean("Invisible"));
		this.noClip = !this.canClip();
		NbtCompound nbtCompound2 = nbt.getCompound("Pose");
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushAway(Entity entity) {
	}

	@Override
	protected void tickCramming() {
		for (Entity entity : this.getWorld().getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE)) {
			if (this.squaredDistanceTo(entity) <= 0.2) {
				entity.pushAwayFrom(this);
			}
		}
	}

	@Override
	public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return ActionResult.PASS;
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		if (this.isRemoved()) {
			return false;
		} else if (!world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && source.getAttacker() instanceof MobEntity) {
			return false;
		} else if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			this.kill(world);
			return false;
		} else if (this.isInvulnerableTo(world, source) || this.invisible) {
			return false;
		} else if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
			this.onBreak(world, source);
			this.kill(world);
			return false;
		} else if (source.isIn(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
			if (this.isOnFire()) {
				this.updateHealth(world, source, 0.15F);
			} else {
				this.setOnFireFor(5.0F);
			}

			return false;
		} else if (source.isIn(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
			this.updateHealth(world, source, 4.0F);
			return false;
		} else {
			boolean bl = source.isIn(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
			boolean bl2 = source.isIn(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
			if (!bl && !bl2) {
				return false;
			} else {
				if (source.getAttacker() instanceof PlayerEntity playerEntity && !playerEntity.getAbilities().allowModifyWorld) {
					return false;
				}

				if (source.isSourceCreativePlayer()) {
					this.playBreakSound();
					this.spawnBreakParticles();
					this.kill(world);
					return true;
				} else {
					long l = world.getTime();
					if (l - this.lastHitTime > 5L && !bl2) {
						world.sendEntityStatus(this, EntityStatuses.HIT_ARMOR_STAND);
						this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
						this.lastHitTime = l;
					} else {
						this.breakAndDropItem(world, source);
						this.spawnBreakParticles();
						this.kill(world);
					}

					return true;
				}
			}
		}
	}

	@Override
	public void handleStatus(byte status) {
		if (status == EntityStatuses.HIT_ARMOR_STAND) {
			if (this.getWorld().isClient) {
				this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
				this.lastHitTime = this.getWorld().getTime();
			}
		} else {
			super.handleStatus(status);
		}
	}

	@Override
	public boolean shouldRender(double distance) {
		double d = this.getBoundingBox().getAverageSideLength() * 4.0;
		if (Double.isNaN(d) || d == 0.0) {
			d = 4.0;
		}

		d *= 64.0;
		return distance < d * d;
	}

	private void spawnBreakParticles() {
		if (this.getWorld() instanceof ServerWorld) {
			((ServerWorld)this.getWorld())
				.spawnParticles(
					new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()),
					this.getX(),
					this.getBodyY(0.6666666666666666),
					this.getZ(),
					10,
					(double)(this.getWidth() / 4.0F),
					(double)(this.getHeight() / 4.0F),
					(double)(this.getWidth() / 4.0F),
					0.05
				);
		}
	}

	private void updateHealth(ServerWorld world, DamageSource damageSource, float amount) {
		float f = this.getHealth();
		f -= amount;
		if (f <= 0.5F) {
			this.onBreak(world, damageSource);
			this.kill(world);
		} else {
			this.setHealth(f);
			this.emitGameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
		}
	}

	private void breakAndDropItem(ServerWorld world, DamageSource damageSource) {
		ItemStack itemStack = new ItemStack(ModItems.ARMORERS_MANNEQUIN);
		itemStack.set(DataComponentTypes.CUSTOM_NAME, this.getCustomName());
		Block.dropStack(this.getWorld(), this.getBlockPos(), itemStack);
		this.onBreak(world, damageSource);
	}

	private void onBreak(ServerWorld world, DamageSource damageSource) {
		this.playBreakSound();
		this.drop(world, damageSource);
	}

	private void playBreakSound() {
		this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
	}

	@Override
	protected float turnHead(float bodyRotation, float headRotation) {
		this.prevBodyYaw = this.prevYaw;
		this.bodyYaw = this.getYaw();
		return 0.0F;
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.canClip()) {
			super.travel(movementInput);
		}
	}

	@Override
	public void setBodyYaw(float bodyYaw) {
		this.prevBodyYaw = this.prevYaw = bodyYaw;
		this.prevHeadYaw = this.headYaw = bodyYaw;
	}

	@Override
	public void setHeadYaw(float headYaw) {
		this.prevBodyYaw = this.prevYaw = headYaw;
		this.prevHeadYaw = this.headYaw = headYaw;
	}

	@Override
	protected void updatePotionVisibility() {
		this.setInvisible(this.invisible);
	}

	@Override
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
		super.setInvisible(invisible);
	}

    private boolean isSlotDisabled(EquipmentSlot slot) {
		return true;
	}

	@Override
	public void kill(ServerWorld world) {
		this.remove(Entity.RemovalReason.KILLED);
		this.emitGameEvent(GameEvent.ENTITY_DIE);
	}

	@Override
	public boolean isImmuneToExplosion(Explosion explosion) {
		return explosion.preservesDecorativeEntities() ? this.isInvisible() : true;
	}

	@Override
	public boolean handleAttack(Entity attacker) {
		return attacker instanceof PlayerEntity && !this.getWorld().canPlayerModifyAt((PlayerEntity)attacker, this.getBlockPos());
	}

	@Override
	public LivingEntity.FallSounds getFallSounds() {
		return new LivingEntity.FallSounds(SoundEvents.ENTITY_ARMOR_STAND_FALL, SoundEvents.ENTITY_ARMOR_STAND_FALL);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ARMOR_STAND_HIT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
	}

	@Override
	public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
	}

	@Override
	public boolean isAffectedBySplashPotions() {
		return false;
	}

	@Override
	public boolean isMobOrPlayer() {
		return false;
	}

	@Override
	public ItemStack getPickBlockStack() {
		return new ItemStack(ModItems.ARMORERS_MANNEQUIN);
	}

	@Override
	public boolean isPartOfGame() {
		return !this.isInvisible();
	}
    
}
