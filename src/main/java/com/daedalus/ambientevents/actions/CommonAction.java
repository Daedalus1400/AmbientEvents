package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.RandomNumber;
import com.daedalus.ambientevents.wrappers.Wrapper;

public class CommonAction implements IAction{
	
	protected INumber chance;

	public CommonAction(JSONObject args) throws Exception {
		if (args.has("chance")) {
			chance = new RandomNumber(0, args.getDouble("chance"));
		} else {
			chance = Wrapper.newNumber(0);
		}
	}
	
	public void execute(EntityPlayer player) {}
	
}
