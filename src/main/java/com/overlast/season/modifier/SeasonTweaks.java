package com.overlast.season.modifier;

import com.overlast.season.Season;
import net.minecraft.world.biome.Biome;

public class SeasonTweaks {
	
	/*
	 * This class contains some miscellaneous season crap.
	 * Crap that doesn't need its own class, nor would it be nice cluttering WorldSeason.class 
	 */
	
	/*
	 * These variables and this method changes grass colors as the seasons progress.
	 */
	/*
	 * 这个类包含了一些杂七杂八的季节废话。
	 * 这些垃圾不需要有自己的类，也不适合在WorldSeason.class中杂乱无章。
	 */

	/*
	 * 这些变量和这个方法随着季节的变化而改变草的颜色。
	 */
	// 草的颜色。
	// Grass colors.
	private final int SUMMER_GRASS_COLOR = 13296206;
	private final int AUTUMN_GRASS_COLOR = 13925888;
	private final int WINTER_GRASS_COLOR = 6008466;
	
	public int getSeasonGrassColor(Season season, Biome biome) {
		
		// Get temperature
		float temp = biome.getDefaultTemperature();
		
		// Determine what season it is
		if (season == Season.SUMMER) {
			
			// Is the temperature above 0.8? We'll change its grass color then.
			if (temp >= 0.80f) {
				
				return SUMMER_GRASS_COLOR;
			}
			
			else {
				
				return 0;
			}
		}
		
		else if (season == Season.AUTUMN) {
			
			// BELOW 0.8?
			if (temp <= 0.80f) {
					
				return AUTUMN_GRASS_COLOR;
			}
			
			else {
				
				return 0;
			}
		}
		
		else if (season == Season.WINTER) {
			
			// BELOW 0.5?
			if (temp <= 0.50f) {
					
				return WINTER_GRASS_COLOR;
			}
			
			else {
				
				return 0;
			}
		}
		
		else {
			
			return 0;
		}
	}

}