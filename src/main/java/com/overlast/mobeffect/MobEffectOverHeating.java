package com.overlast.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class MobEffectOverHeating extends MobEffectMod {

    //过热，起火，理智消耗加快 226,17,12
	public MobEffectOverHeating() {
		super("over_heating", true, 226, 17, 12);
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
