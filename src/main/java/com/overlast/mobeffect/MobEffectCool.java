package com.overlast.mobeffect;

public class MobEffectCool extends MobEffectMod {

    //透心凉:降低温度，降低理智消耗速率 天蓝
	public MobEffectCool() {
		super("cool", false, 135, 206, 235);
	}

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
