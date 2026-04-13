package com.overlast.cap.courage;

public interface ICourage {
	
	// Stuff we can do with Courage.
	public void increase(float amount);
	public void decrease(float amount);
	public void set(float amount);
	
	// Messing with min and maxes.
	public void setMax(float amount);
	public void setMin(float amount);
	
	// Getting values
	public float getCourage();
	public float getMaxCourage();
	public float getMinCourage();
}