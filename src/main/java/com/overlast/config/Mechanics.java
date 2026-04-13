package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:mechanics")
public class Mechanics {

    @Config.LangKey("config.overlast:mechanics.showRequestDirtyClock")
    @Config.RequiresWorldRestart
    public boolean showRequestDirtyClock = false;

    @Config.LangKey("config.overlast:mechanics.hideUI")
    @Config.Comment("Hide the evolution bar of the specified phase. (eg: -1,0,3)")
    @Config.RequiresWorldRestart
    public int[] hideUI={
    };
    
}
