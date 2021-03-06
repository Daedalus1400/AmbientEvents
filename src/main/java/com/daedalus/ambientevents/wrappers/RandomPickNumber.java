package com.daedalus.ambientevents.wrappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class RandomPickNumber implements INumber {

	protected ArrayList<WeightedNumber> values;
	protected double total;

	public RandomPickNumber(JSONObject args) throws Exception {
		if (args.has("value")) {

			Object value = args.get("value");
			if (value instanceof JSONArray) {

				int max = ((JSONArray) value).length();
				this.values = new ArrayList<WeightedNumber>(max);

				for (int i = 0; i < max; i++) {
					Object number = ((JSONArray) value).get(i);
					if (number instanceof Double) {
						this.values.add(new WeightedNumber((double) number, 1.0D));
					} else if (number instanceof JSONObject) {
						WeightedNumber wn = new WeightedNumber();
						if (((JSONObject) number).has("string")) {
							wn.number = ((JSONObject) number).getDouble("number");
						} else {
							throw new Exception("No string specified");
						}
						if (((JSONObject) number).has("weight")) {
							wn.weight = ((JSONObject) number).getDouble("weight");
						} else {
							wn.weight = 1;
						}
						this.values.add(wn);
					}
				}
			} else {
				throw new Exception("Unrecognized text value specified");
			}
		} else {
			throw new Exception("No text specified");
		}

		for (int i = 0; i < this.values.size(); i++) {
			this.total += this.values.get(i).weight;
		}
	}

	@Override
	public double getValue() {
		double test = ClientEventHandler.random.nextDouble() * this.total;
		double subtotal = 0;
		for (int i = 0; i < this.values.size(); i++) {
			subtotal += this.values.get(i).weight;
			if (test < subtotal) {
				return this.values.get(i).number;
			}
		}

		return 0;
	}

	protected class WeightedNumber {
		public double number;
		public double weight;

		public WeightedNumber() {
		}

		public WeightedNumber(double numberIn, double weightIn) {
			this.number = numberIn;
			this.weight = weightIn;
		}
	}

}
