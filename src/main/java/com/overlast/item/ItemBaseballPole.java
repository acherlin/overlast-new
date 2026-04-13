package com.overlast.item;

import com.fantasticsource.dynamicstealth.common.potions.Potions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBaseballPole extends ItemSword  {
    private final String name = "baseball_pole";
    public ItemBaseballPole() {
        super(ToolMaterial.WOOD);
        this.setTranslationKey(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        HashMultimap hashMultimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            hashMultimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -0.8D, 0));
        }
        return (Multimap<String, AttributeModifier>)hashMultimap;
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack((Item)this);
            Map<Enchantment, Integer> map = new HashMap<>();
            map.put(Enchantments.KNOCKBACK, Integer.valueOf(10));
            EnchantmentHelper.setEnchantments(map, stack);
            items.add(stack);
        }
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.overlast.baseball_pole.tooltip"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        // Server side.
        if (!world.isRemote) {
        }
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

        // Server side.
        if (!world.isRemote && entityLiving instanceof EntityPlayerMP&&stack.getItemDamage()<53) {
            //
            if (Loader.isModLoaded("dynamicstealth")) {
                entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20, 0, false, false));
                entityLiving.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 300, 0, false, false));
            }
            // Basic variables.
            EntityPlayerMP player = (EntityPlayerMP) entityLiving;
            AxisAlignedBB boundingBox = player.getEntityBoundingBox().grow(64, 64, 64);
            List nearbyMobs = player.world.getEntitiesWithinAABB(EntityMob.class, boundingBox);
            // Now iterate through each mob that appears on the list of nearby mobs.
            for (int numMobs = 0; numMobs < nearbyMobs.size(); numMobs++) {

                // Chosen mob
                EntityMob mob = (EntityMob) nearbyMobs.get(numMobs);

                //Chosen SRP Mob
                if(String.valueOf(mob.getClass()).substring(0,19).equals("class com.dhanantry")) {
                    mob.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 300, 0, false, false));
                }
            }
            stack.setItemDamage(stack.getItemDamage()+5);

        }

        return stack;
    }

    // How long it takes to use it (how long to show the animation).
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 20;
    }
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.BOW;
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return slotChanged;
    }

}
