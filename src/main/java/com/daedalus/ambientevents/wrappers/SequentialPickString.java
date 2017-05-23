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

				this.max = ((JSONArray) text).length();
				this.values = new ArrayList<String>(this.max);

				for (int i = 0; i < this.max; i++) {
					this.values.add(((JSONArray) text).getString(i));

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
		if (this.index == this.max) {
			this.index = 0;
		}

		return this.values.get(this.index++);
	}
}
