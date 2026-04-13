package com.overlast.cap;

import com.overlast.OverLast;
import com.overlast.cap.courage.CourageProvider;
import com.overlast.cap.parasitic.ParasiticProvider;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.cap.temperature.TemperatureProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {
	
	/*
	 * This event handler attaches the capabilities onto the player.
	 */
	
	// Resource Locations
	private static final ResourceLocation SANITY_CAP = new ResourceLocation(OverLast.MOD_ID, "sanity");
	private static final ResourceLocation TEMPERATURE_CAP = new ResourceLocation(OverLast.MOD_ID, "temperature");
	private static final ResourceLocation PARASITIC_CAP = new ResourceLocation(OverLast.MOD_ID, "parasitic");
	private static final ResourceLocation COURAGE_CAP = new ResourceLocation(OverLast.MOD_ID, "courage");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		
		if(!(event.getObject() instanceof EntityPlayer)) return;

		event.addCapability(SANITY_CAP, new SanityProvider());
		event.addCapability(TEMPERATURE_CAP, new TemperatureProvider());
		event.addCapability(PARASITIC_CAP, new ParasiticProvider());
		event.addCapability(COURAGE_CAP, new CourageProvider());
	}
}