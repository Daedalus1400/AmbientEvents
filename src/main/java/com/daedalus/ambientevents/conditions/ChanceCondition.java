package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.handlers.ClientEventHandler;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class ChanceCondition implements ICondition {

	protected INumber chance;

	public ChanceCondition(JSONObject args) throws Exception {
		if (args.has("chance")) {
			this.chance = Wrapper.newNumber(args.get("chance"));
		} else {
			throw new Exception("No chance specified");
		}
	}

	@Override
	public boolean isMet(EntityPlayer player) {
		if (ClientEventHandler.random.nextDouble() * this.chance.getValue() < 1) {
			return true;
		}
		return false;
	}

}
