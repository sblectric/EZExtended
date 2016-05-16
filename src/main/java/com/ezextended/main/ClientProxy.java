package com.ezextended.main;

import com.ezextended.blocks.EEBlocks;
import com.ezextended.items.EEItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/** The client proxy */
public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	public void onInit(FMLInitializationEvent event) {
		super.onInit(event);
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		EEBlocks.registerRendering();
		EEItems.registerRendering();
	}

}
