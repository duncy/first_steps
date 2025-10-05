package nz.duncy.first_steps;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import nz.duncy.first_steps.data.ModBlockTagProvider;
import nz.duncy.first_steps.data.ModItemTagProvider;
import nz.duncy.first_steps.data.ModLootTableProvider;
import nz.duncy.first_steps.data.ModModelProvider;
import nz.duncy.first_steps.data.ModWorldGenerator;
import nz.duncy.first_steps.data.recipe.ModRecipeProvider;
import nz.duncy.first_steps.world.ModConfiguredFeatures;
import nz.duncy.first_steps.world.ModPlacedFeatures;

public class FirstStepsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModWorldGenerator::new);
	}

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::boostrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::boostrap);
    }
}
