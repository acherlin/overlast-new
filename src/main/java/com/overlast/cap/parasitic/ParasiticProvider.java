package com.overlast.cap.parasitic;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

// Provides sanity mechanic to the player.
public class ParasiticProvider implements ICapabilitySerializable<NBTBase> {
	
	@CapabilityInject(IParasitic.class)
	public static final Capability<IParasitic> PARASITIC_CAP = null;
	
	private IParasitic instance = PARASITIC_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		return capability == PARASITIC_CAP;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		
		return capability == PARASITIC_CAP ? PARASITIC_CAP.<T> cast(this.instance) : null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		
		return PARASITIC_CAP.getStorage().writeNBT(PARASITIC_CAP, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {

		PARASITIC_CAP.getStorage().readNBT(PARASITIC_CAP, this.instance, null, nbt);
	}
}