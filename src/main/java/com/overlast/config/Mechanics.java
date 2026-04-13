package com.overlast.config;

import net.minecraftforge.common.config.Config;


@Config.LangKey("config.overlast:mechanics")
public class Mechanics {

    @Config.LangKey("config.overlast:mechanics.showRequestDirtyClock")
    @Config.RequiresWorldRestart
    @Config.Comment("If set to true, the natural evolution bar will only be displayed when holding a dirty clock.")
    public boolean showRequestDirtyClock = false;

    @Config.LangKey("config.overlast:mechanics.enableSanity")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the sanity mechanic.")
    public boolean enableSanity = true;

    @Config.LangKey("config.overlast:mechanics.sanityScale")
    @Config.RequiresWorldRestart
    @Config.Comment("The rate of Sanity change, both increasing and decreasing.")
    @Config.RangeDouble(min = 0.1, max = 10.0)
    public double sanityScale = 1.0;


    @Config.LangKey("config.overlast:mechanics.enableLightEffect")
    @Config.Comment("Whether brightness effects sanity.")
    @Config.RequiresWorldRestart
    public boolean enableLightEffect=true;

    @Config.LangKey("config.overlast:mechanics.enableDailyBOSS")
    @Config.Comment("Whether to summon the daily boss during the high evolution phase.")
    @Config.RequiresWorldRestart
    public boolean enableDailyBOSS=true;

    @Config.LangKey("config.overlast:mechanics.naturalEvolutionScale")
    @Config.RequiresWorldRestart
    @Config.Comment("Evolution phase 3 or above will enable the natural evolution, \nthe higher the speed of the faster. Set to 0 for it to never occur.")
    @Config.RangeDouble
    public double naturalEvolutionScale = 1.0;

    @Config.LangKey("config.overlast:mechanics.enableTemperature")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the temperature mechanic. \nEffective only in summer and winter.")
    public boolean enableTemperature = true;

    @Config.LangKey("config.overlast:mechanics.temperatureScale")
    @Config.RequiresWorldRestart
    @Config.Comment("The rate at which a player heats up and cools down.")
    @Config.RangeDouble(min = 0.1, max = 10.0)
    public double temperatureScale = 1.0;

    @Config.LangKey("config.overlast:mechanics.enableCourage")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the courage mechanic.")
    public boolean enableCourage = true;

    @Config.LangKey("config.overlast:mechanics.courageScale")
    @Config.RequiresWorldRestart
    @Config.Comment("The rate of courage change, both increasing and decreasing.")
    @Config.RangeDouble(min = 0.1, max = 10.0)
    public double courageScale = 1.0;

    @Config.LangKey("config.overlast:mechanics.enableParasitic")
    @Config.RequiresWorldRestart
    @Config.Comment("Whether to enable the parasitic mechanic.")
    public boolean enableParasitic = true;

    @Config.LangKey("config.overlast:mechanics.parasiticScale")
    @Config.RequiresWorldRestart
    @Config.Comment("The rate of parasitic change, both increasing and decreasing.")
    @Config.RangeDouble(min = 0.1, max = 10.0)
    public double parasiticScale = 1.0;

    @Config.LangKey("config.overlast:mechanics.enableRadio")
    @Config.RequiresWorldRestart
    @Config.Comment("For the features provided by the modpacks, the modpacks author is able to customize the radio text through the resource pack.")
    public boolean enableRadio = true;

}
