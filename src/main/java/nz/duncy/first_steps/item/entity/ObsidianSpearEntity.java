package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import nz.duncy.first_steps.entity.ModEntities;
import nz.duncy.first_steps.item.custom.ModItems;

public class ObsidianSpearEntity extends SpearEntity {
    private static final ItemStack DEFAULT_STACK = new ItemStack(ModItems.OBSIDIAN_SPEAR);

    public ObsidianSpearEntity(ServerWorld world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.OBSIDIAN_SPEAR, world, owner, stack);
    }

    public ObsidianSpearEntity(EntityType<SpearEntity> entityType, World world) {
        super(ModEntities.OBSIDIAN_SPEAR, world);
    }

    public ObsidianSpearEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.OBSIDIAN_SPEAR, world, x, y ,z, stack);
    }

    @Override
	protected ItemStack getDefaultItemStack() {
        return DEFAULT_STACK;
	}
}
