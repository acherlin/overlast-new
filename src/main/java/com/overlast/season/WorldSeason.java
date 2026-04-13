package com.overlast.season;

import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.overlast.config.OverConfig;
import com.overlast.packet.OverPackets;
import com.overlast.packet.SeasonPacket;
import com.overlast.season.modifier.*;
import com.overlast.util.OverUtil;
import com.overlast.util.OverWorldData;
import com.overlast.util.WorldDataMgr;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.apache.commons.lang3.ArrayUtils;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;


@Mod.EventBusSubscriber
public class WorldSeason {

    /*
     * The main class that controls the seasons and the universe. Alright, I exaggerated on t0he universe part.
     * This does not affect temperature. That's another file.
     * 控制季节和宇宙的主类。好吧，我夸大了宇宙的部分。
     * 这并不影响温度。那是另一个文件。
     */

    //这部分信息会保存在文件里
    public static Season getSeason() {
        return season;
    }

    public static void setSeason(Season season) {
        WorldSeason.season = season;
    }

    public static int getDaysIntoSeason() {
        return daysIntoSeason;
    }

    public static void setDaysIntoSeason(int daysIntoSeason) {
        WorldSeason.daysIntoSeason = daysIntoSeason;
    }

    public static int getWorldState() {
        return worldState;
    }

    public static void setWorldState(int worldState) {
        WorldSeason.worldState = worldState;
    }

    public static int getTextTime() {
        return textTime;
    }

    public static void setTextTime(int textTime) {
        WorldSeason.textTime = textTime;
    }

    public static int getFinalTime() {
        return finalTime;
    }

    public static void setFinalTime(int finalTime) {
        WorldSeason.finalTime = finalTime;
    }

    public static int getWaveNum() {
        return waveNum;
    }

    public static void setWaveNum(int waveNum) {
        WorldSeason.waveNum = waveNum;
    }

    public static int getBattleFlag() {
        return battleFlag;
    }

    public static void setBattleFlag(int battleFlag) {
        WorldSeason.battleFlag = battleFlag;
    }

    public static int getBattlePosX() {
        return battlePosX;
    }

    public static void setBattlePosX(int battlePosX) {
        WorldSeason.battlePosX = battlePosX;
    }

    public static int getBattlePosY() {
        return battlePosY;
    }

    public static void setBattlePosY(int battlePosY) {
        WorldSeason.battlePosY = battlePosY;
    }

    public static int getBattlePosZ() {
        return battlePosZ;
    }

    public static void setBattlePosZ(int battlePosZ) {
        WorldSeason.battlePosZ = battlePosZ;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        WorldSeason.score = score;
    }

    public static int getDifficultyPoint() {
        return difficultyPoint;
    }

    public static void setDifficultyPoint(int difficultyPoint) {
        WorldSeason.difficultyPoint = difficultyPoint;
    }

    // Wonderful variables for this class yay
    // Anytime these change, save them
    // 本类的奇妙变量 yay
    // 当这些变量发生变化时，请保存它们
    private static Season season;
    private static int daysIntoSeason;
    private static int worldState;

    private static int textTime;
    private static int finalTime;
    private static int waveNum;

    private static int battleFlag;
    private static int battlePosX;
    private static int battlePosY;
    private static int battlePosZ;


    private static int score;


    private static int difficultyPoint;

    // To help set the rain stuff correctly 为了帮助正确设置雨的东西
    private boolean didRainStart = true;

    // Handlers and Melters.
    private final WeatherHandler weatherHandler = new WeatherHandler();
    private final BiomeTempController biomeTemp = new BiomeTempController();
    private final SeasonTweaks tweaks = new SeasonTweaks();
    private final SnowMelter melter = new SnowMelter();
    private final LeavesChanger leaves = new LeavesChanger();


    // This fires on server startup. Load the data from file here 这在服务器启动时启动。从这里加载文件中的数据
    public static void getSeasonData(Season dataSeason, int days) {

        season = dataSeason;
        daysIntoSeason = days;

        // Change biome temperatures immediately. 立即改变生物群落的温度。
        if (OverConfig.SEASONS.aenableSeasons) {
            BiomeTempController temporaryController = new BiomeTempController();
            temporaryController.changeBiomeTemperatures(season, daysIntoSeason);
            temporaryController = null;
        }
    }

    //其他数据
    public static void getFinalBattleData(int dataWorldState, int dataTextTime, int dataFinalTime, int dataWaveNum, int dataBattleFlag, int dataBattlePosX, int dataBattlePosY, int dataBattlePosZ, int dataScore, int dataDifficultyPoint) {
        worldState = dataWorldState;
        textTime = dataTextTime;
        finalTime = dataFinalTime;
        waveNum = dataWaveNum;
        battleFlag = dataBattleFlag;
        battlePosX = dataBattlePosX;
        battlePosY = dataBattlePosY;
        battlePosZ = dataBattlePosZ;
        score = dataScore;
        difficultyPoint = dataDifficultyPoint;
    }


    @SubscribeEvent
    public void onPlayerLogsIn(PlayerLoggedInEvent event) {
        if (OverConfig.SEASONS.aenableSeasons) {

            EntityPlayer player = event.player;

            if (player instanceof EntityPlayerMP) {

                // Sync server stuff with client.
                // This is needed so the snow, foliage, and stuff gets rendered correctly.
                // 将服务器上的东西与客户端同步。
                // 这是很有必要的，这样雪、树叶和其他东西才能被正确地呈现出来。
                int seasonInt = OverWorldData.seasonToInt(season);
                IMessage msg = new SeasonPacket.SeasonMessage(seasonInt, daysIntoSeason);
                TextComponentTranslation i18season;
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
                player.sendMessage(new TextComponentTranslation("message.seasons.login", i18season, daysIntoSeason));

                OverPackets.net.sendTo(msg, (EntityPlayerMP) player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogsOut(PlayerLoggedOutEvent event) {
        if (OverConfig.SEASONS.aenableSeasons) {

            // Turn off day-night cycle if no more people on  如果没有更多的人在线，则关闭昼夜循环。
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

            // Is this singleplayer? If so, reset their temps here. 这是单人游戏吗？如果是的话，在这里重置他们的温度。
            if (server.isSinglePlayer()) {

                biomeTemp.resetBiomeTemperatures();
            }

            // No? Do it via packet. 没有吗？通过数据包做吧。
            else if (event.player instanceof EntityPlayerMP) {

                int seasonInt = OverWorldData.seasonToInt(season);
                IMessage msg = new SeasonPacket.SeasonMessage(seasonInt, -21);
                OverPackets.net.sendTo(msg, (EntityPlayerMP) event.player);
            }
        }
    }

    // The clock - determines when to move on to stuff 时间--决定什么时候继续做事情
    @SubscribeEvent
    public void onPlayerUpdate(LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {

            // Player
            EntityPlayer player = (EntityPlayer) event.getEntity();

            // World
            World world = player.world;

            // Server-side
            if (!world.isRemote && OverConfig.SEASONS.aenableSeasons) {

                // Time
                long worldTime = world.getWorldTime();

                // Is it early morning? It's not exactly 0 because of beds. And it's an odd number because CycleController.
                // 现在是清晨吗？因为有床，所以不完全是0。而且它是一个奇数，因为CycleController。

                MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();
                if (worldTime % 24000 == 41 && (player == mc.getPlayerList().getPlayers().get(0) || mc.isSinglePlayer())) {
                    // Increment daysIntoSeason 递增天数在季节中
                    daysIntoSeason++;
                    // Is it the next season? 是下一季吗？
                    if (daysIntoSeason > season.getLength()) {

                        // Head on over to the next season. 前往下一季。
                        daysIntoSeason = 1;
                        season = season.nextSeason();

                        // Cycle through the seasons if some are disabled. 如果有些被禁用，则循环使用四季。
                        while (season.getLength() == 0) {

                            season = season.nextSeason();
                        }

                        if (worldState == 1) {
                            season = Season.WINTER;
                        }
                        if (worldState == 2) {
                            season = Season.SUMMER;
                        }
                    }
                    //寄生等级小于6时解冻
                    if (SRPSaveData.get(player.getEntityWorld()).getEvolutionPhase(player.getEntityWorld().provider.getDimension()) < 6 && worldState < 3) {
                        worldState = 0;
                    }
                    //静谧四季联动
                    if(Loader.isModLoaded("sereneseasons"))
                    {
                        SeasonSavedData data = SeasonHandler.getSeasonSavedData(player.world);
                        int seasonCycleTicks = data.seasonCycleTicks;
                        SeasonTime time = new SeasonTime(seasonCycleTicks);
                        sereneseasons.api.season.Season.SubSeason sSeason = time.getSubSeason();
                        int subSeasonDuration = time.getSubSeasonDuration();
                        int index = ArrayUtils.indexOf(sereneseasons.api.season.Season.SubSeason.VALUES, sSeason);
                        if (index == 11)
                        {
                            index = -1;
                        }
                        int ticksTillNext = seasonCycleTicks-subSeasonDuration * index;
                        int sDay=time.getDay();
                        //int sDay=time.getDay()- SyncedConfig.getIntValue(SeasonsOption.SUB_SEASON_DURATION)* index;
                        //OverLast.logger.info("当前季节"+sSeason+" 天数 "+time.getDay()+"ticksTillNext "+ticksTillNext+" time "+time.getDay()+" seasonCycleTicks "+seasonCycleTicks);
                        season= OverUtil.UTIL.SSeasonCompatible(sSeason);
                        daysIntoSeason=sDay-OverUtil.UTIL.SDayCompatible(sSeason)+1;
                    }
                    WorldDataMgr.save(season, daysIntoSeason, worldState, textTime, finalTime, waveNum, battleFlag, battlePosX, battlePosY, battlePosZ, score, difficultyPoint);

                    if(Loader.isModLoaded("sereneseasons")) {
                        // Send new season data to client 向客户发送新季节数据
                        int seasonInt = OverWorldData.seasonToInt(season);
                        IMessage msg = new SeasonPacket.SeasonMessage(seasonInt, daysIntoSeason);
                        OverPackets.net.sendTo(msg, (EntityPlayerMP) player);
                        return;
                    }
                    if (worldTime % 24000 == 41) {

                        // Is it the start of spring or autumn? Initiate initial leaf changing. 现在是春天还是秋天的开始？启动最初的换叶。
                        //替换树叶 每天都触发一次
                        leaves.changeInitial(season, world, player);


                        // Send new season data to client 向客户发送新季节数据
                        int seasonInt = OverWorldData.seasonToInt(season);
                        IMessage msg = new SeasonPacket.SeasonMessage(seasonInt, daysIntoSeason);
                        OverPackets.net.sendTo(msg, (EntityPlayerMP) player);

                        // Change temperatures
                        biomeTemp.changeBiomeTemperatures(season, daysIntoSeason);

                        // Determine the weather. The season is the main factor. 确定天气。季节是主要因素。
                        float randWeather = (float) Math.random();

                        if (randWeather < season.getPrecipitationChance()) {

                            weatherHandler.makeItRain(world, season, player);
                            didRainStart = false;
                        } else {

                            weatherHandler.makeItNotRain(world, player);
                        }

                        // Log it
                        //OverLast.logger.info("Day " + daysIntoSeason + " of " + season + ".");
                    }


                    // If it's going to rain, we'll need to send the rain data when it starts.
                    // 如果要下雨，我们就需要在下雨的时候发送雨量数据。
                    if (world.isRaining() && !didRainStart) {

                        didRainStart = true;
                        weatherHandler.applyToRain(world);
                    }

                    // We need to melt snow and ice manually in the spring.
                    // Summer has a different melting method.
                    // 我们需要在春天人工融化冰雪。
                    // 夏天有不同的融化方法。

                    if (season == Season.SPRING && player.dimension == 0) {

                        melter.melt(world, player, daysIntoSeason);
                    }
                }
            }
        }
    }


    // Helps to melt snow in summer. Where there shouldn't be any snow.
    // Also does the leaves.
    // 有助于在夏天融化雪。在不应该有雪的地方。
    // 还可以做树叶。
    @SubscribeEvent
    public void onChunkWalkIn(EntityEvent.EnteringChunk event) {
        if(Loader.isModLoaded("sereneseasons"))
            return;
        // Was this a player?
        if (event.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.getEntity();

            if (OverConfig.SEASONS.aenableSeasons) {

                // Is it summer? Then let's try to remove some snow and ice.
                // 现在是夏天吗？那么让我们试着清除一些雪和冰。
                if (season == Season.SUMMER) {

                    if (!player.world.isRemote && player.dimension == 0) {

                        int chunkCoordX = event.getNewChunkX();
                        int chunkCoordZ = event.getNewChunkZ();
                        melter.meltCompletely(chunkCoordX, chunkCoordZ, player.world);
                    }
                }

                // How about spring or autumn? Let's try to change some leaves. 春天或秋天怎么样？让我们试着改变一些树叶。
                else if (season == Season.SPRING || season == Season.AUTUMN) {

                    if (!player.world.isRemote && player.dimension == 0) {

                        int chunkCoordX = event.getNewChunkX();
                        int chunkCoordZ = event.getNewChunkZ();
                        leaves.change(chunkCoordX, chunkCoordZ, player.world, season);
                    }
                }
                //都不是，手动更新下方块？
                else {
                    // 强制更新草地颜色
                    BlockPos pos= event.getEntity().getPosition();
                    World world = event.getEntity().getEntityWorld();
                    world.markBlockRangeForRenderUpdate(pos, pos.add(16, 16, 0));
                }
            }
        }
    }

    // Change grass color in Autumn and Summer. 在秋季和夏季改变草的颜色。
    @SubscribeEvent
    public void biomeGrass(BiomeEvent.GetGrassColor event) {
        if(Loader.isModLoaded("sereneseasons"))
            return;
        int color;

        if (OverConfig.SEASONS.aenableSeasons) {

            color = tweaks.getSeasonGrassColor(season, event.getBiome());
        } else {

            color = 0;
        }


        if (color == 0) {

            ;
        } else if (event.getNewColor() != color) {

            event.setNewColor(color);
        }
    }


    // Make nothing grow in winter, and more grow in summer 让冬天停止生长，夏日长的更快
    @SubscribeEvent
    public void affectGrowth(BlockEvent.CropGrowEvent.Pre event) {
        if(Loader.isModLoaded("sereneseasons"))
            return;
        // Server-side
        if (!event.getWorld().isRemote) {

            // Winter?
            if (season == Season.WINTER && OverConfig.SEASONS.aenableSeasons) {

                event.setResult(Event.Result.DENY);
            }

            // Summer?
            else if (season == Season.SUMMER && OverConfig.SEASONS.aenableSeasons) {

                event.setResult(Event.Result.ALLOW);
            }

            // Backwards-compat code TODO remove next release
            GameRules gamerules = event.getWorld().getGameRules();
            gamerules.setOrCreateGameRule("randomTickSpeed", "3");
        }
    }


    @SubscribeEvent
    public void affectBlock(BlockEvent event) {
        if(Loader.isModLoaded("sereneseasons"))
            return;
        // Server-side
        if (!event.getWorld().isRemote&& OverConfig.SEASONS.aenableSeasons) {
            // Winter worldState
            GameRules gamerules = event.getWorld().getGameRules();
            if (season == Season.WINTER && worldState == 1) {
                gamerules.setOrCreateGameRule("randomTickSpeed", "0");
            } else if (season == Season.SUMMER && worldState == 2) {
                gamerules.setOrCreateGameRule("randomTickSpeed", "6");
            } else if (worldState == 0) {
                gamerules.setOrCreateGameRule("randomTickSpeed", "3");
            }
        }
    }
}