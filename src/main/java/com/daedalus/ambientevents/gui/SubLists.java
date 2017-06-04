package com.daedalus.ambientevents.gui;

import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.logging.log4j.Level;
import org.json.JSONObject;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.wrappers.JSONKeyValuePair;

public class SubLists extends WTabView {
	
	protected Consumer<JSONKeyValuePair> selectedCallback;
	
	public SubLists(WWidget parentIn) {
		super(parentIn);
				
		Iterator<String> keys = ConfiguratorGUI.manifestJSON.getJSONObject("events").keys();
		while(keys.hasNext()) {
			String key = keys.next();
			SubListEditor subListEditor = new SubListEditor(this, key);
			subListEditor.setOnElementSelectedAction(this::elementSelected);
			this.addTab(subListEditor, key);
		}
	}
	
	public void populate(JSONObject event) {
		if (event == null) {
			for (WWidget subList : this.widgets) {
				((SubListEditor) subList).clear();
			}
			return;
		}
		for (int i = 0; i < this.labels.size(); i++) {
			if (event.has(this.labels.get(i).getLabel())) {
				((SubListEditor) this.widgets.get(i)).populate(event.getJSONArray(this.labels.get(i).getLabel()));
			}
		}
	}
	
	public void elementSelected(JSONKeyValuePair element) {
		for (int i = 0; i < this.labels.size(); i++) {
			if (!labels.get(i).isSelected()) {
				((SubListEditor) this.widgets.get(i)).deselect();
			}
		}
		
		if (this.selectedCallback != null) {
			this.selectedCallback.accept(element);
		}
	}
	
	public void setOnElementSelectedAction(Consumer<JSONKeyValuePair> onElementSelectedAction) {
		this.selectedCallback = onElementSelectedAction;
	}

}
