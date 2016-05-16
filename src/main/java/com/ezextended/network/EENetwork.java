package com.ezextended.network;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.ezextended.ref.RefStrings;
import com.ezextended.tiles.TileEntitySecure.SecurePlayer;

public class EENetwork {
	
	public static SimpleNetworkWrapper net;
	public static int id;
	private static final double maxDistance = 1024;
	
	public static void mainRegistry() {
		id = 0;
		registerNetwork();
	}
	
	private static void registerNetwork() {
		net = NetworkRegistry.INSTANCE.newSimpleChannel(RefStrings.SHORTNAME + ".net");
		net.registerMessage(MessageSecureSync.Handler.class, MessageSecureSync.class, id++, Side.CLIENT);
		net.registerMessage(MessageSecurePlayer.Handler.class, MessageSecurePlayer.class, id++, Side.SERVER);
	}
	
	/** Send a message to sync a security box to the client(s) */
	public static void sendSecureSyncMsg(World world, BlockPos pos, List<SecurePlayer> whitelist) {
		net.sendToAllAround(new MessageSecureSync(pos, whitelist), 
				new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), maxDistance));
	}
	
	/** Send an addition or removal request from client to server */
	public static void sendSecurePlayerMsg(World world, BlockPos pos, SecurePlayer addition, boolean add) {
		net.sendToServer(new MessageSecurePlayer(pos, addition, world.provider.getDimension(), add));
	}

}
