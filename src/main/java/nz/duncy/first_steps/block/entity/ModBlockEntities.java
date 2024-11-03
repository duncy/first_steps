package nz.duncy.first_steps.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<RockBlockEntity> ROCK_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstSteps.MOD_ID, "rock_block_be"),
            FabricBlockEntityTypeBuilder.create(RockBlockEntity::new,
                ModBlocks.STONE_ROCK, ModBlocks.FLINT_ROCK, ModBlocks.BASALT_ROCK, ModBlocks.OBSIDIAN_ROCK).build());

    public static final BlockEntityType<KilnBlockEntity> KILN_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstSteps.MOD_ID, "kiln_block_be"),
            FabricBlockEntityTypeBuilder.create(KilnBlockEntity::new,
                ModBlocks.KILN).build());

    public static final BlockEntityType<CrucibleBlockEntity> CRUCIBLE_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstSteps.MOD_ID, "crucible_block_be"),
            FabricBlockEntityTypeBuilder.create(CrucibleBlockEntity::new,
                ModBlocks.FIRED_CRUCIBLE).build());

    public static final BlockEntityType<PottersWheelBlockEntity> POTTERS_WHEEL_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstSteps.MOD_ID, "potters_wheel_block_be"),
            FabricBlockEntityTypeBuilder.create(PottersWheelBlockEntity::new,
                ModBlocks.POTTERS_WHEEL).build());

    public static final BlockEntityType<UnfiredDecoratedPotBlockEntity> UNFIRED_DECORATED_POT_BLOCK_ENTITY = 
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(FirstSteps.MOD_ID, "unfired_decorated_pot_block_be"),
            FabricBlockEntityTypeBuilder.create(UnfiredDecoratedPotBlockEntity::new,
                ModBlocks.UNFIRED_DECORATED_POT).build());

    public static void registerModBlockEntities() {
        FirstSteps.LOGGER.info("Registering block entities for " + FirstSteps.MOD_ID);
    }

}
