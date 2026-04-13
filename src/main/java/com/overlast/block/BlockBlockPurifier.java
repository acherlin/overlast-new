package com.overlast.block;

import com.dhanantry.scapeandrunparasites.SRPMain;
import com.dhanantry.scapeandrunparasites.block.*;
import com.dhanantry.scapeandrunparasites.block.slabs.BlockSlabRubble;
import com.dhanantry.scapeandrunparasites.network.SRPPacketParticle;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import com.overlast.util.OverUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Random;

public class BlockBlockPurifier
        extends Block {

    private final String name = "blockpurifier";
    public BlockBlockPurifier() {
        super(Material.SPONGE);
        this.setTranslationKey(OverLast.MOD_ID + "."+name);
        this.setRegistryName(name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setHardness(5.0F);
        setDefaultState(this.blockState.getBaseState());
    }


    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        if (worldIn.isRemote) return flag;

        ItemStack head = new ItemStack(playerIn.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem());
        if (head.getItem() != Items.AIR) return flag;

            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            OverUtil.UTIL.killBiome(worldIn, pos, 16);
            int distanceX = pos.getX();
            int distanceY = pos.getY();
            int distanceZ = pos.getZ();
            for (int y = 0; y <= 255; y++) {
                for (int x = -16; x <= 16; x++) {
                    for (int z = -16; z <= 16; z++) {
                        Block targetBlock = worldIn.getBlockState(pos.add(x, y - distanceY, z)).getBlock();
                        //空气
                        if (targetBlock instanceof BlockAir) {
                            continue;
                        }
                        //泥土
                        if (targetBlock instanceof BlockInfestedStain || targetBlock instanceof BlockParasiteStain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.DIRT.getDefaultState());
                            continue;
                        }

                        //感染方块
                        if (targetBlock instanceof BlockInfestedRemain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }

                        //感染原木
                        if (targetBlock instanceof BlockInfestedTrunk || targetBlock instanceof BlockParasiteTrunk) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.LOG.getDefaultState());
                            continue;
                        }

                        //感染石头
                        if (targetBlock instanceof BlockInfestedRubble || targetBlock instanceof BlockParasiteRubble || targetBlock instanceof BlockParasiteRubbleDense) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.STONE.getDefaultState());
                            continue;
                        }

                        //感染草脉+嘴
                        if (targetBlock instanceof BlockInfestedBush || targetBlock instanceof BlockParasiteBush || targetBlock instanceof BlockParasiteMouth || targetBlock instanceof BlockStairBase || targetBlock instanceof BlockSlab || targetBlock instanceof BlockSlabRubble
                                || targetBlock instanceof BlockParasiteCanister || targetBlock instanceof BlockParasiteThin) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }
                    }
                }
            }
            //worldIn.setBlockState(pos, Blocks.STONE.getDefaultState());
            for (int i = 0; i <= 3; i++) {
                SRPMain.network.sendToAll((IMessage)new SRPPacketParticle(pos.getX(), (pos.getY() + 1), pos.getZ(), 0.5F, 0.5F, (byte)2));
            }
        return flag;
    }


    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote)
            return;
        OverUtil.UTIL.killBiome(worldIn, pos, 16);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

        for (int i = 0; i <= 3; i++)
            SRPMain.network.sendToAll((IMessage)new SRPPacketParticle(pos.getX(), (pos.getY() + 1), pos.getZ(), 0.5F, 0.5F, (byte)2));
    }



}


