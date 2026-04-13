package com.overlast.handlers;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPInfected;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.entity.monster.deterrent.nexus.EntityVenkrolSIV;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.*;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.overlast.OverLast;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.gui.RenderHUD;
import com.overlast.lib.ModMobEffects;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import com.overlast.util.client.KeyBinds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStone;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


@EventBusSubscriber(modid = OverLast.MOD_ID)
public class EventHandlerServer {

    private static int updateTimer = 0;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBinds.KEY_SWITCH.isPressed()) {
            RenderHUD.switchhud = !RenderHUD.switchhud;
        }
    }

    //属性条渲染事件
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerStatusUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (RenderHUD.StatusUpdate>=2&&RenderHUD.StatusUpdate<1442) {
                player.sendStatusMessage(new TextComponentTranslation("message.finalBattle.timing1",1,4,(1442-RenderHUD.StatusUpdate)/5), true);
            } else if(RenderHUD.StatusUpdate>=1442&&RenderHUD.StatusUpdate<2882) {
                player.sendStatusMessage(new TextComponentTranslation("message.finalBattle.timing1",2,4,(2882-RenderHUD.StatusUpdate)/5), true);
            }else if(RenderHUD.StatusUpdate>=2882&&RenderHUD.StatusUpdate<=4322) {
                player.sendStatusMessage(new TextComponentTranslation("message.finalBattle.timing1",3,4,(4322-RenderHUD.StatusUpdate)/5), true);
            }else if(RenderHUD.StatusUpdate>=4322&&RenderHUD.StatusUpdate<=4802) {
                player.sendStatusMessage(new TextComponentTranslation("message.finalBattle.timing2",4,4,(4802-RenderHUD.StatusUpdate)/5), true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerMining(BlockEvent.HarvestDropsEvent e) {
        if (e.getHarvester() != null && !(e.getHarvester().getActivePotionEffect(ModMobEffects.FORTUNATE) == null)) {
            if (e.isSilkTouching())
                return;
            Block origBlock = e.getState().getBlock();
            if (!e.getHarvester().getHeldItemMainhand().canHarvestBlock(e.getState()))
                return;
            if (origBlock instanceof BlockStone)
                return;
            if (!(origBlock instanceof BlockOre))
                return;
            if(origBlock == Blocks.DIAMOND_ORE || origBlock == Blocks.COAL_ORE || origBlock == Blocks.LAPIS_ORE || origBlock == Blocks.EMERALD_ORE || origBlock == Blocks.QUARTZ_ORE || origBlock == Blocks.QUARTZ_ORE)
                e.getDrops().get(0).grow(1);
        }
    }

    /*
    @SubscribeEvent
    public void onPlayerMining(BlockEvent.HarvestDropsEvent e) {
        if(e.getHarvester() != null&&!(e.getHarvester().getActivePotionEffect(ModMobEffects.FORTUNATE) ==null)) {
            ItemStack pick = e.getHarvester().getHeldItemMainhand();
            if(e.isSilkTouching())
                return;
            List<ItemStack> list = e.getDrops();
            for(int x = 0; x < list.size(); x++) {
                ItemStack stack = list.get(x);
                Block origBlock = e.getState().getBlock();
                if(!e.getHarvester().getHeldItemMainhand().canHarvestBlock(e.getState()))
                    return;
                if(origBlock instanceof BlockStone)
                    return;
                if(!(origBlock instanceof BlockOre))
                    return;
                if(stack.getCount() == 1) {
                    if(e.getHarvester().getRNG().nextInt(100) < 25) { //25%概率触发
                        int quantity = e.getHarvester().getRNG().nextInt(3);
                        list.get(x).grow(quantity);
                        pick.damageItem(e.getHarvester().getRNG().nextInt(quantity), e.getHarvester());//伤害工具耐久
                    }
                }
                else {
                    if(e.getHarvester().getRNG().nextInt(100) < 20) {
                        float mult = 1.25f + (e.getHarvester().getRNG().nextFloat() * 3 * 0.25f);
                        int count = Math.round(stack.getCount() * mult);
                        stack.grow(count);
                        pick.damageItem(e.getHarvester().getRNG().nextInt(3) + 1, e.getHarvester());
                    }
                }
            }
        }
    }
    */


    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntity().world.isRemote) {
            if (event.getEntity() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                if (!(player.getActivePotionEffect(ModMobEffects.EVIL) == null)&&player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null) {
                    player.removePotionEffect(MobEffects.INSTANT_HEALTH);
                    player.removePotionEffect(MobEffects.REGENERATION);
                }
            }
            EntityUpdate(event.getEntity());

            if (event.getEntity() instanceof EntityAnimal) {
                EntityAnimal annimals = (EntityAnimal) event.getEntity();
                if (!(annimals.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    annimals.removePotionEffect(SRPPotions.COTH_E);
                    annimals.removePotionEffect(SRPPotions.FEAR_E);
                    annimals.removePotionEffect(SRPPotions.BLEED_E);
                    annimals.removePotionEffect(SRPPotions.CORRO_E);
                    annimals.removePotionEffect(SRPPotions.VIRA_E);
                    annimals.removePotionEffect(SRPPotions.EPEL_E);
                }
            }
            if (event.getEntity() instanceof EntityVillager) {
                EntityVillager villager = (EntityVillager) event.getEntity();
                if (!(villager.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    villager.removePotionEffect(SRPPotions.COTH_E);
                    villager.removePotionEffect(SRPPotions.FEAR_E);
                    villager.removePotionEffect(SRPPotions.BLEED_E);
                    villager.removePotionEffect(SRPPotions.CORRO_E);
                    villager.removePotionEffect(SRPPotions.EPEL_E);
                }
            }
        }
    }

    public void EntityUpdate(Entity entity) {
        if (updateTimer < 20) {
            updateTimer++;
        } else {
            updateTimer = 0;
            if (entity instanceof EntityMob) {
                // Instance of player.
                EntityMob mob = (EntityMob) entity;

                if (String.valueOf(mob.getClass()).startsWith("class com.dhanantry")) {
                    BlockPos blockPos = mob.getPosition();

                    /*
                    Collection<PotionEffect> collectPotion = mob.getActivePotionEffects();
                    for(int i = 0; i < collectPotion.size(); i++) {
                        if (!collectPotion.iterator().hasNext())
                            return;
                        PotionEffect Potions = collectPotion.iterator().next();
                        mob.removeActivePotionEffect(Potions.getPotion());
                        OverLast.logger.info("Potions removed");
                    }
                    */


                    //是寄生兽，且处于阳光下，则会燃烧，并且排斥
                    if (WorldSeason.getSeason() == Season.SUMMER && mob.world.isDaytime() && mob.world.canBlockSeeSky(blockPos)&&OverConfig.SEASONS.enableSummerParasiteEffect) {
                        mob.setFire(2);
                        //大好太阳，故排斥之
                        if(mob.world.getWorldTime()%24000>=3000&&mob.world.getWorldTime()%24000<=6000)
                        mob.addPotionEffect(new PotionEffect(SRPPotions.EPEL_E, 100, 0, false, false));
                    }
                    //是寄生兽，寒冬，处于海平面以上，我主强化
                    if (WorldSeason.getSeason() == Season.WINTER && mob.posY >= mob.world.getSeaLevel() - 15&&OverConfig.SEASONS.enableWinterPlayerEffect) {
                        mob.addPotionEffect(new PotionEffect(MobEffects.SPEED, 100, 0, false, false));
                        mob.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 100, 0, false, false));
                    }

                }

                if(mob instanceof EntityTerla||mob instanceof EntityOronco||mob instanceof EntityVenkrolSIV) {
                    if(WorldSeason.getWaveNum()>=1) {
                        AxisAlignedBB boundingBoxPlayers = mob.getEntityBoundingBox().grow(8, 4, 8);
                        List nearbyPlayers = mob.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
                        // And for players.
                        for (int numPlayers = 0; numPlayers < nearbyPlayers.size(); numPlayers++) {
                            // Chosen player
                            EntityPlayerMP otherPlayer = (EntityPlayerMP) nearbyPlayers.get(numPlayers);
                            otherPlayer.addPotionEffect(new PotionEffect(ModMobEffects.EVIL, 200, 0, false, false));
                        }
                    }
                }
            }
            if (entity instanceof EntityPInfected) {
                double dx = entity.posX;
                double dy = entity.posY;
                double dz = entity.posZ;
                if (!(((EntityPInfected) entity).getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)
                        && ((EntityPInfected) entity).getActivePotionEffect(ModMobEffects.PARASITESPURIFY).getDuration() <= 40) {
                    EntityMob infAnnimals = (EntityMob) entity;
                    if (infAnnimals instanceof EntityDorpa) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntitySpider entityageable = new EntitySpider(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfBear) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityPolarBear(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfCow) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityCow(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfEnderman) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityEnderman entityageable = new EntityEnderman(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfHorse) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityHorse(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfHuman) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityZombie entityageable = new EntityZombie(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfPig) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityPig(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfSheep) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntitySheep(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfVillager) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityVillager entityageable = new EntityVillager(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfWolf) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityWolf(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                }
            }
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (!(player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    if (OverConfig.MECHANICS.enableSanity) {
                        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
                        sanity.increase(0.05f);
                    }
                    player.removePotionEffect(SRPPotions.COTH_E);
                    player.removePotionEffect(SRPPotions.FEAR_E);
                    player.removePotionEffect(SRPPotions.BLEED_E);
                    player.removePotionEffect(SRPPotions.CORRO_E);
                    player.removePotionEffect(SRPPotions.VIRA_E);
                    player.removePotionEffect(SRPPotions.EPEL_E);
                    player.removePotionEffect(SRPPotions.RAGE_E);
                    player.removePotionEffect(SRPPotions.SENS_E);
                    player.removePotionEffect(SRPPotions.PREY_E);
                    player.removePotionEffect(SRPPotions.DEBAR_E);
                    player.removePotionEffect(SRPPotions.DLER_E);
                    player.removePotionEffect(SRPPotions.FOSTER_E);
                    player.removePotionEffect(SRPPotions.LINK_E);
                }
            }
        }
    }

}