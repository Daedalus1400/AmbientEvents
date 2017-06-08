package com.daedalus.ambientevents.gui;

import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.json.JSONObject;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.wrappers.JSONKeyValuePair;

public class ValueTabs extends WTabView {

	public ValueTabs(WWidget parentIn) {
		super(parentIn);
	}
	
	public void populate(JSONKeyValuePair element) {
		this.clear();
		JSONObject events;
		if (ConfiguratorGUI.manifestJSON.has("events")) {
			events = ConfiguratorGUI.manifestJSON.getJSONObject("events");
		} else {
			AmbientEvents.logger.log(Level.ERROR, "No events in manifest.json");
			return;
		}
		
		JSONObject sublist;
		
		if (events.has(element.getKey())) {
			sublist = events.getJSONObject(element.getKey());
		} else {
			AmbientEvents.logger.log(Level.ERROR, String.format("No %s in manifest.json", element.getKey()));
			return;
		}
		
		JSONObject subElement;
		
		if (sublist.has(element.getJSONObject().getString("type"))) {
			subElement = sublist.getJSONObject(element.getJSONObject().getString("type"));
		} else {
			AmbientEvents.logger.log(Level.ERROR, String.format("No %s in manifest.json", element.getJSONObject().getString("type")));
			return;
		}
		
		Iterator<String> keys = subElement.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			this.addTab(new WWidget(this), key);
		}
		
		if (this.isVisible()) {
			this.show();
		}
	}
}
