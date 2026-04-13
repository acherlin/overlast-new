package com.overlast.lib;


import com.overlast.OverLast;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class ModPotions {
	public static final PotionType HEATRADIATION = new PotionType(OverLast.MOD_ID + ":heat_radiation", new PotionEffect(ModMobEffects.HEATRADIATION, 600)).setRegistryName(OverLast.MOD_ID + ":heat_radiation");

	public static final PotionType OVERHEATING = new PotionType(OverLast.MOD_ID + ":over_heating", new PotionEffect(ModMobEffects.OVERHEATING, 600)).setRegistryName(OverLast.MOD_ID + ":over_heating");

	public static final PotionType HYPOTHERMIA = new PotionType(OverLast.MOD_ID + ":hypothermia", new PotionEffect(ModMobEffects.HYPOTHERMIA, 1800)).setRegistryName(OverLast.MOD_ID + ":hypothermia");

	public static final PotionType FROSTY = new PotionType(OverLast.MOD_ID + ":frosty", new PotionEffect(ModMobEffects.FROSTY, 600)).setRegistryName(OverLast.MOD_ID + ":frosty");

	public static final PotionType PARASITESPURIFY = new PotionType(OverLast.MOD_ID + ":parasites_purify", new PotionEffect(ModMobEffects.PARASITESPURIFY, 1800)).setRegistryName(OverLast.MOD_ID + ":parasites_purify");

	public static final PotionType PARASITESINFECT = new PotionType(OverLast.MOD_ID + ":parasites_infect", new PotionEffect(ModMobEffects.PARASITESINFECT, 600)).setRegistryName(OverLast.MOD_ID + ":parasites_infect");

	public static final PotionType COOL = new PotionType(OverLast.MOD_ID + ":cool", new PotionEffect(ModMobEffects.COOL, 600)).setRegistryName(OverLast.MOD_ID + ":cool");

	public static final PotionType FORTUNATE = new PotionType(OverLast.MOD_ID + ":fortunate", new PotionEffect(ModMobEffects.FORTUNATE, 600)).setRegistryName(OverLast.MOD_ID + ":fortunate");

	public static final PotionType EVIL = new PotionType(OverLast.MOD_ID + ":evil", new PotionEffect(ModMobEffects.EVIL, 600)).setRegistryName(OverLast.MOD_ID + ":evil");

}
