package com.overlast.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;


@ZenExpansion("crafttweaker.player.IPlayer")
@ZenRegister
public class PlayerCapSH {

    @ZenGetter("sanity")
    public static double getSanity(IPlayer player) {
        return TweakManager.getSanity(player);
    }

    @ZenSetter("sanity")
    public static void setSanity(IPlayer player, double value) {
        TweakManager.setSanity(player, value);
    }

    @ZenGetter("courage")
    public static double getCourage(IPlayer player) {
        return TweakManager.getCourage(player);
    }

    @ZenSetter("courage")
    public static void setCourage(IPlayer player, double value) {
        TweakManager.setCourage(player, value);
    }

    @ZenGetter("temperature")
    public static double getTemperature(IPlayer player) {
        return TweakManager.getTemperature(player);
    }

    @ZenSetter("temperature")
    public static void setTemperature(IPlayer player, double value) {
        TweakManager.setTemperature(player, value);
    }

    @ZenGetter("parasitic")
    public static double getParasitic(IPlayer player) {
        return TweakManager.getParasitic(player);
    }

    @ZenSetter("parasitic")
    public static void setParasitic(IPlayer player, double value) {
        TweakManager.setParasitic(player, value);
    }

    @ZenGetter("phase")
    public static int getEvolutionPhase(IPlayer player) {
        return TweakManager.getEvolutionPhase(player);
    }

    @ZenSetter("phase")
    public static void setEvolutionPhase(IPlayer player, byte value) {
        TweakManager.setEvolutionPhase(player, value);
    }

    @ZenGetter("cooldown")
    public static int getCooldown(IPlayer player) {
        return TweakManager.getCooldown(player);
    }

    @ZenSetter("cooldown")
    public static void setCooldown(IPlayer player, int value) {
        TweakManager.setCooldown(player, value);
    }

    @ZenGetter("point")
    public static int getEvoPoint(IPlayer player) {
        return TweakManager.getEvoPoint(player);
    }

    @ZenSetter("point")
    public static void addEvoPoint(IPlayer player, int value) {
        TweakManager.addEvoPoint(player, value);
    }



}
