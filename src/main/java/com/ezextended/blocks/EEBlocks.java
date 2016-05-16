package com.ezextended.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.ezextended.config.EEConfig;
import com.ezextended.registry.IRegistryBlock;
import com.ezextended.tiles.TileEntitySecure;
import com.ezextended.util.JointList;

/** Mod block class */
public class EEBlocks {
	
	/** The list of blocks to help with registration */
	private static JointList<IRegistryBlock> blocks;
	
	/** The main block registry */
	public static void mainRegistry() {
		blocks = new JointList();
		addBlocks();
		registerBlocks();
		registerTileEntities();
	}
	
	// the blocks
	public static BlockBlank blankBox;
	public static BlockSuperStorage superBox;
	public static BlockUltraStorage ultraBox;
	public static BlockTerminal terminal;
	public static BlockSecure secureBox;
	
	/** Add the blocks */
	private static void addBlocks() {
		blocks.join(
			blankBox = new BlockBlank("blankBox"),
			superBox = new BlockSuperStorage("superBox"),
			ultraBox = new BlockUltraStorage("ultraBox"),
			terminal = new BlockTerminal("terminal"),
			secureBox = new BlockSecure("secureBox")
		);
		if(!EEConfig.enableTerminal) blocks.remove(terminal); // terminal disabled
		if(!EEConfig.enableSecurity) blocks.remove(secureBox); // security disabled
	}
	
	/** Register the blocks */
	private static void registerBlocks() {
		// iterate through them
		for(IRegistryBlock block : blocks) {
			GameRegistry.registerBlock((Block)block, block.getItemClass(), block.getShorthandName(), block.getItemClassArgs());
		}
	}
	
	/** Register the tile entities */
	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySecure.class, "TileEntitySecureEZE");
	}
	
	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	    
		// iterate through them
		for(IRegistryBlock block : blocks) {
			block.registerRender(mesher);
		}
	}

}
