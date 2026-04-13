package com.overlast.crafttweaker;

import com.overlast.cap.courage.CourageProvider;
import com.overlast.cap.parasitic.ParasiticProvider;
import com.overlast.cap.sanity.SanityProvider;
import com.overlast.cap.temperature.TemperatureProvider;
import com.overlast.util.OverUtil;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;

@ModOnly("overlast")
@ZenRegister
@ZenClass("com.overlast.crafttweaker.TweakManager")
public class TweakManager {

    @ZenMethod
    public static void setSanity(IPlayer player, double value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        if (entPlayer.getCapability(SanityProvider.SANITY_CAP, null) != null) {
            Objects.requireNonNull(entPlayer.getCapability(SanityProvider.SANITY_CAP, null)).set((float) value);
        } else {
            CraftTweakerAPI.logInfo("cannot set sanity because the player's sanity data is null");
        }
    }

    @ZenMethod
    public static double getSanity(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        double value = 0;
        if (entPlayer.getCapability(SanityProvider.SANITY_CAP, null) != null) {
            value=Objects.requireNonNull(entPlayer.getCapability(SanityProvider.SANITY_CAP, null)).getSanity();
        } else {
            CraftTweakerAPI.logInfo("cannot get sanity because the player's sanity data is null");
        }
        return value;
    }

    @ZenMethod
    public static void setCourage(IPlayer player, double value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        if (entPlayer.getCapability(CourageProvider.COURAGE_CAP, null) != null) {
            Objects.requireNonNull(entPlayer.getCapability(CourageProvider.COURAGE_CAP, null)).set((float) value);
        } else {
            CraftTweakerAPI.logInfo("cannot set courage because the player's sanity data is null");
        }
    }

    @ZenMethod
    public static double getCourage(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        double value = 0;
        if (entPlayer.getCapability(CourageProvider.COURAGE_CAP, null) != null) {
            value=Objects.requireNonNull(entPlayer.getCapability(CourageProvider.COURAGE_CAP, null)).getCourage();
        } else {
            CraftTweakerAPI.logInfo("cannot get courage because the player's sanity data is null");
        }
        return value;
    }

    @ZenMethod
    public static void setTemperature(IPlayer player, double value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        if (entPlayer.getCapability(TemperatureProvider.TEMPERATURE_CAP, null) != null) {
            Objects.requireNonNull(entPlayer.getCapability(TemperatureProvider.TEMPERATURE_CAP, null)).set((float) value);
        } else {
            CraftTweakerAPI.logInfo("cannot set temperature because the player's sanity data is null");
        }
    }

    @ZenMethod
    public static double getTemperature(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        double value = 0;
        if (entPlayer.getCapability(TemperatureProvider.TEMPERATURE_CAP, null) != null) {
            value=Objects.requireNonNull(entPlayer.getCapability(TemperatureProvider.TEMPERATURE_CAP, null)).getTemperature();
        } else {
            CraftTweakerAPI.logInfo("cannot get temperature because the player's sanity data is null");
        }
        return value;
    }

    @ZenMethod
    public static void setParasitic(IPlayer player, double value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        if (entPlayer.getCapability(ParasiticProvider.PARASITIC_CAP, null) != null) {
            Objects.requireNonNull(entPlayer.getCapability(ParasiticProvider.PARASITIC_CAP, null)).setCurrentSanity((float) value);
        } else {
            CraftTweakerAPI.logInfo("cannot set parasitic because the player's sanity data is null");
        }
    }

    @ZenMethod
    public static double getParasitic(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        double value = 0;
        if (entPlayer.getCapability(ParasiticProvider.PARASITIC_CAP, null) != null) {
            value=Objects.requireNonNull(entPlayer.getCapability(ParasiticProvider.PARASITIC_CAP, null)).getParasitic();
        } else {
            CraftTweakerAPI.logInfo("cannot get parasitic because the player's sanity data is null");
        }
        return value;
    }

    @ZenMethod
    public static int getEvolutionPhase(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        return OverUtil.UTIL.getPhase(entPlayer.getEntityWorld());
    }

    @ZenMethod
    public static void setEvolutionPhase(IPlayer player, byte value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        OverUtil.UTIL.setPhase(entPlayer.getEntityWorld(),value);
    }

    @ZenMethod
    public static int getCooldown(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        return OverUtil.UTIL.getCooldown(entPlayer.getEntityWorld());
    }

    @ZenMethod
    public static void setCooldown(IPlayer player, int value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        OverUtil.UTIL.setCooldown(entPlayer.getEntityWorld(),value);
    }

    @ZenMethod
    public static int getEvoPoint(IPlayer player) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        return OverUtil.UTIL.getTotalPoint(entPlayer.getEntityWorld());
    }

    @ZenMethod
    public static void addEvoPoint(IPlayer player, int value) {
        EntityPlayer entPlayer = CraftTweakerMC.getPlayer(player);
        OverUtil.UTIL.addEvoPoint(entPlayer.getEntityWorld(),value);
    }
}
