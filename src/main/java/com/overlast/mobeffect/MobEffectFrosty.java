package com.overlast.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MobEffectFrosty extends MobEffectMod {

    //冻结buff: 低温，缓慢，冻伤，理智冻结 海军蓝
	public MobEffectFrosty() {
		super("frosty", true, 0, 0, 128);
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
            ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.01F * (float)(amplifier + 1));
        }
    }
}
