package com.d1m.client.management.utils.animations.easings.utilities;

public class Progression {

	double value;
	
	public Progression() {
		this.value = 0;
	}
	
	public Progression(double value) {
		this.value = value;
	}
	
	public final double getValue() {
		return value;
	}
	
	public final void setValue(double value) {
		this.value = value;
	}
	
	public final void reset() {
		this.value = 0;
	}
	
}
