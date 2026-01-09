package nz.duncy.first_steps;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import nz.duncy.first_steps.data.loot.ModLootTableProvider;
import nz.duncy.first_steps.data.models.ModModelProvider;
import nz.duncy.first_steps.data.recipes.ModRecipeProvider;
import nz.duncy.first_steps.data.tags.ModBlockTagProvider;
import nz.duncy.first_steps.data.tags.ModItemTagProvider;

public class FirstStepsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModRecipeProvider::new);
	}
}
