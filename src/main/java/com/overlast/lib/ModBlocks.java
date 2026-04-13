package com.overlast.lib;

import com.overlast.block.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Loader;

public class ModBlocks {
	/*
	 *  A list of all blocks in the game, used to quickly register and render everything. kek
	 */
	
	// List for easy referencing.

	public static final Block RED_LEAVES = new BlockRedLeaves();
	public static final Block YELLOW_LEAVES = new BlockYellowLeaves();
	public static final Block ORANGE_LEAVES = new BlockOrangeLeaves();
	public static final Block BLUE_LEAVES = new BlockBlueLeaves();
	public static final Block BROWN_LEAVES = new BlockBrownLeaves();
	public static final Block BlockPurifier = new BlockBlockPurifier();
	public static final Block WinterCore = new BlockWinterCore();
	public static final Block SummerCore = new BlockSummerCore();
	public static final Block SpecimenFarm = new BlockSpecimen("specimen_farm");
	public static final Block SpecimenCell = new BlockSpecimen("specimen_cell");
	public static final Block SpecimenInfect = new BlockSpecimen("specimen_infect");
	public static final Block FinalSpecimen = new BlockFinalSpecimen();

	public static final Block Crops = new BlockCrops();

	public static final Block[] BLOCKS = {
			RED_LEAVES,
			YELLOW_LEAVES,
			ORANGE_LEAVES,
			BLUE_LEAVES,
			BROWN_LEAVES,
			BlockPurifier,
			WinterCore,
			SummerCore,
			SpecimenFarm,
			SpecimenCell,
			SpecimenInfect,
			FinalSpecimen,
			Crops
	};

	public static void ExBlockRegister() {
		if (Loader.isModLoaded("spartanshields")) {

		}
	}
}