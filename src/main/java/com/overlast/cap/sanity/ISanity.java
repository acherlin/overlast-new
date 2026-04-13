package com.overlast.cap.sanity;

public interface ISanity {
	
	// Stuff we can do with sanity.
	public void increase(float amount);
	public void decrease(float amount);
	public void set(float amount);
	
	// Messing with min and maxes.
	public void setMaxSanity(float amount);
	public void setMinSanity(float amount);
	
	// Getting values
	public float getSanity();
	public float getMaxSanity();
	public float getMinSanity();
}