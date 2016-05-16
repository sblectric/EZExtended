package com.ezextended.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.ezextended.blocks.EEBlocks;
import com.ezextended.config.EEConfig;
import com.ezextended.crafting.EECraftingManager;
import com.ezextended.events.SecurityEvents;
import com.ezextended.gui.EEGuiHandler;
import com.ezextended.items.EEItems;
import com.ezextended.network.EENetwork;

/** The common proxy */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		EEConfig.mainRegistry(event.getSuggestedConfigurationFile());
		EENetwork.mainRegistry();
	}

	public void onInit(FMLInitializationEvent event) {
		EEBlocks.mainRegistry();
		EEItems.mainRegistry();
	}

	public void postInit(FMLPostInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(EZExtended.modInstance, new EEGuiHandler());
		MinecraftForge.EVENT_BUS.register(new SecurityEvents());
		EECraftingManager.mainRegistry();
	}

}
