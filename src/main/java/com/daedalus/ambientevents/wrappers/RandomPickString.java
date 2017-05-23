package com.daedalus.ambientevents.wrappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class RandomPickString implements IString {

	protected ArrayList<WeightedString> values;
	protected double total;

	public RandomPickString(JSONObject args) throws Exception {
		if (args.has("text")) {

			Object text = args.get("text");
			if (text instanceof JSONArray) {

				int max = ((JSONArray) text).length();
				this.values = new ArrayList<WeightedString>(max);

				for (int i = 0; i < max; i++) {
					Object string = ((JSONArray) text).get(i);
					if (string instanceof String) {
						this.values.add(new WeightedString((String) string, 1.0D));
					} else if (string instanceof JSONObject) {
						WeightedString ws = new WeightedString();
						if (((JSONObject) string).has("string")) {
							ws.string = ((JSONObject) string).getString("string");
						} else {
							throw new Exception("No string specified");
						}
						if (((JSONObject) string).has("weight")) {
							ws.weight = ((JSONObject) string).getDouble("weight");
						} else {
							ws.weight = 1;
						}
						this.values.add(ws);
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
	public String getValue() {
		double test = ClientEventHandler.random.nextDouble() * this.total;
		double subtotal = 0;
		for (int i = 0; i < this.values.size(); i++) {
			subtotal += this.values.get(i).weight;
			if (test < subtotal) {
				return this.values.get(i).string;
			}
		}

		return "";
	}

	protected class WeightedString {
		public String string;
		public double weight;

		public WeightedString() {
		}

		public WeightedString(String stringIn, double weightIn) {
			this.string = stringIn;
			this.weight = weightIn;
		}
	}
}
