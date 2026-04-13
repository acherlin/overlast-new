package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:seasons")
public class Seasons {

    @Config.LangKey("config.overlast:seasons.enableSeasons")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the Seasons, will affect the ambient temperature and leaf color.")
    public boolean aenableSeasons = true;

    @Config.LangKey("config.overlast:seasons.winterLength")
    @Config.RequiresWorldRestart
    @Config.Comment("Length of winter, during which most biome will snow and crops will stop growing. \nSet to 0 for it to never occur.")
    public int winterLength = 3;

    @Config.LangKey("config.overlast:seasons.springLength")
    @Config.RequiresWorldRestart
    @Config.Comment("Length of winter, color of the leaves will recover in the spring. \nSet to 0 for it to never occur.")
    public int springLength = 3;

    @Config.LangKey("config.overlast:seasons.summerLength")
    @Config.RequiresWorldRestart
    @Config.Comment("Length of summer, when most biome become hot. \nSet to 0 for it to never occur.")
    public int summerLength = 3;

    @Config.LangKey("config.overlast:seasons.autumnLength")
    @Config.RequiresWorldRestart
    @Config.Comment("Length of autumn, in which the leaves change color. \nSet to 0 for it to never occur.")
    public int autumnLength = 3;

    @Config.LangKey("config.overlast:seasons.defaultSeason")
    @Config.RequiresWorldRestart
    @Config.Comment("Season when you first create the world.WINTER: 1;SPRING:  2;SUMMER: 3;AUTUMN: 4;default: 0;")
    public int defaultSeason = 0;

    @Config.LangKey("config.overlast:seasons.enableSummerParasiteEffect")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the Summer ParasiteEffect, will affect the Parasites spontaneous combustion.")
    public boolean enableSummerParasiteEffect = true;

    @Config.LangKey("config.overlast:seasons.enableSummerPlayerEffect")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the Summer PlayerEffect, will affect the Player spontaneous combustion.")
    public boolean enableSummerPlayerEffect = true;

    @Config.LangKey("config.overlast:seasons.enableWinterParasiteEffect")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the Winter ParasiteEffect, will affect the Parasites speed and strength.")
    public boolean enableWinterParasiteEffect = true;

    @Config.LangKey("config.overlast:seasons.enableWinterPlayerEffect")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the Winter PlayerEffect, will affect the Player sanity and natural evolution rate.")
    public boolean enableWinterPlayerEffect = true;


}
