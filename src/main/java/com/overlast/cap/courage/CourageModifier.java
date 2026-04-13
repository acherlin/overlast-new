package com.overlast.cap.courage;


/*
 * Where Courage is modified.
 */

import com.mrcrayfish.apexguns.core.ModItems;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.enchantment.UtilityAccessor;
import com.overlast.util.OverUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Loader;


public class CourageModifier {
    private int courageTimer = 0;
    private boolean startCharged = false;
    public void onPlayerUpdate(EntityPlayer player) {

        // Capabilities
        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
        ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);




        // Player's coordinates. 玩家的所处坐标
        double playerPosX = player.posX;
        double playerPosY = player.posY;
        double playerPosZ = player.posZ;

        // Player's Block position.
        BlockPos blockPos = player.getPosition();

        // Modifier from config
        float modifier = (float) OverConfig.MECHANICS.courageScale;

        if(courage.getCourage()>5) {
            if (!player.world.isDaytime()) {
                courage.decrease(0.002f * modifier);
            } else {
                courage.decrease(0.001f * modifier);
            }
        }

        //勇气值不会高于理智值
        if(courage.getCourage()>sanity.getSanity()) {
            courage.set(sanity.getSanity());
        }





        //20s脱战检测
        if(courageTimer<400){
            if(!OverUtil.UTIL.CheckNearbySRP(player)) {
                courageTimer++;
            }
            else {
                courageTimer = 0;
                startCharged=false;
            }
        } else {
            courageTimer = 0;
            startCharged=true;
        }

        //高理智时充能
        int courageChargeRate,courageChargeMaxValue,absorptionLevel;
        switch (OverUtil.UTIL.getPhase(player.getEntityWorld())) {
            case 0:
            case 1:
            case 2:
                courageChargeRate = 1;
                courageChargeMaxValue = 15;
                break;
            case 3:
            case 4:
            case 5:
                courageChargeRate = 2;
                courageChargeMaxValue = 20;
                break;
            case 6:
            case 7:
            case 8:
                courageChargeRate = 3;
                courageChargeMaxValue = 30;
                break;
            case 9:
            case 10:
                courageChargeRate = 4;
                courageChargeMaxValue = 40;
                break;
            default:
                courageChargeRate = 0;
                courageChargeMaxValue = 15;
        }

        switch ((int) (courage.getCourage()/10)) {
            case 0:
            case 1:
            case 2:absorptionLevel=1;break;
            case 3:
            case 4:
            case 5:absorptionLevel=2;break;
            case 6:
            case 7:
            case 8:absorptionLevel=3;break;
            case 9:
            case 10:absorptionLevel=4;break;
            default:
                absorptionLevel=1;
        }
        if(sanity.getSanity()>=90&&courage.getCourage()<courageChargeMaxValue&&startCharged) {
            courage.increase(0.005f * modifier*courageChargeRate);
        }
        if(startCharged&&courage.getCourage()>=10) {
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 12000, absorptionLevel-1, false, false));
            startCharged=false;
        }

    }

    public void onPlayerKillSRP(Entity entityKilled, DamageSource damageSource) {
        // Was this mob killed by a player? (and server-side).
        if ((damageSource.getDamageType().equals("bullet")||damageSource.getDamageType().equals("player")) && entityKilled instanceof EntityMob && !entityKilled.world.isRemote) {
            // Instance of player
            EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
            // Instance of mob
            EntityMob mob = (EntityMob) entityKilled;
            // Capability
            ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);

            // Courage amount
            if(compare(entityKilled,"infected")) {
                courage.increase(1);
            }else if(compare(entityKilled,"crude")) {
                courage.increase(1);
            }else if(compare(entityKilled,"awakened")) {
                courage.increase(4);
            }else if(compare(entityKilled,"derived")) {
                courage.increase(3);
            }else if(compare(entityKilled,"derived")) {
                courage.increase(3);
            }else if(compare(entityKilled,"deterrent")) {
                courage.increase(2);
            }else if(compare(entityKilled,"focused")) {
                courage.increase(2);
            }else if(compare(entityKilled,"inborn")) {
                courage.increase(1);
            }else if(compare(entityKilled,"primitive")) {
                courage.increase(2);
            }else if(compare(entityKilled,"adapted")) {
                courage.increase(3);
            }else if(compare(entityKilled,"pure")) {
                courage.increase(4);
            }else if(compare(entityKilled,"ancient")) {
                courage.increase(5);
            }

            // Check mob Glowing
            /*
            if(!(mob.getActivePotionEffect(MobEffects.GLOWING)==null)) {
                if(player.getHealth()>=20) {
                    player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 12000, 0, false, false));
                } else {
                    player.setHealth(player.getHealth() + 4);
                }
            }
            */

        }
    }

    public void onPlayerHurt(Entity entityHurted, DamageSource damageSource, LivingHurtEvent event) {
        if(!entityHurted.world.isRemote && entityHurted instanceof EntityPlayer && damageSource.getDamageType().equals("mob")) {
            // Instance of player
            EntityPlayer player = (EntityPlayer) entityHurted;
            Entity monster = damageSource.getTrueSource();
            // Capability
            ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
            ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);
            if(courage.getCourage()>=5) {
                if (compare(monster, "infected")) {
                    courage.decrease(1f);
                    event.setAmount(event.getAmount()*0.75f);
                } else if (compare(monster, "primitive")) {
                    courage.decrease(2f);
                    event.setAmount(event.getAmount()*0.6f);
                } else if (compare(monster, "adapted")) {
                    courage.decrease(3f);
                    event.setAmount(event.getAmount()*0.5f);
                } else if (compare(monster, "pure")) {
                    courage.decrease(4f);
                    event.setAmount(event.getAmount()*0.4f);
                } else if (compare(monster, "ancient")) {
                    courage.decrease(5f);
                    event.setAmount(event.getAmount()*0.4f);
                }else if(compare(monster,"crude")) {
                    courage.increase(1);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"awakened")) {
                    courage.increase(2);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"derived")) {
                    courage.increase(2);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"derived")) {
                    courage.increase(2);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"deterrent")) {
                    courage.increase(2);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"focused")) {
                    courage.increase(2);
                    event.setAmount(event.getAmount()*0.6f);
                }else if(compare(monster,"inborn")) {
                    courage.increase(1);
                    event.setAmount(event.getAmount()*0.75f);
                }
            }
            if(courage.getCourage()>=30&&sanity.getSanity()>=50&&event.getAmount()>player.getHealth()&&((player.getActivePotionEffect(MobEffects.RESISTANCE) ==null||(!(player.getActivePotionEffect(MobEffects.RESISTANCE) ==null)&&player.getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier()<5)))) {
                courage.decrease(30);
                event.setAmount(0);
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 4, false, false));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 5, false, false));
            }
        }

        //apex枪械联动
        if (!entityHurted.world.isRemote && Loader.isModLoaded("apexguns")&&damageSource.getDamageType().equals("bullet") && damageSource.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) damageSource.getTrueSource();
            if(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == ModItems.SPITFIRE) {
                ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);
                if(courage.getCourage()>=20) {
                    UtilityAccessor.damageTarget(event.getEntityLiving(), DamageSource.MAGIC, courage.getCourage()/20);
                }
            }
            if(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == ModItems.KRABER) {
                if (event.getAmount() <= 40) {
                    UtilityAccessor.damageTarget(event.getEntityLiving(), DamageSource.MAGIC, 40);
                }
            }
        }

    }


    public boolean compare(Entity target,String srp){
        String srp2 = "class com.dhanantry.scapeandrunparasites.entity.monster."+srp;
        if(target.getClass().toString().length()>=srp2.length()) {
            String srp1 = String.valueOf(target.getClass()).substring(0, srp2.length());
            return srp1.equals(srp2);
        }else {
            return false;
        }
    }
}