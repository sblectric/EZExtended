package com.ezextended.blocks;

import net.minecraft.block.material.Material;

import com.ezextended.config.EEConfig;
import com.ezextended.registry.IRegistryBlock;
import com.zerofall.ezstorage.block.BlockStorage;

/** An ultra storage box */
public class BlockUltraStorage extends BlockStorage implements IRegistryBlock {
	
	public BlockUltraStorage(String name) {
		super(name, Material.iron);
	}
	
	@Override
	public int getCapacity() {
		return EEConfig.ultraCapacity;
	}

}
