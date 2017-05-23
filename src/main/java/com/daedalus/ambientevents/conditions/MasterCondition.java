package com.daedalus.ambientevents.conditions;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

public class MasterCondition implements ICondition {

	ArrayList<ICondition> conditions;

	public static ICondition newCondition(JSONObject args) throws Exception {
		// Factory method for creating new conditions from JSON based config

		if (args.has("type")) {
			switch (args.getString("type")) {

			case "playerpos":
				return new PlayerPosCondition(args);
			case "alwaystrue":
				return new AlwaysTrueCondition();
			case "chance":
				return new ChanceCondition(args);
			case "timeofday":
				return new TimeOfDayCondition(args);
			case "worldtime":
				return new WorldTimeCondition(args);
			case "timesincesleep":
				return new TimeSinceSleepCondition(args);
			case "once":
				return new OnceCondition();
			case "weather":
				return new WeatherCondition(args);
			case "canseesky":
				return new CanSeeSkyCondition();

			default:
				throw new Exception("Unrecogized condition type: " + args.getString("type"));
			}
		} else {
			throw new Exception("No type specified");
		}
	}

	public MasterCondition(JSONArray args) throws Exception {
		this.conditions = new ArrayList<ICondition>();
		for (int i = 0; i < args.length(); i++) {
			this.conditions.add(newCondition(args.getJSONObject(i)));
		}
	}

	@Override
	public boolean isMet(EntityPlayer player) {

		for (int i = 0; i < this.conditions.size(); i++) {
			if (!this.conditions.get(i).isMet(player)) {
				return false;
			}
		}

		return true;
	}
}
