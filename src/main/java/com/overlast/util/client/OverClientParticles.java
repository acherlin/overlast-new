package com.overlast.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;

public class OverClientParticles {
	
	/*
	 * Responsible for spawning particles CLIENT-SIDE.
	 * Mainly for insanity and ambiance.
	 * 负责在客户端产生粒子效果
	 * 主要是为了疯狂和氛围
	 */
	
	// Main method to spawn particles.
	public static void summonParticle(String uuid, String particleMethod, double posX, double posY, double posZ) {
		
		// Instance of Minecraft.
		Minecraft mc = Minecraft.getMinecraft();
		
		// Instance of the player.
		EntityPlayer player = mc.player;
		
		// The dimension/world the player is in.
		WorldClient world = mc.world;
		
		// Is this the correct player? 是正确的玩家吗
		if (player.getCachedUniqueIdString().equals(uuid) && player.world.isRemote) {
			
			// Determine what particles need to be summoned/spawned/rendered/i used a million ways to describe that process of making particles appear.
			//确定哪些粒子需要被召唤/产生/渲染/我用了一百万种方法来描述那个使粒子出现的过程。
			if (particleMethod.equals("EndermanParticles")) { spawnEndermanParticles(posX, posY, posZ, world); }
			else if (particleMethod.equals("ExplosionParticles")) { spawnExplosionParticles(posX, posY, posZ, world); }
			else if (particleMethod.equals("CreepyMistParticles")) { spawnCreepyMistParticles(posX, posY, posZ, world); }
			else if (particleMethod.equals("GuardianParticles")) { spawnGuardianParticles(posX, posY, posZ, world); }
			else if (particleMethod.equals("SmokeParticles")) { spawnSmokeParticles(posX, posY, posZ, world); }
		}
	}
	// ===============================================================================
	//                Below particles are mainly for INSANITY.
	// ===============================================================================
	
	// Enderman particles. Purple and spooky.
	private static void spawnEndermanParticles(double posX, double posY, double posZ, WorldClient world) {
		
		for (int count = 0; count < 20; count++) {
			
			world.spawnParticle(EnumParticleTypes.PORTAL, true, posX, posY, posZ, 5d, 5d, 5d, null);
		}
	}
	
	// Explosion particles. BOOM
	private static void spawnExplosionParticles(double posX, double posY, double posZ, WorldClient world) {
		
		for (int count = 0; count < 5; count++) {
			
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, true, posX, posY, posZ, 0.5d, 0.5d, 0.5d, null);
		}
	}
	
	// Creepy mist particles... huh?
	private static void spawnCreepyMistParticles(double posX, double posY, double posZ, WorldClient world) {
		
		for (int count = 0; count < 200; count++) {
			
			world.spawnParticle(EnumParticleTypes.CLOUD, true, posX, posY, posZ, 0.5d, 0.6d, 0.5d, null);
		}
	}
	
	// Dumb guardian particle that gets right in your face.
	private static void spawnGuardianParticles(double posX, double posY, double posZ, WorldClient world) {
		
		for (int count = 0; count < 1; count++) {
			
			world.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, true, posX, posY, posZ, 0.5d, 0.5d, 0.5d, null);
		}
	}
	
	// Smoke particles
	private static void spawnSmokeParticles(double posX, double posY, double posZ, WorldClient world) {
		
		for (int count = 0; count < 5; count++) {
			
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, true, posX, posY, posZ, 0.5d, 0.5d, 0.5d, null);
		}
	}
}