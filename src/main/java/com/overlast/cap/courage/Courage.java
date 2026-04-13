package com.overlast.cap.courage;

public class Courage implements ICourage {
	
	// Create Courage variables.
	private float Courage = 0.00f;
	private float maxCourage = 100.00f;
	private float minCourage = 0.00f;
	
	// Methods for messing with Courage or getting it.
	public void increase(float amount) {
		
		this.Courage += amount;
		
		if (this.Courage > this.maxCourage) {
			
			this.Courage = this.maxCourage;
		}
		
		else if (this.Courage < this.minCourage) {
			
			this.Courage = this.minCourage;
		}
	}
	
	public void decrease(float amount) {
		
		this.Courage -= amount;
		
		if (this.Courage < this.minCourage) {
			
			this.Courage = this.minCourage;
		}
		
		else if (this.Courage > this.maxCourage) {
			
			this.Courage = this.maxCourage;
		}
	}
	
	public void set(float amount) {
		
		this.Courage = amount;
		
		if (this.Courage > this.maxCourage) {
			
			this.Courage = this.maxCourage;
		}
		
		else if (this.Courage < this.minCourage) {
			
			this.Courage = this.minCourage;
		}
	}
	
	public void setMax(float amount) {
		
		this.maxCourage = amount;
	}
	
	public void setMin(float amount) {
		
		this.minCourage = amount;
	}
	
	public float getCourage() {
		
		return this.Courage;
	}
	
	public float getMaxCourage() {
		
		return this.maxCourage;
	}
	
	public float getMinCourage() {
		
		return this.minCourage;
	}
}