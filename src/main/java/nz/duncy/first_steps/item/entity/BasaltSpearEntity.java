package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.ModItems;

public class BasaltSpearEntity extends SpearEntity {
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.BASALT_SPEAR);

    public BasaltSpearEntity(ServerWorld world, LivingEntity owner, ItemStack stack) {
        super(ModItemEntities.BASALT_SPEAR, world, owner, stack);
    }

    public BasaltSpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(ModItemEntities.BASALT_SPEAR, world);
    }

    public BasaltSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModItemEntities.BASALT_SPEAR, world, x, y ,z, stack);
    }

    @Override
	protected ItemStack getDefaultItemStack() {
        return DEFAULT_STACK;
	}
}
