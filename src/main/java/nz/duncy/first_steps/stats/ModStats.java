package nz.duncy.first_steps.stats;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import nz.duncy.first_steps.FirstSteps;

public class ModStats {
    public static Identifier ROCKS_KNAPPED = makeCustomStat("rocks_knapped", StatFormatter.DEFAULT);

    private static Identifier makeCustomStat(String string, StatFormatter statFormatter) {
        Identifier identifier = Identifier.fromNamespaceAndPath(FirstSteps.MOD_ID, string);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, string, identifier);
        Stats.CUSTOM.get(identifier, statFormatter);
        return identifier;
    }

    public static void initialize() {
        FirstSteps.LOGGER.info("Registering stats for " + FirstSteps.MOD_ID);
    }
    
}
