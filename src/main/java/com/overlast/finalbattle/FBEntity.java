package com.overlast.finalbattle;

import com.dhanantry.scapeandrunparasites.entity.monster.adapted.*;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityOronco;
import com.dhanantry.scapeandrunparasites.entity.monster.ancient.EntityTerla;
import com.dhanantry.scapeandrunparasites.entity.monster.crude.EntityHost;
import com.dhanantry.scapeandrunparasites.entity.monster.crude.EntityLesh;
import com.dhanantry.scapeandrunparasites.entity.monster.deterrent.EntityTonro;
import com.dhanantry.scapeandrunparasites.entity.monster.deterrent.EntityUnvo;
import com.dhanantry.scapeandrunparasites.entity.monster.deterrent.nexus.*;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityButhol;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityMudo;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityNuuh;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityRathol;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.*;
import com.dhanantry.scapeandrunparasites.entity.monster.primitive.*;
import com.dhanantry.scapeandrunparasites.entity.monster.pure.*;
import com.dhanantry.scapeandrunparasites.entity.monster.pure.preeminent.*;
import com.fantasticsource.dynamicstealth.common.potions.Potions;
import com.overlast.config.OverConfig;
import com.overlast.handlers.EventFinalBattle;
import com.overlast.season.WorldSeason;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class FBEntity {
    public static FBEntity fbEntity =new FBEntity();

    //被同化的肉块
    public void onSpawnEntityLesh(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityLesh them = new EntityLesh(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));

            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }


    //被同化的冒险者
    public void onSpawnEntityInfPlayer(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityInfPlayer them = new EntityInfPlayer(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 2, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            world.spawnEntity(them);
        }
    }


    //寄生柱
    public void onSpawnEntityHost(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityHost them = new EntityHost(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //天哥
    public void onSpawnEntityInfBear(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityInfBear them = new EntityInfBear(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 3, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 2, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //同化末影人
    public void onSpawnEntityInfEnderman(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityInfEnderman them = new EntityInfEnderman(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 5, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //同化大蜘蛛
    public void onSpawnEntityDorpa(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityDorpa them = new EntityDorpa(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 2, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //裂兽
    public void onSpawnEntityMudo(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityMudo them = new EntityMudo(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 10000, 2, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始长臂兽
    public void onSpawnEntityShyco(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityShyco them = new EntityShyco(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始咀骨兽
    public void onSpawnEntityHull(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityHull them = new EntityHull(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始毒腥兽
    public void onSpawnEntityNogla(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityNogla them = new EntityNogla(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始黄眸兽
    public void onSpawnEntityEmana(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityEmana them = new EntityEmana(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始召唤兽
    public void onSpawnEntityCanra(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityCanra them = new EntityCanra(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始增协兽
    public void onSpawnEntityBano(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityBano them = new EntityBano(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //原始蛛形兽
    public void onSpawnEntityRanrac(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityRanrac them = new EntityRanrac(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应长臂兽
    public void onSpawnEntityShycoAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityShycoAdapted them = new EntityShycoAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应咀骨兽
    public void onSpawnEntityHullAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityHullAdapted them = new EntityHullAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应毒腥兽
    public void onSpawnEntityNoglaAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityNoglaAdapted them = new EntityNoglaAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应黄眸兽
    public void onSpawnEntityEmanaAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityEmanaAdapted them = new EntityEmanaAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应召唤兽
    public void onSpawnEntityCanraAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityCanraAdapted them = new EntityCanraAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应增协兽
    public void onSpawnEntityBanoAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityBanoAdapted them = new EntityBanoAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //适应蛛形兽
    public void onSpawnEntityRanracAdapted(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityRanracAdapted them = new EntityRanracAdapted(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //监察兽
    public void onSpawnEntityAlafha(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityAlafha them = new EntityAlafha(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //巡兽 EntityAnged
    public void onSpawnEntityAnged(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityAnged them = new EntityAnged(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //看守兽 EntityGanro
    public void onSpawnEntityGanro(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityGanro them = new EntityGanro(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //轻型轰炸兽 EntityOmboo
    public void onSpawnEntityOmboo(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityOmboo them = new EntityOmboo(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //掠夺兽 EntityEsor
    public void onSpawnEntityEsor(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityAlafha them = new EntityAlafha(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //步行兽 EntityFlog
    public void onSpawnEntityFlog(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityFlog them = new EntityFlog(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }


    //统御兽
    public void onSpawnEntityOrch(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityOrch them = new EntityOrch(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //卓越种-重型轰炸兽
    public void onSpawnEntityJinjo(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityJinjo them = new EntityJinjo(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //幽鬼体
    public void onSpawnEntityElvia(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityElvia them = new EntityElvia(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //怖怪体
    public void onSpawnEntityLencia(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityLencia them = new EntityLencia(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //逐猎兽
    public void onSpawnEntityPheon(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityPheon them = new EntityPheon(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //种群载体 EntityVesta
    public void onSpawnEntityVesta(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityVesta them = new EntityVesta(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //远古惧魔 EntityOronco EntityOroncoTen
    public void onSpawnEntityOronco(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityOronco them = new EntityOronco(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), 150, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //远古君魔 EntityTerla
    public void onSpawnEntityTerla(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityTerla them = new EntityTerla(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), 80, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            for (int x = -3; x < 4; x++) {
                for (int y = -1; y < 9; y++) {
                    for (int z = -3; z < 4; z++) {
                        them.world.setBlockToAir(them.getPosition().add(x, y, z));
                    }
                }
            }
            world.spawnEntity(them);
        }
    }


    //一级唤兽柱
    public void onSpawnEntityVenkrol(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityVenkrol them = new EntityVenkrol(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //二级唤兽柱
    public void onSpawnEntityVenkrolSII(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityVenkrolSII them = new EntityVenkrolSII(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //三级唤兽柱
    public void onSpawnEntityVenkrolSIII(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityVenkrolSIII them = new EntityVenkrolSIII(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //四级唤兽柱
    public void onSpawnEntityVenkrolSIV(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityVenkrolSIV them = new EntityVenkrolSIV(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 4, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
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
            world.spawnEntity(them);
        }
    }

    //一级调度柱
    public void onSpawnEntityDod(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityDod them = new EntityDod(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //二级调度柱
    public void onSpawnEntityDodII(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityDodSII them = new EntityDodSII(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //三级调度柱
    public void onSpawnEntityDodIII(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityDod them = new EntityDod(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //四级调度柱
    public void onSpawnEntityDodSIV(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityDodSIV them = new EntityDodSIV(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 4, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
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
            world.spawnEntity(them);
        }
    }

    //飞行母体
    public void onSpawnEntityButhol(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityButhol them = new EntityButhol(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 52, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }


    //凶裂兽 EntityNuuh
    public void onSpawnEntityNuuh(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityNuuh them = new EntityNuuh(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 2, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //重型母体
    public void onSpawnEntityRathol(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityRathol them = new EntityRathol(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 52, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //曲击兽
    public void onSpawnEntityTonro(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityTonro them = new EntityTonro(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 52, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //守卫兽
    public void onSpawnEntityUnvo(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityUnvo them = new EntityUnvo(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 52, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;

            world.spawnEntity(them);
        }
    }

    //末影龙
    public void onSpawnEntityDragon(World world,int count) {
        for (int i = 0; i < count; i++) {
            EntityInfDragonE them = new EntityInfDragonE(world);
            them.setLocationAndAngles(WorldSeason.getBattlePosX() + getRandomPos(), WorldSeason.getBattlePosY() + 52, WorldSeason.getBattlePosZ() + getRandomPos(), 0.0f, 0);
            them.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10000, 1, false, false));
            them.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10000, 2, false, false));
            if (Loader.isModLoaded("dynamicstealth")) {
                them.addPotionEffect(new PotionEffect(Potions.POTION_SOULSIGHT, 10000, 0, false, false));
            }
            them.preventEntitySpawning = true;
            world.spawnEntity(them);
        }
    }

    //获取随机坐标
    public double getRandomPos() {
        double randOffsetToSummonThem = Math.random() * 30;
        double posOrNeg = Math.round(Math.random());
        if (posOrNeg == 0) {
            randOffsetToSummonThem = randOffsetToSummonThem * -1;
        }
        return randOffsetToSummonThem;
    }

    public void onSpawnMob(int updateRank, int playerCount) {
        int realCount=playerCount / 4+WorldSeason.getDifficultyPoint();
        GameRules gamerules = EventFinalBattle.fbWorld.getGameRules();
        if(gamerules.getBoolean("keepInventory")) {
            realCount=realCount+2;
        }

        //onTextSend("生成怪物成功");
        switch (updateRank) {
            case 1:
                switch ((int) (Math.random() * 6)) {
                    case 0:
                        FBEntity.fbEntity.onSpawnEntityAlafha(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 1:
                        FBEntity.fbEntity.onSpawnEntityAnged(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 2:
                        FBEntity.fbEntity.onSpawnEntityGanro(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 3:
                        FBEntity.fbEntity.onSpawnEntityOmboo(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 4:
                        FBEntity.fbEntity.onSpawnEntityEsor(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 5:
                        FBEntity.fbEntity.onSpawnEntityFlog(EventFinalBattle.fbWorld,realCount);
                        break;
                }
            case 2:
                switch ((int) (Math.random() * 6)) {
                    case 0:
                        FBEntity.fbEntity.onSpawnEntityShycoAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 1:
                        FBEntity.fbEntity.onSpawnEntityRanracAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 2:
                        FBEntity.fbEntity.onSpawnEntityBanoAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 3:
                        FBEntity.fbEntity.onSpawnEntityEmanaAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 4:
                        FBEntity.fbEntity.onSpawnEntityHullAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 5:
                        FBEntity.fbEntity.onSpawnEntityNoglaAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                }
            case 3:
                switch ((int) (Math.random() * 7)) {
                    case 0:
                        FBEntity.fbEntity.onSpawnEntityRanracAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 1:
                        FBEntity.fbEntity.onSpawnEntityBanoAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 2:
                        FBEntity.fbEntity.onSpawnEntityCanraAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 3:
                        FBEntity.fbEntity.onSpawnEntityEmanaAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 4:
                        FBEntity.fbEntity.onSpawnEntityNoglaAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 5:
                        FBEntity.fbEntity.onSpawnEntityHullAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 6:
                        FBEntity.fbEntity.onSpawnEntityShycoAdapted(EventFinalBattle.fbWorld,realCount);
                        break;
                }
            case 4:
                switch ((int) (Math.random() * 7)) {
                    case 0:
                        FBEntity.fbEntity.onSpawnEntityMudo(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 1:
                        FBEntity.fbEntity.onSpawnEntityDorpa(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 2:
                        FBEntity.fbEntity.onSpawnEntityInfEnderman(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 3:
                        FBEntity.fbEntity.onSpawnEntityInfBear(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 4:
                        FBEntity.fbEntity.onSpawnEntityHost(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 5:
                        FBEntity.fbEntity.onSpawnEntityInfPlayer(EventFinalBattle.fbWorld,realCount);
                        break;
                    case 6:
                        FBEntity.fbEntity.onSpawnEntityVenkrolSIII(EventFinalBattle.fbWorld,realCount);
                        break;
                }
        }
        EventFinalBattle.finalBattle.onSaveData();
    }
}
