package com.daedalus.ambientevents.wrappers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SequentialPickString implements IString {

	protected ArrayList<String> values;
	protected int index;
	protected int max;
	
	public SequentialPickString(JSONObject args) throws Exception {
		if (args.has("text")) {
			
			Object text = args.get("text");
			if (text instanceof JSONArray) {
				
				max = ((JSONArray)text).length();
				values = new ArrayList<String>(max);
				
				for (int i = 0; i < max; i++) {
					values.add(((JSONArray)text).getString(i));
					
				}
			} else {
				throw new Exception("Unrecognized text value specified");
			}
		} else {
			throw new Exception("No text specified");
		}
	}
	
	@Override
	public String getValue() {
		if (index == max) {
			index = 0;
		}

		return values.get(index++);
	}
}
