package nz.duncy.first_steps.world.level.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import nz.duncy.first_steps.FirstSteps;
import nz.duncy.first_steps.world.level.block.ModBlocks;

public class ModBlockEntityType {
    public static final BlockEntityType<DecoratedJarBlockEntity> DECORATED_JAR = register("decorated_jar", DecoratedJarBlockEntity::new, ModBlocks.DECORATED_JAR);
    public static final BlockEntityType<UnfiredDecoratedPotBlockEntity> UNFIRED_DECORATED_POT = register("unfired_decorated_pot", UnfiredDecoratedPotBlockEntity::new, ModBlocks.UNFIRED_DECORATED_POT);
    public static final BlockEntityType<UnfiredDecoratedJarBlockEntity> UNFIRED_DECORATED_JAR = register("unfired_decorated_jar", UnfiredDecoratedJarBlockEntity::new, ModBlocks.UNFIRED_DECORATED_JAR);
    public static final BlockEntityType<PottersWheelBlockEntity> POTTERS_WHEEL = register("potters_wheel", PottersWheelBlockEntity::new, ModBlocks.POTTERS_WHEEL);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering block entities for " + FirstSteps.MOD_ID);
    }
}