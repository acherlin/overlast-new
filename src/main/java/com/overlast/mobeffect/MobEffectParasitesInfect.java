package com.overlast.mobeffect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class MobEffectParasitesInfect extends MobEffectMod {

    //寄生感染，适中的紫罗兰红色	#C71585	199,21,133
	public MobEffectParasitesInfect() {
		super("parasites_infect", false, 199, 21, 133);
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
