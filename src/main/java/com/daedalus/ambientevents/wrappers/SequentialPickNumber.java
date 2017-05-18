package com.daedalus.ambientevents.wrappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SequentialPickNumber implements INumber {

	protected ArrayList<Double> values;
	protected int index;
	protected int max;
	
	public SequentialPickNumber(JSONObject args) throws Exception {
		if (args.has("text")) {
			
			Object text = args.get("text");
			if (text instanceof JSONArray) {
				
				max = ((JSONArray)text).length();
				values = new ArrayList<Double>(max);
				
				for (int i = 0; i < max; i++) {
					values.add(((JSONArray)text).getDouble(i));
					
				}
			} else {
				throw new Exception("Unrecognized text value specified");
			}
		} else {
			throw new Exception("No text specified");
		}
	}
	
	@Override
	public double getValue() {
		if (index == max) {
			index = 0;
		}

		return values.get(index++);
	}

}
