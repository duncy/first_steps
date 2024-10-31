package nz.duncy.first_steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import nz.duncy.first_steps.block.ModBlocks;
import nz.duncy.first_steps.block.entity.ModBlockEntities;
import nz.duncy.first_steps.item.ModItemGroups;
import nz.duncy.first_steps.item.ModItems;
import nz.duncy.first_steps.item.entity.ModItemEntities;
import nz.duncy.first_steps.recipe.ModRecipes;
// import nz.duncy.first_steps.recipe.ModRecipes;
import nz.duncy.first_steps.stat.ModStats;
import nz.duncy.first_steps.world.gen.ModWorldGeneration;
import nz.duncy.first_steps.world.gen.feature.ModFeature;

public class FirstSteps implements ModInitializer {
    public static final String MOD_ID = "first_steps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModItemEntities.registerModItemEntities();
        ModBlocks.registerModBlocks();
        ModBlockEntities.registerModBlockEntities();
        ModItemGroups.registerItemGroups();
        ModFeature.registerFeatures();
        ModWorldGeneration.generateModWorldGen();
        // ModRecipes.registerRecipes();
        ModStats.registerStats();
	}
}
