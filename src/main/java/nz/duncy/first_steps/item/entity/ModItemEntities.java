package nz.duncy.first_steps.item.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModItemEntities {
    public static EntityType<SpearEntity> registerSpear(String id) {
        return Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(FirstSteps.MOD_ID, id),
                FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new)
                .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .build());
    }

    public static final EntityType<SpearEntity> BLACKSTONE_SPEAR = registerSpear("blackstone_spear");

    public static void registerModItemEntities() {
        FirstSteps.LOGGER.info("Registering item entities for " + FirstSteps.MOD_ID);
        
    }
}
