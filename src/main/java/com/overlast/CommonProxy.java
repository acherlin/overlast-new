package com.overlast;

import com.mrcrayfish.guns.common.WorkbenchRegistry;
import com.mrcrayfish.guns.init.ModGuns;
import com.overlast.cap.CapEvents;
import com.overlast.cap.CapabilityHandler;
import com.overlast.cap.courage.Courage;
import com.overlast.cap.courage.CourageStorage;
import com.overlast.cap.courage.ICourage;
import com.overlast.cap.parasitic.IParasitic;
import com.overlast.cap.parasitic.Parasitic;
import com.overlast.cap.parasitic.ParasiticStorage;
import com.overlast.cap.sanity.ISanity;
import com.overlast.cap.sanity.Sanity;
import com.overlast.cap.sanity.SanityStorage;
import com.overlast.cap.temperature.ITemperature;
import com.overlast.cap.temperature.Temperature;
import com.overlast.cap.temperature.TemperatureStorage;
import com.overlast.command.OverCommand;
import com.overlast.config.OverConfig;
import com.overlast.finalbattle.FBTexts;
import com.overlast.handlers.EventFinalBattle;
import com.overlast.handlers.EventHandlerServer;
import com.overlast.lib.ModBlocks;
import com.overlast.packet.OverPackets;
import com.overlast.season.DailyRadio;
import com.overlast.season.WorldSeason;
import com.overlast.season.modifier.BiomeTempController;
import com.overlast.util.OverUtil;
import com.overlast.util.Registererer;
import com.overlast.util.WorldDataMgr;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {

		// Register all new items and blocks.
		MinecraftForge.EVENT_BUS.register(new Registererer());

		// Register cap.
		CapabilityManager.INSTANCE.register(ISanity.class, new SanityStorage(), Sanity::new);
		CapabilityManager.INSTANCE.register(ITemperature.class, new TemperatureStorage(), Temperature::new);
		CapabilityManager.INSTANCE.register(IParasitic.class, new ParasiticStorage(), Parasitic::new);
		CapabilityManager.INSTANCE.register(ICourage.class, new CourageStorage(), Courage::new);
	}

	public void init(FMLInitializationEvent event) {

		// Register event handlers.
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new CapEvents());
		MinecraftForge.EVENT_BUS.register(new WorldSeason());
		MinecraftForge.EVENT_BUS.register(new DailyRadio());
		// Other
		MinecraftForge.EVENT_BUS.register(new EventHandlerServer());
		MinecraftForge.EVENT_BUS.register(new EventFinalBattle());
		MinecraftForge.EVENT_BUS.register(new FBTexts());


		// Register network packets.
		OverPackets.initPackets();

		if (Loader.isModLoaded("cgm")) {
			//枪械工作台配方
			//自动步枪
			WorkbenchRegistry.registerRecipe(new ItemStack(ModGuns.MACHINE_PISTOL),
					new ItemStack(Items.IRON_INGOT, 20));
			//样本方块（感染）
			WorkbenchRegistry.registerRecipe(new ItemStack(ModBlocks.SpecimenInfect),
					OverUtil.UTIL.ConfigRecipe(OverConfig.CUSTOM.specimenInfect)
			);
			//样本方块（农业）
			WorkbenchRegistry.registerRecipe(new ItemStack(ModBlocks.SpecimenFarm),
					OverUtil.UTIL.ConfigRecipe(OverConfig.CUSTOM.specimenFarm)
			);
			//样本方块（细胞）
			WorkbenchRegistry.registerRecipe(new ItemStack(ModBlocks.SpecimenCell),
					OverUtil.UTIL.ConfigRecipe(OverConfig.CUSTOM.specimenCell)
			);
			//世界净化核心
			WorkbenchRegistry.registerRecipe(new ItemStack(ModBlocks.FinalSpecimen),
					OverUtil.UTIL.ConfigRecipe(OverConfig.CUSTOM.finalSpecimen)
			);
		}


	}

	public void postInit(FMLPostInitializationEvent event) {

	}


	public void serverStarted(FMLServerStartedEvent event) {

		// Grab initial biome temperatures.
		BiomeTempController biomeTemp = new BiomeTempController();
		biomeTemp.storeOriginalTemperatures();
		biomeTemp = null;
		WorldDataMgr.loadFromDisk();
	}


	public void serverStarting(FMLServerStartingEvent event)
	{
		//服务器指令 WIP
		event.registerServerCommand(new OverCommand());
	}

	public IThreadListener getThreadListener(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.server;
		} else
			return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player;
		} else
			return null;
	}
}