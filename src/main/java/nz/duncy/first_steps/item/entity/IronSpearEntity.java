package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.custom.ModItems;

public class IronSpearEntity extends SpearEntity {
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.IRON_SPEAR);

    public IronSpearEntity(ServerWorld world, LivingEntity owner, ItemStack stack) {
        super(ModItemEntities.IRON_SPEAR, world, owner, stack);
    }

    public IronSpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(ModItemEntities.IRON_SPEAR, world);
    }

    public IronSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModItemEntities.IRON_SPEAR, world, x, y ,z, stack);
    }

    @Override
	protected ItemStack getDefaultItemStack() {
        return DEFAULT_STACK;
	}
}
