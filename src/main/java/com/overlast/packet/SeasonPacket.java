package com.overlast.packet;

import com.overlast.OverLast;
import com.overlast.packet.SeasonPacket.SeasonMessage;
import com.overlast.season.Season;
import com.overlast.season.modifier.BiomeTempController;
import com.overlast.util.OverWorldData;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SeasonPacket implements IMessageHandler<SeasonMessage, IMessage> {
	
	@Override
	public IMessage onMessage(SeasonMessage message, MessageContext ctx) {
		
		if (ctx.side.isClient()) {
			
			// Get message values
			int seasonInt = message.season;
			int daysIntoSeason = message.daysIntoSeason;
			
			BiomeTempController controller = new BiomeTempController();
			
			// Is daysIntoSeason -21? That's the code to restore biome temperatures.
			// daysIntoSeason是-21吗？那是恢复生物群落温度的代码。
			if (daysIntoSeason == -21) {
				
				controller.resetBiomeTemperatures();
			}
			
			else {
				
				// Get original biome temps if not gotten them already, for the client.
				controller.storeOriginalTemperatures();
				
				// Actual season
				Season season = OverWorldData.intToSeason(seasonInt);
				
				// Change temperatures
				controller.changeBiomeTemperatures(season, daysIntoSeason);

				OverLast.logger.info("Synced the client's season data with the server's.");
			}
		}
		
		return null;
	}
	
	public static class SeasonMessage implements IMessage {
		
		// Variables used in the packet
		private int season;
		private int daysIntoSeason;
		
		// Necessary constructor.
		public SeasonMessage() {}
		
		public SeasonMessage(int season, int daysIntoSeason) {
			
			this.season = season;
			this.daysIntoSeason = daysIntoSeason;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			
			this.season = buf.readInt();
			this.daysIntoSeason = buf.readInt();
			
		}

		@Override
		public void toBytes(ByteBuf buf) {
			
			buf.writeInt(season);
			buf.writeInt(daysIntoSeason);
		}
	}
}
