package com.overlast.cap.parasitic;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

// Save parasitic data for each player.
public class ParasiticStorage implements IStorage<IParasitic> {

	@Override
	public NBTBase writeNBT(Capability<IParasitic> capability, IParasitic instance, EnumFacing side) {
		
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setFloat("parasiticLevel", instance.getParasitic());
		compound.setFloat("maxParasiticLevel", instance.getMaxParasitic());
		compound.setFloat("minParasiticLevel", instance.getMinParasitic());
		
		return compound;
	}
	
	@Override
	public void readNBT(Capability<IParasitic> capability, IParasitic instance, EnumFacing side, NBTBase nbt) {
		
		if (nbt instanceof NBTTagCompound) {
			
			NBTTagCompound compound = (NBTTagCompound) nbt;
		
			if (compound.hasKey("parasiticLevel") && compound.hasKey("maxParasiticLevel") && compound.hasKey("minParasiticLevel")) {
			
				instance.setCurrentSanity(compound.getFloat("parasiticLevel"));
				instance.setMax(compound.getFloat("maxParasiticLevel"));
				instance.setMin(compound.getFloat("minParasiticLevel"));
			}
		}
	}
}