package com.overlast.finalbattle;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class FBWaves {

    public static FBWaves fbWaves =new FBWaves();

    //纯种群
    public void initwave1(World world) {
        FBEntity.fbEntity.onSpawnEntityAnged(world,1);
        FBEntity.fbEntity.onSpawnEntityGanro(world,1);
        FBEntity.fbEntity.onSpawnEntityPheon(world,1);
        FBEntity.fbEntity.onSpawnEntityLencia(world,1);
        FBEntity.fbEntity.onSpawnEntityElvia(world,1);
        FBEntity.fbEntity.onSpawnEntityJinjo(world,1);
        FBEntity.fbEntity.onSpawnEntityOrch(world,1);
        for (int i = 0; i <getSurviveCount(world);) {
            FBEntity.fbEntity.onSpawnEntityOmboo(world,1);
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityFlog(world,1);i++;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityRanracAdapted(world,1);i=i+2;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityButhol(world,1);i++;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityRathol(world,1);i++;
            }
        }
        for (int i = 0; i <getSurviveCount(world);) {
            FBEntity.fbEntity.onSpawnEntityPheon(world,1);
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityLencia(world,1);i++;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityElvia(world,1);i=i+2;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityJinjo(world,1);i++;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityOrch(world,1);i++;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityPheon(world,1);i++;
            }
        }
        FBTexts.fbTexts.onTextSend(new TextComponentTranslation("message.finalBattle.initWave1"));
    }

    //始祖群
    public void initwave2(World world) {
        FBEntity.fbEntity.onSpawnEntityVenkrolSIV(world,1);
        FBEntity.fbEntity.onSpawnEntityOronco(world,1);
        FBEntity.fbEntity.onSpawnEntityTerla(world,1);
        FBEntity.fbEntity.onSpawnEntityDodSIV(world,1);
        for (int i = 0; i <getSurviveCount(world);) {
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityVenkrolSIV(world,1);i=i+2;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityOronco(world,1);i=i+3;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityTerla(world,1);i=i+3;
            }
            if(i <getSurviveCount(world)) {
                FBEntity.fbEntity.onSpawnEntityDodSIV(world,1);i=i+3;
            }
        }
        FBTexts.fbTexts.onTextSend(new TextComponentTranslation("message.finalBattle.initWave2"));
    }

    //适应群
    public void initwave3(World world) {
        FBEntity.fbEntity.onSpawnEntityCanraAdapted(world,1);
        FBEntity.fbEntity.onSpawnEntityEmanaAdapted(world,1);
        for (int i = 0; i <getSurviveCount(world);) {
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityRanracAdapted(world,1);i=i+2;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityBanoAdapted(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityNoglaAdapted(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityHullAdapted(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityShycoAdapted(world,1);i++;}
        }
        FBTexts.fbTexts.onTextSend(new TextComponentTranslation("message.finalBattle.initWave3"));
    }

    //同化群
    public void initwave4(World world) {
        FBEntity.fbEntity.onSpawnEntityInfEnderman(world,1);
        FBEntity.fbEntity.onSpawnEntityCrux(world,1);
        FBEntity.fbEntity.onSpawnEntityHost(world,1);
        for (int i = 0; i <getSurviveCount(world);) {
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityDragon(world,1);i=i+2;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityInfBear(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityInfPlayer(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityDorpa(world,1);i++;}
            if(i <getSurviveCount(world)) {FBEntity.fbEntity.onSpawnEntityMudo(world,1);i++;}
        }
        FBTexts.fbTexts.onTextSend(new TextComponentTranslation("message.finalBattle.initWave4"));
    }

    public int getSurviveCount(World world) {
        int playerCount =world.getMinecraftServer().getPlayerList().getCurrentPlayerCount();
        int spectatorCount=0;
        for (int i = 0; i < playerCount; i++) {
            if(world.getMinecraftServer().getPlayerList().getPlayers().get(i).isSpectator()||world.getMinecraftServer().getPlayerList().getPlayers().get(i).isCreative()) {
                spectatorCount++;
            }
        }
        return playerCount-spectatorCount;
    }
    

}
