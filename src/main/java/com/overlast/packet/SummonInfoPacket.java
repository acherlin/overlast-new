package com.overlast.packet;

import com.overlast.config.OverConfig;
import com.overlast.packet.SummonInfoPacket.SummonInfoMessage;
import com.overlast.util.OverServerParticles;
import com.overlast.util.OverServerSounds;
import com.overlast.util.client.OverClientParticles;
import com.overlast.util.client.OverClientSounds;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/*
 * Used for summoning particles and playing sounds.
 * 用于生成粒子和播放音效
 */
public class SummonInfoPacket implements IMessageHandler<SummonInfoMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SummonInfoMessage message, MessageContext ctx) {
		
		if (ctx.side.isServer()) {
			//大概率导致bug
			String uuid = message.uuid;
			String soundPicker = message.soundPicker;
			String particlePicker = message.particlePicker;
			double posX = message.posX;
			double posY = message.posY;
			double posZ = message.posZ;
			if(OverConfig.CLIENT.enableEffect) {
				OverServerParticles.summonParticle(uuid, particlePicker, posX, posY, posZ);
			}
			if(OverConfig.CLIENT.enableSound){
				OverServerSounds.playSound(uuid, soundPicker, posX, posY, posZ);
			}
		}
		else {
			
			String uuid = message.uuid;
			String soundPicker = message.soundPicker;
			String particlePicker = message.particlePicker;
			double posX = message.posX;
			double posY = message.posY;
			double posZ = message.posZ;
			if(OverConfig.CLIENT.enableEffect) {
				OverClientParticles.summonParticle(uuid, particlePicker, posX, posY, posZ);
			}
			if(OverConfig.CLIENT.enableSound) {
				OverClientSounds.playSound(uuid, soundPicker, posX, posY, posZ);
			}
		}
		
		return null;
	}
	
	public static class SummonInfoMessage implements IMessage {
		
		// Variables used in the packet.
		private String uuid;
		private String soundPicker;
		private String particlePicker;
		private double posX;
		private double posY;
		private double posZ;
		
		// Necessary constructor.
		public SummonInfoMessage() {}
		
		public SummonInfoMessage(String uuid, String soundPicker, String particlePicker, double posX, double posY, double posZ) {
			
			this.uuid = uuid;
			this.soundPicker = soundPicker;
			this.particlePicker = particlePicker;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			
			this.uuid = ByteBufUtils.readUTF8String(buf);
			this.soundPicker = ByteBufUtils.readUTF8String(buf);
			this.particlePicker = ByteBufUtils.readUTF8String(buf);
			this.posX = buf.readDouble();
			this.posY = buf.readDouble();
			this.posZ = buf.readDouble();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			
			ByteBufUtils.writeUTF8String(buf, uuid);
			ByteBufUtils.writeUTF8String(buf, soundPicker);
			ByteBufUtils.writeUTF8String(buf, particlePicker);
			buf.writeDouble(posX);
			buf.writeDouble(posY);
			buf.writeDouble(posZ);
		}
	}
}