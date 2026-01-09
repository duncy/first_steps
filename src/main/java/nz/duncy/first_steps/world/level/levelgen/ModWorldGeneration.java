package nz.duncy.first_steps.world.level.levelgen;

import nz.duncy.first_steps.FirstSteps;

public class ModWorldGeneration {
    public static void initialize() {
        FirstSteps.LOGGER.info("Registering worldgen steps for " + FirstSteps.MOD_ID);
        ModRockGeneration.generateRocks();
    }
}
