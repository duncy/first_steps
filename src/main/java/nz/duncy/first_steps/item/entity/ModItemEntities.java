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
                Identifier.of(FirstSteps.MOD_ID, id),
                EntityType.Builder.create(SpearEntity::new, SpawnGroup.MISC)
                .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .build());
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
