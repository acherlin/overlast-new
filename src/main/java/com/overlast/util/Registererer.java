package com.overlast.util;

import com.overlast.lib.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class Registererer {
	
	/*
	 * This is where all new crap added to the mod is registered (items, blocks, etc.)
	 */
	
	// Register all blocks.
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS);
	}
	
	// Register all items.
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		
		// Register normal items.
		event.getRegistry().registerAll(ModItems.ITEMS);

		//Other items.
		if (Loader.isModLoaded("spartanshields")) {
			event.getRegistry().register(ModExtraItems.shieldHero);
		}


		for (Block block : ModBlocks.BLOCKS) {
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}

	}

	@SubscribeEvent
	public static void onRegisterPotion(RegistryEvent.Register<Potion> event) {
		for (Field field : ModMobEffects.class.getDeclaredFields()) {
			Object obj;
			try {
				obj = field.get(null);
				if (obj instanceof Potion) {
					Potion potion = (Potion) obj;
					event.getRegistry().register(potion);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@SubscribeEvent
	public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event) {
		for (Field field : ModPotions.class.getDeclaredFields()) {
			Object obj;
			try {
				obj = field.get(null);
				if (obj instanceof PotionType) {
					PotionType potiontype = (PotionType) obj;
					event.getRegistry().register(potiontype);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

}