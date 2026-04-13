package com.overlast.lib;

import com.oblivioussp.spartanshields.util.ConfigHandler;
import com.oblivioussp.spartanshields.util.ModHelper;
import com.overlast.item.ItemHeroShield;
import net.minecraft.item.Item;

public class ModExtraItems {

	public static final Item shieldHero = new ItemHeroShield("hero_shield", ConfigHandler.durabilitySignalumShield, ModHelper.materialEnderium);

	// This list is used to actually register the items.
	public final static Item[] ITEMS = {
			shieldHero
	};

}