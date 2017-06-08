package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;
import java.util.function.Consumer;

public class WTabView extends WWidget {

	protected ArrayList<WTabLabel> labels = new ArrayList<WTabLabel>();
	protected ArrayList<WWidget> widgets = new ArrayList<WWidget>();
	
	protected Consumer<String> callback;
	
	protected int border = 2;
	protected int selected;
	
	public WTabView(WWidget parentIn) {
		super(parentIn);
	}
	
	public void addTab(WWidget widgetIn, String labelIn) {
		WTabLabel label = new WTabLabel(this, this.labels.size(), labelIn);
		label.setOnClickAction(this::onTabSwitch);
		if (this.labels.size() > 0) {
			int location = this.labels.get(this.labels.size()-1).offsetX + this.labels.get(this.labels.size()-1).width;
			label.move(location, 0);
		} else {
			label.move(this.border, 0);
		}
		this.labels.add(label);
		
		widgetIn.setParent(this);
		widgetIn.move(this.border, label.height);
		widgetIn.setSize(this.width - 2 * this.border, this.height - this.border - label.height);
		this.widgets.add(widgetIn);
		if (this.labels.size() == 1) {
			this.labels.get(0).select();
		}
	}
	
	public void clear() {
		this.labels.clear();
		this.widgets.clear();
		this.subWidgets.clear();
	}
	
	public void onTabSwitch(int tabID) {
		this.selected = tabID;
		for(int i = 0; i < this.widgets.size(); i++) {
			if (i == tabID) {
				this.widgets.get(i).show();
			} else {
				this.widgets.get(i).hide();
				this.labels.get(i).deselect();
			}
		}
		
		if (this.callback != null) {
			this.callback.accept(this.labels.get(this.selected).label);
		}
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		for (WWidget widget : this.widgets) {
			widget.setSize(this.width - 2 * this.border, this.height - this.border - this.labels.get(0).height);
		}
	}
	
	@Override
	public void show() {
		this.visible = true;
		for (WTabLabel label : labels) {
			label.show();
		}
		this.onTabSwitch(this.selected);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible && this.labels.size() > 0) {
			this.drawRect(0, this.labels.get(0).height, this.width, this.height, this.palette.edging);
			super.draw(mouseX, mouseY, partialTicks);
		}
	}
}
