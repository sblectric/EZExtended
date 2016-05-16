package com.ezextended.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.ezextended.tiles.TileEntitySecure;
import com.ezextended.tiles.TileEntitySecure.SecurePlayer;

/** Send a single SecurePlayer instance from client to server for addition or removal from a security box */
public class MessageSecurePlayer implements IMessage {
	
	private BlockPos pos;
	private SecurePlayer player;
	private int dimension;
	private boolean add;
	
	public MessageSecurePlayer() {}
	
	public MessageSecurePlayer(BlockPos pos, SecurePlayer player, int dimension, boolean add) {
		this.pos = pos;
		this.player = player;
		this.dimension = dimension;
		this.add = add;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		pos = BlockPos.fromLong(tag.getLong("pos"));
		player = new SecurePlayer(tag.getUniqueId("secureID"), tag.getString("secureName"));
		dimension = tag.getInteger("dim");
		add = tag.getBoolean("add");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("pos", pos.toLong());
		tag.setUniqueId("secureID", player.id);
		tag.setString("secureName", player.name);
		tag.setInteger("dim", dimension);
		tag.setBoolean("add", add);
		ByteBufUtils.writeTag(buf, tag);
	}
	
	/** Receive this SecurePlayer instance */
	public static class Handler implements IMessageHandler<MessageSecurePlayer, IMessage> {
        @Override
        public IMessage onMessage(MessageSecurePlayer m, MessageContext ctx) {        	
        	// make sure the provided player is valid
        	PlayerList list = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
        	for(EntityPlayerMP p : list.getPlayerList()) {
        		if(p.dimension == m.dimension && p.getUniqueID().toString().equals(m.player.id.toString())) {
        			TileEntitySecure tile = (TileEntitySecure)p.worldObj.getTileEntity(m.pos);
        			// add or remove from the list
        			if(m.add) {
        				tile.addAllowedPlayer(p);
        			} else {
        				tile.removeAllowedPlayer(p);
        			}
        			tile.markDirty();
        			EENetwork.sendSecureSyncMsg(p.worldObj, m.pos, tile.getAllowedPlayers()); // now resync with clients
        		}
        	}
        	
            return null; // no specific replies
        }
	}

}
