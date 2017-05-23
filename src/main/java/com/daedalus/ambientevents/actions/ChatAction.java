package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ChatAction extends CommonAction {

	protected IString message;

	public ChatAction(JSONObject args) throws Exception {
		super(args);

		if (args.has("message")) {
			this.message = Wrapper.newString(args.get("message"));
		} else {
			throw new Exception("No message specified");
		}
	}

	@Override
	public void execute(EntityPlayer player) {
		player.sendMessage(new TextComponentString(this.message.getValue()));
	}

}
