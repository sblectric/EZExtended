package com.ezextended.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.ezextended.blocks.BlockSecure;
import com.ezextended.tiles.TileEntitySecure;
import com.zerofall.ezstorage.block.StorageMultiblock;
import com.zerofall.ezstorage.util.BlockRef;
import com.zerofall.ezstorage.util.EZStorageUtils;

/** Utilities for working with EZStorage stuff */
public class EZUtils {
	
	/** Instead of searching for a core, find a secure block */
	public static TileEntitySecure findSecureBlock(BlockRef br, World world, Set<BlockRef> scanned) {
		if(scanned == null) scanned = new HashSet<BlockRef>();
		List<BlockRef> neighbors = EZStorageUtils.getNeighbors(br.pos.getX(), br.pos.getY(), br.pos.getZ(), world);
		for (BlockRef blockRef : neighbors) {
			if (blockRef.block instanceof StorageMultiblock) {
				if (blockRef.block instanceof BlockSecure) {
					return (TileEntitySecure)world.getTileEntity(blockRef.pos);
				} else {
					if (scanned.add(blockRef) == true) {
						TileEntitySecure entity = findSecureBlock(blockRef, world, scanned);
						if (entity != null) {
							return entity;
						}
					}
				}
			}
		}
		return null;
	}
	
	/** Get up to maxCount of nearby players */
	public static List<EntityPlayer> getNearbyPlayers(World world, BlockPos pos, double distance, int maxCount) {
		int count = 0;
		JointList<EntityPlayer> list = new JointList();
		for(EntityPlayer p : world.playerEntities) {
			if(count < maxCount && p.getDistanceSq(pos) < distance * distance) {
				list.add(p);
				count++;
			}
		}
		return list;
	}

}
