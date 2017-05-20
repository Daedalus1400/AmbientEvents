package com.daedalus.ambientevents.handlers;

import java.util.Random;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.GenericEvent;
import com.daedalus.ambientevents.wrappers.MapNumber;

import org.json.*;

public class ClientEventHandler extends CommonEventHandler {
	
	// Client side event handler
	
	public EntityPlayer player;
	public File configPath;

	protected int tick = 0;
	public static Random random = new Random();
	protected boolean active = false;
	protected boolean configGood = false;
	
	protected ArrayList<GenericEvent> events;
	
	// Tracking Variables
	public static long lastSleep = 0;
	
	@Override
	public void init() {

		File JSONPath = new File(configPath.getPath() + "/AmbientEvents");
		File JSONFile = new File(JSONPath.getPath() + "/events.json");
		
		if (!JSONPath.exists()) {
			try {
				JSONPath.mkdirs();
				JSONFile.createNewFile();
			} catch (Exception e) {
				AmbientEvents.logger.log(Level.ERROR, "Cannot create JSON file");
			}
			return;
		}
		
		JSONObject eventsJSON;
			
		try {
			eventsJSON = new JSONObject(new String(Files.readAllBytes(JSONFile.toPath())));
		} catch (Exception e) {
			AmbientEvents.logger.log(Level.ERROR, "Cannot parse JSON file");
			return;
		}
		
		events = new ArrayList<GenericEvent>();
		
		Iterator<String> eventIt = eventsJSON.keys();
		
		while (eventIt.hasNext()) {
			String eventName = eventIt.next();
			try {
				events.add(new GenericEvent(eventsJSON.getJSONObject(eventName)));
			} catch (Exception e) {
				AmbientEvents.logger.log(Level.ERROR, "Error parsing event: " + eventName, e);
			}
		}
		
		configGood = true;
	}
	
	@SubscribeEvent
	public void onWakeUp(PlayerWakeUpEvent e) {
		if (e.getEntityPlayer() == player) {
			lastSleep = player.world.getTotalWorldTime();
		}
	}
	
	@SubscribeEvent
	public void onLogon(PlayerLoggedInEvent e) {
		player = e.player;
		MapNumber.player = player;
		
		if (lastSleep == 0) {
			lastSleep = player.world.getTotalWorldTime();
		}
		
		if (configGood) {
			active = true;
		}
	}
	
	@SubscribeEvent
	public void onLogoff(PlayerLoggedOutEvent e) {
		active = false;
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		if (e.phase == Phase.START && active) {
			
			for (int i = tick; i < events.size(); i+=20) {
				events.get(i).process(player);
			}
			
			tick++;
			if (tick == 20) {
				tick = 0;
			}
		}
	}
}
