package com.overlast.item.potion;


import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.overlast.OverLast;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.creativetab.TabOverLast;
import com.overlast.lib.ModMobEffects;
import lombok.val;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDrinkedPotion extends Item {

    /*
     * A simple canteen for all your thirst needs. Ends up being full of code. Go figure.
     * 一个简单的食堂，满足你所有的口渴需求。结果是充满了代码。你想啊。
     */


    // Number of sips. 可以饮用次数
    private final int canteenSips = 6;
    // Durability 耐久
    private final int canteenDurability = 30;

    public ItemDrinkedPotion() {

        // Set registry name.
        this.setRegistryName(new ResourceLocation(OverLast.MOD_ID, "drinking_potion"));

        // Basic properties.
        this.setMaxStackSize(1);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setNoRepair();
        this.setHasSubtypes(true);

    }

    // Drinking from the canteen. 饮用行为
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

        // Server-side. 服务端
        if (entityLiving instanceof EntityPlayerMP && !world.isRemote) {

            // Basic variables.
            EntityPlayerMP player = (EntityPlayerMP) entityLiving;

            int canteenType = stack.getMetadata();

            // Capabilities
            ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);


            // Determine type of water, and quench thirst accordingly. 确定水的类型，并相应地解渴。
            // Fresh water
            if (canteenType == 1) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 1800, 0, false, true));
                if (OverConfig.MECHANICS.enableSanity) {
                    sanity.increase(10f);
                }
            }

            // Dirty water
            else if (canteenType == 2) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESINFECT, 1800, 0, false, true));
                if (OverConfig.MECHANICS.enableSanity) {
                    sanity.decrease(10f);
                }
            }

            // Salt water
            else if (canteenType == 3) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESINFECT, 3600, 1, false, true));
                if (OverConfig.MECHANICS.enableSanity) {
                    sanity.decrease(15f);
                }
            }

            // Decrease durability and set sips.
            NBTTagCompound nbt = stack.getTagCompound();

            if (nbt != null) {

                if (nbt.getInteger("sips") > 0) {

                    nbt.setInteger("sips", nbt.getInteger("sips") - 1);
                } else {

                    nbt.setInteger("sips", nbt.getInteger("sips") - 1);
                    stack.setItemDamage(0);
                }

                nbt.setInteger("durability", nbt.getInteger("durability") - 1);
                stack.setTagCompound(nbt);
            }
        }

        return stack;
    }


    // Right click to get water. If the player is holding an empty canteen, it will attempt to fill it up with water.
    // If holding a canteen partially filled with water, it will refill it with water.
    // If holding a full canteen with the same type of water it's going to get, it'll empty itself, becoming an empty canteen.
    // 右键单击以获得水。如果玩家拿着一个空的水壶，它将试图把它装满水。
    // 如果拿着一个部分装满水的水壶，它将重新装满水。
    // 如果拿着一个装满水的水壶，并有相同类型的水，它将自己清空，成为一个空水壶。
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        // Server-side.
        if (!world.isRemote) {
            // Held item. 手持物品
            ItemStack heldItem = player.getHeldItem(hand);

            // NBT Tag of canteen (which shouldn't be null by now). 水壶的NBT标签（现在不应该是空的）。
            NBTTagCompound nbt = heldItem.getTagCompound();

            if (heldItem.getMetadata() == 0) {

                return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
            } else {

                player.setActiveHand(hand);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
            }
        } else {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
    }

    //未本地化名
    @Override
    public String getTranslationKey(ItemStack stack) {
        int metadata = stack.getMetadata();
        if (metadata < 4 && metadata >= 0) {
            return "item." + OverLast.MOD_ID + "." + "drinking_potion_" + metadata;
        } else {
            stack.setItemDamage(0);
            return "item." + OverLast.MOD_ID + "." + "drinking_potion";
        }

    }

    // When crafted, give this item an NBT tag to store its number of sips available, along with durability.
    //在制作时，给这个物品一个NBT标签，以储存其可用的小口数量，以及耐久性。
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

        if (!stack.hasTagCompound()) {

            // Making a new NBT tag compound.
            NBTTagCompound nbt = new NBTTagCompound();


            if (stack.getMetadata() == 0) {
                nbt.setInteger("sips", 0);
            } else {
                nbt.setInteger("sips", canteenSips);
            }

            // Add durability tag.
            nbt.setInteger("durability", canteenDurability);
            stack.setTagCompound(nbt);
        }
    }

    // This is to ensure the canteen always has NBT.
    //这是为了确保水瓶始终有NBT。
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (!stack.hasTagCompound()) {

            // Making a new NBT tag compound.
            NBTTagCompound nbt = new NBTTagCompound();

            if (stack.getMetadata() == 0) {

                nbt.setInteger("sips", 0);
            } else {

                nbt.setInteger("sips", canteenSips);
            }

            // Add durability tag.
            nbt.setInteger("durability", canteenDurability);
            stack.setTagCompound(nbt);
        } else {

            // If there are zero sips left, make it an empty canteen. 如果剩下零口，就把它作为一个空水壶。
            NBTTagCompound nbt = stack.getTagCompound();

            if (nbt.getInteger("sips") <= 0) {

                stack.setItemDamage(0);
            }

            // If durability is 0, destroy the canteen.
            if (nbt.getInteger("durability") <= 0) {

                stack.shrink(1);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        // NBT Tag and percentLeft float to determine color.
        NBTTagCompound nbt = stack.getTagCompound();
        RangeMap<Float, String> floatTextFormattingRangeMap = TreeRangeMap.create();
        floatTextFormattingRangeMap.put(Range.closed(0.8f, 1.0f), TextFormatting.GREEN + "");
        floatTextFormattingRangeMap.put(Range.closedOpen(0.4f, 0.8f), TextFormatting.YELLOW + "");
        floatTextFormattingRangeMap.put(Range.closedOpen(0.0f, 0.4f), TextFormatting.RED + "");

        if (nbt != null) {

            int sips = nbt.getInteger("sips");
            float percentLeft = 1.0f * sips / canteenSips;

            String tooltipText = sips + I18n.format("item.overlast.drinking_potion.tooltip");

            tooltip.add(floatTextFormattingRangeMap.get(percentLeft) + tooltipText);

            // Durability info. In an if-statement just in case.
            if (nbt.hasKey("durability")) {

                tooltip.add(TextFormatting.GOLD + Integer.toString(nbt.getInteger("durability")) + I18n.format("item.overlast.drinking_potion.durability"));
            }
        } else {

            tooltip.add(TextFormatting.BLUE + I18n.format("item.overlast.drinking_potion.potion"));
        }
    }

    // Create sub items 创建物品堆
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (this.isInCreativeTab(tab)) {

            for (int i = 0; i < 4; i++) {

                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    // Makes it a drink.
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.DRINK;
    }

    // How long it takes to drink it (how long to show the animation).
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 32;
    }

    // Figure out when to show durability.
    @Override
    public boolean showDurabilityBar(ItemStack stack) {

        // NBT
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null) {

            // What is configured?
            // Show sips
            if (nbt.getInteger("sips") > 0) {
                return true;
            } else {

                return false;
            }
        } else {

            return false;
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        // NBT Tag and necessary variables.
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null) {


            return  1.0D * (nbt.getInteger("sips") * 100) / canteenSips;

        } else {

            return 1;
        }
    }
}