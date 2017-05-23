package com.daedalus.ambientevents;

import org.json.JSONObject;

import com.daedalus.ambientevents.actions.IAction;
import com.daedalus.ambientevents.actions.MasterAction;
import com.daedalus.ambientevents.conditions.ICondition;
import com.daedalus.ambientevents.conditions.MasterCondition;

import net.minecraft.entity.player.EntityPlayer;

public class GenericEvent {

	protected ICondition condition;
	protected IAction action;

	public GenericEvent(JSONObject args) throws Exception {
		this.condition = new MasterCondition(args.getJSONArray("conditions"));
		this.action = new MasterAction(args.getJSONArray("actions"));
	}

	public void process(EntityPlayer player) {
		if (this.condition.isMet(player)) {
			this.action.execute(player);
		}
	}
}
