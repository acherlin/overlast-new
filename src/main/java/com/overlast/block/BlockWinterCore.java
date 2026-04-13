package com.overlast.block;

import com.dhanantry.scapeandrunparasites.SRPMain;
import com.dhanantry.scapeandrunparasites.network.SRPPacketParticle;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import com.overlast.util.WorldDataMgr;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Random;

public class BlockWinterCore extends Block {
    private final String name = "winter_core";
    public BlockWinterCore() {
        super(Material.IRON);
        this.setTranslationKey(OverLast.MOD_ID + "."+name);
        this.setRegistryName(name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setHardness(50.0F);
        this.setResistance(1200.0F);
        setDefaultState(this.blockState.getBaseState());
    }


    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        if (worldIn.isRemote) return flag;

        ItemStack head = new ItemStack(playerIn.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem());
        if (head.getItem() != Items.AIR) return flag;

        WorldSeason.setWorldState(1);
        WorldSeason.setSeason(Season.WINTER);

        MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();
        mc.getPlayerList().sendMessage(new TextComponentString("永冻核心已启动"));

        //保存世界状态
        WorldDataMgr.save(WorldSeason.getSeason(), WorldSeason.getDaysIntoSeason(),WorldSeason.getWorldState(),WorldSeason.getTextTime(),WorldSeason.getFinalTime(),WorldSeason.getWaveNum(),WorldSeason.getBattleFlag(),WorldSeason.getBattlePosX(),WorldSeason.getBattlePosY(),WorldSeason.getBattlePosZ(),WorldSeason.getScore(),WorldSeason.getDifficultyPoint());

        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

        for (int i = 0; i <= 3; i++) {
            SRPMain.network.sendToAll((IMessage)new SRPPacketParticle(pos.getX(), (pos.getY() + 1), pos.getZ(), 0.5F, 0.5F, (byte)2));
        }
        return flag;
    }


    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote)
            return;
        for (int i = 0; i <= 3; i++)
            SRPMain.network.sendToAll((IMessage)new SRPPacketParticle(pos.getX(), (pos.getY() + 1), pos.getZ(), 0.5F, 0.5F, (byte)2));
    }
}


