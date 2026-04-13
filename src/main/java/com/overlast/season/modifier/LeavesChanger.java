package com.overlast.season.modifier;

import com.overlast.lib.ModBlocks;
import com.overlast.season.Season;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.chunk.Chunk;

public class LeavesChanger {
	
	/*
	 * Changes leaves in Autumn and Spring.
	 * 
	 * Oaks will have red leaves and birches will have yellow leaves.
	 * 
	 * The colors revert back to normal in the spring.
	 * 在秋季和春季改变树叶。
	 * 橡树会有红叶，白桦树会有黄叶。
	 * 春天时，颜色又恢复正常。
	 */
	
	// Goto next chunk
	private Chunk nextChunk(World world, int currentX, int currentZ) {
		
		Chunk chunk = world.getChunk(currentX, currentZ);
		
		if (chunk.isLoaded()) {
			
			return chunk;
		}
		
		else {
			
			return null;
		}
	}
	
	// When Autumn or Spring first starts, we're going to go through all chunks around the player, changing the leaves.
	// We'll use the same method as SnowMelter.melt(), except with all rows, not just a random one.
	// This only happens once in each season.
	// 当秋天或春天刚开始的时候，我们要穿过玩家周围的所有小块，改变树叶。
	// 我们将使用与SnowMelter.melt()相同的方法，除了所有的行，而不仅仅是随机的行。
	// 这在每个季节只发生一次。
	public void changeInitial(Season season, World world, EntityPlayer player) {
		
		// Is X negative or positive?
		int changeX = 1;
		int posX = player.getPosition().getX();
		
		if (posX < 0) {
			
			changeX = -1;
		}
		
		// Grab first chunk
		Chunk chunk = world.getChunk(player.chunkCoordX + (-8 * changeX), player.chunkCoordZ - 8);
		
		// Go up to 16 chunks in that row, 16 rows
		int chunkLimit = 16;
		int chunkNum = 0;
		int rowLimit = 17;
		int rowNum = 0;
		
		// Let's go
		while (rowNum < rowLimit) {
			
			// One row while loop
			while (chunkNum < chunkLimit && chunk != null) {
			
				// Go through the selected chunk
				changeSingleChunk(chunk, world, season);
				
				// Goto next chunk
				chunk = nextChunk(world, chunk.x + changeX, chunk.z);
				
				// Increment chunkcount
				chunkNum++;
			}
			
			// Goto next chunk in next row
			chunk = nextChunk(world, chunk.x + (-16 * changeX), chunk.z + 1);
			
			// Increment rowNum
			rowNum++;
			
			// Reset chunkNum
			chunkNum = 0;
		}
	}
	
	// This method is the actual one that's called continuously in the Autumn.
	// 这个方法是在秋季连续调用的实际方法。
	public void change(int startX, int startZ, World world, Season season) {
			
		/*
		 * We'll go like this, to avoid wasting precious CPU. Because this'll happen everytime someone enters a chunk.
		 * ^---------------------->
		 * |                      |
		 * |                      |
		 * |          o           |
		 * |                      |
		 * |                      |
		 * |                      |
		 * <----------------------V
		 */
		
		// Starting chunk.
		Chunk chunk = world.getChunk(startX - 8, startZ - 8);

		// Northwest corner. Add one to x.
		for (int x = 0; x < 15; x++) {
			
			changeSingleChunk(chunk, world, season);
			chunk = world.getChunk(chunk.x + 1, chunk.z);
		}
		
		// Northeast corner. Add one to z.
		for (int z = 0; z < 15; z++) {
			
			chunk = world.getChunk(chunk.x, chunk.z + 1);
			changeSingleChunk(chunk, world, season);
		}
		
		// Southeast corner. Subtract one from x.
		for (int x = 0; x < 15; x++) {
			
			chunk = world.getChunk(chunk.x - 1, chunk.z);
			changeSingleChunk(chunk, world, season);
		}
		
		// Southwest corner. Subtract one from z.
		for (int z = 0; z < 15; z++) {
			
			chunk = world.getChunk(chunk.x, chunk.z - 1);
			changeSingleChunk(chunk, world, season);
		}
	}
	
	// Loops through one chunk, calling the private method below quite a lot.
	//循环通过一个块，大量调用下面的私有方法。
	private void changeSingleChunk(Chunk chunk, World world, Season season) {
		
		// Starting coords
		int cx = chunk.getPos().getXStart();
		int cz = chunk.getPos().getZStart();
		BlockPos pos = new BlockPos(cx, 64, cz);
		
		// Biome
		Biome biome = chunk.getBiome(pos, world.getBiomeProvider());
		
		// Does this biome allow for changing leaves?  雨林不换树叶
		if (biome instanceof BiomeJungle) {
			
			;
		}
		
		else {
			
			// Iterate through all of the top-blocks and remove any snow and ice.  遍历所有的顶块，清除任何雪和冰。
			for (int x = 0; x < 16; x++) {
				
				for (int z = 0; z < 16; z++) {
					
					// New BlockPos; get the top-most y-value. Then go down one, because precipitation height is one block above the ground.
					// 得到最上面的y值。然后往下走一个，因为沉淀的高度是离地面一个街区。
					pos = chunk.getPrecipitationHeight(pos);
					pos = pos.down();
					
					// Loop through each y-level below until it's not leaves, or the column isn't worth changing.
					// 循环浏览下面的每个y级，直到它不是叶子，或者该列不值得改变。
					for (int y = 0; y < 20; y++) {
						
						// Determine if this block should be replaced.
						// 确定是否应更换该块。
						boolean success = tryToChangeSingleBlock(world, pos, season);
						
						// Next y-level down, if this succeeded.
						if (success) {
							
							pos = pos.down();
						}
						
						else {
							
							break;
						}
					}
	
					// Goto next z
					pos = pos.south();
				}
				
				// Goto next x
				pos = pos.east();
				pos = pos.north(16);
			}
		}
		
		// Mark chunk dirty just in case
		chunk.markDirty();
	}
		
	// Actually does the logic in replacing a single block.
	// 实际上是在替换单个区块的逻辑。
	private boolean tryToChangeSingleBlock(World world, BlockPos pos, Season season) {
		
		// Blockstate
		IBlockState state = world.getBlockState(pos);
		
		// Is this block a normal leaf block?  这是一个正常的叶块吗？
		if (state.getBlock() == Blocks.LEAVES) {
			
			// Alright, it's a normal type of leaves (so not acacia or dark oak)
			// Is it Autumn, where they should be colorful?
			// 好吧，这是一种普通的树叶（所以不是金合欢或黑橡树）。
			// 现在是秋天，它们应该是彩色的吗？

			if (season == Season.AUTUMN) {
				
				// Okay. This block needs to change. What type of leaves?
				if (state.getBlock() instanceof BlockOldLeaf) {
					
					// Birch leaves? Make them yellow. 白桦树叶？让它们变成黄色。
					if (state.getValue(BlockOldLeaf.VARIANT) == EnumType.BIRCH && state.getValue(BlockLeaves.DECAYABLE)) {
						
						world.setBlockState(pos, ModBlocks.YELLOW_LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false));
						return true;
					}
					
					// No? Oak leaves? Make them red. 没有？橡树叶？让它们变成红色。
					else if (state.getValue(BlockOldLeaf.VARIANT) == EnumType.OAK && state.getValue(BlockLeaves.DECAYABLE)) {
						
						world.setBlockState(pos, ModBlocks.RED_LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false));
						return true;
					}
					
					// No? Screw it. They're fine. 没有？去它的吧。他们很好。
					else {
						
						return false;
					}
				}
				
				else {
					
					// Ehh no. Not enough metadata. Perhaps with 1.13, when metadata is gonna disappear. Oh dear.
					// Ehh no. 没有足够的元数据。也许在1.13版本时，元数据会消失。哦，天哪。
					return false;
				}	
			}


			else if (season == Season.WINTER) {

				// Okay. This block needs to change. What type of leaves? 冬天，冰洁色
				if (state.getBlock() instanceof BlockOldLeaf) {
					if (state.getValue(BlockOldLeaf.VARIANT) == EnumType.OAK && state.getValue(BlockLeaves.DECAYABLE)) {

						//橡树变蓝白
						world.setBlockState(pos, ModBlocks.BLUE_LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false));
						return true;
					}
					else {
						return false;
					}
				}

				else {
					return false;
				}
			}
			{
				return false;
			}
		}

		// What about the OTHER type of leaf block (acacia and dark oak)? 还原树叶方块
		else if (state.getBlock() == Blocks.LEAVES2) {
			
			// Alright, it's the secondary type of leaves.
			// Is it Autumn, where they should be colorful?
			// 好吧，这是第二种类型的树叶。
			//现在是秋天，它们应该是彩色的吗？
			if (season == Season.AUTUMN) {
				// Okay. This block needs to change. What type of leaves? Just in case
				//好的。这块需要改变。什么类型的叶子？以防万一
				if (state.getBlock() instanceof BlockNewLeaf) {
					// Dark oak leaves? Make them orange. 深色橡木~橙色
					if (state.getValue(BlockNewLeaf.VARIANT) == EnumType.DARK_OAK && state.getValue(BlockLeaves.DECAYABLE)) {
						world.setBlockState(pos, ModBlocks.ORANGE_LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false));
						return true;
					}
					// No? Screw it. They're fine. Acacia's fine. 金合欢，很好。
					else {
						return false;
					}
				}
				else {
					// Ehh no. Not enough metadata. Perhaps with 1.13, when metadata is gonna disappear. Oh dear.
					return false;
				}	
			}

			else {
				return false;
			}
		}
		
		// No? How about one of the modded leaves? Red? 没有吗？有一个修改过的叶子怎么样？红色的？ 还原颜色
		else if (state.getBlock() ==  ModBlocks.RED_LEAVES && state.getValue(BlockLeaves.DECAYABLE)) {
			
			// Okay, red leaves. Is it Spring?
			if (season == Season.SPRING) {
				
				// Alrighty. Change them back. 红色->橡树
				world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockOldLeaf.VARIANT, EnumType.OAK));
				return true;
			}
			
			else {
				
				// Don't bother.
				return false;
			}
		}
		
		// Yellow?
		else if (state.getBlock() ==  ModBlocks.YELLOW_LEAVES && state.getValue(BlockLeaves.DECAYABLE)) {
			
			// Okay, yellow leaves. Is it Spring?
			if (season == Season.SPRING) {
				
				// Alrighty. Change them back. 黄色->白桦
				world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockOldLeaf.VARIANT, EnumType.BIRCH));
				return true;
			}
			
			else {
				
				// Don't bother.
				return false;
			}
		}
		
		// Orange?
		else if (state.getBlock() ==  ModBlocks.ORANGE_LEAVES && state.getValue(BlockLeaves.DECAYABLE)) {
			
			// Okay, orange leaves. Is it Spring?
			if (season == Season.SPRING) {
				
				// Alrighty. Change them back. 橙色->深色橡木
				world.setBlockState(pos, Blocks.LEAVES2.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockNewLeaf.VARIANT, EnumType.DARK_OAK));
				return true;
			}
			
			else {
				
				// Don't bother.
				return false;
			}
		}

		//BLUE
		else if (state.getBlock() ==  ModBlocks.BLUE_LEAVES && state.getValue(BlockLeaves.DECAYABLE)) {
			if (season == Season.SPRING) {

				// Alrighty. Change them back. 蓝色->橡木
				world.setBlockState(pos, Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockOldLeaf.VARIANT, EnumType.OAK));
				return true;
			}
			else {
				return false;
			}
		}

		// Air? Continue anyway. 空气，随他去吧
		else if (state.getBlock() == Blocks.AIR) {
			
			return true;
		}
		
		// Not leaves? Screw it.
		else {
			
			return false;
		}
	}
}