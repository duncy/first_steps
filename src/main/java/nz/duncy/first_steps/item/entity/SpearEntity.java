package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.ModItems;

public class SpearEntity extends PersistentProjectileEntity {
    private static final TrackedData<Boolean> ENCHANTED = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.BLACKSTONE_SPEAR);

    public SpearEntity(EntityType<SpearEntity> type, double x, double y, double z, World world, ItemStack stack) {
        super(type, x, y, z, world, stack);
    }

    public SpearEntity(LivingEntity owner, World world, ItemStack stack) {
        super(ModItemEntities.BLACKSTONE_SPEAR, owner, world, stack);
        this.dataTracker.set(ENCHANTED, stack.hasGlint());
    }

    public SpearEntity(EntityType<SpearEntity> type, World world) {
        super(type, world, DEFAULT_STACK);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ENCHANTED, false);
    }


    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }
}
