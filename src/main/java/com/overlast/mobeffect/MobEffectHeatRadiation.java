package com.overlast.mobeffect;

public class MobEffectHeatRadiation extends MobEffectMod {

    //高热辐射，低阶过热，理智降低，橙红色 	255,69,0
	public MobEffectHeatRadiation() {
		super("heat_radiation", true, 255, 69, 0);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
