package com.overlast.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class OverServerSounds {
	
	/*
	 * Responsible for playing sounds server-side, so everyone hears them. Client-side is a different file.
	 * Only call playSound on the server, if you can't figure that out.
	 * 负责在服务器端播放声音，以便每个人都能听到。客户端是一个不同的文件。如果你不知道该怎么做，只在服务器上调用playSound。
	 */
	
	// Main method to play sounds.
	public static void playSound(String uuid, String soundMethod, double posX, double posY, double posZ) {
		
		// Basic variables.
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		int playerCount = server.getCurrentPlayerCount();
		String[] playerList = server.getOnlinePlayerNames();
		
		// Iterate through each player on the server.获取所有玩家数量
		for (int num = 0; num < playerCount; num++) {
			
			// Instance of the player.
			EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerList[num]);
			
			// Position of the sound.
			BlockPos pos = new BlockPos(posX, posY, posZ);
			
			// Is this the right player? If not, go to the next player.
			if (player.getCachedUniqueIdString().equals(uuid) && !player.world.isRemote) {
				
				// Now determine what sound needs to be played.
				if (soundMethod.equals("WaterSound")) { playWaterSound(player, pos); }
				else if (soundMethod.equals("PortalSound")) { playPortalSound(player, pos); }
				else if (soundMethod.equals("TowelDrySound")) { playTowelDrySound(player, pos); } 
				
			}
		}
	}
	
	// Plays cute splash sound when a player drinks water from a water source.
	private static void playWaterSound(Entity player, BlockPos pos) {
		
		player.world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SWIM, SoundCategory.NEUTRAL, 0.5f, 1.5f);
	}

	
	// Plays the portal sounds during resurrection of a player.
	private static void playPortalSound(Entity player, BlockPos pos) {
		
		player.world.playSound(null, pos, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.AMBIENT, 0.5f, 1.5f);
	}
	
	// Plays the towel drying sound.
	private static void playTowelDrySound(Entity player, BlockPos pos) {
		
		player.world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 1.0f, 0.5f);
	}
}