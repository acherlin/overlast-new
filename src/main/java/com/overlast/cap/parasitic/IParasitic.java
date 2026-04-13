package com.overlast.cap.parasitic;

public interface IParasitic {
	
	// Stuff we can do with sanity.
	public void increase(float amount);
	public void decrease(float amount);
	public void setCurrentSanity(float amount);
	
	// Messing with min and maxes.
	public void setMax(float amount);
	public void setMin(float amount);
	
	// Getting values
	public float getParasitic();
	public float getMaxParasitic();
	public float getMinParasitic();
}