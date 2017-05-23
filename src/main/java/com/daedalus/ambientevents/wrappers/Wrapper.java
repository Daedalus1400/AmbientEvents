package com.daedalus.ambientevents.wrappers;

import org.json.JSONObject;

public class Wrapper {

	// Because I love abstraction, I'm abracting away primitives.
	// It's for good reason, though.
	// It's so that you can use either primitives or other things that return
	// primitives.
	// Like a random number generator, or a mapping function.
	// As long as it behaves according the interface, it doesn't matter how you
	// get the value.

	public static INumber newNumber(Object args) throws Exception {

		if (args instanceof Double) {
			return new RawNumber((double) args);

		} else if (args instanceof Integer) {
			return new RawNumber((int) args);

		} else if (args instanceof JSONObject) {

			if (((JSONObject) args).has("type")) {

				switch (((JSONObject) args).getString("type")) {

				case "random":
					return new RandomNumber((JSONObject) args);
				case "randompick":
					return new RandomPickNumber((JSONObject) args);
				case "sequentialpick":
					return new SequentialPickNumber((JSONObject) args);
				case "map":
					return new MapNumber((JSONObject) args);

				default:
					throw new Exception("Numeric type not recognized");
				}
			}
		}

		throw new Exception("Numeric value not recognized");
	}

	public static IString newString(Object args) throws Exception {

		if (args instanceof String) {
			return new RawString((String) args);
		} else if (args instanceof JSONObject) {

			if (((JSONObject) args).has("type")) {

				switch (((JSONObject) args).getString("type")) {

				case "randompick":
					return new RandomPickString((JSONObject) args);
				case "sequentialpick":
					return new SequentialPickString((JSONObject) args);

				default:
					throw new Exception("String type not recognized");
				}
			}
		}

		throw new Exception("String value not recognized");
	}
}
