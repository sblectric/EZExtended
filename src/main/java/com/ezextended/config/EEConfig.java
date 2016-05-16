package com.ezextended.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.ezextended.ref.RefStrings;

/** Mod config */
public class EEConfig {
	
	// config definition
	private static Configuration config;
	
	/** Config stuff */
	public static void mainRegistry(File f) {
		config = new Configuration(f);
		getConfig();
	}
	
	// defined fields
	public static int superCapacity;
	public static int ultraCapacity;
	public static boolean blankRecipes;
	public static boolean toughHyper;
	public static boolean enableTerminal;
	public static boolean enableSecurity;
	
	/** Do it up */
	private static void getConfig() {
		final String OPTIONS = config.CATEGORY_GENERAL + config.CATEGORY_SPLITTER + "options";
		
		config.load();
		
		superCapacity = config.getInt("Super Storage Capacity", OPTIONS, 20000, 100, 100000, "Super");
		ultraCapacity = config.getInt("Ultra Storage Capacity", OPTIONS, 80000, 100, 400000, "Ultra");
		blankRecipes = config.getBoolean("Enable Blank Box Recipes", OPTIONS, true, "If true, alternate box recipes using blank boxes will be appended.");
		toughHyper = config.getBoolean("Harder Hyper Recipe", OPTIONS, true, "If true, the hyper storage box will use 2 nether stars instead of 1.");
		enableTerminal = config.getBoolean("Enable Access Terminal", OPTIONS, true, "Should the access terminal be enabled?");
		enableSecurity = config.getBoolean("Enable Security", OPTIONS, true, "Should the security features be enabled?");
		
		if(config.hasChanged()) config.save();
	}

}
