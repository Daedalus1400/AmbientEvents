package com.daedalus.ambientevents.wrappers;

import org.json.JSONObject;

import com.daedalus.ambientevents.EventHandler;

public class RandomNumber implements INumber {
	
	private double upperBound = 0;
	private double lowerBound = 0;
	private double range;
	
	public RandomNumber(JSONObject args) throws Exception {
		if (args.has("upperbound")) {
			upperBound = args.getDouble("upperbound");
		}
		
		if (args.has("lowerbound")) {
			lowerBound = args.getDouble("lowerbound");
		}
		
		if (upperBound == lowerBound) {
			throw new Exception("Invalid bounds for random number");
		}
		
		range = upperBound - lowerBound;
	}
	
	public double getValue() {
		
		return EventHandler.random.nextDouble()*range+lowerBound;
	}

}
