package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

public class CommonAction {

	public static IAction newAction(JSONObject args) throws Exception {
		// Factory method for creating new actions from JSON based config
		IAction output = null;
		
		String type = args.getString("type");
		
		switch (type) {
		
		case "lightning":	output = new LightningAction(args);
		break;
		
		default:			throw new Exception("Unrecogized action type: " + type);
		}
		
		return output;
	}
	
	public CommonAction(JSONObject args) {}
	public CommonAction() {}
	public void execute(EntityPlayer player) {}
}
