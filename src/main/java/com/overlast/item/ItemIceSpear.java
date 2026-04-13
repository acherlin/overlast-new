package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import com.overlast.entity.projectile.EntityIceSpear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemIceSpear extends Item
{
    private final String name = "ice_spear";
    public ItemIceSpear()
    {
        this.setTranslationKey(OverLast.MOD_ID +"."+ name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
        this.setMaxStackSize(8);

    }




    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack item = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            item.shrink(1);
        }

        if (!worldIn.isRemote)
        {

            EntityIceSpear entityIceSpear = new EntityIceSpear(worldIn, playerIn);
            float pitch = playerIn.rotationPitch, yaw = playerIn.rotationYaw;
            entityIceSpear.shoot(playerIn, pitch, yaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(entityIceSpear);

        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}