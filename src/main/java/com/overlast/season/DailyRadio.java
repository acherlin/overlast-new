package com.overlast.season;

import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.entity.monster.deterrent.nexus.EntityVenkrolSIV;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.fantasticsource.dynamicstealth.common.potions.Potions;
import com.overlast.config.OverConfig;
import com.overlast.finalbattle.FBTexts;
import com.overlast.util.OverWorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;


@Mod.EventBusSubscriber
public class DailyRadio {

    public enum RadioPhase {
        daily,
        dailySpring,
        dailySummer,
        dailyFall,
        dailyWinter,
        dailyEnd;
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingUpdateEvent event) {

        if (!event.getEntity().getEntityWorld().isRemote &&event.getEntity() instanceof EntityPlayer) {
            // Player
            EntityPlayer player = (EntityPlayer) event.getEntity();
            // World
            World world = player.world;

            // Time
            long worldTime = world.getWorldTime();
            MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();

            if (player == mc.getPlayerList().getPlayers().get(0) || mc.isSinglePlayer()) {
                //向玩家发送广播
                TextComponentTranslation i18season;
                if (worldTime % 24000 == 43) {
                    //获取季节
                    int seasonInt = OverWorldData.seasonToInt(WorldSeason.getSeason());
                    switch (seasonInt) {
                        case 1:
                            i18season = new TextComponentTranslation("message.seasons.winter");
                            break;
                        case 2:
                            i18season = new TextComponentTranslation("message.seasons.spring");
                            break;
                        case 3:
                            i18season = new TextComponentTranslation("message.seasons.summer");
                            break;
                        case 4:
                            i18season = new TextComponentTranslation("message.seasons.fall");
                            break;
                        default:
                            i18season = new TextComponentTranslation("message.seasons.error");
                    }

                    mc.getPlayerList().sendMessage(new TextComponentTranslation("message.seasons.forecast0", i18season, WorldSeason.getDaysIntoSeason()));

                    if (OverConfig.MECHANICS.enableRadio) {
                        //小于7级播放常规广播
                        if (WorldSeason.getWorldState() < 3 && SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()) < 7) {
                            int seatext = (int) (Math.random() * 20);
                            sendMessageOnPhase(RadioPhase.daily,seatext);
                            //春季
                            seatext = (int) (Math.random() * 7);
                            if (seasonInt == 2)
                                sendMessageOnPhase(RadioPhase.dailySpring,seatext);
                            //夏季
                            seatext = (int) (Math.random() * 5);
                            if (seasonInt == 3)
                                sendMessageOnPhase(RadioPhase.dailySummer,seatext);
                            //秋季
                            seatext = (int) (Math.random() * 6);
                            if (seasonInt == 4)
                                sendMessageOnPhase(RadioPhase.dailyFall,seatext);
                            //冬季
                            seatext = (int) (Math.random() * 8);
                            if (seasonInt == 1)
                                sendMessageOnPhase(RadioPhase.dailyWinter,seatext);
                        }
                        int seatext = (int) (Math.random() * 20);
                        if (WorldSeason.getWorldState() < 3 && SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()) >= 7) {
                            sendMessageOnPhase(RadioPhase.dailyEnd,seatext);
                        }
                    }

                }

                if (OverConfig.MECHANICS.enableDailyBOSS && (worldTime % 24000 == 100 && (player == mc.getPlayerList().getPlayers().get(0) || mc.isSinglePlayer()) && SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()) == 8)) {
                    List<EntityPlayer> dim0 = mc.getWorld(0).playerEntities;
                    if (dim0.isEmpty()) return;
                    int RandPlayer = (int) (Math.random() * dim0.size()); //范围主世界随机一个玩家()
                    EntityPlayer TargetPlayer = dim0.get(RandPlayer);

                    TargetPlayer.sendMessage(new TextComponentTranslation("message.seasons.forecast1"));

                    double playerPosX = TargetPlayer.posX;
                    double playerPosY = TargetPlayer.posY;
                    double playerPosZ = TargetPlayer.posZ;
                    double randOffsetToSummonThem = (Math.random() * 30) + 10;
                    double posOrNeg = Math.round(Math.random());
                    if (posOrNeg == 0) {
                        randOffsetToSummonThem = randOffsetToSummonThem * -1;
                    }

                    int RandMob = (int) (Math.random() * 3);

                    if (RandMob == 0) {//四级柱子
                        EntityVenkrolSIV them = new EntityVenkrolSIV(player.world);
                        them.setLocationAndAngles(playerPosX + randOffsetToSummonThem, playerPosY + 2, playerPosZ + randOffsetToSummonThem, 0.0f, 0);
                        them.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600, 0, false, false));
                        them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 0, false, false));
                        if (Loader.isModLoaded("dynamicstealth")) {
                            them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
                        }
                        them.preventEntitySpawning = true;
                        for (int x = -1; x < 2; x++) {
                            for (int y = -1; y < 9; y++) {
                                for (int z = -1; z < 2; z++) {
                                    them.world.setBlockToAir(them.getPosition().add(x, y, z));
                                }
                            }
                        }
                        TargetPlayer.world.spawnEntity(them);

                    } else if (RandMob == 1) {//远古君魔
                        EntityTerla them = new EntityTerla(player.world);
                        them.setLocationAndAngles(playerPosX + randOffsetToSummonThem, 80, playerPosZ + randOffsetToSummonThem, 0.0f, 0);
                        them.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600, 0, false, false));
                        them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 0, false, false));
                        if (Loader.isModLoaded("dynamicstealth")) {
                            them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
                        }
                        them.preventEntitySpawning = true;
                        TargetPlayer.world.spawnEntity(them);
                    } else {//远古惧魔
                        EntityOronco them = new EntityOronco(player.world);
                        them.setLocationAndAngles(playerPosX + randOffsetToSummonThem, 150, playerPosZ + randOffsetToSummonThem, 0.0f, 0);
                        them.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 600, 0, false, false));
                        them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 0, false, false));
                        if (Loader.isModLoaded("dynamicstealth")) {
                            them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
                        }
                        them.preventEntitySpawning = true;
                        TargetPlayer.world.spawnEntity(them);
                    }
                }

            }
        }
    }

    public void sendMessageOnPhase(RadioPhase phase, int order) {
        String key = String.format("message.seasons.%s%d", phase.name(), order);
        MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();
        mc.getPlayerList().sendMessage(new TextComponentTranslation(key));
    }
    public void sendMessageOnPhase(RadioPhase phase) {
        String key = String.format("message.seasons.%s", phase.name());
        MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();
        mc.getPlayerList().sendMessage(new TextComponentTranslation(key));
    }
}