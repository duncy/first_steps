package nz.duncy.first_steps;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.inventory.AbstractContainerMenu;
import nz.duncy.first_steps.events.ModEvents;
import nz.duncy.first_steps.network.protocol.common.custom.KnappingRecipePacketPayload;
import nz.duncy.first_steps.network.protocol.common.custom.KnappingSelectionPacketPayload;
import nz.duncy.first_steps.network.protocol.common.custom.PottersWheelRecipePayload;
import nz.duncy.first_steps.network.protocol.common.custom.PottersWheelSelectionPacketPayload;
import nz.duncy.first_steps.stats.ModStats;
import nz.duncy.first_steps.world.inventory.KnappingMenu;
import nz.duncy.first_steps.world.inventory.ModMenuType;
import nz.duncy.first_steps.world.inventory.PottersWheelMenu;
import nz.duncy.first_steps.world.item.ModItems;
import nz.duncy.first_steps.world.item.crafting.ModRecipeBookCategories;
import nz.duncy.first_steps.world.item.crafting.ModRecipeSerializer;
import nz.duncy.first_steps.world.item.crafting.ModRecipeType;
import nz.duncy.first_steps.world.level.block.ModBlocks;
import nz.duncy.first_steps.world.level.block.entity.ModBlockEntityType;
import nz.duncy.first_steps.world.level.block.state.properties.ModBlockStateProperties;
import nz.duncy.first_steps.world.level.levelgen.ModWorldGeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstSteps implements ModInitializer {
	public static final String MOD_ID = "first_steps";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.initialize();
		ModBlockStateProperties.initialize();
		ModBlocks.initialize();
        ModBlockEntityType.initialize();
        ModWorldGeneration.initialize();
        ModStats.initialize();
        ModEvents.initialize();
        ModMenuType.initialize();
        ModRecipeType.initialize();
        ModRecipeSerializer.initialize();
        ModRecipeBookCategories.initialize();

        PayloadTypeRegistry.playS2C().register(KnappingRecipePacketPayload.TYPE, KnappingRecipePacketPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(KnappingSelectionPacketPayload.TYPE, KnappingSelectionPacketPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(PottersWheelRecipePayload.TYPE, PottersWheelRecipePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PottersWheelSelectionPacketPayload.TYPE, PottersWheelSelectionPacketPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(KnappingSelectionPacketPayload.TYPE, (payload, context) -> {
            AbstractContainerMenu containerMenu = context.player().containerMenu;
            if (containerMenu instanceof KnappingMenu knappingMenu) {
                knappingMenu.dropHead(payload.selection(), context.player());
            }
	    });

        ServerPlayNetworking.registerGlobalReceiver(PottersWheelSelectionPacketPayload.TYPE, (payload, context) -> {
            AbstractContainerMenu containerMenu = context.player().containerMenu;
            if (containerMenu instanceof PottersWheelMenu pottersWheelMenu) {
                pottersWheelMenu.shapeClay(payload.selection(), context.player());
            }
	    });

		LOGGER.info("Finished main initialisation of " + MOD_ID + ", have fun! :^)");
	}
}