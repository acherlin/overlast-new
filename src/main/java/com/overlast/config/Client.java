package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:client")
public class Client {

    @Config.LangKey("config.overlast:client.enableSound")
    @Config.Comment("Whether to enable low sanity sound effects,may conflict with other mods.")
    public boolean enableSound = true;

    @Config.LangKey("config.overlast:client.enableEffect")
    @Config.Comment("Whether to enable low sanity visual effects,may conflict with other mods.")
    public boolean enableEffect = false;

    @Config.LangKey("config.overlast:client.barPos")
    @Config.Comment("Specify where to place the GUI bars. You can set it to any of the following: \ntop left \ntop right \nmiddle left \nmiddle right \nbottom left \nbottom right \nInclude the space. If you mess up, it'll default to middle right.")
    public String barPositions = "middle right";

    @Config.LangKey("config.overlast:client.Xoffset")
    @Config.Comment("When set to greater than 0, the UI bar will be offset to the right, \nand when set to less than 0, the UI bar will be offset to the left.")
    public int Xoffset = 0;

    @Config.LangKey("config.overlast:client.Yoffset")
    @Config.Comment("When set to greater than 0, the UI bar will be offset to the bottom, \n and when set to less than 0, the UI bar will be offset to the top.")
    public int Yoffset = 0;

}