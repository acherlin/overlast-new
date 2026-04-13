package com.overlast.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class MobEffectParasitesPurify extends MobEffectMod {

    //寄生净化，抵抗负面效果 柠檬薄纱	#FFFACD	255,250,205
	public MobEffectParasitesPurify() {
		super("parasites_purify", true, 255, 250, 205);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
    
    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        if (entityLivingBaseIn.isNonBoss() && (entityLivingBaseIn.getHealth() / entityLivingBaseIn.getMaxHealth()) < (0.05f * (amplifier + 1)))
        {
        	entityLivingBaseIn.attackEntityFrom(DamageSource.WITHER.setDamageIsAbsolute(), 1005.0F);
        }
    }

}
