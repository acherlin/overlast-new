package com.overlast.lib;


import com.overlast.mobeffect.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;

public class  ModMobEffects {
	public static final Potion HEATRADIATION = new MobEffectHeatRadiation();
	public static final Potion FROSTY = new MobEffectFrosty().registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -0.05D, 2);;
	public static final Potion HYPOTHERMIA = new MobEffectHypothermia().registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -0.1D, 2);
	public static final Potion OVERHEATING = new MobEffectOverHeating();
	public static final Potion PARASITESPURIFY = new MobEffectParasitesPurify();
	public static final Potion PARASITESINFECT = new MobEffectParasitesInfect();
	public static final Potion COOL = new MobEffectCool();
	public static final Potion FORTUNATE = new MobEffectFortunate();
	public static final Potion EVIL = new MobEffectEvil();
}
