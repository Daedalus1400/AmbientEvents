package com.daedalus.ambientevents.wrappers;

public class RawNumber implements INumber {
	
	protected double value;

	public RawNumber(double valueIn) {
		value = valueIn;
	}
	
	@Override
	public double getValue() {
		return value;
	}

}
