package com.overlast.util;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

/*
 * Responsible for affecting players with status effects, if the methods trying to do them are client-side.
 * This does them server-side. In case this is not obvious, ONLY CALL THIS ON THE SERVER. Unless you want a crash.
 */
/*
 * 负责影响玩家的状态效果，如果试图做这些的方法是客户端的。
 * 这是在服务器端进行的。如果这一点不明显的话，只在服务器上调用这个。除非你想发生崩溃。
 */
public class OverServerEffects {

	// Main method.
	public static void affectPlayer(String uuid, String effect, int duration, int amplifier, boolean isAmbient, boolean showParticles) {
		
		// Basic variables. 基本变量。
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		int playerCount = server.getCurrentPlayerCount();
		String[] playerList = server.getOnlinePlayerNames();
		
		// Iterate through each player. 遍历每个玩家。
		for (int num = 0; num < playerCount; num++) {
			
			// Instance of player. 玩家的实例。
			EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerList[num]);
			
			// Is this the right player? check UUIDs. 这是正确的玩家吗？检查UUIDs。
			if (!player.isCreative() && player.getCachedUniqueIdString().equals(uuid) && !player.world.isRemote) {
				
				// Decipher potion effect string and affect the player accordingly.
				// Poison
				// 比较药水效果字符串，并相应地影响玩家。
				// 中毒
				if (effect.equals("poison")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.POISON, duration, amplifier, isAmbient, showParticles));
				}
				
				// Mining fatigue 挖掘疲劳
				else if (effect.equals("mining_fatigue")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, duration, amplifier, isAmbient, showParticles));
				}
				
				// Nausea 反胃
				else if (effect.equals("nausea")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, amplifier, isAmbient, showParticles));
				}
				
				// Instant damage 瞬间伤害
				else if (effect.equals("instant_damage")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, duration, amplifier, isAmbient, showParticles));
				}
				
				// Instant health 瞬间治疗
				else if (effect.equals("instant_health")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, duration, amplifier, isAmbient, showParticles));
				}
				
				// Slowness 缓慢
				else if (effect.equals("slowness")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, amplifier, isAmbient, showParticles));
				}
				
				// Weakness 虚弱
				else if (effect.equals("weakness")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration, amplifier, isAmbient, showParticles));
				}
				
				// Hunger 饥饿
				else if (effect.equals("hunger")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, duration, amplifier, isAmbient, showParticles));
				}
				
				// Invisibility 隐身
				else if (effect.equals("invisibility")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, duration, amplifier, isAmbient, showParticles));
				}
				
				// Resistance 抗性
				else if (effect.equals("resistance")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, duration, amplifier, isAmbient, showParticles));
				}
				
				// Saturation 饱食
				else if (effect.equals("saturation")) {
					
					player.addPotionEffect(new PotionEffect(MobEffects.SATURATION, duration, amplifier, isAmbient, showParticles));
				}

				//SRPPotions.COTH_E 寄群之唤
				else if (effect.equals("coth")) {

					player.addPotionEffect(new PotionEffect(SRPPotions.COTH_E, duration, amplifier, isAmbient, showParticles));
				}
				//SRPPotions.COTH_E 恐惧
				else if (effect.equals("fear")) {

					player.addPotionEffect(new PotionEffect(SRPPotions.FEAR_E, duration, amplifier, isAmbient, showParticles));
				}
				//SRPPotions.COTH_E 病毒
				else if (effect.equals("vira")) {

					player.addPotionEffect(new PotionEffect(SRPPotions.VIRA_E, duration, amplifier, isAmbient, showParticles));
				}
				//SRPPotions.BLEED_E 流血
				else if (effect.equals("bleed")) {

					player.addPotionEffect(new PotionEffect(SRPPotions.BLEED_E, duration, amplifier, isAmbient, showParticles));
				}
				//SRPPotions.CORRO_E 腐蚀
				else if (effect.equals("corro")) {

					player.addPotionEffect(new PotionEffect(SRPPotions.CORRO_E, duration, amplifier, isAmbient, showParticles));
				}
			}
		}		
	}
}