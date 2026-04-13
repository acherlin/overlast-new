package com.overlast.cap.parasitic;


public class Parasitic implements IParasitic {
	
	// Create sanity variables.
	private float sanity = 100.00f;
	private float maxParasitic = 100.00f;
	private float minParasitic = 0.00f;
	
	// Methods for messing with sanity or getting it.
	public void increase(float amount) {

		this.setCurrentSanity(this.sanity + amount);
		
	}
	
	public void decrease(float amount) {

		this.setCurrentSanity(this.sanity - amount);

	}
	
	public void setCurrentSanity(float amount) {

		if (amount > this.maxParasitic) {

			amount = this.maxParasitic;
		}
		
		else if (amount < this.minParasitic) {

			amount = this.minParasitic;
		}

		this.sanity = amount;
	}
	
	public void setMax(float amount) {
		
		this.maxParasitic = amount;
	}
	
	public void setMin(float amount) {
		
		this.minParasitic = amount;
	}
	
	public float getParasitic() {
		
		return this.sanity;
	}
	
	public float getMaxParasitic() {
		
		return this.maxParasitic;
	}
	
	public float getMinParasitic() {
		
		return this.minParasitic;
	}
}