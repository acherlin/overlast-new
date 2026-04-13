package com.overlast.command;

import com.google.common.collect.Lists;
import com.overlast.season.Season;
import com.overlast.season.WorldSeason;
import com.overlast.util.OverWorldData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

import static com.overlast.season.WorldSeason.getDaysIntoSeason;


public class OverCommand extends CommandBase
{
    @Override
    public String getName()
    {
        return "overlast";
    }

    @Override
    public List getAliases()
    {
        return Lists.newArrayList("ol");
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.overlast.usage";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException("commands.overlast.usage");
        }
        else if ("setseason".equals(args[0]))
        {
            setSeason(sender, args);
        } else if ("getseason".equals(args[0])){
            getSeason(sender, args);
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.overlast.usage"));
        }
    }

    private void getSeason(ICommandSender sender, String[] args) throws  CommandException
    {
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        Season season = WorldSeason.getSeason();

        TextComponentTranslation i18season;
        switch (OverWorldData.seasonToInt(season)) {
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

        sender.sendMessage(new TextComponentTranslation("message.seasons.forecast0", i18season, getDaysIntoSeason()));
    }

    private void setSeason(ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP player = getCommandSenderAsPlayer(sender);
        String sseason = args[1];
        if(sseason.equals("spring")) {
            WorldSeason.setSeason(Season.SPRING);
        }else if(sseason.equals("summer")) {
            WorldSeason.setSeason(Season.SUMMER);
        }else if(sseason.equals("autumn")) {
            WorldSeason.setSeason(Season.AUTUMN);
        }else if(sseason.equals("winter")) {
            WorldSeason.setSeason(Season.WINTER);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, "getseason","setseason");
        }
        else if (args.length == 2)
        {
            return getListOfStringsMatchingLastWord(args, "spring","summer","autumn","winter");
        }


        return super.getTabCompletions(server, sender, args, pos);
    }

}
