package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.custom.ModItems;

public class CopperSpearEntity extends SpearEntity {
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.COPPER_SPEAR);

    public CopperSpearEntity(ServerWorld world, LivingEntity owner, ItemStack stack) {
        super(ModItemEntities.COPPER_SPEAR, world, owner, stack);
    }

    public CopperSpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(ModItemEntities.COPPER_SPEAR, world);
    }

    public CopperSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModItemEntities.COPPER_SPEAR, world, x, y ,z, stack);
    }

    @Override
	protected ItemStack getDefaultItemStack() {
        return DEFAULT_STACK;
	}
}
