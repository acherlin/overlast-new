package com.overlast;

import com.mrcrayfish.guns.client.gui.DisplayProperty;
import com.mrcrayfish.guns.client.gui.GuiWorkbench;
import com.mrcrayfish.guns.init.ModGuns;
import com.overlast.gui.RenderHUD;
import com.overlast.lib.ModBlocks;
import com.overlast.tileentity.TileOldBeaconRenderer;
import com.overlast.util.client.KeyBinds;
import com.overlast.util.client.ModelRegisterer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		
		// Register models.
		MinecraftForge.EVENT_BUS.register(new ModelRegisterer());
		KeyBinds.register();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		
		super.init(event);
		if (Loader.isModLoaded("cgm")) {
			GuiWorkbench.addDisplayProperty(new ItemStack(ModGuns.MACHINE_PISTOL), new DisplayProperty(0.0F, 0.55F, -0.25F, 0.0F, 0.0F, 0.0F, 3.0F));
			GuiWorkbench.addDisplayProperty(new ItemStack(ModBlocks.SpecimenInfect), new DisplayProperty(0.0F, 0.55F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5F));
			GuiWorkbench.addDisplayProperty(new ItemStack(ModBlocks.SpecimenFarm), new DisplayProperty(0.0F, 0.55F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5F));
			GuiWorkbench.addDisplayProperty(new ItemStack(ModBlocks.SpecimenCell), new DisplayProperty(0.0F, 0.55F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5F));
			GuiWorkbench.addDisplayProperty(new ItemStack(ModBlocks.FinalSpecimen), new DisplayProperty(0.0F, 0.55F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5F));
		}
	}


	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
		super.postInit(event);
		
		// Render stat bars.
		MinecraftForge.EVENT_BUS.register(new RenderHUD());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBeacon.class, (TileEntitySpecialRenderer)new TileOldBeaconRenderer());
	}


	public void registerItemRenderer(Item item, int meta, String id){
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}


	public void registerItemRenderer(Item item, int meta, String pathName, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(pathName), id));
	}


	@Override
	public IThreadListener getThreadListener(MessageContext context){
		if (context.side.isClient())
		{
			return Minecraft.getMinecraft();
		}
		else
		{
			return context.getServerHandler().player.server;
		}
	}

	@Override
	public EntityPlayer getPlayer(MessageContext context)
	{
		if (context.side.isClient())
		{
			return Minecraft.getMinecraft().player;
		}
		else
		{
			return context.getServerHandler().player;
		}
	}
}