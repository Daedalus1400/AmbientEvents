package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class WeatherCondition implements ICondition {

	protected IString condition;
	
	public WeatherCondition(JSONObject args) throws Exception {
		if (args.has("condition")) {
			condition = Wrapper.newString(args.get("condition"));
		} else {
			throw new Exception("No weather conditions specified");
		}
	}
	
	@Override
	public boolean isMet(EntityPlayer player) {
		
		switch(condition.getValue()) {
		case "raining":		return player.world.getWorldInfo().isRaining();
		case "thundering":	return player.world.getWorldInfo().isThundering();
		case "clear":		return !(player.world.getWorldInfo().isRaining() || player.world.getWorldInfo().isThundering());
		
		default:			return false;
		}
		
	}

}
