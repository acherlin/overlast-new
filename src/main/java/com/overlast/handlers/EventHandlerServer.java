package com.overlast.handlers;

import com.overlast.OverLast;
import com.overlast.gui.RenderHUD;
import com.overlast.util.client.KeyBinds;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@EventBusSubscriber(modid = OverLast.MOD_ID)
public class EventHandlerServer {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBinds.KEY_SWITCH.isPressed()) {
            if (RenderHUD.switchhud) {
                RenderHUD.switchhud = false;
            } else {
                RenderHUD.switchhud = true;
            }
        }
    }

}