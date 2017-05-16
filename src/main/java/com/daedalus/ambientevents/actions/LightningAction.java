package com.daedalus.ambientevents.actions;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.daedalus.ambientevents.EventHandler;
import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;

public class LightningAction extends CommonAction {

	protected IString target;
	protected INumber radius;
	
	protected double x;
	protected double y;
	protected double z;
	
	public LightningAction (JSONObject args) throws Exception {
		super(args);
		
		if (args.has("target")) {
			target = Wrapper.newString(args.get("target"));
			
			switch (target.getValue()) {
			
			case "player":		break;
			case "nearplayer":	break;
			
			default: 		throw new Exception("Unrecognized target: " + target.getValue());
			}
		} else {
			target = Wrapper.newString("player");
		}
		
		if (args.has("radius")) {
			radius = Wrapper.newNumber(args.get("radius"));
		} else { 
			radius = Wrapper.newNumber(1);
		}
	}
	
	@Override
	public void execute(EntityPlayer player) {
		if (chance.getValue() < 1) {

			switch (target.getValue()) {
			case "player":
				x = player.posX;
				y = player.posY;
				z = player.posZ;
			break;
			case "nearplayer":
				x = player.posX + EventHandler.random.nextInt((int)radius.getValue() * 2) - radius.getValue();
				y = player.posY;
				z = player.posZ + EventHandler.random.nextInt((int)radius.getValue() * 2) - radius.getValue();
			break;
			default:
				x = 0;
				y = 0;
				z = 0;
			}
			
			player.world.addWeatherEffect(new EntityLightningBolt(player.world, x, y, z, false));
		}
	}
}
