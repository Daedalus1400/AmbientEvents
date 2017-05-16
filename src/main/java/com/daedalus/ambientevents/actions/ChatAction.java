package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import com.daedalus.ambientevents.wrappers.Wrapper;
import com.daedalus.ambientevents.wrappers.IString;

public class ChatAction extends CommonAction {

	protected IString message;
	
	public ChatAction(JSONObject args) throws Exception {
		super(args);
		
		if (args.has("message")) {
			message = Wrapper.newString(args.get("message")); 
		} else {
			throw new Exception("No message specified");
		}
	}
	
	public void execute(EntityPlayer player) {
		player.sendMessage(new TextComponentString(message.getValue()));
	}

}
