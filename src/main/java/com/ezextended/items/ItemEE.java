package com.ezextended.items;

import net.minecraft.item.Item;

import com.ezextended.registry.IRegistryItem;
import com.zerofall.ezstorage.EZStorage;

/** An item */
public class ItemEE extends Item implements IRegistryItem {
	
	public ItemEE(String name) {
		this.setUnlocalizedName(name);
		this.setCreativeTab(EZStorage.instance.creativeTab);
	}

}
