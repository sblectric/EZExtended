package com.ezextended.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.ezextended.config.EEConfig;
import com.ezextended.registry.IRegistryItem;
import com.ezextended.util.JointList;

public class EEItems {

	/** The list of items to help with registration */
	private static JointList<IRegistryItem> items;

	/** The main item registry */
	public static void mainRegistry() {
		items = new JointList();
		addItems();
		registerItems();
	}
	
	// the items
	public static ItemEE key;

	/** Add the items */
	private static void addItems() {
		items.join(
			key = new ItemEE("key")
		);
		if(!EEConfig.enableSecurity) items.remove(key);
	}

	/** Register the items */
	private static void registerItems() {
		// iterate through them
		for(IRegistryItem item : items) {
			item.setRarity();
			GameRegistry.registerItem((Item)item, item.getShorthandName());
		}
	}

	/** Register the renderers */
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		// iterate through them
		for(IRegistryItem item : items) {
			item.registerRender(mesher);
		}
	}

}
