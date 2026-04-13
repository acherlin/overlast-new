package com.overlast.season.modifier;

import com.overlast.OverLast;
import com.overlast.season.Season;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.Iterator;

public class BiomeTempController {
	
	/*
	 * This stores the original biome temperatures, modifying the base temps if necessary.
	 * It also deals with changing the temperature of each biome according to the season, and where we are in the season.
	 */
	/*
	 * 这将存储原始的生物群落温度，必要时修改基本温度。
	 *它还处理根据季节和我们在季节中的位置来改变每个生物群落的温度。
	 */

	// The array of original temperatures. 原始温度的阵列。
	private static float[] temperatures;
	
	// This method goes through every biome and grabs the original temperature. Called at the beginning.
	// 这个方法穿过每个生物群落，抓取原始温度。在开始时调用。
	public void storeOriginalTemperatures() {
		if(Loader.isModLoaded("sereneseasons"))
			return;
		
		// Is temperatures already filled up? Then don't bother doing this crap again.
		//  温度已经满了吗？那就不要再做这些破事了。
		if (temperatures != null) {

			OverLast.logger.info("Original temperatures already stored. Not doing it again.");
		}
		
		else {
			
			// Number of biomes
			int biomeNumber = ForgeRegistries.BIOMES.getValuesCollection().size();
			
			// Allocate enough space for array
			temperatures = new float[biomeNumber];
			
			// Iterator
			Iterator<Biome> biomeList = ForgeRegistries.BIOMES.getValuesCollection().iterator();
			int counter = 0;
			
			// Iterate kek
			while (biomeList.hasNext()) {
				
				// The biome
				Biome choosenBiome = biomeList.next();
				
				// Grab the temperature and store it and go
				float biomeTemp = choosenBiome.getDefaultTemperature();
				temperatures[counter] = biomeTemp;
				counter++;
			}
			
			OverLast.logger.info("Stored " + (counter + 1) + " biome temperatures.");
		}
	}
	
	// Want some original temp for a specific biome? Use this thingy-a-method here!
	// 想要一些特定生物群落的原始温度吗？请使用这个方法吧!
	private float getOriginalTemperature(Biome biome) {
		
		// Iterator
		Iterator<Biome> biomeList = ForgeRegistries.BIOMES.getValuesCollection().iterator();
		int counter = 0;
		
		// Iterate kek
		while (biomeList.hasNext()) {
			
			// The biome
			Biome choosenBiome = biomeList.next();
			
			// Is this the right one?
			if (biome.equals(choosenBiome)) {
				
				return temperatures[counter];
			}
			
			else {
				
				counter++;
			}
		}
		
		return counter;
	}
	
	// This actually changes the biome temperatures every morning
	// Using reflection! Yeah it's very hacky, hackish, whatever you wanna call it but it works.
	// 这实际上改变了每天早晨的生物群落温度
	// 使用反射! 是的，这是非常hacky，hacky，不管你怎么称呼它，但它是有效的。
	public void changeBiomeTemperatures(Season season, int daysIntoSeason) {
		//兼容静谧四季
		if(Loader.isModLoaded("sereneseasons"))
			return;
		
		// Get all of the biomes and iterate through them
		Iterator<Biome> biomeList = ForgeRegistries.BIOMES.getValuesCollection().iterator();
		
		// Iterate kek
		while (biomeList.hasNext()) {
			
			// The biome
			Biome choosenBiome = biomeList.next();
			
			// Its original temperature
			float origTemp = getOriginalTemperature(choosenBiome);
			
			// Half of the season.
			int halfSeason = (int)Math.ceil(season.getLength() / 2.0);
			
			// The temperature changes everyday, so it's more gradual.
			// Every half of each season has a specified temperature change that should be COMPLETELY added to the ORIGINAL temperature by the end of said half.
			// We can get the total temperature change through the Season class methods.
			// 温度每天都在变化，所以它是比较渐进的。
			// 每个季节的每一半都有一个指定的温度变化，在这一半结束时应该完全加到原来的温度上。
			// 我们可以通过季节类的方法得到总的温度变化。
			float tempDiff = season.getTemperatureDifference(daysIntoSeason);
			
			// Depending on how far we are into the season, we'll only take a portion of the temp difference and add it to the original biome temperatures.
			// Day: 1 2 3 4 5 6 7 8 9 10 11 12 13 14  1
			//      --------------********************-
			// - = first difference, * = second difference
			
			// Our base temp is NOT the original. We'll start out by applying the previous season's difference. (Second half of previous)
			// If we're in a middle of a season, don't use the previous season, use the same one. (First half of current)

			// 根据我们进入季节的程度，我们只取一部分温度差，并将其加入到原始生物群落的温度中。
			// 一天。1 2 3 4 5 6 7 8 9 10 11 12 13 14 1
			// --------------********************-
			// - = 第一个差异，* = 第二个差异
			// 我们的基础温度不是原始温度。我们一开始就应用上一季的差异。(以前的后半部分)
			// 如果我们处于一个季节的中间，不要使用前一个季节，使用相同的一个。(当前的前一半)
			float prevTempDiff;
			
			if (daysIntoSeason > halfSeason) {
				
				prevTempDiff = season.getTemperatureDifference(0);
			}
			
			else {
				
				Season prevSeason = season.prevSeason(season);
				prevTempDiff = prevSeason.getTemperatureDifference(halfSeason + 1);
			}
			
			// Now apply the CURRENT temp difference to the original temp. That's the temp we have to get to.
			//  现在将目前的温度差应用于原始温度。这就是我们必须达到的温度。
			float targetTemp = origTemp + tempDiff;
			
			// The difference between the target temp and current temp
			// 目标温度和当前温度之间的差异
			float actualDiff = targetTemp - (origTemp + prevTempDiff);
			
			// Divide this difference by the amount of days in half a season.
			float diffPerDay = actualDiff / halfSeason;
			
			// Now, we must edit daysIntoSeason so we can correctly calculate the difference to add onto the temperature.
			int halfDays;
			if (daysIntoSeason > halfSeason) {
				
				halfDays = daysIntoSeason - halfSeason;
			}
			
			else {
				
				halfDays = daysIntoSeason;
			}
			
			// Create the REAL difference
			float newDiff = diffPerDay * halfDays;
			
			// NEW temperature!!!!
			float newTemperature = origTemp + prevTempDiff + newDiff;
			
			// Exceptions
			// Any biome with a normal temperature above 0.9 is usually a pretty warm biome. They should really stay warm. Snow on jungles is pretty weird.
			if (origTemp > 0.9f && newTemperature < 0.5f) {
				
				newTemperature = 0.5f;
			}
			
			// Oceans freezing during the winter sound fun but really aren't.
			if (choosenBiome instanceof BiomeOcean && newTemperature < 0.5f) {
				
				newTemperature = 0.5f;
			}
			
			// The Nether and the End aren't normal biomes.
			if (choosenBiome instanceof BiomeHell || choosenBiome instanceof BiomeEnd) {
				
				newTemperature = choosenBiome.getDefaultTemperature();
			}
			
			// Apply the differences. First the previous one from the previous half season. Then the new one we just calculated.
			changeTemp(choosenBiome, newTemperature);
		}
	}
	
	// Used to reset temperatures upon leaving a world, as biome temperatures are changed for the entire JVM session.
	//  用于在离开一个世界时重置温度，因为生物群落的温度在整个JVM会话中被改变。
	public void resetBiomeTemperatures() {
		//兼容静谧四季
		if(Loader.isModLoaded("sereneseasons"))
			return;
		// Iterator
		Iterator<Biome> biomeList = ForgeRegistries.BIOMES.getValuesCollection().iterator();
		int counter = 0;
		
		// Iterate kek
		while (biomeList.hasNext()) {
			
			// The biome
			Biome biome = biomeList.next();
			
			// Grab the original temperature
			float origBiomeTemp = temperatures[counter];
			
			// Now change the temperature back to the original
			changeTemp(biome, origBiomeTemp);

			// Increment counter.
			counter++;
		}
		
		// Log it
		OverLast.logger.info("Restored " + (counter + 1) + " biome temperatures to their originals.");
	}

	// This is the actual reflection crap used to change the temperature of a biome.
	private void changeTemp(Biome biome, float temperature) {

		// The fields appear to be different when compiled. So that's why there's two different ways, as I'll continue using the dev workspace.
		try {
			Field f = Biome.class.getDeclaredField("temperature");
			f.setAccessible(true);
			f.set(biome, temperature);
		} catch (NoSuchFieldException e) {

			try {
				Field f = Biome.class.getDeclaredField("field_76750_F");
				f.setAccessible(true);
				f.set(biome, temperature);
			} catch (NoSuchFieldException ex) {
				OverLast.logger.error("Did not find funky field. Contact Overlast if you get this error. It means he has to work harder. Damn it.");
			} catch (SecurityException ex) {
				ex.printStackTrace();
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}