package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.EventHandler;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class ChanceCondition implements ICondition {

	protected INumber chance;
	
	public ChanceCondition(JSONObject args) throws Exception {
		if (args.has("chance")) {
			chance = Wrapper.newNumber(args.get("chance"));
		} else {
			throw new Exception("No chance specified");
		}
	}
	
	@Override
	public boolean isMet(EntityPlayer player) {
		if (EventHandler.random.nextDouble() * chance.getValue() < 1) {
			return true;
		}
		return false;
	}

}
