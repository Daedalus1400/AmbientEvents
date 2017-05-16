package com.daedalus.ambientevents.wrappers;

public class RawString implements IString {

	protected String value;

	public RawString(String valueIn) {
		value = valueIn;
	}
	
	@Override
	public String getValue() {
		return value;
	}

}
