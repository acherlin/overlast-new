package com.overlast.finalbattle;

import com.creativemd.playerrevive.api.event.PlayerKilledEvent;
import com.overlast.OverLast;
import com.overlast.cap.courage.CourageProvider;
import com.overlast.cap.courage.ICourage;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.handlers.EventFinalBattle;
import com.overlast.season.WorldSeason;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = OverLast.MOD_ID)
public class FBTexts {
    public static FBTexts fbTexts =new FBTexts();

    public enum Phase {
        start,
        prepare,
        report,
        rankOne,
        rankTwo,
        rankThree,
        rankFour,
        death,
        end,
        bad,
        repaired,
        disrupted;
    }


    public void onTextSend(TextComponentTranslation text) {
        EventFinalBattle.mc.getPlayerList().sendMessage(text);
        EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentString(""));
    }

    //文本控制器
    public void onTextControl(int textTimer) {
        if (EventFinalBattle.battleFlag == 0) {
            switch (textTimer) {
                //决战开始
                case 5:
                    sendMessageOnPhase(Phase.start,0);
                    break;
                case 17:
                    sendMessageOnPhase(Phase.start,1);
                    break;
                case 29:
                    sendMessageOnPhase(Phase.start,2);
                    break;
                case 49:
                    onTextSend(new TextComponentTranslation("message.finalBattle.start3", WorldSeason.getBattlePosX(), WorldSeason.getBattlePosY(), WorldSeason.getBattlePosZ()));
                    break;
                case 89:
                    sendMessageOnPhase(Phase.start,4);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 129:
                    sendMessageOnPhase(Phase.start,5);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 161:
                    sendMessageOnPhase(Phase.start,6);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 181:
                    sendMessageOnPhase(Phase.start,7);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 201:
                    sendMessageOnPhase(Phase.start,8);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 221:
                    sendMessageOnPhase(Phase.start,9);
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 241:
                    onTextSend(new TextComponentTranslation("message.finalBattle.report1"));
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 251:
                    int punish=(EventFinalBattle.mc.getWorld(EventFinalBattle.dim).getGameRules().getBoolean("keepInventory"))?2:0;
                    onTextSend(new TextComponentTranslation("message.finalBattle.report2",FBWaves.fbWaves.getSurviveCount(EventFinalBattle.fbWorld)/4,WorldSeason.getDifficultyPoint()+punish,FBWaves.fbWaves.getSurviveCount(EventFinalBattle.fbWorld)/4+WorldSeason.getDifficultyPoint()+punish));
                    addAllSanity(10);
                    addAllCourage(10);
                    break;
                case 261:
                    sendMessageOnPhase(Phase.start,10);
                    addAllSanity(20);
                    addAllCourage(20);
                    break;
                case 271:
                    WorldSeason.setWaveNum(1);
                    WorldSeason.setFinalTime(1);
                    EventFinalBattle.finalBattle.onSaveData();
                    break;
            }
        }

        if (EventFinalBattle.battleFlag == 1) {
            switch (textTimer) {
                //决战开始
                case 40:
                    sendMessageOnPhase(Phase.rankOne,0);
                    break;
                case 80:
                    sendMessageOnPhase(Phase.rankOne,1);
                    break;
                case 120:
                    sendMessageOnPhase(Phase.rankOne,2);
                    break;
                case 160:
                    sendMessageOnPhase(Phase.rankOne,3);
                    break;
                case 200:
                    EventFinalBattle.finalBattle.onSetNode();
                    break;
                case 240:
                    sendMessageOnPhase(Phase.rankOne,4);
                    break;
            }
        }

        if (EventFinalBattle.battleFlag == 2) {
            switch (textTimer) {
                //决战开始
                case 5:
                    sendMessageOnPhase(Phase.rankTwo,0);
                    break;
                case 21:
                    sendMessageOnPhase(Phase.rankTwo,1);
                    break;
                case 41:
                    sendMessageOnPhase(Phase.rankTwo,2);
                    break;
                case 101:
                    sendMessageOnPhase(Phase.rankTwo,3);
                    break;
                case 181:
                    sendMessageOnPhase(Phase.rankTwo,4);
                    break;
                case 261:
                    sendMessageOnPhase(Phase.rankTwo,5);
                    break;
                case 341:
                    sendMessageOnPhase(Phase.rankTwo,6);
                    break;
                case 401:
                    sendMessageOnPhase(Phase.rankTwo,7);
                    break;
                case 481:
                    sendMessageOnPhase(Phase.rankTwo,8);
                    break;
                case 561:
                    sendMessageOnPhase(Phase.rankTwo,9);
                    break;
                case 641:
                    sendMessageOnPhase(Phase.rankTwo,10);
                    break;
                case 721:
                    sendMessageOnPhase(Phase.rankTwo,11);
                    break;
                case 801:
                    sendMessageOnPhase(Phase.rankTwo,12);
                    break;
                case 881:
                    sendMessageOnPhase(Phase.rankTwo,13);
                    break;
                case 921:
                    sendMessageOnPhase(Phase.rankTwo,14);
                    break;
                case 1469:
                    sendMessageOnPhase(Phase.rankTwo,15);
                    break;
            }
        }

        if (EventFinalBattle.battleFlag == 3) {
            switch (textTimer) {
                //适应种
                case 21:
                    sendMessageOnPhase(Phase.rankThree,0);
                    break;
                case 61:
                    sendMessageOnPhase(Phase.rankThree,1);
                    break;
                case 101:
                    sendMessageOnPhase(Phase.rankThree,2);
                    break;
            }
        }

        if (EventFinalBattle.battleFlag == 4) {
            switch (textTimer) {
                //同化种
                case 21:
                    sendMessageOnPhase(Phase.rankFour,0);
                    break;
                case 61:
                    sendMessageOnPhase(Phase.rankFour,1);
                    break;
                case 101:
                    sendMessageOnPhase(Phase.rankFour,2);
                    break;
                case 1660:
                    sendMessageOnPhase(Phase.rankFour,3);
                    break;

            }
        }

        if (EventFinalBattle.battleFlag == 5) {
            switch (textTimer) {
                //大结局
                case 21:
                    sendMessageOnPhase(Phase.end,0);
                    break;
                case 61:
                    sendMessageOnPhase(Phase.end,1);
                    break;
                case 101:
                    sendMessageOnPhase(Phase.end,2);
                    break;
                case 141:
                    sendMessageOnPhase(Phase.end,3);
                    break;
                case 181:
                    sendMessageOnPhase(Phase.end,4);
                    break;
                case 221:
                    sendMessageOnPhase(Phase.end,5);
                    break;
                case 261:
                    sendMessageOnPhase(Phase.end,6);
                    break;
                case 301:
                    sendMessageOnPhase(Phase.end,7);
                    break;
                case 341:
                    sendMessageOnPhase(Phase.end,8);
                    break;
                case 381:
                    sendMessageOnPhase(Phase.end,9);
                    break;
                case 425:
                    sendMessageOnPhase(Phase.end,10);
                    break;
                case 465:
                    sendMessageOnPhase(Phase.end,11);
                    break;
                case 501://Todo 可能存在服务端不兼容问题
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-0"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-1"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-2"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-3"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-4"));
                    onTextSend(new TextComponentTranslation("message.finalBattle.end12-5"));
                    break;
                case 541:
                    sendMessageOnPhase(Phase.end,13);
                    EventFinalBattle.fbWorld.setWorldTime(0);
                    break;
                case 561:
                    sendMessageOnPhase(Phase.end,14);
                    break;
                case 573:
                    sendMessageOnPhase(Phase.end,15);
                    break;
                case 593:
                    sendMessageOnPhase(Phase.end,16);
                    break;
                case 613:
                    sendMessageOnPhase(Phase.end,17);
                    break;
                case 633:
                    sendMessageOnPhase(Phase.end,18);
                    break;
                case 653:
                    sendMessageOnPhase(Phase.end,19);
                    break;
                case 673:
                    sendMessageOnPhase(Phase.end,20);
                    break;
                case 693:
                    sendMessageOnPhase(Phase.end,21);
                    break;
                case 713:
                    sendMessageOnPhase(Phase.report,3);
                    break;
                case 723:
                    onTextSend(new TextComponentTranslation("message.finalBattle.report4",WorldSeason.getScore()));
                    break;
                case 733:
                    sendMessageOnPhase(Phase.report,7);
                    break;
                case 743:
                    sendMessageOnPhase(Phase.end,22);
                    EventFinalBattle.finalBattle.FinalBattleOver(1);
                    break;
            }
        }

        if (EventFinalBattle.battleFlag == 6) {
            switch (textTimer) {
                //BE结局
                case 41:
                    sendMessageOnPhase(Phase.bad,0);
                    break;
                case 61:
                    sendMessageOnPhase(Phase.bad,1);
                    break;
                case 81:
                    sendMessageOnPhase(Phase.bad,2);
                    break;
                case 101:
                    sendMessageOnPhase(Phase.bad,3);
                    break;
                case 121:
                    sendMessageOnPhase(Phase.bad,4);
                    break;
                case 141:
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-0"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-1"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-2"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-3"));
                    EventFinalBattle.mc.getPlayerList().sendMessage(new TextComponentTranslation("message.finalBattle.end12-4"));
                    onTextSend(new TextComponentTranslation("message.finalBattle.end12-5"));
                    break;
                case 181:
                    sendMessageOnPhase(Phase.bad,5);
                    break;
                case 201:
                    sendMessageOnPhase(Phase.bad,6);
                    break;
                case 221:
                    sendMessageOnPhase(Phase.bad,7);
                    break;
                case 245:
                    sendMessageOnPhase(Phase.bad,8);
                    break;
                case 265:
                    sendMessageOnPhase(Phase.bad,9);
                    break;
                case 283:
                    sendMessageOnPhase(Phase.bad,10);
                    EventFinalBattle.finalBattle.FinalBattleOver(0);
                    break;
            }
        }

    }

    public void addAllSanity(int amount) {
        if (OverConfig.MECHANICS.enableSanity) {
            int playerCount = EventFinalBattle.mc.getPlayerList().getCurrentPlayerCount();
            for (int i = 0; i < playerCount; i++) {
                EntityPlayer player = EventFinalBattle.mc.getPlayerList().getPlayers().get(i);
                ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
                sanity.increase(amount);
            }
        }
    }

    public void addAllCourage(int amount) {
        if (OverConfig.MECHANICS.enableCourage) {
            int playerCount = EventFinalBattle.mc.getPlayerList().getCurrentPlayerCount();
            for (int i = 0; i < playerCount; i++) {
                EntityPlayer player = EventFinalBattle.mc.getPlayerList().getPlayers().get(i);
                ICourage courage = player.getCapability(CourageProvider.COURAGE_CAP, null);
                courage.increase(amount);
            }
        }
    }

    @SubscribeEvent
    public void onFinalBattlePlayerNormalDied(LivingDeathEvent event) {
        if (Loader.isModLoaded("playerrevive")&&!EventFinalBattle.mc.isSinglePlayer()) {
            return;
        }
        if (event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote && WorldSeason.getWorldState() == 3) {
            int random =(int) (Math.random() * 10);
            sendMessageOnPhase(Phase.death,random);
            WorldSeason.setScore(WorldSeason.getScore()-3);
        }
    }

    //玩家死亡事件
    @SubscribeEvent
    @Optional.Method(modid = "playerrevive")
    public void onFinalBattlePlayerDied(PlayerKilledEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote && WorldSeason.getWorldState() == 3) {
            int random =(int) (Math.random() * 10);
            sendMessageOnPhase(Phase.death,random);
            WorldSeason.setScore(WorldSeason.getScore()-3);
        }
    }

    public void sendMessageOnPhase(Phase phase,int order) {
        String key = String.format("message.finalBattle.%s%d", phase.name(), order);
        onTextSend(new TextComponentTranslation(key));
    }
    public void sendMessageOnPhase(Phase phase) {
        String key = String.format("message.finalBattle.%s", phase.name());
        onTextSend(new TextComponentTranslation(key));
    }
}
