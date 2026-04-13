package com.overlast.cap.courage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

// Provides Courage mechanic to the player.
public class CourageProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(ICourage.class)
	public static final Capability<ICourage> COURAGE_CAP = null;
	
	private ICourage instance = COURAGE_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == COURAGE_CAP;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		return capability == COURAGE_CAP ? COURAGE_CAP.<T> cast(this.instance) : null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		
		return COURAGE_CAP.getStorage().writeNBT(COURAGE_CAP, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {

		COURAGE_CAP.getStorage().readNBT(COURAGE_CAP, this.instance, null, nbt);
	}
}