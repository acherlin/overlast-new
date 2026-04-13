package com.overlast.packet;

import com.overlast.OverLast;
import com.overlast.config.OverConfig;
import com.overlast.packet.ConfigPacket.ConfigMessage;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ConfigPacket implements IMessageHandler<ConfigMessage, IMessage> {
	
	@Override
	public IMessage onMessage(ConfigMessage message, MessageContext ctx) {
		
		if (ctx.side.isClient()) {
			
			// Sync server values to client.
			boolean showRequestDirtyClock = message.showRequestDirtyClock;
			boolean enableSanity = message.enableSanity;
			double sanityScale = message.sanityScale;
			double naturalEvolutionScale = message.naturalEvolutionScale;

			boolean enableTemperature = message.enableTemperature;
			double temperatureScale = message.temperatureScale;
			boolean enableCourage = message.enableCourage;
			double courageScale =message.courageScale;
			boolean enableParasitic = message.enableParasitic;
			double parasiticScale = message.parasiticScale;

			boolean enableRadio = message.enableRadio;

			boolean aenableSeasons = message.aenableSeasons;

			boolean enableSummerParasiteEffect= message.enableSummerParasiteEffect;
			boolean enableSummerPlayerEffect= message.enableSummerPlayerEffect;
			boolean enableWinterParasiteEffect= message.enableWinterParasiteEffect;
			boolean enableWinterPlayerEffect= message.enableWinterPlayerEffect;

			boolean enableLightEffect = message.enableLightEffect;
			boolean enableDailyBOSS = message.enableDailyBOSS;

			int winterLength = message.winterLength;
			int springLength = message.springLength;
			int summerLength = message.summerLength;
			int autumnLength = message.autumnLength;
			int defaultSeason = message.defaultSeason;

			OverConfig.MECHANICS.showRequestDirtyClock=showRequestDirtyClock;
			OverConfig.MECHANICS.enableSanity = enableSanity;
			OverConfig.MECHANICS.sanityScale = sanityScale;
			OverConfig.MECHANICS.naturalEvolutionScale = naturalEvolutionScale;
			OverConfig.MECHANICS.enableTemperature = enableTemperature;
			OverConfig.MECHANICS.temperatureScale = temperatureScale;
			OverConfig.MECHANICS.enableCourage = enableCourage;
			OverConfig.MECHANICS.courageScale = courageScale;
			OverConfig.MECHANICS.enableParasitic = enableParasitic;
			OverConfig.MECHANICS.parasiticScale = parasiticScale;
			OverConfig.MECHANICS.enableRadio = enableRadio;

			OverConfig.SEASONS.aenableSeasons = aenableSeasons;
			OverConfig.SEASONS.winterLength = winterLength;
			OverConfig.SEASONS.springLength = springLength;
			OverConfig.SEASONS.summerLength = summerLength;
			OverConfig.SEASONS.autumnLength = autumnLength;
			OverConfig.SEASONS.defaultSeason = defaultSeason;

			OverConfig.SEASONS.enableSummerParasiteEffect = enableSummerParasiteEffect;
			OverConfig.SEASONS.enableSummerPlayerEffect = enableSummerPlayerEffect;
			OverConfig.SEASONS.enableWinterParasiteEffect = enableWinterParasiteEffect;
			OverConfig.SEASONS.enableWinterPlayerEffect = enableWinterPlayerEffect;

			OverConfig.MECHANICS.enableLightEffect = enableLightEffect;
			OverConfig.MECHANICS.enableDailyBOSS = enableDailyBOSS;

			
			OverLast.logger.info("Synced the client's config values with the server's.");

		}
		
		return null;
	}
	
	public static class ConfigMessage implements IMessage {
		
		// Variables used in the packet
		private boolean showRequestDirtyClock;
		private boolean enableSanity;
		private double sanityScale;
		private double naturalEvolutionScale;
		private boolean enableTemperature;
		private double temperatureScale;
		private boolean enableCourage;
		private double courageScale;
		private boolean enableParasitic;
		private double parasiticScale;
		private boolean enableRadio;

		private boolean aenableSeasons;
		private int winterLength;
		private int springLength;
		private int summerLength;
		private int autumnLength;
		private int defaultSeason;
		private boolean enableSummerParasiteEffect;
		private boolean enableSummerPlayerEffect;
		private boolean enableWinterParasiteEffect;
		private boolean enableWinterPlayerEffect;

		private boolean enableLightEffect;
		private boolean enableDailyBOSS;
		
		// Necessary constructor.
		public ConfigMessage() {}
		
		public ConfigMessage(boolean showRequestDirtyClock,boolean enableSanity, double sanityScale,double naturalEvolutionScale,boolean enableTemperature,double temperatureScale,boolean enableCourage,double courageScale,boolean enableParasitic,double parasiticScale,boolean enableRadio,boolean aenableSeasons, int winterLength, int springLength, int summerLength, int autumnLength, int defaultSeason,
							 boolean enableSummerParasiteEffect,boolean enableSummerPlayerEffect,boolean enableWinterParasiteEffect,boolean enableWinterPlayerEffect,boolean enableLightEffect,boolean enableDailyBOSS) {
			this.showRequestDirtyClock=showRequestDirtyClock;
			this.enableSanity = enableSanity;
			this.sanityScale = sanityScale;
			this.naturalEvolutionScale=naturalEvolutionScale;
			this.enableTemperature = enableTemperature;
			this.temperatureScale = temperatureScale;
			this.enableCourage = enableCourage;
			this.courageScale =courageScale;
			this.enableParasitic = enableParasitic;
			this.parasiticScale = parasiticScale;
			this.enableRadio=enableRadio;

			this.aenableSeasons = aenableSeasons;
			this.winterLength = winterLength;
			this.springLength = springLength;
			this.summerLength = summerLength;
			this.autumnLength = autumnLength;
			this.defaultSeason=defaultSeason;
			this.enableSummerParasiteEffect=enableSummerParasiteEffect;
			this.enableSummerPlayerEffect=enableSummerPlayerEffect;
			this.enableWinterParasiteEffect=enableWinterParasiteEffect;
			this.enableWinterPlayerEffect=enableWinterPlayerEffect;

			this.enableLightEffect=enableLightEffect;
			this.enableDailyBOSS=enableDailyBOSS;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			this.showRequestDirtyClock=buf.readBoolean();
			this.enableSanity = buf.readBoolean();
			this.sanityScale = buf.readDouble();
			this.naturalEvolutionScale = buf.readDouble();
			this.enableTemperature = buf.readBoolean();
			this.temperatureScale = buf.readDouble();
			this.enableCourage=buf.readBoolean();
			this.courageScale = buf.readDouble();
			this.enableParasitic=buf.readBoolean();
			this.parasiticScale=buf.readDouble();
			this.enableRadio=buf.readBoolean();

			this.aenableSeasons = buf.readBoolean();
			this.winterLength = buf.readInt();
			this.springLength = buf.readInt();
			this.summerLength = buf.readInt();
			this.autumnLength = buf.readInt();
			this.defaultSeason = buf.readInt();
			this.enableSummerParasiteEffect=buf.readBoolean();
			this.enableSummerPlayerEffect=buf.readBoolean();
			this.enableWinterParasiteEffect=buf.readBoolean();
			this.enableWinterPlayerEffect=buf.readBoolean();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBoolean(showRequestDirtyClock);
			buf.writeBoolean(enableSanity);
			buf.writeDouble(sanityScale);
			buf.writeDouble(naturalEvolutionScale);
			buf.writeBoolean(enableTemperature);
			buf.writeDouble(temperatureScale);
			buf.writeBoolean(enableCourage);
			buf.writeDouble(courageScale);
			buf.writeBoolean(enableParasitic);
			buf.writeDouble(parasiticScale);
			buf.writeBoolean(enableRadio);

			buf.writeBoolean(aenableSeasons);
			buf.writeInt(winterLength);
			buf.writeInt(springLength);
			buf.writeInt(summerLength);
			buf.writeInt(autumnLength);
			buf.writeInt(defaultSeason);
			buf.writeBoolean(enableSummerParasiteEffect);
			buf.writeBoolean(enableSummerPlayerEffect);
			buf.writeBoolean(enableWinterParasiteEffect);
			buf.writeBoolean(enableWinterPlayerEffect);
		}
	}
}
