package com.overlast.util;

import com.dhanantry.scapeandrunparasites.util.config.SRPConfigSystems;
import com.dhanantry.scapeandrunparasites.util.handlers.BiomeUpdateQueue;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import com.dhanantry.scapeandrunparasites.world.biome.BiomeParasiteBase;
import com.overlast.OverLast;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.config.OverConfig;
import com.overlast.lib.ModMobEffects;
import com.overlast.season.Season;
import lombok.SneakyThrows;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Optional;
import sereneseasons.api.config.SeasonsOption;
import sereneseasons.api.config.SyncedConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.dhanantry.scapeandrunparasites.block.BlockBiomePurifier.positionToBiome;

public class OverUtil {
    public static OverUtil UTIL = new OverUtil();

    public OverUtil() {
    }

    //重新定义变量名
    public int phaseKillsOne =SRPConfigSystems.phaseKillsOne;
    public int phaseKillsTwo =SRPConfigSystems.phaseKillsTwo;
    public int phaseKillsThree =SRPConfigSystems.phaseKillsThree;
    public int phaseKillsFour =SRPConfigSystems.phaseKillsFour;
    public int phaseKillsFive =SRPConfigSystems.phaseKillsFive;
    public int phaseKillsSix =SRPConfigSystems.phaseKillsSix;
    public int phaseKillsSeven =SRPConfigSystems.phaseKillsSeven;
    public int phaseKillsEight =SRPConfigSystems.phaseKillsEight;// 5亿
    public int phaseKillsNine =SRPConfigSystems.phaseKillsNine;//10亿
    public int phaseKillsTen =SRPConfigSystems.phaseKillsTen;//18亿



    public int getPhase(World world) {
        return SRPSaveData.get(world,0).getEvolutionPhase(world.provider.getDimension());
    }

    public boolean setPhase(World world, byte value) {
        return SRPSaveData.get(world,0).setEvolutionPhase(world.provider.getDimension(), value, true, world);
    }

    public int getTotalPoint(World world) {
        return SRPSaveData.get(world,0).getTotalKills(world.provider.getDimension());
    }

    public void setCooldown(World world,int cd) {
        SRPSaveData.get(world,0).setCooldown(cd,world,world.provider.getDimension(), false);
    }

    public int getCooldown(World world) {
        return SRPSaveData.get(world,0).getCooldown(world,world.provider.getDimension());
    }

    public int getAddEvoPoint(int phase) {
        int evopoint;
        switch (phase) {
            case 3:
                evopoint = (phaseKillsFour-phaseKillsThree)/4000;
                break;
            case 4:
                evopoint = (phaseKillsFive-phaseKillsFour)/4000;
                break;
            case 5:
                evopoint = (phaseKillsSix-phaseKillsFive)/4000;
                break;
            case 6:
                evopoint = (phaseKillsSeven-phaseKillsSix)/4000;
                break;
            case 7:
                evopoint = (phaseKillsEight-phaseKillsSeven)/4000;
                break;
            case 1:
            case 2:
            case 0:
            case -1:
            case -2:
            case 8:
            default:
                evopoint = 0;
        }
        return evopoint;
    }

    public void addEvoPoint(World world,int evoPoint) {
        SRPSaveData.get(world,0).setTotalKills(world.provider.getDimension(), (int)(evoPoint*OverConfig.MECHANICS.naturalEvolutionScale), true, world,true);

        //OverLast.logger.warn("实际增加点数："+(int)(evopoint*OverConfig.MECHANICS.naturalEvolutionScale)+" 比例"+OverConfig.MECHANICS.naturalEvolutionScale);

    }

    public void reduceEvoPoint(World world,int evoPoint,boolean force) {
        if(force) {
            setCooldown(world,0);
        }
        SRPSaveData.get(world,0).setTotalKills(world.provider.getDimension(), (int)(evoPoint*OverConfig.MECHANICS.naturalEvolutionScale), true, world,true);

    }

    @SneakyThrows
    public void ConfigFoodSan(EntityPlayer player, ItemStack item) {
        // Capability
        ISanity sanity = player.getCapability(SanityProvider.SANITY_CAP, null);
        String[] customFood = OverConfig.CUSTOM.foodSanity;
        boolean contain = false;
        for(String s :customFood) {
            String[] split = s.split(";");
            try {
                Item cItem = Item.getByNameOrId(split[0]);
                int meta = Integer.parseInt(split[1]);
                float sanModifier= Float.parseFloat(split[2].replace(",",""));
                if(item.getTranslationKey().equals(new ItemStack(cItem,1,meta).getTranslationKey())) {
                    sanity.increase(sanModifier);
                    contain = true;
                }
            } catch (Exception e) {
                OverLast.logger.warn("Bad Item option : {}", s);
            }
        }
        //如果不在列表内则按饱食度回复理智
        if(!contain&&OverConfig.CUSTOM.enableAutoFoodSan) {
            Item fItem = item.getItem();
            if (fItem instanceof ItemFood) {
               int hunger = ((ItemFood) fItem).getHealAmount(item);
                if(hunger>=8) {
                    sanity.increase(4.0f);
                }else if(hunger>=6) {
                    sanity.increase(3.0f);
                }if(hunger>=4) {
                    sanity.increase(2.0f);
                }if(hunger>=2) {
                    sanity.increase(1.0f);
                }
            }
        }
    }

    public void ConfigLowSanityPotions(EntityPlayer player) {
        String[] customPotions = OverConfig.CUSTOM.lowSanityPotion;
        for (String potion : customPotions) {
            String[] parts = potion.split(";");
            try {
                String potionId = parts[0];
                int level = Integer.parseInt(parts[1]);
                int duration = Integer.parseInt(parts[2]);
                Potion potionType = Potion.REGISTRY.getObject(new ResourceLocation(potionId));
                // 应用药水效果到玩家身上
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(potionType), level, duration));

            } catch (Exception e) {
                OverLast.logger.warn("Bad Potion option : {}", potion);
            }
        }
    }

    public boolean checkSanityDimBlacklist(int dim) {
        return Arrays.asList(OverConfig.CUSTOM.sanityDimBlacklist).contains(dim);
    }

    public void FinialBattleRewards(MinecraftServer mc) {
        ItemStack[] rewards = ConfigRecipe(OverConfig.CUSTOM.finalBattleRewards);
        // 获取所有在线玩家
        for (EntityPlayerMP player : mc.getPlayerList().getPlayers()) {
            // 遍历奖励并给予物品
            for (ItemStack reward : rewards) {
                // 检查玩家的背包空间
                if (!player.inventory.addItemStackToInventory(reward)) {
                    // 背包已满，可以选择处理未能放入背包的物品
                    spawnItemInWorld(player.world, player.posX, player.posY, player.posZ, reward);
                }
            }
        }
    }
    public void ConfigLowSanityParasitesPotions(EntityPlayer player) {
        String[] customPotions = OverConfig.CUSTOM.lowSanityParasites;
        for (String potion : customPotions) {
            String[] parts = potion.split(";");
            try {
                String potionId = parts[0];
                int level = Integer.parseInt(parts[1]);
                int duration = Integer.parseInt(parts[2]);
                int parasites = Integer.parseInt(parts[3]);
                Potion potionType = Potion.REGISTRY.getObject(new ResourceLocation(potionId));
                if(!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() == parasites) {
                    // 应用药水效果到玩家身上
                    player.addPotionEffect(new PotionEffect(Objects.requireNonNull(potionType), level, duration));
                }

            } catch (Exception e) {
                OverLast.logger.warn("Bad Potion option : {}", potion);
            }
        }
    }


    public boolean CheckNearbySRP (EntityPlayer player) {
        boolean checkSRP=false;
        AxisAlignedBB boundingBox = player.getEntityBoundingBox().grow(7, 2, 7);
        List nearbyMobs = player.world.getEntitiesWithinAABB(EntityMob.class, boundingBox);
        for (int numMobs = 0; numMobs < nearbyMobs.size(); numMobs++) {

            // Chosen mob
            EntityMob mob = (EntityMob) nearbyMobs.get(numMobs);
            if(String.valueOf(mob.getClass()).substring(0,19).equals("class com.dhanantry")) {
                checkSRP=true;
            }
            //需要排除漫游者
        }
        return checkSRP;
    }

    public ItemStack[] ConfigRecipe(String[] recipe) {
        ItemStack[] IStack = new ItemStack[recipe.length];
        for(int i=0;i<recipe.length;i++) {
            String[] split = recipe[i].split(";");
            Item cItem = Item.getByNameOrId(split[0]);
            int amount = Integer.parseInt(split[1]);
            int meta = Integer.parseInt(split[2]);
            IStack[i]=new ItemStack(cItem,amount,meta);
        }
        return IStack;
    }

    public void ConfigLowSanityPools(EntityPlayer player, double randOffsetToSummonThem) {
        String[] entities = OverConfig.CUSTOM.lowSanityPools;
        for (String entity : entities) {
            String[] parts = entity.split(";");
            try {
                ResourceLocation name = new ResourceLocation(parts[0]);
                int level = Integer.parseInt(parts[1]);
                double rate = Double.parseDouble(parts[2]);
                if (level <= OverUtil.UTIL.getPhase(player.getEntityWorld()) && rate > Math.random()) {
                    Entity e = EntityList.createEntityByIDFromName(name, player.world);
                    if (e != null) {
                        e.setLocationAndAngles(player.posX + randOffsetToSummonThem, player.posY + 2, player.posZ + randOffsetToSummonThem, 0.0f, 0);
                        e.preventEntitySpawning = true;
                        player.world.spawnEntity(e);
                    }
                }
            } catch (Exception e) {
                OverLast.logger.warn("Bad Entity option : {}", entity);
            }
        }
    }

    @Optional.Method(modid = "sereneseasons")
    public Season SSeasonCompatible(sereneseasons.api.season.Season.SubSeason sSeason) {
        Season season;
        switch (sSeason){
            case EARLY_SPRING:
            case MID_SPRING:
            case LATE_SPRING:
                season=Season.SPRING;break;
            case EARLY_SUMMER:
            case MID_SUMMER:
            case LATE_SUMMER:
                season=Season.SUMMER;break;
            case EARLY_AUTUMN:
            case MID_AUTUMN:
            case LATE_AUTUMN:
                season=Season.AUTUMN;break;
            case EARLY_WINTER:
            case MID_WINTER:
            case LATE_WINTER:
                season=Season.WINTER;break;
            default:season=Season.SPRING;break;
        }
        return season;
    }

    @Optional.Method(modid = "sereneseasons")
    public int SDayCompatible(sereneseasons.api.season.Season.SubSeason sSeason) {
        int days;
        switch (sSeason){
            case EARLY_SPRING:
            case MID_SPRING:
            case LATE_SPRING:
                days= 0;break;
            case EARLY_SUMMER:
            case MID_SUMMER:
            case LATE_SUMMER:
                days= SyncedConfig.getIntValue(SeasonsOption.SUB_SEASON_DURATION)*3;break;
            case EARLY_AUTUMN:
            case MID_AUTUMN:
            case LATE_AUTUMN:
                days= SyncedConfig.getIntValue(SeasonsOption.SUB_SEASON_DURATION)*6;break;
            case EARLY_WINTER:
            case MID_WINTER:
            case LATE_WINTER:
                days= SyncedConfig.getIntValue(SeasonsOption.SUB_SEASON_DURATION)*9;break;
            default:days=0;break;
        }
        return days;
    }

    public void CheckSpawnPosIsAir(Entity mob) {
        int boxX =(int) Math.ceil(mob.getEntityBoundingBox().maxX-mob.getEntityBoundingBox().minX);
        int boxY =(int) Math.ceil(mob.getEntityBoundingBox().maxY-mob.getEntityBoundingBox().minY);
        int boxZ =(int) Math.ceil(mob.getEntityBoundingBox().maxZ-mob.getEntityBoundingBox().minZ);
        boolean isAir = true;

        //检测方块
        for(int x=0;x<=boxX;x++) {
            for(int y=0;y<=boxY;y++) {
                for(int z=0;z<=boxZ;z++) {
                    if(!(mob.world.isAirBlock(mob.getPosition().add(x,y,z)))) {
                        isAir=false;
                    }
                }
            }
        }
        //如果不满足条件，则拆除方块
        if(!isAir) {
            for (int x = 0; x <= boxX; x++) {
                for (int y = 0; y <= boxY; y++) {
                    for (int z = 0; z <= boxZ; z++) {
                        mob.world.setBlockToAir(mob.getPosition().add(x, y, z));
                    }
                }
            }
        }
    }
    public void spawnItemInWorld(World world, double x, double y, double z, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            EntityItem entityItem = new EntityItem(world, x, y, z, itemStack);
            world.spawnEntity(entityItem);
        }
    }

    public void killBiome(World worldIn, BlockPos pos, int range) {
        final SRPWorldData data = SRPWorldData.get(worldIn);
        final int worldTime = 0;
        for (int x = pos.getX() - range; x <= pos.getX() + range; ++x) {
            for (int z = pos.getZ() - range; z <= pos.getZ() + range; ++z) {
                final int age = data.nearestHeartAge(pos, true, worldTime);
                final int distance = data.getDistanceSpreadByAge(age, false);
                final BlockPos convert = new BlockPos(x, pos.getY(), z);
                if (worldIn.getBiome(convert) instanceof BiomeParasiteBase) {
                    Biome originalBiome = worldIn.getBiomeProvider().getBiome(pos, Biomes.PLAINS);
                    positionToBiome(worldIn, convert, Biome.getIdForBiome(originalBiome));
                    BiomeUpdateQueue.enqueue(convert.getX(), convert.getY(), convert.getZ(), false, Biome.getIdForBiome(originalBiome), worldIn.provider.getDimension());
                }
            }
        }
    }

}
