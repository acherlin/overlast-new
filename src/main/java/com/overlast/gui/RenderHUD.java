package com.overlast.gui;

import com.overlast.OverLast;
import com.overlast.config.OverConfig;
import com.overlast.util.OverUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHUD extends Gui {


	//索引值
	private static int SanIndex=1;
	private static int EvoIndex=1;
	//属性条
	public static int StatusUpdate;
	//控制GUI开关
	public static boolean switchhud = true;
	public static boolean heldDirtyClock = false;
	public static boolean hideUI=false;

	public static int SanAdd = 0;
	// The stat bars themselves.
	private static final StatBar SANITY_BAR = new StatBar(StatBar.StatType.SANITY, 113, 29, 80, 23, 32, new ResourceLocation(OverLast.MOD_ID, "textures/gui/sanitybar1.png"));

	private static final StatBar EVOLUTION_BAR = new StatBar(StatBar.StatType.EVOLUTION, 113, 29, 80, 23, 32, new ResourceLocation(OverLast.MOD_ID, "textures/gui/evolutionbar1.png"));

	private static final StatBar ADD_SANITY_BAR = new StatBar(StatBar.StatType.SANITY, 113, 29, 80, 23, 32, new ResourceLocation(OverLast.MOD_ID, "textures/gui/sanaddbar.png"));

	private static final StatBar COURAGE_BAR = new StatBar(StatBar.StatType.SANITY, 113, 29, 80, 23, 32, new ResourceLocation(OverLast.MOD_ID, "textures/gui/couragebar.png"));

	// List of the main bars for easy iteration
	private static final StatBar[] MAIN_BARS = { SANITY_BAR,EVOLUTION_BAR};


	// This method gets the correct stats of the player.  这个方法可以得到玩家的正确属性资料，通过服务端传入的数据包
	public static void retrieveStats(boolean hide, boolean showRequestDirtyClock,float newSanity, float newMaxSanity, int evolution,int phase,int statusUpdate,float newTemperature, float newMaxTemperature,float courage,float maxCourage,float parasitic,float maxParasitic) {
		EvoIndex=phase;
		heldDirtyClock=showRequestDirtyClock;
		hideUI=hide;
		SANITY_BAR.setValue(newSanity);
	    SANITY_BAR.setMaxValue(newMaxSanity);
		EVOLUTION_BAR.setValue(evolution);

		COURAGE_BAR.setValue(courage);
		COURAGE_BAR.setMaxValue(maxCourage);

		if(newSanity>50) SanIndex=1;
		else if(newSanity>30) SanIndex=2;
		else SanIndex=3;

		StatusUpdate=statusUpdate;

		if (newTemperature>100)
			SanAdd=2;
		else if(newTemperature>80)
			SanAdd=1;
		else if (newTemperature<15&&newTemperature>=5)
			SanAdd=3;
		else if (newTemperature<5)
			SanAdd=4;
		else SanAdd=0;



		SANITY_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/sanitybar"+SanIndex+".png"));
		EVOLUTION_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/evolutionbar"+EvoIndex+".png"));

		ADD_SANITY_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/sanaddbar"+SanAdd+".png"));
		COURAGE_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/couragebar.png"));

		switch(phase) {
			case -2:
			case -1:EVOLUTION_BAR.setMaxValue(0);break;
			case 0:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsOne);break;
			case 1:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsTwo);break;
			case 2:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsThree);break;
			case 3:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsFour);break;
			case 4:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsFive);break;
			case 5:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsSix);break;
			case 6:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsSeven);break;
			case 7:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsEight);break;
			case 8:EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsNine);break;
			case 9:
			case 10:
				EVOLUTION_BAR.setMaxValue(OverUtil.UTIL.phaseKillsTen);break;
		}
		EVOLUTION_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/evolutionbar"+EvoIndex+".png"));

	}

	//最终渲染层
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if(!switchhud){
			//System.out.println("事件已经取消");
			return;
		}
		if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			
			// Instance of Minecraft. All of this crap is client-side (well of course) Minecraft的实例。所有这些废话都是客户端的（当然是好）。
			Minecraft mc = Minecraft.getMinecraft();
			
			// Get current screen resolution.  获取当前的屏幕分辨率。
			ScaledResolution scaled = event.getResolution();
			int screenWidth = scaled.getScaledWidth();
			int screenHeight = scaled.getScaledHeight();

			// Variables used to render the bars.  用于渲染条形图的变量。
            int x;
            int y;
            int i = 0;
            ResourceLocation texture;
            int fullWidth;
            int fullHeight;
            int movingTextureX;
            int movingTextureY;
            int fullBarWidth;
            int movingWidth;
            String text;

            // The loop that renders the main stat bars. 循环渲染主要属性条的。
			for (StatBar bar : MAIN_BARS) {

                // RIGHTMOST position of this bar. This is so we can account for the bar widths correctly.
				// 这个条形图的RIGHTMOST位置。这是为了让我们能够正确地考虑到条形的宽度。
                x = getX(screenWidth, i);
                y = getY(screenHeight, i);
                i++;

			    // Should this bar be displayed? 是否应该显示此栏？
                if (bar.shouldBeDisplayed()) {

                    // Get the stuff
                    texture = bar.getTexture();
                    fullWidth = bar.getFullWidth();
                    fullHeight = bar.getFullHeight();
                    movingTextureX = bar.getMovingTextureX();
                    movingTextureY = bar.getMovingTextureY();
                    fullBarWidth = bar.getFullBarWidth();
                    movingWidth = bar.getMovingWidth();
                    text = bar.getTextToDisplay();




					if(bar==EVOLUTION_BAR&&OverConfig.MECHANICS.showRequestDirtyClock&&!heldDirtyClock) {
						continue;
					}
					if(bar==EVOLUTION_BAR&&!hideUI) {
						continue;
					}

                    // Actual rendering. First one is the moving bar. Second one is the whole bar.
                    mc.renderEngine.bindTexture(texture);
                    drawTexturedModalRect(x - fullBarWidth - 10, y + 3, movingTextureX, movingTextureY, movingWidth, fullHeight);
                    drawTexturedModalRect(x - fullWidth, y, 0, 0, fullWidth, fullHeight);
					drawCenteredString(mc.fontRenderer, text, x - fullWidth - 15, y + 13, Integer.parseInt("FFFFFF", 16));

					if(bar==SANITY_BAR) {
						texture = COURAGE_BAR.getTexture();
						mc.renderEngine.bindTexture(texture);
						drawTexturedModalRect(x - fullBarWidth - 10, y + 3, movingTextureX, movingTextureY, COURAGE_BAR.getMovingWidth(), fullHeight);
					}
					if(bar==SANITY_BAR) {
						if(SanAdd>0) {
							texture = ADD_SANITY_BAR.getTexture();
							mc.renderEngine.bindTexture(texture);
							drawTexturedModalRect(x - fullWidth, y, 0, 0, fullWidth, fullHeight);
						}
					}
                }
            }

		}
	}

	
	// Help determine where to place a stat bar.
	// It's more of a base position, and will be modified for whatever texture it's for.

	// This'll either be right by 0 or right by the rightmost edge of the screen.
	// So pos doesn't actually matter.
	// 帮助确定放置状态栏的位置。
	// 这更像是一个基础位置，并且会根据它的纹理进行修改。
	// 这要么是在0的右边，要么是在屏幕的最右边的边缘。
	// 所以位置实际上并不重要。
	private int getX(int screenWidth, int pos) {
		
		// Figure out where the user specified to put the bars (in config)
		// From there, figure out where exactly to put the single bar, according to the config value.
		// On the left of the screen
		if (OverConfig.CLIENT.barPositions.equals("top left") || OverConfig.CLIENT.barPositions.equals("middle left") || OverConfig.CLIENT.barPositions.equals("bottom left")) {
			
			return 150+OverConfig.CLIENT.Xoffset;
		}
		
		// Right of the screen
		else if (OverConfig.CLIENT.barPositions.equals("top right") || OverConfig.CLIENT.barPositions.equals("middle right") || OverConfig.CLIENT.barPositions.equals("bottom right")) {
			
			return screenWidth - 2+OverConfig.CLIENT.Xoffset;
		}
		
		// "middle right" by default.
		else {
			
			return screenWidth - 2+OverConfig.CLIENT.Xoffset;
		}
	}
	
	// The stat bars are 20 pixels apart, vertically. 统计条在垂直方向上相隔20像素。
	private int getY(int screenHeight, int pos) {
		
		// Top of the screen
		if (OverConfig.CLIENT.barPositions.equals("top left") || OverConfig.CLIENT.barPositions.equals("top right")) {
			
			// Is this the 1st bar? 2nd bar? etc.
			return 10 + (20 * pos) + OverConfig.CLIENT.Yoffset;
		}
		
		// Middle of the screen
		else if (OverConfig.CLIENT.barPositions.equals("middle left") || OverConfig.CLIENT.barPositions.equals("middle right")) {
			
			return (screenHeight / 2) - 30 + (20 * pos) + OverConfig.CLIENT.Yoffset;
		}
		
		// Bottom of the screen
		else if (OverConfig.CLIENT.barPositions.equals("bottom left") || OverConfig.CLIENT.barPositions.equals("bottom right")) {
			
			return screenHeight - 80 + (20 * pos) + OverConfig.CLIENT.Yoffset;
		}
		
		// Middle by default
		else {
			
			return (screenHeight / 2) - 30 + (20 * pos) + OverConfig.CLIENT.Yoffset;
		}
	}
}