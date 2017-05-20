package com.daedalus.ambientevents.wrappers;

import org.json.JSONObject;

import com.daedalus.ambientevents.handlers.ClientEventHandler;

import net.minecraft.entity.player.EntityPlayer;

public class MapNumber implements INumber {

	protected double inLow;
	protected double inHigh;
	protected double outLow;
	protected double outHigh;
	protected boolean clamp;
	protected String input;
	
	public static EntityPlayer player;
	
	public MapNumber(JSONObject args) throws Exception {
		if (args.has("input")) {
			input = args.getString("input");
		} else {
			throw new Exception("No input specified");
		}
		
		if (args.has("inlow")) {
			inLow = args.getDouble("inlow");
		} else {
			inLow = 0;
		}
		
		if (args.has("inhigh")) {
			inHigh = args.getDouble("inhigh");
		} else {
			throw new Exception("No input high specified");
		}
		
		if (args.has("outlow")) {
			outLow = args.getDouble("outlow");
		} else {
			outLow = 0;
		}
		
		if (args.has("outhigh")) {
			outHigh = args.getDouble("outhigh");
		} else {
			throw new Exception("No output high specified");
		}
		
		if (args.has("clamp")) {
			clamp = args.getString("clamp").equals("true");
		} else {
			clamp = false;
		}
	}
	
	@Override
	public double getValue() {
		
		double value = 0;
		
		switch (input) {
		
		case "playerposx":	value = player.posX;
		case "playerposy":	value = player.posY;
		case "playerposz":	value = player.posZ;
		case "timeofday":	value = player.world.getWorldTime();
		case "worldtime":	value = (double)player.world.getTotalWorldTime() / 24000;
		case "timesincesleep":	value = (double)(player.world.getTotalWorldTime() - ClientEventHandler.lastSleep) / 24000; 
		}
		
		double output = (value - inLow) / (inHigh - inLow) * (outHigh - outLow) + outLow;
		
		if (clamp) {
			if (output > outHigh) {
				output = outHigh;
			} else if (output < outLow) {
				output = outLow;
			}
		}
		
		return output;
	}

}
