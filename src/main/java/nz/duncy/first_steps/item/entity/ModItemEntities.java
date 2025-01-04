package nz.duncy.first_steps.item.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModItemEntities {
    private static EntityType<SpearEntity> registerSpear(String id) {
        return register(
            id, 
            EntityType.Builder.<SpearEntity>create(SpearEntity::new, SpawnGroup.MISC)
            .dimensions(0.25f, 0.25f)
            .eyeHeight(0.13F)
            .maxTrackingRange(4)
            .trackingTickInterval(20)
        );

    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
		return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FirstSteps.MOD_ID, id), type.build(id));
	}


    public static final EntityType<SpearEntity> STONE_SPEAR = registerSpear("stone_spear");
    public static final EntityType<SpearEntity> FLINT_SPEAR = registerSpear("flint_spear");
    public static final EntityType<SpearEntity> BASALT_SPEAR = registerSpear("basalt_spear");
    public static final EntityType<SpearEntity> OBSIDIAN_SPEAR = registerSpear("obsidian_spear");
    public static final EntityType<SpearEntity> COPPER_SPEAR = registerSpear("copper_spear");
    public static final EntityType<SpearEntity> BRONZE_SPEAR = registerSpear("bronze_spear");
    public static final EntityType<SpearEntity> IRON_SPEAR = registerSpear("iron_spear");
    public static final EntityType<SpearEntity> STEEL_SPEAR = registerSpear("steel_spear");


    public static void registerModItemEntities() {
        FirstSteps.LOGGER.info("Registering item entities for " + FirstSteps.MOD_ID);
        
    }
}
