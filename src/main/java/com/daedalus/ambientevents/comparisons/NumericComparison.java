package com.daedalus.ambientevents.comparisons;

import com.daedalus.ambientevents.wrappers.IString;

public class NumericComparison {

	protected IString symbol;

	protected static boolean greaterThan(double in1, double in2) {
		return in1 > in2;
	}

	protected static boolean lessThan(double in1, double in2) {
		return in1 < in2;
	}

	public NumericComparison(IString symbolIn) {
		this.symbol = symbolIn;
	}

	public boolean compare(double in1, double in2) {
		switch (this.symbol.getValue()) {

		case ">":
			return greaterThan(in1, in2);
		case "<":
			return lessThan(in1, in2);

		default:
			return false;
		}
	}
}
