package com.daedalus.ambientevents.wrappers;

public class RawNumber implements INumber {

	protected double value;

	public RawNumber(double valueIn) {
		this.value = valueIn;
	}

	@Override
	public double getValue() {
		return this.value;
	}

}
