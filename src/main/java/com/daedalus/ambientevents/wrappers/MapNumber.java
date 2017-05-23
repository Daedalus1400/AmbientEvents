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
			this.input = args.getString("input");
		} else {
			throw new Exception("No input specified");
		}

		if (args.has("inlow")) {
			this.inLow = args.getDouble("inlow");
		} else {
			this.inLow = 0;
		}

		if (args.has("inhigh")) {
			this.inHigh = args.getDouble("inhigh");
		} else {
			throw new Exception("No input high specified");
		}

		if (args.has("outlow")) {
			this.outLow = args.getDouble("outlow");
		} else {
			this.outLow = 0;
		}

		if (args.has("outhigh")) {
			this.outHigh = args.getDouble("outhigh");
		} else {
			throw new Exception("No output high specified");
		}

		if (args.has("clamp")) {
			this.clamp = args.getString("clamp").equals("true");
		} else {
			this.clamp = false;
		}
	}

	@Override
	public double getValue() {

		double value = 0;

		switch (this.input) {

		case "playerposx":
			value = player.posX;
		case "playerposy":
			value = player.posY;
		case "playerposz":
			value = player.posZ;
		case "timeofday":
			value = player.world.getWorldTime();
		case "worldtime":
			value = (double) player.world.getTotalWorldTime() / 24000;
		case "timesincesleep":
			value = (double) (player.world.getTotalWorldTime() - ClientEventHandler.lastSleep) / 24000;
		}

		double output = (value - this.inLow) / (this.inHigh - this.inLow) * (this.outHigh - this.outLow) + this.outLow;

		if (this.clamp) {
			if (output > this.outHigh) {
				output = this.outHigh;
			} else if (output < this.outLow) {
				output = this.outLow;
			}
		}

		return output;
	}

}
