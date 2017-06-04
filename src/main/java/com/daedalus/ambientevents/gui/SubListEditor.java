package com.daedalus.ambientevents.gui;

import java.util.Iterator;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.wrappers.JSONKeyValuePair;

public class SubListEditor extends WWidget {
	
	protected Consumer<JSONKeyValuePair> selectedCallback;

	protected WPushButton newButton;
	protected WPushButton deleteButton;
	protected WDropDownMenu<String> dropMenu;
	
	protected WListView<JSONObject> listView;
	protected JSONArray array;
	protected String type;
	
	protected WListElement<JSONObject> selected;
	
	protected int padding = 2;
	
	public SubListEditor(WWidget parentIn, String typeIn) {
		super(parentIn);
		this.type = typeIn;
		
		this.listView = new WListView<JSONObject>(this);
		this.listView.setOnElementSelectedAction(this::elementSelected);
		
		this.newButton = new WPushButton(this, "New");
		this.newButton.setOnClickAction(this::newElement);
		
		this.deleteButton = new WPushButton(this, "Delete");
		this.deleteButton.setOnClickAction(this::deleteSelected);
		
		this.dropMenu = new WDropDownMenu<String>(this);
		if (ConfiguratorGUI.manifestJSON.has("events")) {
			if (ConfiguratorGUI.manifestJSON.getJSONObject("events").has(this.type)) {
				Iterator<String> keys = ConfiguratorGUI.manifestJSON.getJSONObject("events").getJSONObject(this.type).keys();
				while(keys.hasNext()) {
					String key = keys.next();
					this.dropMenu.add(new WListElement(key, key));
				}
			}
		}
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		
		int textHeight = this.fontRendererObj.FONT_HEIGHT + this.padding * 2; 
		
		int sizeX = this.width - this.padding;
		
		this.newButton.setSize((this.width - this.padding) / 2, textHeight);
		this.newButton.move(this.padding, 0);
		
		this.deleteButton.setSize(sizeX - this.newButton.width, this.newButton.height);
		this.deleteButton.move(this.newButton.width + this.newButton.offsetX, this.newButton.offsetY);
		
		this.dropMenu.setSize(sizeX - this.listView.scrollBarWidth, textHeight);
		this.dropMenu.move(this.padding, this.newButton.height);
		
		this.listView.setSize(sizeX, this.height - this.dropMenu.height - this.dropMenu.offsetY);
		this.listView.move(this.padding, this.dropMenu.height + this.dropMenu.offsetY);
		
		this.dropMenu.setMaxMenuLength(this.listView.height);
	}
	
	public void setOnElementSelectedAction(Consumer<JSONKeyValuePair> onElementSelectedAction) {
		this.selectedCallback = onElementSelectedAction;
	}
	
	public void elementSelected(WListElement<JSONObject> element) {
		this.selected = element;
		if (this.selectedCallback != null) {
			this.selectedCallback.accept(new JSONKeyValuePair(this.type, element.item));
		}
	}
	
	public void newElement(int mouseButton) {
		if (this.array == null) {
			return;
		}
		JSONObject newJSON = new JSONObject();
		newJSON.put("type", this.dropMenu.getSelected().item);
		this.array.put(newJSON);
		this.listView.add(new WListElement(this.dropMenu.getSelected().text, newJSON));
	}
	
	public void deleteSelected(int mouseButton) {
		if (this.selected == null) {
			return;
		}
		int index = this.listView.getIndex(this.selected);
		
		this.array.remove(index);
		this.listView.remove(this.selected);
		if (index >= this.listView.getSize()) {
			index = this.listView.getSize() - 1;
		} 
		this.listView.select(index);
	}
	
	public void deselect() {
		this.listView.deselect();
		this.selected = null;
	}
	
	public void clear() {
		this.deselect();
		this.listView.clear();
	}
	
	public void populate(JSONArray arrayIn) {
		this.array = arrayIn;
		this.listView.clear();
		for (int i = 0; i < arrayIn.length(); i++) {
			if (arrayIn.getJSONObject(i).has("type")) {
				this.listView.add(new WListElement<JSONObject>(arrayIn.getJSONObject(i).getString("type"), arrayIn.getJSONObject(i)));
			}
		}
	}
}
