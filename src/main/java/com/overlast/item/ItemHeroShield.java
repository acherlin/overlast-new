package com.overlast.item;

import com.google.common.collect.Multimap;
import com.oblivioussp.spartanshields.item.ItemShieldBasic;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemHeroShield extends ItemShieldBasic {
    private final String name = "hero_shield";

    public ItemHeroShield(String unlocName, int maxDamage, ToolMaterial toolMaterial) {
        super(unlocName, maxDamage, toolMaterial);
    }


    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            modifiers.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(UUID.fromString("c0d49eb3-c7be-4509-a6c8-20c7ee186aa4"), "spartanshields:shieldMovementSpeed", 0.1D, 2));
            modifiers.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(UUID.fromString("045b59bd-7346-4be1-8ae2-25b67fdc6b47"), "spartanshields:shieldMovementKnockback", 0.5D, 0));
        }

        return modifiers;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return OreDictionary.containsMatch(false, OreDictionary.getOres("iron_ingot"), new ItemStack[]{repair});
    }


    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item.spartanshields:hero_shield.tooltip"));
    }



}
