package com.daedalus.ambientevents;

import org.json.JSONObject;
import org.apache.logging.log4j.Level;
import org.json.JSONArray;

import com.daedalus.ambientevents.actions.IAction;
import com.daedalus.ambientevents.actions.MasterAction;
import com.daedalus.ambientevents.conditions.ICondition;
import com.daedalus.ambientevents.conditions.MasterCondition;

import net.minecraft.entity.player.EntityPlayer;

public class GenericEvent {

	protected ICondition condition;
	protected IAction action;
	
	public GenericEvent(JSONObject args) throws Exception {
			condition = new MasterCondition(args.getJSONArray("conditions"));
			action = new MasterAction(args.getJSONArray("actions"));		
	}
	
	public void process(EntityPlayer player) {
		if (condition.isMet(player)) {
			action.execute(player);
		}
	}
}