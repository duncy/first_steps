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

    public static void registerModBlockEntities() {
        FirstSteps.LOGGER.info("Registering block entities for " + FirstSteps.MOD_ID);
        
    }

}
