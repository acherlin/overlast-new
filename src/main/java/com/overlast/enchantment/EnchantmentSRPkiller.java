package com.overlast.enchantment;

import com.overlast.OverLast;
import com.overlast.config.OverConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EnchantmentSRPkiller extends Enchantment
{
    private final String name = "srpkiller";
    //OverConfig.CUSTOM.SRPEnchantRarity==1 EnumEnchantmentType.WEAPON
    public EnchantmentSRPkiller()
    {
        super(OverConfig.CUSTOM.SRPEnchantRarity==0?Rarity.COMMON:(OverConfig.CUSTOM.SRPEnchantRarity==1?Rarity.UNCOMMON:(OverConfig.CUSTOM.SRPEnchantRarity==2?Rarity.RARE:Rarity.VERY_RARE)),
                OverConfig.CUSTOM.SRPEnchantType==0?EnumEnchantmentType.ALL:(OverConfig.CUSTOM.SRPEnchantType==1?EnumEnchantmentType.WEAPON:(OverConfig.CUSTOM.SRPEnchantType==2?EnumEnchantmentType.FISHING_ROD:EnumEnchantmentType.BOW)),
                new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName(OverLast.MOD_ID + "."+name);
        this.setRegistryName(name);
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 16 + enchantmentLevel * 5;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 21 + enchantmentLevel * 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && Enchantments.SWEEPING != ench;
    }

    public static boolean compare(Entity target){
        String srp1 =String.valueOf(target.getClass()).substring(0,19);
        String srp2 = "class com.dhanantry";
        if(srp1.equals(srp2))
            return true;
        else
            return false;
    }
    /*
   @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.world.isRemote)
        {
            ItemStack heldItemMainhand = player.getHeldItemMainhand();
            int level = EnchantmentHelper.getEnchantmentLevel(
                    EnchantmentRegistryHandler.SRPKILLER, heldItemMainhand);
            if (level > 0)
            {

                Entity target = event.getTarget();
                String srp =String.valueOf(target.getClass()).substring(0,19);
                String srp2 = "class com.dhanantry";
                if(srp.equals(srp2)) {
                    target.world.createExplosion(null,
                            target.posX, target.posY, target.posZ, 2 * level, false);
                }

            }
        }


    }

    @SubscribeEvent
    public static void onArrowAttack(ProjectileImpactEvent.Arrow event) {
        EntityArrow e = event.getArrow();
        if (!e.world.isRemote) {
            if (e.shootingEntity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) e.shootingEntity;
                if (EnchantmentHelper.getEnchantmentLevel( EnchantmentRegistryHandler.SRPKILLER, player.getHeldItemMainhand()) >0)
                    e.world.createExplosion(null, e.posX, e.posY, e.posZ, 2.0f, true);
                    player.sendMessage(new TextComponentString("使用了爆炸"));
                }
            }
    }

     */


    @SubscribeEvent
    public static void HandleEnchant(LivingHurtEvent fEvent)
    {
        if(!fEvent.getSource().damageType.equals("player") && !fEvent.getSource().damageType.equals("mob"))
            return;

        if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase))
            return;

        EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
        if(attacker == null)
            return;

        ItemStack dmgSource = ((EntityLivingBase)fEvent.getSource().getTrueSource()).getHeldItemMainhand();
        if(dmgSource == null)
            return;

        if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandler.SRPKILLER, dmgSource) <= 0)
            return;


        int levelPurging = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandler.SRPKILLER, dmgSource);

        if(Math.random() <= 0.5 + (0.1f * levelPurging)){

            UtilityAccessor.damageTarget(fEvent.getEntityLiving(), DamageSource.MAGIC, 1.25f + (OverConfig.CUSTOM.SRPEnchantBaseDamage * levelPurging));
            fEvent.getEntityLiving().hurtResistantTime = 0;
            fEvent.setAmount(fEvent.getAmount() * 1.2f);

        }
    }
}