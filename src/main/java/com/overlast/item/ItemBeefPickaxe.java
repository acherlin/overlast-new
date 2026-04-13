package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ItemBeefPickaxe extends ItemPickaxe implements IItemToolEdible {
    private final String name = "beef_pickaxe";

    public ItemBeefPickaxe(int healAmount, float saturation)
    {
        super(ToolMaterial.IRON);
        this.setTranslationKey(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
        this.healAmount = healAmount;
        this.saturationModifier = saturation;
    }




    private final int healAmount;
    private final float saturationModifier;

    @Override
    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public float getSaturationModifier() {
        return saturationModifier;
    }
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return IItemToolEdible.super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return IItemToolEdible.super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        IItemToolEdible.super.onFoodEaten(stack, worldIn, player);
    }
}
