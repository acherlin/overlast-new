package com.overlast.cap.sanity;

import com.creativemd.playerrevive.server.PlayerReviveServer;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityMudo;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.fantasticsource.dynamicstealth.common.potions.Potions;
import com.overlast.cap.courage.CourageProvider;
import com.overlast.cap.courage.ICourage;
import com.overlast.config.OverConfig;
import com.overlast.lib.ModMobEffects;
import com.overlast.packet.OverPackets;
import com.overlast.packet.SummonInfoPacket;
import com.overlast.util.OverServerEffects;
import com.overlast.util.OverUtil;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.List;

/*
 * Where sanity is modified.
 */

public class SanityModifier {

    // The first variable is used for the timer at the end of onPlayerUpdate to allow for a hallucination once per 20 ticks.
    // The other is for spawning "Them".
    // 第一个变量用于onPlayerUpdate末尾的定时器，以允许每20个刻度出现一次幻觉。
    // 另一个是用来产生 "他们 "的。
    private int lucidTimer = 0;
    private int spawnThemTimer = 0;
    private int sanTimer = 0;

    public void onPlayerUpdate(EntityPlayer player) {

        // Capabilities
        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
        ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);

        // Block position of player.
        BlockPos playerPos = player.getPosition();

        // ACTUAL position of player.
        double playerPosX = player.posX;
        double playerPosY = player.posY;
        double playerPosZ = player.posZ;

        // Lists of entities near the player.
        AxisAlignedBB boundingBox = player.getEntityBoundingBox().grow(7, 2, 7);
        AxisAlignedBB boundingBoxPlayers = player.getEntityBoundingBox().grow(5, 3, 5);
        List nearbyMobs = player.world.getEntitiesWithinAABB(EntityMob.class, boundingBox);
        List nearbyAnimals = player.world.getEntitiesWithinAABB(EntityAnimal.class, boundingBox);
        List nearbyPlayers = player.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
        List nearbyVillagers = player.world.getEntitiesWithinAABB(EntityVillager.class, boundingBoxPlayers);

        // Modifier from config
        float modifier = (float) OverConfig.MECHANICS.sanityScale;

        // Being awake late at night is only for crazy people and college students.
        //深夜不睡觉只是疯子和大学生的专利。
        if (!player.world.isDaytime() && playerPosY >= player.world.getSeaLevel()) {

            sanity.decrease(0.001f * modifier);
        }

        // Constant drain in caves... because why not! y轴过高过低也给我掉理智！
        if (playerPosY >= (player.world.getSeaLevel() + 50)) {

            sanity.decrease(0.0005f * modifier);
        }

        if (playerPosY <= (player.world.getSeaLevel() - 15)) {

            sanity.decrease(0.0015f * modifier);
        }

        if (playerPosY <= player.world.getSeaLevel()-30) {

            sanity.decrease(0.0015f * modifier);
        }

        // Being in the nether or the end isn't too sane. 身处下界或末世并不太理智。
        if (player.dimension == -1 || player.dimension == 1) {

            sanity.decrease(0.002f * modifier);
        }


        if(OverConfig.MECHANICS.enableLightEffect&&OverUtil.UTIL.checkSanityDimBlacklist(player.getEntityWorld().provider.getDimension())) {
            // Being in the dark in general, is pretty spooky. 一般来说，在黑暗中，是相当可怕的。
            //有夜视的情况下，免疫亮度4以下的理智降低
            if (player.getActivePotionEffect(MobEffects.NIGHT_VISION) ==null&&player.world.getLight(playerPos, true) < 2 && player.dimension != -1 && player.dimension != 1) {
                sanity.decrease(0.02f * modifier);
            } else if (player.getActivePotionEffect(MobEffects.NIGHT_VISION) ==null&&player.world.getLight(playerPos, true) < 4 && player.dimension != -1 && player.dimension != 1) {
                sanity.decrease(0.01f * modifier);
            } else if (player.world.getLight(playerPos, true) < 7 && player.dimension != -1 && player.dimension != 1 && (playerPosY <= player.world.getSeaLevel())) {

                sanity.decrease(0.005f * modifier);
            } else if (player.world.getLight(playerPos, true) > 14 && player.dimension != -1 && player.dimension != 1) {

                sanity.increase(0.001f * modifier);
            }
        }



        //高理智对抗寄生虫
        //寄群之唤
        if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) ==null)&&!(player.getActivePotionEffect(SRPPotions.COTH_E) ==null)&&sanity.getSanity()>=80) {
            if(OverConfig.MECHANICS.enableCourage&&courage.getCourage()>5) {
                courage.decrease(1.5f * modifier);
            }else {
                sanity.decrease(3 * modifier);
            }
            player.removePotionEffect(SRPPotions.COTH_E);
            player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 30, 0, false, false));
        }

        //恐惧
        if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) ==null)&&!(player.getActivePotionEffect(SRPPotions.FEAR_E) ==null)&&sanity.getSanity()>=80) {
            if(OverConfig.MECHANICS.enableCourage&&courage.getCourage()>5) {
                courage.decrease(1f * modifier);
            } else {
                sanity.decrease(2 * modifier);
            }
            player.removePotionEffect(SRPPotions.FEAR_E);
            player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 20, 0, false, false));
        }

        //穿刺 DLER_E
        if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) ==null)&&!(player.getActivePotionEffect(SRPPotions.DLER_E) ==null)&&OverConfig.MECHANICS.enableCourage&&courage.getCourage()>5) {
            courage.decrease(2f * modifier);
            player.removePotionEffect(SRPPotions.DLER_E);
            player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 40, 0, false, false));
        }

        //腐蚀 CORRO_P
        if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) ==null)&&!(player.getActivePotionEffect(SRPPotions.CORRO_E) ==null)&&OverConfig.MECHANICS.enableCourage&&courage.getCourage()>5) {
            courage.decrease(3f * modifier);
            player.removePotionEffect(SRPPotions.FEAR_E);
            player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 60, 0, false, false));
        }


        //高温，过热会降低理智，但是有防火除外
        if(!(player.getActivePotionEffect(ModMobEffects.HEATRADIATION) ==null)&&(player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) ==null)) {
            sanity.decrease(0.002F);
        }

        if(!(player.getActivePotionEffect(ModMobEffects.OVERHEATING) ==null)&&(player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) ==null)) {
            sanity.decrease(0.003F);
        }



        // Now iterate through each mob that appears on the list of nearby mobs.
        for (int numMobs = 0; numMobs < nearbyMobs.size(); numMobs++) {

            // Chosen mob
            EntityMob mob = (EntityMob) nearbyMobs.get(numMobs);

            // Now change sanity according to what it is.
            if (mob instanceof EntityEnderman) {

                sanity.decrease(0.005f * modifier);
            }

            else if(String.valueOf(mob.getClass()).substring(0,19).equals("class com.dhanantry")) {
                if(OverConfig.MECHANICS.enableCourage&&courage.getCourage()>1) {
                    courage.decrease(0.001f * modifier);
                }else if(!String.valueOf(mob.getClass()).equals("class com.dhanantry.scapeandrunparasites.entity.monster.crude.EntityLesh")) {
                    sanity.decrease(0.005f * modifier);
                }
            }
        }

        // Do the same for animals.
        for (int numAnimals = 0; numAnimals < nearbyAnimals.size(); numAnimals++) {

            // Chosen animal
            EntityAnimal animal = (EntityAnimal) nearbyAnimals.get(numAnimals);

            // Now change sanity according to what it is.
            if (animal instanceof EntityWolf || animal instanceof EntityOcelot || animal instanceof EntityParrot) {

                sanity.increase(0.003f * modifier);
            }

            else if (animal instanceof EntitySheep) {

                sanity.increase(0.002f * modifier);
            }

            else if (animal instanceof EntityCow) {

                sanity.increase(0.001f * modifier);
            }
        }

        // And for players. 玩家贴贴
        for (int numPlayers = 0; numPlayers < nearbyPlayers.size(); numPlayers++) {

            // Chosen player
            sanity.increase(0.001f * modifier);

        }

        if (Loader.isModLoaded("playerrevive")) {
            //如果玩家倒地，贴贴就会变成恐惧
            if((PlayerReviveServer.isPlayerBleeding(player))) {
                //降低性能要求
                AxisAlignedBB boundingBoxBleedPlayers = player.getEntityBoundingBox().grow(48, 8, 48);
                List nearbyBleedPlayers = player.world.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxBleedPlayers);

                for (int numBleedPlayers = 0; numBleedPlayers < nearbyBleedPlayers.size(); numBleedPlayers++) {
                    EntityPlayerMP otherPlayer = (EntityPlayerMP) nearbyBleedPlayers.get(numBleedPlayers);
                    //如果玩家倒地，贴贴就会变成恐惧
                    ISanity othersanity = otherPlayer.getCapability(SanityProvider.SANITY_CAP, null);
                    othersanity.decrease(0.01f * modifier);
                }
            }
        }




        // Villagers are nice as well.
        for (int numVillagers = 0; numVillagers < nearbyVillagers.size(); numVillagers++) {

            sanity.increase(0.001f * modifier);
        }

        // ===========================================================================
        //                  The Side Effects of Insanity
        // ===========================================================================

        // Every 20 ticks (1 second) there is a chance for a hallucination to appear; visual, audial, or both.
        // In this case, a hallucination is a client-only particle/sound. The "things" (Maxwell refers to them as "Them") are a different area.
        // The more insane the player is, the bigger the chance is.
        // 精神错乱的副作用
        // 每20次（1秒）就有一次机会出现幻觉；视觉、听觉，或两者都有。
        // 在这种情况下，幻觉是一种只属于客户的粒子/声音。那些 "东西"（麦克斯韦称它们为 "它们"）是一个不同的区域。
        // 玩家越是疯狂，机会就越大。

        if(sanTimer<1200){
            sanTimer++;
        } else {
            sanTimer=0;
            //常规触发，一分钟一次
            int phase = OverUtil.UTIL.getPhase(player.getEntityWorld());
            int evopoint = OverUtil.UTIL.getAddEvoPoint(phase);

            //大于1w进化点数时候，低san会额外增加点数，一分钟触发一次
            if((sanity.getSanity() <= (sanity.getMaxSanity() * 0.3))) {
                if(phase>=3) {
                    //惩罚演化，20倍自然演化
                    OverUtil.UTIL.addEvoPoint(player.getEntityWorld(),evopoint*20);

                    //降低当前演化CD
                    /*
                    if(OverUtil.UTIL.getCooldown(player)>60) {
                        OverUtil.UTIL.setCooldown(player,OverUtil.UTIL.getCooldown(player)-60);
                    }
                    */

                    //致盲+灵魂视觉
                    if (Loader.isModLoaded("dynamicstealth")) {
                        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0, false, false));
                        player.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 400, 0, false, false));
                    } else {
                        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60, 0, false, false));
                        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, 0, false, false));
                    }

                    IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "InsanityAncientSoundLoud", "null", playerPosX, playerPosY, playerPosZ);
                    OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);

                    //低san随机文本
                    switch((int) (Math.random() * 10)) {
                        case 0:player.sendMessage(new TextComponentTranslation("message.lowsan.text0"));break;
                        case 1:player.sendMessage(new TextComponentTranslation("message.lowsan.text1"));break;
                        case 2:player.sendMessage(new TextComponentTranslation("message.lowsan.text2"));break;
                        case 3:player.sendMessage(new TextComponentTranslation("message.lowsan.text3"));break;
                        case 4:player.sendMessage(new TextComponentTranslation("message.lowsan.text4"));break;
                        case 5:player.sendMessage(new TextComponentTranslation("message.lowsan.text5"));break;
                        case 6:player.sendMessage(new TextComponentTranslation("message.lowsan.text6"));break;
                        case 7:player.sendMessage(new TextComponentTranslation("message.lowsan.text7"));break;
                        case 8:player.sendMessage(new TextComponentTranslation("message.lowsan.text8"));break;
                        case 9:player.sendMessage(new TextComponentTranslation("message.lowsan.text9"));break;
                    }
                }
            }
        }



        if (lucidTimer < 20) {

            // Increment timer until it reaches 20. 计时器递增，直到达到20。
            lucidTimer++;
        }
        else {

            // Reset timer 重置时间
            lucidTimer = 0;

            // Increment THIS timer
            spawnThemTimer++;

            //感染药水效果
            if(!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==0) {
                if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY)==null)) {
                    sanity.decrease(0.1f * modifier);
                    player.addPotionEffect(new PotionEffect(SRPPotions.COTH_E, 1200, 1, false, false));
                }
            }else if (!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==1) {
                if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY)==null)) {
                    sanity.decrease(0.2f * modifier);
                    player.addPotionEffect(new PotionEffect(SRPPotions.COTH_E, 1200, 3, false, false));
                }
            }

            // There'll only be hallucinations for players with less than 70% of their sanity. 只有理智不足50%的玩家才会出现幻觉。
            if (sanity.getSanity() <= (sanity.getMaxSanity() * 0.50)) {

                // Determine if a hallucination should appear. 确定是否应该出现幻觉。
                double chanceOfHallucination = (double) (sanity.getSanity() / 100) + 0.30;
                double randomLucidNumber = Math.random();
                boolean shouldSpawnHallucination = chanceOfHallucination < randomLucidNumber;//如果随机Hall<1.0

                // So... should one appear?  那么......应该出现一个吗？
                if (shouldSpawnHallucination) {

                    // Now pick one... more random numbers! 现在选一个......更多的随机数字!
                    double pickAHallucination = Math.random();
                    double randOffset = Math.random() * 6;
                    int posOrNeg = (int) Math.round(Math.random());

                    if (posOrNeg == 0) { randOffset = randOffset * -1; }

                    // As of now... ten possibilities... all weighted equally.
                    // These will be called on the client, so no one else can see/hear them. Random positions nearby the player too.
                    // 从现在起......有十种可能性......所有的权重都是一样的。
                    // 这些将在客户端调用，所以没有人可以看到/听到它们。玩家附近的随机位置也是如此。

                    // Enderman noise + particles
                    if (pickAHallucination >= 0 && pickAHallucination < 0.10) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "EndermanSound", "EndermanParticles", playerPosX+randOffset, playerPosY+1, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Zombie sound
                    else if (pickAHallucination >= 0.10 && pickAHallucination < 0.20) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "ZombieSound", "null", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Ghast sound
                    else if (pickAHallucination >= 0.20 && pickAHallucination < 0.30) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "GhastSound", "null", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Explosion sound + particles
                    else if (pickAHallucination >= 0.30 && pickAHallucination < 0.40) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "ExplosionSound", "ExplosionParticles", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Stone sound
                    else if (pickAHallucination >= 0.40 && pickAHallucination < 0.50) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "StoneBreakSound", "null", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Mist in the air... tf???????
                    else if (pickAHallucination >= 0.50 && pickAHallucination < 0.60) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "null", "CreepyMistParticles", playerPosX+randOffset, playerPosY+1, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // A guardian appearing in your face. This one still scares the crap out of me. 远古守卫者糊脸
                    else if (pickAHallucination >= 0.60 && pickAHallucination < 0.70) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "null", "GuardianParticles", playerPosX, playerPosY, playerPosZ);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Fire sounds + smoke particles
                    else if (pickAHallucination >= 0.70 && pickAHallucination < 0.80) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "FireSound", "SmokeParticles", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // A�villager sound... are they lost? 改成寄生虫
                    else if (pickAHallucination >= 0.80 && pickAHallucination < 0.90) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "InsanityAncientSoundLoud", "null", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }

                    // Lava sound
                    else if (pickAHallucination >= 0.90 && pickAHallucination <= 1.00) {

                        IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "LavaSound", "null", playerPosX+randOffset, playerPosY+randOffset, playerPosZ+randOffset);
                        OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                    }
                }
            }

            // There are other side effects of insanity other than hallucinations.
            // Here, the player's view is wobbled/distorted
            // Some weird ambience is added to make insanity feel more insane. And... weird. It's just the right word.
            // Also, They may come and attack you.

            // Make the screen of the insane player wobble.
            // 除了幻觉之外，精神错乱还有其他副作用。
            // 在这里，玩家的视线是晃动/扭曲的
            // 加入了一些奇怪的氛围，使精神错乱的感觉更加疯狂。还有......奇怪。这个词恰到好处。
            // 另外，他们可能会来攻击你。
            // 让疯狂的玩家的屏幕晃动起来。
            if (sanity.getSanity() <= (sanity.getMaxSanity() * 0.30)) {
                player.removePotionEffect(MobEffects.RESISTANCE);
                //todo 低理智效果转配置文件
                //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "coth", 100, 3, false, false);
                //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "invisibility", 100, 0, false, false);
                //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "resistance", 100, 0, false, false);
                //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "speed", 100, 1, false, false);

                OverUtil.UTIL.ConfigLowSanityPotions(player);

                //todo 低理智药水效果自定义
                //if(!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==0){
                    //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "resistance", 600, 1, false, false);
                    //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "speed", 600, 2, false, false);
                    //player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2, false, false));
                //} else if(!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==1){
                    //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "resistance", 600, 2, false, false);
                    //OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "speed", 600, 3, false, false);
                    //player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 3, false, false));
                //}
                OverUtil.UTIL.ConfigLowSanityParasitesPotions(player);
            }

            // Add some weird insanity ambiance.
            if (sanity.getSanity() <= (sanity.getMaxSanity() * 0.3)) {

                // Random chance so it doesn't overlap with itself.
                double randInsanityAmbience = Math.random(); //0.2开始吵人

                if (randInsanityAmbience < 0.10) {

                    IMessage msgStuff = new SummonInfoPacket.SummonInfoMessage(player.getCachedUniqueIdString(), "InsanityAncientSoundLoud", "null", playerPosX, playerPosY, playerPosZ);
                    OverPackets.net.sendTo(msgStuff, (EntityPlayerMP) player);
                }
            }



            // Add and spawn "Them". As of now, it's just a bunch of invisible endermen. They drop "Lucid Dream Essence."
            // They can be seen by all players, that's alright. They just like to gather near black holes void of sanity.
            // If the player's sanity is really low, spawn a bunch of "Them" and make "Them" attack the player.
            // 添加并催生 "他们"。就目前而言，它只是一群看不见的末影人。他们会掉落 "Lucid Dream Essence"。
            // 他们可以被所有的玩家看到，这没有问题。他们只是喜欢聚集在没有理智的黑洞附近。
            // 如果玩家的理智真的很低，就会产生一群 "他们 "并让 "他们 "攻击玩家。  EntityLesh
            if ((sanity.getSanity() <= (sanity.getMaxSanity() * 0.25)) && spawnThemTimer >= 15) {

                // Random numbers... gotta love random numbers. 随机数......得爱随机数。
                double randOffsetToSummonThem = Math.random() * 30;
                double posOrNeg = Math.round(Math.random());

                // Reset spawnThemTimer
                spawnThemTimer = 0;

                if (posOrNeg == 0) { randOffsetToSummonThem = randOffsetToSummonThem * -1; }
                //小幅增加点数，弥补新版本肉块合成bug带来的数值
                //OverUtil.UTIL.addEvoPoint(player.getEntityWorld(),2);
                // Instance of Them  它们的实例 移动肉块
                // 改版后会读取配置文件生成，先判断是否是指定维度，再判断实体类型和权重

                    //EntityLesh them = new EntityLesh(player.world);
                    // Position Them
                    //them.setLocationAndAngles(playerPosX + randOffsetToSummonThem, playerPosY + 2, playerPosZ + randOffsetToSummonThem, 0.0f, 0);

                    // Aggroe Them 攻击目标修改
                    //them.setAttackTarget((EntityLivingBase) player);

                    // Add to the "entity limit"... Them
                    //them.preventEntitySpawning = true;
                    // Summon Them
                    //player.world.spawnEntity(them);

                OverUtil.UTIL.ConfigLowSanityPools(player,randOffsetToSummonThem);
                }

            // Otherwise just rarely summon them.
            else if (sanity.getSanity() <= (sanity.getMaxSanity() * 0.30)) {

                // Random numbers... YEE
                double randChanceToSummonThem = Math.random();
                double randOffsetToSummonThem = Math.random() * 30;
                double posOrNeg = Math.round(Math.random());

                if (posOrNeg == 0) { randOffsetToSummonThem = randOffsetToSummonThem * -1; }

                if (randChanceToSummonThem < 0.03) {

                    // Instance of Them 他们的实例 裂兽
                    EntityMudo them = new EntityMudo(player.world);


                    // Position Them
                    them.setLocationAndAngles(playerPosX+randOffsetToSummonThem, playerPosY+2, playerPosZ+randOffsetToSummonThem, 0.0f, 0);

                    // Add to the "entity limit"... Them
                    them.preventEntitySpawning = true;

                    // Summon Them
                    player.world.spawnEntity(them);
                }
            }


        }
    }

    // This checks any consumed item by the player, and affects sanity accordingly. Just vanilla items for now.
    public void onPlayerConsumeItem(EntityPlayer player, ItemStack item) {
        OverUtil.UTIL.ConfigFoodSan(player,item);
    }

    // This checks if the player is sleeping.
    // It's mostly for servers, as not everyone may be asleep at the same time. This method alone doesn't run for too long on singleplayer.
    public void onPlayerSleepInBed(EntityPlayer player) {

        // Capability
        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);

        sanity.increase(0.01f);

        // Induce some hunger.
        OverServerEffects.affectPlayer(player.getCachedUniqueIdString(), "hunger", 20, 5, false, false);
    }

    // At this point, the player has awoke from their sleep. This "sleep" could've been 1 second or 1 day.
    // Figure out if it is daytime (the sleep is successful). If so, grant extra sanity and drain extra hunger.
    public void onPlayerWakeUp(EntityPlayer player) {

        // Capability
        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
        // Is it daytime? If not, the player just clicked "Leave Bed" or something related to try to cheat the system (and might've succeeded).
        //现在是白天吗？如果不是，玩家只是点击了 "离开床 "或相关的东西，试图欺骗系统（而且可能已经成功了）。
        if (player.world.getWorldTime() % 24000 <= 1000) {
            sanity.increase(33f);

            // Make player hungry for breakfast (or something...).
            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 6);
        }
    }
}