package com.overlast.util;

import com.overlast.config.OverConfig;
import com.overlast.season.Season;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class OverWorldData extends WorldSavedData {

	// Identifier
	private static final String ID = "overlast";
	
	// Stuff to save
	// Season data
	// 1 = winter, 2 = spring, 3 = summer, 4 = autumn
	public int season = 0;
	public int daysIntoSeason = 0;
	public int worldState = 0;
	public int textTime = 0;
	public int finalTime = 0;
	public int waveNum =0;
	public int battleFlag = 0;
	public int battlePosX =0;
	public int battlePosY =0;
	public int battlePosZ =0;
	public int score =0;
	public int difficultyPoint =0;

	// Put anymore data here whenever necessary
	
	// Constructors
	public OverWorldData() {
		
		super(ID);
	}
	
	public OverWorldData(String id) {
		
		super(id);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		season = nbt.getInteger("season");
		daysIntoSeason = nbt.getInteger("daysIntoSeason");
		worldState = nbt.getInteger("worldState");
		textTime = nbt.getInteger("textTime");
		finalTime = nbt.getInteger("finalTime");
		waveNum = nbt.getInteger("waveNum");
		battleFlag = nbt.getInteger("battleFlag");
		battlePosX = nbt.getInteger("battlePosX");
		battlePosY = nbt.getInteger("battlePosY");
		battlePosZ = nbt.getInteger("battlePosZ");
		score = nbt.getInteger("score");
		difficultyPoint= nbt.getInteger("difficultyPoint");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		compound.setInteger("season", season);
		compound.setInteger("daysIntoSeason", daysIntoSeason);
		compound.setInteger("worldState", worldState);
		compound.setInteger("textTime", textTime);
		compound.setInteger("finalTime", finalTime);
		compound.setInteger("waveNum", waveNum);
		compound.setInteger("battleFlag", battleFlag);
		compound.setInteger("battlePosX", battlePosX);
		compound.setInteger("battlePosY", battlePosY);
		compound.setInteger("battlePosZ", battlePosZ);
		compound.setInteger("score", score);
		compound.setInteger("difficultyPoint", difficultyPoint);
		
		return compound;
	}
	
	// Easy loading (Do OverWorldData data = OverWorldData.load(world);)
	public static OverWorldData load(World world) {
		
		com.overlast.util.OverWorldData data = (com.overlast.util.OverWorldData) world.getMapStorage().getOrLoadData(com.overlast.util.OverWorldData.class, ID);

		// Does it not exist?
		if (data == null) {
			
			//OverLast.logger.warn("No world data found for OverLast. Creating new file.");
			
			data = new OverWorldData();
			
			// Predetermine some values if necessary
			
			// Seasons
			// Determine starting season and daysIntoSeason 决定季节是春还是秋
			double springOrFall = Math.random();
			
			if (springOrFall < 0.50) {
				data.season = 2;
			} else {
				
				data.season = 4;
			}
			
			if (data.season == 2) {
				
				data.daysIntoSeason = (OverConfig.SEASONS.springLength / 2) - 1;
			}
			
			else {
				
				data.daysIntoSeason = 0;
			}
			if(OverConfig.SEASONS.defaultSeason!=0) {
				data.season =OverConfig.SEASONS.defaultSeason;
			}
			data.markDirty();
			world.getMapStorage().setData(ID, data);
		}
			
		return data;
	}
	
	// Conversion methods for seasons
	public static int seasonToInt(Season season) {
		
		switch(season) {
		
			case WINTER: return 1;
			case SPRING: return 2;
			case SUMMER: return 3;
			case AUTUMN: return 4;
			default: return 0;
		}
	}
	
	public static Season intToSeason(int seasonInt) {
		
		switch(seasonInt) {
		
			case 1: return Season.WINTER;
			case 2: return Season.SPRING;
			case 3: return Season.SUMMER;
			case 4: return Season.AUTUMN;
			default: return null;
		}
	}
	
	public Season getSeasonFromData() {
		
		switch(this.season) {
			
			case 1: return Season.WINTER;
			case 2: return Season.SPRING;
			case 3: return Season.SUMMER;
			case 4: return Season.AUTUMN;
			default: return null;
		
		}
	}
}
