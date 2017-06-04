package com.daedalus.ambientevents.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.logging.log4j.Level;
import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.wrappers.JSONKeyValuePair;

public class EventList extends WWidget {

	protected Consumer<JSONKeyValuePair> selectedCallback;
	protected WListView<JSONKeyValuePair> listView;
	protected WVanillaTextField nameField;
	protected WPushButton newButton;
	protected WPushButton duplicateButton;
	protected WPushButton deleteButton;
	
	protected JSONObject eventsJSON;
	protected JSONObject manifestJSON;
	protected WListElement<JSONKeyValuePair> selected;
	
	protected int padding = 2;
	
	public static int eventCounter = 0;
	
	public EventList(WWidget parentIn, JSONObject eventsJSONIn) {
		super(parentIn);
		
		this.listView = new WListView<JSONKeyValuePair>(this);
		this.listView.setOnElementSelectedAction(this::eventSelected);
		
		this.nameField = new WVanillaTextField(this);
		this.nameField.setOnTextChangedAction(this::renameEvent);
		this.nameField.setValidRegex("[\\w ]*");
		
		this.newButton = new WPushButton(this, "New");
		this.newButton.setOnClickAction(this::newEvent);
		
		this.duplicateButton = new WPushButton(this, "Duplicate");
		this.duplicateButton.setOnClickAction(this::duplicateEvent);
		
		this.deleteButton = new WPushButton(this, "Delete");
		this.deleteButton.setOnClickAction(this::deleteSelected);
		Iterator<String> events = ConfiguratorGUI.eventsJSON.keys();
		while(events.hasNext()) {
			String eventName = events.next();
			this.listView.add(new WListElement<JSONKeyValuePair> (eventName, new JSONKeyValuePair(eventName, ConfiguratorGUI.eventsJSON.getJSONObject(eventName))));
		}
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		
		int textHeight = this.fontRendererObj.FONT_HEIGHT + this.padding * 2; 
		
		int sizeX = this.width - this.padding * 2;
		
		this.nameField.setSize(widthIn - this.padding * 3, textHeight);
		this.nameField.move(this.padding + 1, this.padding + 1);
		
		this.newButton.setSize((this.width - this.padding * 2) / 3, textHeight);
		this.newButton.move(this.padding, this.padding * 2 + textHeight);
		
		this.duplicateButton.setSize(this.newButton.width, this.newButton.height);
		this.duplicateButton.move(this.newButton.width + this.newButton.offsetX, this.newButton.offsetY);
		
		this.deleteButton.setSize(sizeX - this.newButton.width - this.duplicateButton.width, this.newButton.height);
		this.deleteButton.move(this.duplicateButton.width + this.duplicateButton.offsetX, this.newButton.offsetY);
		
		this.listView.setSize(this.width - this.padding * 2, heightIn - this.newButton.height - this.newButton.offsetY);
		this.listView.move(this.padding, textHeight * 2 + this.padding * 2);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.drawRect(0, 0, this.width, this.height, this.palette.edging);
		this.drawRect(this.padding, this.padding, this.width - this.padding, this.height - this.padding, this.palette.primary);
		
		super.draw(mouseX, mouseY, partialTicks);
	}
	
	public void newEvent(int mouseButton) {
		
		String newName = String.format("UnnamedEvent%d", eventCounter++);
		
		ArrayList<JSONKeyValuePair> pairs = new ArrayList<JSONKeyValuePair> ();

		Iterator<String> keys = ConfiguratorGUI.manifestJSON.getJSONObject("events").keys();
		while (keys.hasNext()) {
			String keyName = keys.next();
			pairs.add(new JSONKeyValuePair(keyName, new JSONArray()));
		}
		
		JSONObject newEvent = new JSONObject();
		
		for (int i = 0; i < pairs.size(); i++) {
			newEvent.put(pairs.get(i).getKey(), pairs.get(i).getJSONArray());
		}
		
		ConfiguratorGUI.eventsJSON.put(newName, newEvent);
		this.listView.add(new WListElement<JSONKeyValuePair> (newName, new JSONKeyValuePair(newName, newEvent)));
	}
	
	public void duplicateEvent(int mouseButton) {
		if (this.selected == null) {
			return;
		}
		String newName = String.format(this.selected.text + "%d", eventCounter++);
		JSONKeyValuePair newPair = new JSONKeyValuePair(newName, this.selected.item.getJSONObject());
		WListElement<JSONKeyValuePair> newElement = new WListElement<JSONKeyValuePair> (newName, newPair);
		ConfiguratorGUI.eventsJSON.put(newName, newElement.item.getJSONObject());
		this.listView.add(newElement);
	}
	
	public void deleteSelected(int mouseButton) {
		int index = this.listView.getIndex(this.selected);
		this.listView.remove(this.selected);
		ConfiguratorGUI.eventsJSON.remove(this.selected.text);
		if (index >= this.listView.getSize()) {
			index = this.listView.getSize() - 1;
		} 
		this.listView.select(index);
	}
	
	public void renameEvent(String newName) {
		if (this.selected == null || this.listView.contains(newName)) {
			return;
		}

		int index = this.listView.getIndex(this.selected);
		ConfiguratorGUI.eventsJSON.remove(this.selected.item.getKey());
		this.listView.remove(this.selected);
		this.selected.item.setKey(newName);
		WListElement<JSONKeyValuePair> newElement = new WListElement<JSONKeyValuePair> (newName, this.selected.item);
		ConfiguratorGUI.eventsJSON.put(newName, newElement.item.getJSONObject());
		this.listView.add(newElement, index);
		this.selected = newElement;
	}
	
	public void setOnEventSelectedAction(Consumer<JSONKeyValuePair> onEventSelectedAction) {
		this.selectedCallback = onEventSelectedAction;
	}
	
	protected void eventSelected(WListElement<JSONKeyValuePair> selectedElement) {
		this.selected = selectedElement;
		this.nameField.setText(this.selected.item.getKey());
		this.onEventSelected(selectedElement.item);
	}

	public void onEventSelected(JSONKeyValuePair selectedElement) {
		if (this.selectedCallback != null) {
			this.selectedCallback.accept(selectedElement);
		}
	}
}
