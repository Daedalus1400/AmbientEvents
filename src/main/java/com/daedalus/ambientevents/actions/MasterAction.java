package com.daedalus.ambientevents.actions;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;

public class MasterAction implements IAction {

	public static IAction newAction(JSONObject args) throws Exception {
		// Factory method for creating new actions from JSON based config

		if (args.has("type")) {
			switch (args.getString("type")) {

			case "lightning":
				return new LightningAction(args);
			case "chat":
				return new ChatAction(args);
			case "potioneffect":
				return new PotionEffectAction(args);
			case "playsound":
				return new PlaySoundAction(args);

			default:
				throw new Exception("Unrecogized action type: " + args.getString("type"));
			}
		} else {
			throw new Exception("No type specified");
		}

	}

	ArrayList<IAction> actions;

	public MasterAction(JSONArray args) throws Exception {
		this.actions = new ArrayList<IAction>();
		for (int i = 0; i < args.length(); i++) {
			this.actions.add(newAction(args.getJSONObject(i)));
		}
	}

	@Override
	public void execute(EntityPlayer player) {
		for (int i = 0; i < this.actions.size(); i++) {
			this.actions.get(i).execute(player);
		}
	}
}
