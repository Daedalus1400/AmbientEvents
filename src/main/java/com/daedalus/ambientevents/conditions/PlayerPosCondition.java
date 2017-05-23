package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.comparisons.NumericComparison;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerPosCondition implements ICondition {

	protected IString dimension;
	protected NumericComparison comparison;
	protected INumber compareValue;

	public PlayerPosCondition(JSONObject args) throws Exception {

		if (args.has("dimension")) {
			this.dimension = Wrapper.newString(args.get("dimension"));
		} else {
			throw new Exception("No dimension specified");
		}

		if (args.has("comparison")) {
			this.comparison = new NumericComparison(Wrapper.newString(args.get("comparison")));
		} else {
			throw new Exception("No comparison specified");
		}

		if (args.has("value")) {
			this.compareValue = Wrapper.newNumber(args.get("value"));
		} else {
			throw new Exception("No value specified");
		}
	}

	@Override
	public boolean isMet(EntityPlayer player) {
		double playerValue;

		switch (this.dimension.getValue()) {
		case "x":
			playerValue = player.posX;
			break;
		case "y":
			playerValue = player.posY;
			break;
		case "z":
			playerValue = player.posZ;
			break;

		default:
			return false;
		}

		return this.comparison.compare(playerValue, this.compareValue.getValue());
	}
}
