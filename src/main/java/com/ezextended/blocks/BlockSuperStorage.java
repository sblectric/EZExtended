package com.ezextended.blocks;

import net.minecraft.block.material.Material;

import com.ezextended.config.EEConfig;
import com.ezextended.registry.IRegistryBlock;
import com.zerofall.ezstorage.block.BlockStorage;
import com.zerofall.ezstorage.configuration.EZConfiguration;

/** A super storage box */
public class BlockSuperStorage extends BlockStorage implements IRegistryBlock {
	
	public BlockSuperStorage(String name) {
		super(name, Material.iron);
	}
	
	@Override
	public int getCapacity() {
		return EEConfig.superCapacity;
	}

}
