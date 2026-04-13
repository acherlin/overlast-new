package com.overlast.block;

import com.dhanantry.scapeandrunparasites.SRPMain;
import com.dhanantry.scapeandrunparasites.network.SRPPacketParticle;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Random;

public class BlockFinalSpecimen extends Block {
    private final String name = "final_specimen";
    public BlockFinalSpecimen() {
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
