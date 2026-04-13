package com.overlast.block;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCrops extends Block {
    private final String name = "crops";
    public BlockCrops() {
        super(Material.WOOD);
        this.setTranslationKey(OverLast.MOD_ID + "."+name);
        this.setRegistryName(name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        setDefaultState(this.blockState.getBaseState());
    }

}


