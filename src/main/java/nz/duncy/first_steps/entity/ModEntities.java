package nz.duncy.first_steps.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.entity.decoration.ArmorersMannequinEntity;
import nz.duncy.first_steps.item.entity.BasaltSpearEntity;
import nz.duncy.first_steps.item.entity.BronzeSpearEntity;
import nz.duncy.first_steps.item.entity.CopperSpearEntity;
import nz.duncy.first_steps.item.entity.FlintSpearEntity;
import nz.duncy.first_steps.item.entity.IronSpearEntity;
import nz.duncy.first_steps.item.entity.ObsidianSpearEntity;
import nz.duncy.first_steps.item.entity.SpearEntity;
import nz.duncy.first_steps.item.entity.StoneSpearEntity;

public class ModEntities {
    private static <T extends SpearEntity> EntityType<T> registerSpear(String id, EntityType.EntityFactory<T> factory) {
        return register(
            id, 
            EntityType.Builder.<T>create(factory, SpawnGroup.MISC)
            .dimensions(0.25f, 0.25f)
            .eyeHeight(0.13F)
            .maxTrackingRange(4)
            .trackingTickInterval(20)
        );
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(FirstSteps.MOD_ID, id));
        FirstSteps.LOGGER.info(key.toString());
		return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FirstSteps.MOD_ID, id), type.build(key));
	}

    public static final EntityType<SpearEntity> STONE_SPEAR = registerSpear("stone_spear", StoneSpearEntity::new);
    public static final EntityType<SpearEntity> FLINT_SPEAR = registerSpear("flint_spear", FlintSpearEntity::new);
    public static final EntityType<SpearEntity> BASALT_SPEAR = registerSpear("basalt_spear", BasaltSpearEntity::new);
    public static final EntityType<SpearEntity> OBSIDIAN_SPEAR = registerSpear("obsidian_spear", ObsidianSpearEntity::new);
    public static final EntityType<SpearEntity> COPPER_SPEAR = registerSpear("copper_spear", CopperSpearEntity::new);
    public static final EntityType<SpearEntity> BRONZE_SPEAR = registerSpear("bronze_spear", BronzeSpearEntity::new);
    public static final EntityType<SpearEntity> IRON_SPEAR = registerSpear("iron_spear", IronSpearEntity::new);
    public static final EntityType<ArmorersMannequinEntity> ARMORERS_MANNEQUIN = register("armorers_mannequin", EntityType.Builder.<ArmorersMannequinEntity>create(ArmorersMannequinEntity::new, SpawnGroup.MISC).dimensions(0.5F, 1.975F).eyeHeight(1.7775F).maxTrackingRange(10));

    public static void registerModItemEntities() {
        FirstSteps.LOGGER.info("Registering entities for " + FirstSteps.MOD_ID);
    }
}
