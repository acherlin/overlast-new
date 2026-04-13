package com.overlast.lib;

import com.overlast.OverLast;
import com.overlast.item.*;
import com.overlast.item.potion.ItemDrinkedPotion;
import com.overlast.item.potion.ItemInjectedPotion;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;

public class ModItems {

	public static final Item ItemBeefPickaxe = new ItemBeefPickaxe(2,0.3F);
	public static final Item woodenswordplus = new ItemWoodenSwordplus();
	public static final Item baseballpole = new ItemBaseballPole();

	public static final ItemChocolateSmoothie CHOCOLATE_SMOOTHIE = new ItemChocolateSmoothie(4, 0.6F, false);

	public static final ItemIceSpear ICE_SPEAR = new ItemIceSpear();


	public static final ItemPolluteBowlHerbal Pollute_Bowl_Herbal = new ItemPolluteBowlHerbal(1, 0.1F, false);
	public static final ItemBowlHerbal Bowl_Herbal = new ItemBowlHerbal(1, 0.1F, false);
	public static final ItemMelonIce Melon_Ice = new ItemMelonIce(4,0.2F,false);
	public static final ItemIceSucker Ice_Sucker = new ItemIceSucker(1,0.2F,false);

	public static final ItemDrinkedPotion Drinked_Potion = new ItemDrinkedPotion();

	public static final ItemInjectedPotion Injected_Potion = new ItemInjectedPotion();

	public static final Item DUMPLING = new ItemSpringFestivalFood(4, 0.2F)
			.setPotionEffect(new PotionEffect(MobEffects.SATURATION, 20), 0.06F)
			.setAlwaysEdible()
			.setTranslationKey(OverLast.MOD_ID + ".dumpling")
			.setRegistryName("dumpling");

	public static final ItemEvoDevice EVO_DEVICE = new ItemEvoDevice();

	public static final ItemCustom Boss_Chip = new ItemCustom("boss_chip");


	// This list is used to actually register the items.
	public final static Item[] ITEMS = {
			ItemBeefPickaxe,
			woodenswordplus,
			baseballpole,
			CHOCOLATE_SMOOTHIE,
			ICE_SPEAR,
			Pollute_Bowl_Herbal,
			Bowl_Herbal,
			Melon_Ice,
			Drinked_Potion,
			Injected_Potion,
			Ice_Sucker,
			DUMPLING,
			EVO_DEVICE,
			Boss_Chip
	};

}