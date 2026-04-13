package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.item.Item;


public class ItemCustom extends Item {

    /*
     * A nice handheld fan that instantly cools the player down.
     */

    public ItemCustom(String name) {
        // Set registry and unlocalized name.
        this.setTranslationKey(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
        // Basic properties.
        setMaxStackSize(64);
    }

}