package com.daedalus.ambientevents.gui;

import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.json.JSONObject;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.gui.widgets.*;

public class SubLists extends WTabView {
	
	public SubLists(WWidget parentIn) {
		super(parentIn);
				
		Iterator<String> keys = ConfiguratorGUI.manifestJSON.getJSONObject("events").keys();
		while(keys.hasNext()) {
			String key = keys.next();
			SubListEditor subListEditor = new SubListEditor(this);
			subListEditor.setOnElementSelectedAction(this::elementSelected);
			this.addTab(subListEditor, key);
		}
	}
	
	public void populate(JSONObject event) {
		for (int i = 0; i < this.labels.size(); i++) {
			if (event.has(this.labels.get(i).getLabel())) {
				((SubListEditor) this.widgets.get(i)).populate(event.getJSONArray(this.labels.get(i).getLabel()));
			}
		}
	}
	
	public void elementSelected(JSONObject element) {
		for (int i = 0; i < this.labels.size(); i++) {
			if (!labels.get(i).isSelected()) {
				((SubListEditor) this.widgets.get(i)).deselect();
			}
		}
	}

}
