package com.overlast.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemWoodenSwordplus extends ItemSword  {
    private final String name = "wooden_sword_plus";
    public ItemWoodenSwordplus() {
        super(ToolMaterial.WOOD);
        this.setTranslationKey(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);

    }


    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        HashMultimap hashMultimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            hashMultimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.0D, 0));
        }
        return (Multimap<String, AttributeModifier>)hashMultimap;
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack((Item)this);
            Map<Enchantment, Integer> map = new HashMap<>();
            map.put(Enchantments.SHARPNESS, Integer.valueOf(18));
            EnchantmentHelper.setEnchantments(map, stack);
            items.add(stack);
        }
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.overlast.wooden_sword_plus.tooltip"));
    }

}
