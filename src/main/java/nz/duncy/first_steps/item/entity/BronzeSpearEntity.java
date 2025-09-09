package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import nz.duncy.first_steps.entity.ModEntities;
import nz.duncy.first_steps.item.custom.ModItems;

public class BronzeSpearEntity extends SpearEntity {
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.BRONZE_SPEAR);

    public BronzeSpearEntity(ServerWorld world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.BRONZE_SPEAR, world, owner, stack);
    }

    public BronzeSpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(ModEntities.BRONZE_SPEAR, world);
    }

    public BronzeSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.BRONZE_SPEAR, world, x, y ,z, stack);
    }

    @Override
	protected ItemStack getDefaultItemStack() {
        return DEFAULT_STACK;
	}
}
