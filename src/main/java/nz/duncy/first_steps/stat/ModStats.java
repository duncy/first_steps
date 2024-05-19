package nz.duncy.first_steps.stat;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import nz.duncy.first_steps.FirstSteps;

public class ModStats {
    public static Identifier INTERACT_WITH_KILN;

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(FirstSteps.MOD_ID, id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void registerStats() {
        INTERACT_WITH_KILN = register("interact_with_kiln", StatFormatter.DEFAULT);
    }
}
