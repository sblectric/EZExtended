package com.ezextended.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.ezextended.tiles.TileEntitySecure;
import com.ezextended.tiles.TileEntitySecure.SecurePlayer;
import com.ezextended.util.JointList;

/** Send a message from server to client to sync security boxes */
public class MessageSecureSync implements IMessage {
	
	private BlockPos pos;
	private JointList<SecurePlayer> whitelist;
	
	public MessageSecureSync() {}
	
	public MessageSecureSync(BlockPos pos, List<SecurePlayer> list) {
		this.pos = pos;
		this.whitelist = new JointList().join(list);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		pos = BlockPos.fromLong(tag.getLong("pos"));
		int count = tag.getInteger("whitelistCount");
		whitelist = new JointList();
		for(int i = 0; i < count; i++) {
			whitelist.join(new SecurePlayer(tag.getUniqueId("whitelist" + i + "_id"), tag.getString("whitelist" + i + "_name")));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setLong("pos", pos.toLong());
		tag.setInteger("whitelistCount", whitelist.size());
		int i = 0;
		for(SecurePlayer p : whitelist) {
			tag.setUniqueId("whitelist" + i + "_id", p.id);
			tag.setString("whitelist" + (i++) + "_name", p.name);
		}
		ByteBufUtils.writeTag(buf, tag);
	}
	
	/** Update the tile entity whitelist */
	public static class Handler implements IMessageHandler<MessageSecureSync, IMessage> {
        @Override
        public IMessage onMessage(MessageSecureSync m, MessageContext ctx) {
        	try {
	        	TileEntitySecure tile = (TileEntitySecure)Minecraft.getMinecraft().theWorld.getTileEntity(m.pos);
	        	tile.setAllowedPlayers(m.whitelist);
        	} catch (Exception e) {
        		System.out.println("Message exception caught: " + e.getMessage());
        	}
            return null; // no response in this case
        }
	}

}
