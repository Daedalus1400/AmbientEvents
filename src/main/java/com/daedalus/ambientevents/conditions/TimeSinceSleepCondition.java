package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.EventHandler;
import com.daedalus.ambientevents.comparisons.NumericComparison;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class TimeSinceSleepCondition implements ICondition {
	
	protected NumericComparison comparison;
	protected INumber checkValue;
	
	public TimeSinceSleepCondition(JSONObject args) throws Exception {
		if (args.has("comparison")) {
			comparison = new NumericComparison(Wrapper.newString(args.get("comparison")));
		} else {
			throw new Exception("No comparison specified");
		}
		
		if (args.has("value")) {
			checkValue = Wrapper.newNumber(args.get("value"));
		} else {
			throw new Exception("No value specified");
		}
	}
	
	@Override
	public boolean isMet(EntityPlayer player) {
		if (comparison.compare(player.world.getTotalWorldTime() - EventHandler.lastSleep, checkValue.getValue() * 24000)) {
			return true;
		}
		return false;
	}

}
