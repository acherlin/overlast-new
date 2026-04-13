package com.overlast.cap;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import com.overlast.config.OverConfig;
import com.overlast.packet.HUDRenderPacket;
import com.overlast.packet.OverPackets;


/*
 * This is the event handler regarding capabilities and changes to individual stats.
 * Most of the actual code is stored in the modifier classes of each stat, and fired here.
 */
@Mod.EventBusSubscriber
public class CapEvents {

	// When a player logs on, give them their stats stored on the server.
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {

		EntityPlayer player = event.player;

		if (player instanceof EntityPlayerMP) {

			// Capabilities
			// Send data to client for rendering.
			IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(false,SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()),SRPSaveData.get(player.getEntityWorld()).getTotalKills(player.getEntityWorld().provider.getDimension()),false);
			OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);

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
				int[] hideUI =OverConfig.MECHANICS.hideUI;
				boolean hideBool=true;
				if(hideUI!=null) {
					for (int level : hideUI) {
						if(level== SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension())) {
							hideBool=false;
						}
					}
				}
                IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(hideBool,SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()),SRPSaveData.get(player.getEntityWorld()).getTotalKills(player.getEntityWorld().provider.getDimension()),!OverConfig.MECHANICS.showRequestDirtyClock||(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == SRPItems.itemEVClock || player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == SRPItems.itemEVClock));
                OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);
			}
		}
	}
	



}
