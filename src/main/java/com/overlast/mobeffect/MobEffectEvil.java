package com.overlast.mobeffect;

public class MobEffectEvil extends MobEffectMod {

    //邪恶：瞬间治疗无效化 黑色
	public MobEffectEvil() {
		super("evil", false, 28, 28, 28);
	}

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
