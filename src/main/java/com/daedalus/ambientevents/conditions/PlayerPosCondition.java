package com.daedalus.ambientevents.conditions;

import org.apache.logging.log4j.Level;
import org.json.JSONException;
import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.comparisons.*;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

public class PlayerPosCondition implements ICondition {

	protected enum Dimension {x, y, z};
	protected IString dimension;
	protected NumericComparison comparison;
	protected double compareValue;
	
	public PlayerPosCondition(JSONObject args) throws Exception {
		
		if (args.has("dimension")) {
			dimension = Wrapper.newString(args.get("dimension"));
		} else {
			throw new Exception("No dimension specified");
		}
		
		if (args.has("comparison")) {
			comparison = new NumericComparison(Wrapper.newString(args.get("comparison")));
		} else {
			throw new Exception("No comparison specified");
		}
		
		try {
			compareValue = args.getDouble("value");
		} catch (JSONException e) {
			throw new Exception("No value specified");
		}
	}
	
	public boolean isMet(EntityPlayer player) {
		double playerValue;
		
		switch (dimension.getValue()) {
			case "x": playerValue = player.posX;
			break;
			case "y": playerValue = player.posY;
			break;
			case "z": playerValue = player.posZ;
			break;
			
			default:return false;
		}
		
		return comparison.compare(playerValue, compareValue);
	}
}
