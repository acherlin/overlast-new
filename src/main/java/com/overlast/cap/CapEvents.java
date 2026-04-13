package com.overlast.cap;

import com.creativemd.playerrevive.api.event.PlayerKilledEvent;
import com.creativemd.playerrevive.api.event.PlayerRevivedEvent;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.overlast.cap.courage.CourageModifier;
import com.overlast.cap.courage.CourageProvider;
import com.overlast.cap.courage.ICourage;
import com.overlast.cap.parasitic.IParasitic;
import com.overlast.cap.parasitic.ParasiticProvider;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityModifier;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.cap.temperature.ITemperature;
import com.overlast.cap.temperature.TemperatureModifier;
import com.overlast.cap.temperature.TemperatureProvider;
import com.overlast.config.OverConfig;
import com.overlast.lib.ModItems;
import com.overlast.lib.ModMobEffects;
import com.overlast.packet.HUDRenderPacket;
import com.overlast.packet.OverPackets;
import com.overlast.season.WorldSeason;
import com.overlast.util.OverUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.List;

/*
 * This is the event handler regarding capabilities and changes to individual stats.
 * Most of the actual code is stored in the modifier classes of each stat, and fired here.
 */
@Mod.EventBusSubscriber
public class CapEvents {

	// Modifiers
	private final SanityModifier sanityMod = new SanityModifier();
	private int evoTimer = 0;
	private final TemperatureModifier tempMod = new TemperatureModifier();
	private final CourageModifier courMod = new CourageModifier();

	// When a player logs on, give them their stats stored on the server.
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {

		EntityPlayer player = event.player;
		int phase = OverUtil.UTIL.getPhase(player.getEntityWorld());
		if (player instanceof EntityPlayerMP) {

			// Capabilities
			ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);

			ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAP, null);

			ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP,null);

			IParasitic parasitic = player.getCapability(ParasiticProvider.PARASITIC_CAP,null);


			// Send data to client for rendering.
			IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(true,false,sanity.getSanity(), sanity.getMaxSanity(), OverUtil.UTIL.getTotalPoint(player.getEntityWorld()),phase, WorldSeason.getFinalTime(),temperature.getTemperature(), temperature.getMaxTemperature(),courage.getCourage(),courage.getMaxCourage(),parasitic.getParasitic(),parasitic.getMaxParasitic());
			OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);

		}

		player.sendMessage(new TextComponentTranslation("message.evopoint.login",phase,(int)(OverUtil.UTIL.getAddEvoPoint(phase)*OverConfig.MECHANICS.naturalEvolutionScale)));

		if (phase == 8) {
			player.sendMessage(new TextComponentTranslation("message.evopoint.eight"));
		}


	}

	// When an entity's drops are dropped (so usually when one dies). 用于掉落本源触须
	@SubscribeEvent
	public void onDropsDropped(LivingDropsEvent event) {

		// The entity that was killed.
		Entity entityKilled = event.getEntity();

		// Server-side
		if (!entityKilled.world.isRemote) {
			// A list of their drops.
			List<EntityItem> drops = event.getDrops();
			// Damage source.
			DamageSource damageSource = event.getSource();
			if (((damageSource.getDamageType().equals("bullet")&&damageSource.getTrueSource() instanceof EntityPlayer)||damageSource.getDamageType().equals("player")) && !entityKilled.world.isRemote) {
				// Instance of player
				EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
				if (entityKilled instanceof EntityOronco||entityKilled instanceof EntityTerla) {
					// Drop some item.
					drops.add(new EntityItem(player.world, entityKilled.posX, entityKilled.posY, entityKilled.posZ, new ItemStack(ModItems.Boss_Chip, 1)));
				}
			}
		}
	}

	// When an entity is updated. So, all the time.
	// This also deals with packets to the client.
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {
		// Only continue if it's a player.
		if (event.getEntity() instanceof EntityPlayer) {

			// Instance of player.
			EntityPlayer player = (EntityPlayer) event.getEntity();

			// Server-side
			if (!player.world.isRemote) {

				// Whether the player's stats should be changed. Not in creative and spectator mode.
				boolean shouldStatsChange = true;

				if (player.isCreative() || player.isSpectator()) {

					shouldStatsChange = false;
				}

				if (OverConfig.MECHANICS.enableCourage) {

					courMod.onPlayerUpdate(player);

				}

				if(OverConfig.MECHANICS.naturalEvolutionScale>0) {
					if(evoTimer<1200){
						evoTimer++;
					} else {
						evoTimer = 0;
						//常规触发，一分钟一次
						//自然演化，单次
						OverUtil.UTIL.addEvoPoint(player.getEntityWorld(), OverUtil.UTIL.getAddEvoPoint(OverUtil.UTIL.getPhase(player.getEntityWorld())));
					}
				}

				// Now fire every method that should be fired here, passing the player as a parameter.
				// If in creative mode (or if the mechanic is disabled in the config), don't fire these at all.
				if (shouldStatsChange) {

					if (OverConfig.MECHANICS.enableSanity) {

						if((player.getActivePotionEffect(ModMobEffects.FROSTY) ==null)) {
							sanityMod.onPlayerUpdate(player);
						}

						// Fire this if the player is sleeping
						if (player.isPlayerSleeping()) {

							sanityMod.onPlayerSleepInBed(player);
						}
					}
					if (OverConfig.MECHANICS.enableTemperature) {

						tempMod.onPlayerUpdate(player);
					}

				}

				// Send capability data to clients for rendering 发包到客户端
				ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
				ITemperature temperature = player.getCapability(TemperatureProvider.TEMPERATURE_CAP, null);
				ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP,null);
				IParasitic parasitic = player.getCapability(ParasiticProvider.PARASITIC_CAP,null);

				//SRPWorldData data = SRPWorldData.get(player.getEntityWorld());
				//data.setTotalKills(100, true, player.getEntityWorld());

				int[] hideUI =OverConfig.CUSTOM.hideUI;
				boolean hideBool=true;
				if(hideUI!=null) {
					for (int level : hideUI) {
						if(level== SRPSaveData.get(player.getEntityWorld(),0).getEvolutionPhase(player.getEntityWorld().provider.getDimension())) {
							hideBool=false;
						}
					}
				}

				IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(hideBool,!OverConfig.MECHANICS.showRequestDirtyClock||(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == SRPItems.itemEVClock || player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == SRPItems.itemEVClock),
						sanity.getSanity(), sanity.getMaxSanity() ,OverUtil.UTIL.getTotalPoint(player.getEntityWorld()),OverUtil.UTIL.getPhase(player.getEntityWorld()), WorldSeason.getFinalTime(),temperature.getTemperature(), temperature.getMaxTemperature(),courage.getCourage(),courage.getMaxCourage(),parasitic.getParasitic(),parasitic.getMaxParasitic());
				OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);


			}
		}
	}


	// When a player (kind of) finishes using an item. Technically one tick before it's actually consumed.
	//当玩家（某种程度上）使用完一个物品
	@SubscribeEvent
	public void onPlayerUseItem(LivingEntityUseItemEvent.Tick event) {

		if (event.getEntity() instanceof EntityPlayer) {

			// Instance of player.
			EntityPlayer player = (EntityPlayer) event.getEntity();

			// Server-side
			if (!player.world.isRemote && event.getDuration() == 1) {

				// Instance of item.
				ItemStack itemUsed = event.getItem();

				// Fire methods.
				if (!player.isCreative()) {

					if (OverConfig.MECHANICS.enableSanity) {
						sanityMod.onPlayerConsumeItem(player, itemUsed);
					}
					if (OverConfig.MECHANICS.enableTemperature) {

						tempMod.onPlayerConsumeItem(player, itemUsed);
					}
				}
			}

		}
	}

	// When a player wakes up from bed.
	@SubscribeEvent
	public void onPlayerWakeUp(PlayerWakeUpEvent event) {

		// Instance of player.
		EntityPlayer player = event.getEntityPlayer();

		if (!player.world.isRemote) {

			// Fire methods
			sanityMod.onPlayerWakeUp(player);
		}
	}

	//玩家击杀怪物事件
	@SubscribeEvent
	public void onPlayerKillSRP(LivingDeathEvent event) {
		Entity entityKilled = event.getEntity();
		// Server-side
		if (!entityKilled.world.isRemote) {
			// Damage source.
			DamageSource damageSource = event.getSource();
			// Fire methods.
			if(OverConfig.MECHANICS.enableCourage) {
				courMod.onPlayerKillSRP(entityKilled,damageSource);
			}
		}
	}

	//玩家受伤事件
	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event) {
		Entity entityHurted = event.getEntity();
		// Server-side
		if (!entityHurted.world.isRemote) {
			// Damage source.
			DamageSource damageSource = event.getSource();
			// Fire methods.
			if(OverConfig.MECHANICS.enableCourage) {
				courMod.onPlayerHurt(entityHurted,damageSource,event);
			}
		}
	}


	// When a player respawns. 玩家重生事件
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		// Instance of player.
		EntityPlayer player = event.player;
		// Server-side
		if (!player.world.isRemote) {
			if(OverConfig.MECHANICS.enableSanity) {
				ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);

				if (OverUtil.UTIL.getPhase(player.getEntityWorld()) >= 3 && sanity.getSanity() <= 30) {
					player.sendMessage(new TextComponentTranslation("message.lowsan.respawn1"));
					player.sendMessage(new TextComponentTranslation("message.lowsan.respawn2", OverUtil.UTIL.getAddEvoPoint(OverUtil.UTIL.getPhase(player.getEntityWorld())) * 20));
					OverUtil.UTIL.addEvoPoint(player.getEntityWorld(),OverUtil.UTIL.getAddEvoPoint(OverUtil.UTIL.getPhase(player.getEntityWorld())) * 20);
				}
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 4, false, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 1, false, false));

				if (sanity.getSanity() <= 50) {
					sanity.set(35.0f);
				} else if (sanity.getSanity() >= 80) {
					sanity.set(80.0f);
				} else if (sanity.getSanity() >= 50) {
					sanity.set(50.0f);
				}
				player.getFoodStats().setFoodLevel(8);
			}
			if(WorldSeason.getWorldState()==3) {
				if(player.getEntityWorld().getGameRules().getBoolean("keepInventory")) {
					player.addPotionEffect(new PotionEffect(ModMobEffects.EVIL, 600, 0, false, false));
				} else {
					player.addPotionEffect(new PotionEffect(ModMobEffects.EVIL, 200, 0, false, false));
				}
				player.sendMessage(new TextComponentTranslation("message.finalBattle.report5"));
			}
		}
	}


	// When a player Revived. 玩家救援完成事件
	@SubscribeEvent
	@Optional.Method(modid = "playerrevive")
	public void onPlayerRevived(PlayerRevivedEvent event) {
		if(event.getEntityLiving()instanceof EntityPlayer && !event.getEntityLiving().world.isRemote&&OverConfig.MECHANICS.enableSanity){
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			AxisAlignedBB boundingBoxPlayers = player.getEntityBoundingBox().grow(10, 5, 10);
			List nearbyPlayers = player.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
			for (int numPlayers = 0; numPlayers < nearbyPlayers.size(); numPlayers++) {
				// Chosen player
				EntityPlayerMP otherPlayer = (EntityPlayerMP) nearbyPlayers.get(numPlayers);
				ISanity sanity = otherPlayer.getCapability(SanityProvider.SANITY_CAP, null);

				sanity.increase(3.0f);
			}
		}
	}


	// When a player died. 玩家死亡事件
	@SubscribeEvent
	@Optional.Method(modid = "playerrevive")
	public void onPlayerDied(PlayerKilledEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote&&OverConfig.MECHANICS.enableSanity) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			AxisAlignedBB boundingBoxPlayers = player.getEntityBoundingBox().grow(48, 5, 48);
			List nearbyPlayers = player.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
			// And for players.
			for (int numPlayers = 0; numPlayers < nearbyPlayers.size(); numPlayers++) {

				// Chosen player
				EntityPlayerMP otherPlayer = (EntityPlayerMP) nearbyPlayers.get(numPlayers);
				ISanity sanity = otherPlayer.getCapability(SanityProvider.SANITY_CAP, null);
				sanity.decrease(10.0f);
			}
		}
	}

	// When a player died. 玩家正常死亡事件
	@SubscribeEvent
	public void onPlayerNormalDied(LivingDeathEvent event) {
		if (Loader.isModLoaded("playerrevive")) {
			return;
		}
		if (event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote&&OverConfig.MECHANICS.enableSanity) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			AxisAlignedBB boundingBoxPlayers = player.getEntityBoundingBox().grow(48, 5, 48);
			List nearbyPlayers = player.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
			// And for players.
			for (int numPlayers = 0; numPlayers < nearbyPlayers.size(); numPlayers++) {
				// Chosen player
				EntityPlayerMP otherPlayer = (EntityPlayerMP) nearbyPlayers.get(numPlayers);
				ISanity sanity = otherPlayer.getCapability(SanityProvider.SANITY_CAP, null);
				sanity.decrease(10.0f);
			}
		}
	}

}