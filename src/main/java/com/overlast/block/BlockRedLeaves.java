package com.overlast.block;

import com.overlast.OverLast;
import com.overlast.lib.ModBlocks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class BlockRedLeaves extends BlockColoredLeaves  {
	
	/*
	 * Red oak leaves for Autumn. Bit messed up. But otherwise fine.
	 */
	public final String name = "red_leaves";
	
	public BlockRedLeaves() {
		
		super();
		
		// Registry and Unlocalized names.
		setRegistryName(OverLast.MOD_ID, name);
		setTranslationKey(OverLast.MOD_ID + "." + name);
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {

		List<ItemStack> list = new ArrayList();
		list.add(new ItemStack(ModBlocks.RED_LEAVES));
		return list;
	}

	@Override
	public EnumType getWoodType(int meta) {

		return EnumType.OAK;
	}


}