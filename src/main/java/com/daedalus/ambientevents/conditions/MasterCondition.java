package com.daedalus.ambientevents.conditions;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.json.JSONArray;
import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

import com.daedalus.ambientevents.AmbientEvents;

public class MasterCondition implements ICondition {
	
	ArrayList<ICondition> conditions;
	
	public static ICondition newCondition(JSONObject args) throws Exception {
		// Factory method for creating new conditions from JSON based config
		ICondition output = null;
		
		String type = args.getString("type");
		
		switch (type) {
		
		case "playerpos":	output = new PlayerPosCondition(args);
		break;
		
		default:			throw new Exception("Unrecogized condition type: " + type);
		}
		
		return output;
	}

	public MasterCondition(JSONArray args) throws Exception {
		conditions = new ArrayList<ICondition>();
		for (int i = 0; i < args.length(); i++) {
			conditions.add(newCondition(args.getJSONObject(i)));
		}
	}
	
	public boolean isMet(EntityPlayer player) {
		
		for (int i = 0; i < conditions.size(); i++) {
			if (!conditions.get(i).isMet(player)) {
				return false;
			}
		}
		
		return true;
	}
}
