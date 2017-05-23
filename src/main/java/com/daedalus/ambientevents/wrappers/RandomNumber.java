package com.daedalus.ambientevents.wrappers;

import org.json.JSONObject;

import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class RandomNumber implements INumber {

	private double upperBound = 0;
	private double lowerBound = 0;

	public RandomNumber(JSONObject args) throws Exception {
		if (args.has("upperbound")) {
			this.upperBound = args.getDouble("upperbound");
		}

		if (args.has("lowerbound")) {
			this.lowerBound = args.getDouble("lowerbound");
		}

		if (this.upperBound == this.lowerBound) {
			throw new Exception("Invalid bounds for random number");
		}
	}

	public RandomNumber(double lowerIn, double upperIn) {
		this.lowerBound = lowerIn;
		this.upperBound = upperIn;
	}

	@Override
	public double getValue() {

		return ClientEventHandler.random.nextDouble() * (this.upperBound - this.lowerBound) + this.lowerBound;
	}

}
