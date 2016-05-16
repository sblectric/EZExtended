package com.ezextended.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.ezextended.blocks.EEBlocks;
import com.ezextended.config.EEConfig;
import com.ezextended.items.EEItems;
import com.zerofall.ezstorage.init.EZBlocks;

/** Recipes for the mod */
public class EECraftingManager {
	
	public static void mainRegistry() {
		removeCraftingRecipes();
		addCraftingRecipes();
	}
	
	/** Remove recipes to change them later */
	private static void removeCraftingRecipes() {
		// remove the old hyper box recipe
		RecipeHelper.removeRecipes(new ItemStack(EZBlocks.hyper_storage_box));
	}
	
	/** New recipes to add */
	private static void addCraftingRecipes() {
		
		// blank box
		RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.blankBox, 2), "ABA","BCB","ABA", 
				'A',"logWood", 'B',"plankWood", 'C',"stickWood");
		
		// alternative recipes with the blank box
		if(EEConfig.blankRecipes) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.storage_core), "C","B","L", 
					'B',new ItemStack(EEBlocks.blankBox), 'C',Blocks.chest, 'L',"logWood");
			RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.storage_box), " C ","CBC"," C ", 
					'B',new ItemStack(EEBlocks.blankBox), 'C',Blocks.chest);
			RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.input_port), "HQH","PBP","PHP", 
					'B',new ItemStack(EEBlocks.blankBox), 'H',Blocks.hopper, 'P',Blocks.piston, 'Q',"blockQuartz");
			RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.crafting_box), "EDE","CBC","CEC", 
					'B',new ItemStack(EEBlocks.blankBox), 'E',Items.ender_eye, 'C',Blocks.crafting_table, 'D',"gemDiamond");
			RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.search_box), "EDE","CBC","CEC", 
					'B',new ItemStack(EEBlocks.blankBox), 'E',Items.enchanted_book, 'C',"blockIron", 'D',Items.compass);
		}
		
		// super box
		RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.superBox), "ACA","CBC","ACA", 
		    	'A',"blockGold", 'B',EZBlocks.condensed_storage_box, 'C',Items.gold_nugget);
		
		// ultra box
		RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.ultraBox), "ACA","CBC","ACA", 
		    	'A',"blockDiamond", 'B',EEBlocks.superBox, 'C',Items.diamond);
		
		// change the hyper box recipe
		RecipeHelper.addShapedOreRecipe(new ItemStack(EZBlocks.hyper_storage_box), "ABA","ACA", EEConfig.toughHyper ? "ABA" : "AAA", 
				'A',Blocks.obsidian, 'B',Items.nether_star, 'C',EEBlocks.ultraBox);
		
		// terminal
		if(EEConfig.enableTerminal) RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.terminal), "IXI","XAX","IXI", 
				'X',"paneGlass", 'I',Blocks.iron_bars, 'A',EZBlocks.storage_core);
		
		// key
		if(EEConfig.enableSecurity) RecipeHelper.addShapedOreRecipe(new ItemStack(EEItems.key), "XXI","XX ", 'I',"ingotGold", 'X',"nuggetGold");
		
		// security box
		if(EEConfig.enableSecurity) {
			RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.secureBox), "ABA","BCB","ABA", 
					'A',"blockIron", 'B',Blocks.iron_bars, 'C',EEItems.key);
			RecipeHelper.addShapedOreRecipe(new ItemStack(EEBlocks.secureBox), "EDE","CBC","CEC", 
					'B',new ItemStack(EEBlocks.blankBox), 'E',"blockIron", 'C',Blocks.iron_bars, 'D',EEItems.key);
		}
		
		
	}

}
