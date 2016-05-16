package com.ezextended.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.ezextended.gui.client.*;
import com.ezextended.gui.server.*;
import com.ezextended.tiles.*;

/** The mod's gui handler */
public class EEGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case 0:
			TileEntitySecure tile = (TileEntitySecure)world.getTileEntity(pos);
			return new ContainerSecure(player.inventory, tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case 0:
			TileEntitySecure tile = (TileEntitySecure)world.getTileEntity(pos);
			return new GuiSecure(player.inventory, tile, pos);
		}
		return null;
	}

}
