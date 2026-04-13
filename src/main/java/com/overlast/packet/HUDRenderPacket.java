package com.overlast.packet;

import com.overlast.gui.RenderHUD;
import com.overlast.packet.HUDRenderPacket.HUDRenderMessage;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HUDRenderPacket implements IMessageHandler<HUDRenderMessage, IMessage> {
	
	@Override
	public IMessage onMessage(HUDRenderMessage message, MessageContext ctx) {
		
		if (ctx.side.isClient()) {

			// Sanity
			boolean hide=message.hide;
			boolean showRequestDirtyClock=message.showRequestDirtyClock;
			float sanity = message.sanity;
			float maxSanity = message.maxSanity;

			int evolution =  message.evolution;
			int phase = message.phase;
			int statusUpdate =  message.statusUpdate;

			float newTemperature =  message.temperature;
			float newMaxTemperature =  message.maxTemperature;

			float courage =  message.courage;
			float maxCourage = message.maxCourage;

			float parasitic = message.parasitic;
			float maxParasitic = message.maxParasitic;

			RenderHUD.retrieveStats(hide,showRequestDirtyClock,sanity, maxSanity, evolution,phase,statusUpdate,newTemperature,newMaxTemperature,courage,maxCourage,parasitic,maxParasitic);
		}
		
		return null;
	}
	
	public static class HUDRenderMessage implements IMessage {
		
		// Variables sent to the client for rendering.
		private boolean hide;
		//SRP Item
		private boolean showRequestDirtyClock;

		// Sanity
		private float sanity;
		private float maxSanity;


		//SRP
		private int evolution;
		private int phase;
		//statusUpdate
		private int statusUpdate;

		// Temperature
		private float temperature;
		private float maxTemperature;

		//Courage
		private float courage;
		private float maxCourage;

		//Parasitic
		private float parasitic;
		private float maxParasitic;
		
		// Necessary constructor.
		public HUDRenderMessage() {}
		
		public HUDRenderMessage(boolean hide, boolean showRequestDirtyClock,float sanity, float maxSanity,int evolution,int phase,int statusUpdate,float temperature, float maxTemperature,float courage,float maxCourage,float parasitic,float maxParasitic) {
			this.hide = hide;
			this.showRequestDirtyClock=showRequestDirtyClock;
			this.sanity = sanity;
			this.maxSanity = maxSanity;
			this.evolution = evolution;
			this.phase = phase;
			this.statusUpdate = statusUpdate;
			this.temperature = temperature;
			this.maxTemperature = maxTemperature;
			this.courage=courage;
			this.maxCourage=maxCourage;
			this.parasitic = parasitic;
			this.maxParasitic = maxParasitic;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			this.hide=buf.readBoolean();
			this.showRequestDirtyClock=buf.readBoolean();
			this.sanity = buf.readFloat();
			this.maxSanity = buf.readFloat();
			this.evolution = buf.readInt();
			this.phase = buf.readInt();
			this.statusUpdate = buf.readInt();
			this.temperature = buf.readFloat();
			this.maxTemperature = buf.readFloat();
			this.courage = buf.readFloat();
			this.maxCourage = buf.readFloat();
			this.parasitic = buf.readFloat();
			this.maxParasitic = buf.readFloat();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBoolean(hide);
			buf.writeBoolean(showRequestDirtyClock);
			buf.writeFloat(sanity);
			buf.writeFloat(maxSanity);
			buf.writeInt(evolution);
			buf.writeInt(phase);
			buf.writeInt(statusUpdate);
			buf.writeFloat(temperature);
			buf.writeFloat(maxTemperature);
			buf.writeFloat(courage);
			buf.writeFloat(maxCourage);
			buf.writeFloat(parasitic);
			buf.writeFloat(maxParasitic);
		}
	}
}