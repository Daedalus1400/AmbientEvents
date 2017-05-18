package com.daedalus.ambientevents.wrappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.EventHandler;

public class RandomPickString implements IString {

	protected ArrayList<WeightedString> values;
	protected double total;

	public RandomPickString(JSONObject args) throws Exception {
		if (args.has("text")) {
			
			Object text = args.get("text");
			if (text instanceof JSONArray) {
				
				int max = ((JSONArray)text).length();
				values = new ArrayList<WeightedString>(max);
				
				for (int i = 0; i < max; i++) {
					Object string = ((JSONArray) text).get(i);
					if (string instanceof String) {
						values.add(new WeightedString((String)string, 1.0D));
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
						values.add(ws);
					}
				}
			} else {
				throw new Exception("Unrecognized text value specified");
			}
		} else {
			throw new Exception("No text specified");
		}
		
		for (int i = 0; i < values.size(); i++) {
			total += values.get(i).weight;
		}
	}
	
	@Override
	public String getValue() {
		double test = EventHandler.random.nextDouble() * total;
		double subtotal = 0;
		for (int i = 0; i < values.size(); i++) {
			subtotal += values.get(i).weight;
			if (test < subtotal) {
				return values.get(i).string;
			}
		}
		
		return "";
	}

	protected class WeightedString {
		public String string;
		public double weight;
		
		public WeightedString() {}
		
		public WeightedString(String stringIn, double weightIn) {
			string = stringIn;
			weight = weightIn;
		}
	}
}
