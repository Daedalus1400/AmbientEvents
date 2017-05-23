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

				this.max = ((JSONArray) text).length();
				this.values = new ArrayList<Double>(this.max);

				for (int i = 0; i < this.max; i++) {
					this.values.add(((JSONArray) text).getDouble(i));

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
		if (this.index == this.max) {
			this.index = 0;
		}

		return this.values.get(this.index++);
	}

}
