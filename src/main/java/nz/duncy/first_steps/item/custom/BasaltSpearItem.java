package nz.duncy.first_steps.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import nz.duncy.first_steps.item.entity.SpearEntity;
import nz.duncy.first_steps.item.entity.BasaltSpearEntity;

public class BasaltSpearItem extends SpearItem {
    public BasaltSpearItem(ToolMaterial toolMaterial, float attackDamage, Item.Settings settings) {
        super(toolMaterial, attackDamage, settings);
    }

    @Override
    public SpearEntity spawnWithVelocity(ServerWorld serverWorld, ItemStack stack, PlayerEntity playerEntity) {
        return ProjectileEntity.spawnWithVelocity(BasaltSpearEntity::new, serverWorld, stack, playerEntity, 0.0F, 2.5F, 1.0F);
    }

    @Override
    public SpearEntity getEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new BasaltSpearEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
    }
}
