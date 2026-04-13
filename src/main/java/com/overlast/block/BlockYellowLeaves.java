package com.overlast.block;

import com.overlast.OverLast;
import com.overlast.lib.ModBlocks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockYellowLeaves extends BlockColoredLeaves  {
	
	/*
	 * Yellow birch leaves for Autumn. Bit messed up. But otherwise fine.
	 */
	public final String name = "yellow_leaves";

	public BlockYellowLeaves() {
		
		super();
		
		// Registry and Unlocalized names.
		setRegistryName(OverLast.MOD_ID, name);
		setTranslationKey(OverLast.MOD_ID + "." + name);

	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {

		List<ItemStack> list = new ArrayList();
		list.add(new ItemStack(ModBlocks.YELLOW_LEAVES));
		return list;
	}

	@Override
	public EnumType getWoodType(int meta) {

		return EnumType.BIRCH;
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		int chance = this.getSaplingDropChance(state);

		if (fortune > 0)
		{
			chance -= 2 << fortune;
			if (chance < 10) chance = 10;
		}

		if (rand.nextInt(chance) == 0)
		{
			ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), 1, 2);
			if (!drop.isEmpty())
				drops.add(drop);
		}

		chance = 200;
		if (fortune > 0)
		{
			chance -= 10 << fortune;
			if (chance < 40) chance = 40;
		}

		this.captureDrops(true);
		if (world instanceof World)
			this.dropApple((World)world, pos, state, chance); // Dammet mojang
		drops.addAll(this.captureDrops(false));
	}

}