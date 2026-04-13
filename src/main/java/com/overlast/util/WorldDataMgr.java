package com.overlast.util;

import com.overlast.OverLast;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WorldDataMgr {
	
	/*
	 * Manage your world data today!
	 * 今天就管理你的世界数据
	 */
	
	public static void loadFromDisk() {
		
		// Instance of the server.
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		// Instance of the server world.
		World world = server.getEntityWorld();
		
		// Load stuff from world data file
		OverWorldData data = OverWorldData.load(world);
		
		// Notification
		OverLast.logger.info("Loaded world data. Current season is " + data.getSeasonFromData() + " and we are " + data.daysIntoSeason + " days into this season.");
		
		// Now send this data to all around the mod
		// Seasons
		// 现在把这个数据发送到所有的mod周围。
		// 季节
		WorldSeason.getSeasonData(data.getSeasonFromData(), data.daysIntoSeason);
		//决战
		WorldSeason.getFinalBattleData(data.worldState,data.textTime,data.finalTime,data.waveNum,data.battleFlag,data.battlePosX,data.battlePosY,data.battlePosZ,data.score,data.difficultyPoint);
	}
	
	public static void save(Season seasonNew, int daysIntoSeasonNew,int worldStateNew,int textTimeNew,int finalTimeNew,int waveNumNew,int battleFlagNew,int battlePosXNew ,int battlePosYNew,int battlePosZNew,int scoreNew,int difficultyPointNew) {
		
		// Instance of the server.
		// Yes we have to load it again so we can overwrite it in this function yay yay yay
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		
		// Instance of the server world.
		World world = server.getEntityWorld();
		
		// Load stuff from world data file
		OverWorldData data = OverWorldData.load(world);
		
		// Overwrite data
		data.season = OverWorldData.seasonToInt(seasonNew);
		data.daysIntoSeason = daysIntoSeasonNew;
		data.worldState = worldStateNew;
		data.textTime = textTimeNew;
		data.finalTime = finalTimeNew;
		data.waveNum = waveNumNew;
		data.battleFlag = battleFlagNew;
		data.battlePosX = battlePosXNew;
		data.battlePosY = battlePosYNew;
		data.battlePosZ = battlePosZNew;
		data.score = scoreNew;
		data.difficultyPoint = difficultyPointNew;
		
		OverLast.logger.info("Saving data to disk.");
		
		// Mark dirty for saving
		data.markDirty();
	}
}
