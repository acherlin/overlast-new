package com.overlast.season.modifier;

import com.overlast.OverLast;
import com.overlast.season.Season;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import java.util.Random;

public class WeatherHandler {
	
	/*
	 * Handles the weather, every f*cking day. So the lying weather man on TV DOES control the weather! 
	 */
	/*
	 * 处理天气，每一个该死的日子。
	 */

	// 用来实际改变天气的变量
	// Variables used to actually change weather
	private static boolean isThunder;
	private static int tickDuration = 0;
	
	// This doesn't actually change the weather, but randomizes it, and pushes stuff to a method that does change it.
	// 这实际上并不改变天气，而是将其随机化，并将东西推送到一个确实能改变天气的方法。
	public void makeItRain(World world, Season season, EntityPlayer player) {
		
		// Time for more randomness, to determine whether to have a thunderstorm or not.
		// Summer usually has more thunderstorms than other seasons, followed by spring and autumn.
		// 是时候进行更多的随机性，以确定是否有雷暴。
		// 夏季通常比其他季节有更多的雷暴，其次是春季和秋季。

		float luckyNumber = 0f;
		
		if (season == Season.SPRING) {
			
			luckyNumber = 0.5f;
		}
		
		else if (season == Season.SUMMER) {
			
			luckyNumber = 0.5f;
		}
		
		else {
			
			luckyNumber = 0.3f;
		}
		
		// Now the actual randomness. Gonna be a lot, so may as well make a new generator.
		//现在是实际的随机性。将会有很多，所以不妨做一个新的生成器。
		Random rand = new Random();
		
		// Determine if thunderstorm or not
		float rand1 = rand.nextFloat();
		
		if (rand1 < luckyNumber) {
			
			isThunder = true;
		}
		
		else {
			
			isThunder = false;
		}
		
		// Determine when to start
		// It shall be a tick number between 0 and 9000
		// 确定何时开始
		// 它应是一个介于0和9000之间的刻度数字
		int ticksUntilStart;
		ticksUntilStart = rand.nextInt(9000);
		
		// Determine how long the rain shall last
		// 决定雨水应持续多久
		tickDuration = rand.nextInt(18000);
		
		if (tickDuration < 9000) {
			
			tickDuration += 9000;
		}
		
		// Actually change the weather 真正改变天气
		world.getWorldInfo().setRainTime(ticksUntilStart);
		
		OverLast.logger.info("Rain is on the way in " + ticksUntilStart + " ticks.");
		// The other variables will be used when it actually starts raining.
		// 其他变量将在真正开始下雨的时候使用。

		if(!isThunder) {
			player.sendMessage(new TextComponentTranslation("message.weather.noThunder",(ticksUntilStart/1000),tickDuration/1000));
		}
		else {
			player.sendMessage(new TextComponentTranslation("message.weather.isThunder",(ticksUntilStart/1000),tickDuration/1000));
		}
	}
	
	public void makeItNotRain(World world,EntityPlayer player) {
		
		// WorldInfo instance
		WorldInfo worldinfo = world.getWorldInfo();
		
		// Make it not rain
		worldinfo.setRaining(false);
		worldinfo.setThundering(false);
		worldinfo.setRainTime(100000000);
		player.sendMessage(new TextComponentTranslation("message.weather.sunny"));
	}
	
	public void applyToRain(World world) {
		
		// Apply the stuff
		// WorldInfo instance
		// 应用这些东西
		// WorldInfo实例
		WorldInfo worldinfo = world.getWorldInfo();
		
		worldinfo.setThundering(isThunder);
		worldinfo.setRainTime(tickDuration);
		
		// Logit
		OverLast.logger.info("It's raining now. It's " + isThunder + " that it is thundering. It'll last for " + tickDuration + " ticks.");
	}
}