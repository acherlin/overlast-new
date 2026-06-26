
package com.overlast.handlers;

import com.creativemd.playerrevive.server.PlayerReviveServer;
import com.dhanantry.scapeandrunparasites.block.*;
import com.dhanantry.scapeandrunparasites.block.slabs.BlockSlabRubble;
import com.dhanantry.scapeandrunparasites.init.SRPBlocks;
import com.dhanantry.scapeandrunparasites.util.ParasiteEventWorld;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import com.overlast.OverLast;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.finalbattle.FBEntity;
import com.overlast.finalbattle.FBTexts;
import com.overlast.finalbattle.FBWaves;
import com.overlast.lib.ModBlocks;
import com.overlast.lib.ModMobEffects;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import com.overlast.util.CustomTeleporter;
import com.overlast.util.OverUtil;
import com.overlast.util.WorldDataMgr;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;



@EventBusSubscriber(modid = OverLast.MOD_ID)
public class EventFinalBattle {

    private int spawnTimer = 0;
    public static int battleFlag;
    private boolean beaconTrueText;
    private boolean beaconFalseText;

    public static int dim= OverConfig.CUSTOM.finalBattleDimensionID;
    public static MinecraftServer mc;
    public static World fbWorld;

    public static EventFinalBattle finalBattle =new EventFinalBattle();

    //决战准备
    @SubscribeEvent
    public void onFinalPrepare(BlockEvent.EntityPlaceEvent event) {
        if (!event.getWorld().isRemote) {
            if (!(event.getPlacedBlock().getBlock() == Blocks.BEACON))
                return;
            if (WorldSeason.getWorldState() == 3)
                return;
            if (event.getWorld().provider.getDimension()!=dim)
                return;
            int posX = event.getPos().getX();
            int posY = event.getPos().getY();
            int posZ = event.getPos().getZ();
            int beaconPoint = 0;
            boolean isFinalBlock = false;
            boolean isMiscBlock = false;
            boolean isCannotSeeSky = false;
            int difficultyPoint= 0;
            for (int i = -3; i < 4; i++) {
                for (int j = -3; j < 4; j++) {
                    for (int k = -3; k < 4; k++) {
                        if (checkBlock(event, i, j, k, ModBlocks.FinalSpecimen)) {
                            isFinalBlock = true;
                        }
                    }
                }
            }
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y < 0; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (checkBlock(event, x, y, z, Blocks.IRON_BLOCK) || checkBlock(event, x, y, z, Blocks.EMERALD_BLOCK) || checkBlock(event, x, y, z, Blocks.GOLD_BLOCK) || checkBlock(event, x, y, z, Blocks.DIAMOND_BLOCK)) {
                            beaconPoint++;
                        }
                    }
                }
            }

            for (int x = -5; x < 6; x++) {
                for (int y = -1; y < 7; y++) {
                    for (int z = -5; z < 6; z++) {
                        if (!(checkBlockPos(posX + x, posY + y, posZ + z, Blocks.IRON_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.GOLD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.EMERALD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.DIAMOND_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.BEACON) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.AIR) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.TORCH) ||
                                (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.FinalSpecimen)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenCell)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenFarm)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenInfect)) || (!checkBlockPos(posX + x, posY + y+3, posZ + z, Blocks.AIR))
                        )) {
                            isMiscBlock = true;
                        }
                    }
                }
            }

            //检测是否露天
            for (int x = -5; x < 6; x++) {
                for (int y = 0; y < 1; y++) {
                    for (int z = -5; z < 6; z++) {
                        if (!(checkBlockCanSeeSky(posX + x,posY + y,posZ + z))) {
                            isCannotSeeSky = true;
                        }
                    }
                }
            }

            for (int x = -5; x < 6; x++) {
                for (int y = -1; y < 7; y++) {
                    for (int z = -5; z < 6; z++) {
                        if (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenInfect)) {
                            difficultyPoint++;
                        }
                        if (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenCell)) {
                            difficultyPoint--;
                        }
                    }
                }
            }


            //.out.println("杂物方块"+isMiscBlock);
            //System.out.println("isFinalBlock"+isFinalBlock+" beaconPoint"+beaconPoint+" WorldSeason.getWaveNum()"+WorldSeason.getWaveNum());
            if (!isCannotSeeSky&&isFinalBlock && beaconPoint == 9 && WorldSeason.getWaveNum() == 0 && (!isMiscBlock) && WorldSeason.getWorldState() < 3) {
                WorldSeason.setDifficultyPoint(difficultyPoint);
                onBattleInit(posX, posY, posZ);
                FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.prepare,0);
            } else if (isFinalBlock && WorldSeason.getWorldState() < 3) {
                FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.prepare,1);
            } else if (!isFinalBlock && WorldSeason.getWorldState() < 3) {
                FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.prepare,2);
            } else {
                FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.prepare,3);
            }
        }
    }

    //决战初始化
    public void onBattleInit(int x, int y, int z) {
        WorldSeason.setTextTime(1);
        WorldSeason.setWorldState(3);
        WorldSeason.setSeason(Season.SPRING);
        WorldSeason.setBattlePosX(x);
        WorldSeason.setBattlePosY(y);
        WorldSeason.setBattlePosZ(z);
        WorldSeason.setDaysIntoSeason(1);
        WorldSeason.setScore(100);
        fixFloor(x,y,z,Blocks.COBBLESTONE.getDefaultState());

        BlockPos pos = BlockPos.ORIGIN.add(x, y, z);
        //生成战斗场地
        for (int posX = -5; posX < 6; posX++) {
            for (int posY = -2; posY < -1; posY++) {
                for (int posZ = -5; posZ < 6; posZ++) {
                    if (!(checkBlockPos(posX + x, posY + y, posZ + z, Blocks.IRON_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.GOLD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.EMERALD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.DIAMOND_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.FinalSpecimen))) {
                        if (posX == -5 || posX == 5 || posZ == -5 || posZ == 5) {
                            fbWorld.setBlockState(pos.add(posX, posY, posZ), Blocks.OBSIDIAN.getDefaultState());
                        } else {
                            fbWorld.setBlockState(pos.add(posX, posY, posZ), Blocks.COBBLESTONE.getDefaultState());
                        }
                    }
                }
            }
        }

        //保存世界状态
        onSaveData();
    }




    //每5 tick增加1 ，也就是0.25秒
    @SubscribeEvent
    public void onFinalBattle(TickEvent.ServerTickEvent event) {
        //过滤一半阶段
        if (event.phase == TickEvent.Phase.END)
            return;
        //获取实例化对象
        mc = FMLCommonHandler.instance().getMinecraftServerInstance();
        fbWorld = mc.getWorld(dim);
        //若已经经历过决战了，则取消
        if (WorldSeason.getWorldState() == 4)
            return;
        spawnTimer++;//怪物生成时间增加
        //启用文本计时器，
        int updateTextTimer = WorldSeason.getTextTime();
        if(updateTextTimer==0) {
            return;//非决战状态时退出该事件
        }
        if (updateTextTimer > 0 && spawnTimer % 5 == 0) {
            updateTextTimer++;
            WorldSeason.setTextTime(updateTextTimer);
            FBTexts.fbTexts.onTextControl(updateTextTimer);
        }
        //决战时间，需要保存到本地数据
        int updateTimer = WorldSeason.getFinalTime();

        battleFlag = WorldSeason.getBattleFlag();
        if (updateTimer == 0)
            return;
        //满足结构时，决战时间增加点数减少，先得念完开场白
        if (spawnTimer % 5 == 0) {
            if (onCheckStructure()&&battleFlag<6) {
                onEvoPurifier();
                updateTimer++;
                WorldSeason.setFinalTime(updateTimer);
            }else if(spawnTimer %1200==0) {
                WorldSeason.setScore(WorldSeason.getScore()-2);
            }
        }
        //每6000刻(5分钟)一个阶段，阶段1在完成开场白后设置 由于降速处理，全部除以5
        switch (updateTimer) {
            case 1442:
                WorldSeason.setWaveNum(2);
                break;
            case 2882:
                WorldSeason.setWaveNum(3);
                break;
            case 4322:
                WorldSeason.setWaveNum(4);
                break;
            case 4802:
                WorldSeason.setWaveNum(5);
                break;
        }
        //波数等级
        int updateRank = WorldSeason.getWaveNum();
        //生存玩家数量
        int playerCount = FBWaves.fbWaves.getSurviveCount(fbWorld);

        //每分钟自然减少1点点数，分散在各个生成支援怪物中了

        //每分钟修复一次基地，破坏一次地狱门，传送一次怪物
        if(spawnTimer % 1200==0) {
            int posX = WorldSeason.getBattlePosX();
            int posY = WorldSeason.getBattlePosY();
            int posZ = WorldSeason.getBattlePosZ();
            if(OverUtil.UTIL.getPhase(fbWorld)>=5) {
                fixFloor(posX, posY, posZ, SRPBlocks.InfestedRubble.getDefaultState());
            }else {
                fixFloor(posX, posY, posZ, Blocks.COBBLESTONE.getDefaultState());
            }
            destroyObsidian(posX,posY,posZ);
            teleportSRP(posX,posY,posZ);
        }

        //波数为1时，召唤第一波，随后每分钟召唤支援
        if (updateRank == 1 && battleFlag == 0) {
            FBWaves.fbWaves.initwave1(fbWorld);
            battleFlag++;
            WorldSeason.setBattleFlag(battleFlag);
            WorldSeason.setTextTime(1);
            onSaveData();
        }
        if (updateRank == 1 && spawnTimer % 1200 == 2) {
            FBEntity.fbEntity.onSpawnMob(updateRank, playerCount);
            for(int i = 0; i < playerCount;) {
                FBEntity.fbEntity.onSpawnEntityRanracAdapted(fbWorld,1);i=i+2;
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityEmanaAdapted(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityTonro(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityUnvo(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityOmboo(fbWorld,1);i++;
                }
            }
            onSaveData();
        }

        //波数为2，召唤第2波，随后每分钟召唤支援，第四分钟时召唤随机BOSS。
        if (updateRank == 2 && battleFlag == 1) {
            FBWaves.fbWaves.initwave2(fbWorld);
            battleFlag++;
            WorldSeason.setBattleFlag(battleFlag);
            WorldSeason.setTextTime(1);
            onSaveData();
        }
        if (updateRank == 2 && spawnTimer % 1200 == 2) {
            FBEntity.fbEntity.onSpawnMob(updateRank, playerCount);
            for(int i = 0; i < playerCount;) {
                FBEntity.fbEntity.onSpawnEntityRanracAdapted(fbWorld,1);i=i+2;
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityEmanaAdapted(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityTonro(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityUnvo(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityOmboo(fbWorld,1);i++;
                }
            }
            onSaveData();
        }

        //随机始祖增援
        if (updateRank >= 2 && updateRank <= 3 && spawnTimer % 3000 == 2) {
            if (Math.random() > 0.5)
                FBEntity.fbEntity.onSpawnEntityTerla(fbWorld,1);
            else
                FBEntity.fbEntity.onSpawnEntityOronco(fbWorld,1);
        }

        //第3波，适应寄群
        if (updateRank == 3 && battleFlag == 2) {
            FBWaves.fbWaves.initwave3(fbWorld);
            battleFlag++;
            WorldSeason.setBattleFlag(battleFlag);
            WorldSeason.setTextTime(1);
            onSaveData();
        }
        //适应寄群增援
        if (updateRank == 3 && spawnTimer % 1200 == 2) {
            FBEntity.fbEntity.onSpawnMob(updateRank, playerCount);
            for(int i = 0; i < playerCount;) {
                FBEntity.fbEntity.onSpawnEntityTonro(fbWorld,1);i++;
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityUnvo(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityOmboo(fbWorld,1);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityDorpa(fbWorld,2);i++;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityInfEnderman(fbWorld,1);i++;
                }
            }
            onSaveData();
        }

        //第4波，同化寄群
        if (updateRank == 4 && battleFlag == 3) {
            FBWaves.fbWaves.initwave4(fbWorld);
            battleFlag++;
            WorldSeason.setBattleFlag(battleFlag);
            WorldSeason.setTextTime(1);
            onSaveData();
        }
        //同化寄群增援
        if (updateRank == 4 && spawnTimer % 1200 == 2) {
            FBEntity.fbEntity.onSpawnMob(updateRank, playerCount);
            for(int i = 0; i < playerCount;) {
                FBEntity.fbEntity.onSpawnEntityDorpa(fbWorld,2);
                i++;
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityInfEnderman(fbWorld,2);i=i+2;
                }
                if(i< playerCount) {
                    FBEntity.fbEntity.onSpawnEntityDragon(fbWorld,1);i=i+2;
                }
            }
            onSaveData();
        }

        //BE结局
        if(updateRank>=1&&updateRank<=4&&WorldSeason.getScore()<0) {
            WorldSeason.setWaveNum(6);
            WorldSeason.setTextTime(1);
            WorldSeason.setBattleFlag(6);
            initBadEnd();
            onSaveData();
        }

        //波数为5，开始念大结局
        if (updateRank == 5 && battleFlag == 4) {
            //检测结局分支条件
            battleFlag++;
            WorldSeason.setBattleFlag(battleFlag);
            WorldSeason.setTextTime(1);
            onSaveData();
        }

    }



    //检查方块属性
    public boolean checkBlock(BlockEvent.EntityPlaceEvent te, int rx, int ry, int rz, Block block) {
        BlockPos pos = te.getPos().add(rx, ry, rz);
        Block blockToCheck = te.getWorld().getBlockState(pos).getBlock();
        if (blockToCheck == block) {
            return true;
        }
        return false;
    }

    //检测坐标方块属性
    public boolean checkBlockPos(int rx, int ry, int rz, Block block) {
        BlockPos pos = BlockPos.ORIGIN.add(rx, ry, rz);
        Block blockToCheck = fbWorld.getBlockState(pos).getBlock();
        if (blockToCheck == block) {
            return true;
        }
        return false;
    }

    //检测是否露天
    public boolean checkBlockCanSeeSky(int rx, int ry, int rz) {
        BlockPos pos = BlockPos.ORIGIN.add(rx, ry, rz);
        if (fbWorld.canSeeSky(pos)) {
            return true;
        }
        return false;
    }


    //检测净化结构完整性
    public boolean onCheckStructure() {
        int posX = WorldSeason.getBattlePosX();
        int posY = WorldSeason.getBattlePosY();
        int posZ = WorldSeason.getBattlePosZ();
        int beaconPoint = 0;
        boolean isFinalBlock = false;
        boolean isBeaconBlock = false;
        boolean isNearBlock = false;
        boolean isMiscBlock = false;
        boolean isCannotSeeSky =false;
        int difficultyPoint = 0;

        AxisAlignedBB boundingBoxPlayers = new AxisAlignedBB(posX - 5, posY - 2, posZ - 5, posX + 5, posY + 2, posZ + 5);
        List nearbyPlayers = fbWorld.getEntitiesWithinAABB(EntityPlayer.class, boundingBoxPlayers);
        if (!nearbyPlayers.isEmpty()) {
            isNearBlock = true;
            for (Object nearbyPlayer : nearbyPlayers) {
                EntityPlayer otherPlayer = (EntityPlayer) nearbyPlayer;
                if (Loader.isModLoaded("playerrevive")) {
                    if (PlayerReviveServer.isPlayerBleeding(otherPlayer)) {
                        isNearBlock = false;
                    }
                }
                otherPlayer.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 100, 0, false, false));
            }
        }

        if (battleFlag == 3) {
            List allPlayers = mc.getPlayerList().getPlayers();
            for (Object allPlayer : allPlayers) {
                EntityPlayer otherPlayer = (EntityPlayer) allPlayer;
                otherPlayer.addPotionEffect(new PotionEffect(ModMobEffects.EVIL, 100, 0, false, false));
            }
        }

        if (checkBlockPos(posX, posY, posZ, Blocks.BEACON)) {
            isBeaconBlock = true;
        }
        //检测最终方块
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                for (int k = -3; k < 4; k++) {
                    if (checkBlockPos(posX + i, posY + j, posZ + k, ModBlocks.FinalSpecimen)) {
                        isFinalBlock = true;
                    }
                }
            }
        }

        //检测信标底座
        for (int x = -5; x < 6; x++) {
            for (int y = -1; y < 7; y++) {
                for (int z = -5; z < 6; z++) {
                    if (checkBlockPos(posX + x, posY + y, posZ + z, Blocks.IRON_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.GOLD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.EMERALD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.DIAMOND_BLOCK)) {
                        beaconPoint++;
                    }
                }
            }
        }

        //检测地表障碍方块
        for (int x = -5; x < 6; x++) {
            for (int y = -1; y < 7; y++) {
                for (int z = -5; z < 6; z++) {
                    if (!(checkBlockPos(posX + x, posY + y, posZ + z, Blocks.IRON_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.GOLD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.EMERALD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.DIAMOND_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.BEACON) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.AIR) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.TORCH) ||
                            (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.FinalSpecimen)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenCell)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenFarm)) || (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenInfect)) || (checkBlockPos(posX + x, posY + y, posZ + z, Blocks.BEACON))||(!checkBlockPos(posX + x, posY + y+3, posZ + z, Blocks.AIR)))) {
                        isMiscBlock = true;
                    }
                }
            }
        }

        //检测地心障碍方块
        for (int x = -5; x < 6; x++) {
            for (int y = -2; y < -1; y++) {
                for (int z = -5; z < 6; z++) {
                    if (!(checkBlockPos(posX + x, posY + y, posZ + z, Blocks.OBSIDIAN) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.COBBLESTONE) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.IRON_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.GOLD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.EMERALD_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, Blocks.DIAMOND_BLOCK) || checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.FinalSpecimen))) {
                        isMiscBlock = true;
                    }
                }
            }
        }

        //检测难度方块数量
        for (int x = -5; x < 6; x++) {
            for (int y = -1; y < 7; y++) {
                for (int z = -5; z < 6; z++) {
                    if (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenInfect)) {
                        difficultyPoint++;
                    }
                    if (checkBlockPos(posX + x, posY + y, posZ + z, ModBlocks.SpecimenCell)) {
                        difficultyPoint--;
                    }
                }
            }
        }
        //赋值给难度
        WorldSeason.setDifficultyPoint(difficultyPoint);

        //检测是否露天
        for (int x = -5; x < 6; x++) {
            for (int y = 0; y < 1; y++) {
                for (int z = -5; z < 6; z++) {
                    if (!(checkBlockCanSeeSky(posX + x,posY + y,posZ + z))) {
                        isCannotSeeSky = true;
                    }
                }
            }
        }

        if (isFinalBlock && beaconPoint == 9 && isBeaconBlock && !isMiscBlock &&!isCannotSeeSky&& !beaconTrueText) {
            //满足信标条件，则状态为1
            FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.repaired);
            beaconTrueText = true;
            beaconFalseText = false;
        }

        if (!(isFinalBlock && beaconPoint == 9 && isBeaconBlock && !isMiscBlock&&!isCannotSeeSky) && !beaconFalseText) {
            //不满足信标条件，则为-1
            FBTexts.fbTexts.sendMessageOnPhase(FBTexts.Phase.disrupted);
            beaconFalseText = true;
            beaconTrueText = false;
        }


        if (!isCannotSeeSky&&isNearBlock && isFinalBlock && beaconPoint == 9 && isBeaconBlock && !isMiscBlock) {
            return true;
        } else {
            return false;
        }
    }

    //结束保存事件
    public void FinalBattleOver(int end) {
        WorldDataMgr.save(WorldSeason.getSeason(), WorldSeason.getDaysIntoSeason(), 4, 0, 0, 0, 0, WorldSeason.getBattlePosX(), WorldSeason.getBattlePosY(), WorldSeason.getBattlePosZ(),WorldSeason.getScore(),WorldSeason.getDifficultyPoint());
        //0 BE 1 TE
        if(end ==1) {
            OverUtil.UTIL.FinialBattleRewards(mc);
        }
    }

    //世界净化事件
    public void onEvoPurifier() {
        int rank = SRPSaveData.get(fbWorld,0).getEvolutionPhase(dim);
        int evopoint = SRPSaveData.get(fbWorld,0).getTotalKills(dim);
        int updateTimer = WorldSeason.getFinalTime();
        //总共12000次减少，~->8,8-7,7-6,6-5,5-4,4-3,3-2,2-1,1-0,-1,-2
        //每个阶段1000次吧~


        if (updateTimer >= 1442 && updateTimer <= 2162 && rank >= 8) {
            onEvoRemove(5000000);
            //针对高演化阶段的额外减值
            if(OverUtil.UTIL.getPhase(fbWorld)>=9) {
                onEvoRemove(8000000);
            }
            if(OverUtil.UTIL.getPhase(fbWorld)==10) {
                onEvoRemove(10000000);
            }
        } else if (updateTimer > 2162 && updateTimer <= 2882 && rank >= 7) {
            onEvoRemove(1500000);
        } else if (updateTimer > 2882 && updateTimer <= 3602 && rank >= 6) {
            onEvoRemove(120000);
        } else if (updateTimer > 3602 && updateTimer <= 4322 && rank >= 5) {
            onEvoRemove(60000);
        } else if (updateTimer > 4322 && updateTimer <= 4562 && rank >= 4) {
            onEvoRemove(6000);
        } else if (updateTimer > 4562 && updateTimer <= 4802 && rank >= 3) {
            onEvoRemove(600);
            onRemoveNode();
            if (rank >= 4)
                onEvoRemove(6000);
        } else if (updateTimer > 4802 && updateTimer <= 5002 && rank >= 0 && evopoint >= 12) {
            onEvoRemove(12);
            SRPSaveData.get(fbWorld,0).setGaining(false,dim);
            if (rank >= 2)
                onEvoRemove(6000);
        } else if (updateTimer > 4802) {
            int generation = SRPSaveData.get(fbWorld,0).getGeneration(dim);
            SRPSaveData.get(fbWorld,0).setTotalKills(dim,-200,true, fbWorld,true, generation);
        }
        //世界净化开始
        switch (updateTimer) {
            case 4322:
                onWorldPurify(0, 0);
                break;
            case 4342:
                onWorldPurify(32, 0);
                break;
            case 4362:
                onWorldPurify(-32, 0);
                break;
            case 4382:
                onWorldPurify(0, 32);
                break;
            case 4402:
                onWorldPurify(0, -32);
                break;
            case 4422:
                onWorldPurify(32, 32);
                break;
            case 4442:
                onWorldPurify(32, -32);
                break;
            case 4462:
                onWorldPurify(-32, 32);
                break;
            case 4482:
                onWorldPurify(-32, -32);
                break;
        }

    }


    public void onSetNode() {
        int posX = WorldSeason.getBattlePosX();
        int posY = WorldSeason.getBattlePosY();
        int posZ = WorldSeason.getBattlePosZ();
        onRemoveNode();
        ParasiteEventWorld.placeHeartInWorld(fbWorld, new BlockPos(posX + FBEntity.fbEntity.getRandomPos() * 3 + 20, posY - 50, posZ + FBEntity.fbEntity.getRandomPos() * 3 + 20),1);
        ParasiteEventWorld.placeHeartInWorld(fbWorld, new BlockPos(posX + 11000, posY, posZ),1);
        ParasiteEventWorld.placeHeartInWorld(fbWorld, new BlockPos(posX - 11000, posY, posZ),1);
        ParasiteEventWorld.placeHeartInWorld(fbWorld, new BlockPos(posX, posY, posZ + 11000),1);
        ParasiteEventWorld.placeHeartInWorld(fbWorld, new BlockPos(posX, posY, posZ - 11000),1);
    }

    public void onRemoveNode() {
        SRPWorldData data = SRPWorldData.get(fbWorld);
        ArrayList<Integer> nodesX = data.getNodes("x");
        ArrayList<Integer> nodesY = data.getNodes("y");
        ArrayList<Integer> nodesZ = data.getNodes("z");
        for (int i = 0; i < nodesX.size(); i++) {
            data.removeNode(nodesX.get(i), nodesY.get(i), nodesZ.get(i));
        }
    }

    public void onEvoRemove(int evopoint) {
        OverUtil.UTIL.reduceEvoPoint(fbWorld,evopoint,true);
    }

    public void onWorldPurify(int targetX, int targetZ) {
        {
            World worldIn = fbWorld;
            BlockPos pos = BlockPos.ORIGIN.add(WorldSeason.getBattlePosX(), WorldSeason.getBattlePosY(), WorldSeason.getBattlePosZ());
            int distanceY = pos.getY();
            OverUtil.UTIL.killBiome(worldIn, pos, 16);
            for (int y = 0; y <= 255; y++) {
                for (int x = -16 + targetX; x <= targetX + 16; x++) {
                    for (int z = -16 + targetZ; z <= 16 + targetZ; z++) {
                        Block targetBlock = worldIn.getBlockState(pos.add(x, y - distanceY, z)).getBlock();
                        //空气
                        if (targetBlock instanceof BlockAir) {
                            continue;
                        }
                        //泥土
                        if (targetBlock instanceof BlockInfestedStain || targetBlock instanceof BlockParasiteStain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.DIRT.getDefaultState());
                            continue;
                        }

                        //感染方块
                        if (targetBlock instanceof BlockInfestedRemain) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }

                        //感染原木
                        if (targetBlock instanceof BlockInfestedTrunk || targetBlock instanceof BlockParasiteTrunk) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.LOG.getDefaultState());
                            continue;
                        }

                        //感染石头
                        if (targetBlock instanceof BlockInfestedRubble || targetBlock instanceof BlockParasiteRubble || targetBlock instanceof BlockParasiteRubbleDense) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.STONE.getDefaultState());
                            continue;
                        }

                        //感染草脉+嘴
                        if (targetBlock instanceof BlockInfestedBush || targetBlock instanceof BlockParasiteBush || targetBlock instanceof BlockParasiteMouth || targetBlock instanceof BlockStairBase || targetBlock instanceof BlockSlab || targetBlock instanceof BlockSlabRubble
                                || targetBlock instanceof BlockParasiteCanister || targetBlock instanceof BlockParasiteThin) {
                            worldIn.setBlockState(pos.add(x, y - distanceY, z), Blocks.AIR.getDefaultState());
                            continue;
                        }
                    }
                }
            }
        }
    }

    //人类增援事件
    @SubscribeEvent
    public void onWorldKill(LivingEvent.LivingUpdateEvent event) {
        if (WorldSeason.getWorldState() != 3)
            return;
        if (battleFlag == 2 && WorldSeason.getTextTime() > 1480 && WorldSeason.getTextTime() <= 1560 || battleFlag == 4 && WorldSeason.getTextTime() > 1680 && WorldSeason.getTextTime() <= 1720) {
            if (!event.getEntity().world.isRemote) {
                if (event.getEntity() instanceof EntityMob) {
                    EntityMob entity = (EntityMob) event.getEntity();
                    double dx = entity.posX;
                    double dy = entity.posY;
                    double dz = entity.posZ;
                    entity.world.createExplosion(event.getEntity(), dx, dy + 0.2F, dz, 0, true);
                    entity.setHealth(entity.getHealth() - 50);
                    entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, dx, dy, dz, true));
                }


                if (event.getEntity() instanceof EntityPlayer&&OverConfig.MECHANICS.enableSanity) {
                    EntityPlayer player = (EntityPlayer) event.getEntity();
                    ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
                    if(sanity.getSanity()<=35) {
                        double dx = player.posX;
                        double dy = player.posY;
                        double dz = player.posZ;
                        player.world.createExplosion(event.getEntity(), dx, dy + 0.2F, dz, 0, true);
                        player.world.addWeatherEffect(new EntityLightningBolt(player.world, dx, dy, dz, true));
                        if(WorldSeason.getTextTime()==1500||WorldSeason.getTextTime()==1700) {
                            player.sendMessage(new TextComponentTranslation("message.finalBattle.report6"));
                        }
                    }
                }

            }
        }
    }

    public void initBadEnd() {
        WorldSeason.setFinalTime(10000);
        int playerCount =mc.getPlayerList().getCurrentPlayerCount();
        for (int i = 0; i < playerCount; i++) {
            if (Loader.isModLoaded("lostcities")) {
                EntityPlayer player =mc.getPlayerList().getPlayers().get(i);
                BlockPos location = player.getPosition();
                CustomTeleporter.teleportToDimension(player, 111, location);
            }else {
                EntityPlayer player =mc.getPlayerList().getPlayers().get(i);
                BlockPos location = player.getPosition();
                CustomTeleporter.teleportToDimension(player, -1, location);
            }
        }
    }



    public void fixFloor(int x, int y, int z, IBlockState blockState) {
        BlockPos pos = BlockPos.ORIGIN.add(x, y, z);
        for (int posX = -16; posX <= 16; posX++) {
            for (int posY = -2; posY < -1; posY++) {
                for (int posZ = -16; posZ <= 16; posZ++) {
                    if (checkBlockPos(posX + x, posY + y, posZ + z, Blocks.AIR)) {
                        fbWorld.setBlockState(pos.add(posX, posY, posZ), blockState);
                    }
                }
            }
        }
    }

    public void destroyObsidian(int x, int y, int z) {
        BlockPos pos = BlockPos.ORIGIN.add(x, y, z);
        for (int posX = -16; posX <= 16; posX++) {
            for (int posY = -2; posY < 5; posY++) {
                for (int posZ = -16; posZ <= 16; posZ++) {
                    if (checkBlockPos(posX + x, posY + y, posZ + z, Blocks.PORTAL)) {
                        fbWorld.setBlockState(pos.add(posX, posY, posZ), Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    public void teleportSRP(int x, int y, int z) {
        AxisAlignedBB boundingBox = new AxisAlignedBB(x - 32, y - 32, z - 32, x + 32, y + 2, z + 32);
        List nearbyMobs = fbWorld.getEntitiesWithinAABB(EntityMob.class, boundingBox);
        for (int numMobs = 0; numMobs < nearbyMobs.size(); numMobs++) {
            EntityMob mob = (EntityMob) nearbyMobs.get(numMobs);
            mob.setLocationAndAngles(mob.getPosition().getX(), y+2,mob.getPosition().getZ(),mob.rotationYaw,mob.rotationPitch);
        }


    }

    public void onSaveData() {
        WorldDataMgr.save(WorldSeason.getSeason(), WorldSeason.getDaysIntoSeason(), WorldSeason.getWorldState(), WorldSeason.getTextTime(), WorldSeason.getFinalTime(), WorldSeason.getWaveNum(), WorldSeason.getBattleFlag(), WorldSeason.getBattlePosX(), WorldSeason.getBattlePosY(), WorldSeason.getBattlePosZ(),WorldSeason.getScore(),WorldSeason.getDifficultyPoint());
    }

}