package com.daedalus.ambientevents.conditions;

import org.json.JSONObject;

import com.daedalus.ambientevents.comparisons.NumericComparison;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class TimeOfDayCondition implements ICondition {

	protected NumericComparison comparison;
	protected INumber checkValue;

	public TimeOfDayCondition(JSONObject args) throws Exception {
		if (args.has("comparison")) {
			this.comparison = new NumericComparison(Wrapper.newString(args.get("comparison")));
		} else {
			throw new Exception("No comparison specified");
		}

		if (args.has("value")) {
			this.checkValue = Wrapper.newNumber(args.get("value"));
		} else {
			throw new Exception("No value specified");
		}
	}

	@Override
	public boolean isMet(EntityPlayer player) {
		if (this.comparison.compare(player.world.getWorldTime(), this.checkValue.getValue())) {
			return true;
		}
		return false;
	}

}
