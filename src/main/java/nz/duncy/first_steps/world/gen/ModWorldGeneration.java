package nz.duncy.first_steps.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModOreGeneration.generateOres();
        ModRockGeneration.generateRocks();
    }
}
