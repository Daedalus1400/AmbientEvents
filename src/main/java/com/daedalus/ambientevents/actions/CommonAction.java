package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.RandomNumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;

public class CommonAction implements IAction {

	protected INumber chance;

	public CommonAction(JSONObject args) throws Exception {
		if (args.has("chance")) {
			this.chance = new RandomNumber(0, args.getDouble("chance"));
		} else {
			this.chance = Wrapper.newNumber(0);
		}
	}

	@Override
	public void execute(EntityPlayer player) {
	}

}
