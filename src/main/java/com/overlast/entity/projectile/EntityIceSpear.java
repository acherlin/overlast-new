package com.overlast.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityIceSpear extends EntityThrowable
{
    private final String name = "ice_spear";
    public EntityIceSpear(World worldIn)
    {
        super(worldIn);
    }

    public EntityIceSpear(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityIceSpear(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                float amount = 6.0F;
                DamageSource source = DamageSource.causeThrownDamage(this, this.getThrower());
                if (result.entityHit instanceof EntityLivingBase)
                {
                    EntityLivingBase target = ((EntityLivingBase) result.entityHit);
                }
                result.entityHit.attackEntityFrom(source, amount);
            }
            this.setDead();
        }
    }
}