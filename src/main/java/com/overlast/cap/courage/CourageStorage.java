package com.overlast.cap.courage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

// Save Courage data for each player.
public class CourageStorage implements IStorage<ICourage> {

	@Override
	public NBTBase writeNBT(Capability<ICourage> capability, ICourage instance, EnumFacing side) {
		
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setFloat("CourageLevel", instance.getCourage());
		compound.setFloat("maxCourageLevel", instance.getMaxCourage());
		compound.setFloat("minCourageLevel", instance.getMinCourage());
		
		return compound;
	}
	
	@Override
	public void readNBT(Capability<ICourage> capability, ICourage instance, EnumFacing side, NBTBase nbt) {
		
		if (nbt instanceof NBTTagCompound) {
			
			NBTTagCompound compound = (NBTTagCompound) nbt;
		
			if (compound.hasKey("CourageLevel") && compound.hasKey("maxCourageLevel") && compound.hasKey("minCourageLevel")) {
			
				instance.set(compound.getFloat("CourageLevel"));
				instance.setMax(compound.getFloat("maxCourageLevel"));
				instance.setMin(compound.getFloat("minCourageLevel"));
			}
		}
	}
}