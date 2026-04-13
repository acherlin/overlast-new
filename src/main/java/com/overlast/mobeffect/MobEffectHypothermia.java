package com.overlast.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MobEffectHypothermia extends MobEffectMod {

    //低温过低，移速减慢 深天蓝
	public MobEffectHypothermia() {
		super("hypothermia", true, 0, 191, 255);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn instanceof EntityPlayer)
        {
            ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.005F * (float)(amplifier + 1));
        }
    }

}
