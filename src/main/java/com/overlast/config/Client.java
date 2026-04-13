package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:client")
public class Client {

    @Config.LangKey("config.overlast:client.barPos")
    public String barPositions = "top left";

    @Config.LangKey("config.overlast:client.Xoffset")
    @Config.Comment("When set to greater than 0, the UI bar will be offset to the right, \nand when set to less than 0, the UI bar will be offset to the left.")
    public int Xoffset = 0;

    @Config.LangKey("config.overlast:client.Yoffset")
    @Config.Comment("When set to greater than 0, the UI bar will be offset to the bottom, \n and when set to less than 0, the UI bar will be offset to the top.")
    public int Yoffset = 0;

}