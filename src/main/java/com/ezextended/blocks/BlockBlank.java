package com.ezextended.blocks;

import net.minecraft.block.material.Material;

import com.ezextended.registry.IRegistryBlock;
import com.zerofall.ezstorage.block.StorageMultiblock;

/** A blank multiblock */
public class BlockBlank extends StorageMultiblock implements IRegistryBlock {

	public BlockBlank(String name) {
		super(name, Material.wood);
	}

}
