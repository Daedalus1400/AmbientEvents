package com.daedalus.ambientevents.gui;

import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.wrappers.JSONKeyValuePair;

public class SubListEditor extends WWidget {
	
	protected Consumer<JSONObject> selectedCallback;

	protected WPushButton newButton;
	protected WPushButton duplicateButton;
	protected WPushButton deleteButton;
	
	protected WListView<JSONObject> listView;
	
	protected int padding = 2;
	
	public SubListEditor(WWidget parentIn) {
		super(parentIn);
		
		this.listView = new WListView<JSONObject>(this);
		this.listView.setOnElementSelectedAction(this::elementSelected);
		
		this.newButton = new WPushButton(this, "New");
		this.newButton.setOnClickAction(this::newElement);
		
		this.duplicateButton = new WPushButton(this, "Duplicate");
		this.duplicateButton.setOnClickAction(this::duplicateElement);
		
		this.deleteButton = new WPushButton(this, "Delete");
		this.deleteButton.setOnClickAction(this::deleteSelected);
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		
		int textHeight = this.fontRendererObj.FONT_HEIGHT + this.padding * 2; 
		
		int sizeX = this.width - this.padding;
		
		this.newButton.setSize((this.width - this.padding) / 3, textHeight);
		this.newButton.move(this.padding, 0);
		
		this.duplicateButton.setSize(this.newButton.width, this.newButton.height);
		this.duplicateButton.move(this.newButton.width + this.newButton.offsetX, this.newButton.offsetY);
		
		this.deleteButton.setSize(sizeX - this.newButton.width - this.duplicateButton.width, this.newButton.height);
		this.deleteButton.move(this.duplicateButton.width + this.duplicateButton.offsetX, this.newButton.offsetY);
		
		this.listView.setSize(sizeX, this.height - this.newButton.height - this.newButton.offsetY);
		this.listView.move(this.padding, this.newButton.height);
	}
	
	public void setOnElementSelectedAction(Consumer<JSONObject> onElementSelectedAction) {
		this.selectedCallback = onElementSelectedAction;
	}
	
	public void elementSelected(WListElement<JSONObject> element) {
		this.selectedCallback.accept(element.item);
	}
	
	public void newElement(int mouseButton) {
		
	}
	
	public void duplicateElement(int mouseButton) {
		
	}
	
	public void deleteSelected(int mouseButton) {
		
	}
	
	public void deselect() {
		this.listView.deselect();
	}
	
	public void populate(JSONArray array) {
		this.listView.clear();
		for (int i = 0; i < array.length(); i++) {
			if (array.getJSONObject(i).has("type")) {
				this.listView.add(new WListElement<JSONObject>(array.getJSONObject(i).getString("type"), array.getJSONObject(i)));
			}
		}
	}
}
