package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;
import java.util.function.Consumer;

public class WTabView extends WWidget {

	protected ArrayList<WTabLabel> labels = new ArrayList<WTabLabel>();
	protected ArrayList<WWidget> widgets = new ArrayList<WWidget>();
	
	protected Consumer<String> callback;
	
	protected int border = 2;
	
	public WTabView(WWidget parentIn) {
		super(parentIn);
	}
	
	public void addTab(WWidget widgetIn, String labelIn) {
		WTabLabel label = new WTabLabel(this, this.labels.size(), labelIn);
		label.setOnClickAction(this::onTabSwitch);
		this.labels.add(label);
		widgetIn.setParent(this);
		widgetIn.move(this.border, label.height);
		widgetIn.setSize(this.width - 2 * this.border, this.height - this.border - label.height);
		this.widgets.add(widgetIn);
	}
	
	public void onTabSwitch(int tabID) {
		for(int i = 0; i < this.widgets.size(); i++) {
			if (i == tabID) {
				this.widgets.get(i).show();
			} else {
				this.widgets.get(i).hide();
			}
		}
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.drawRect(0, 0, this.width, this.height, this.palette.edging);
		super.draw(mouseX, mouseY, partialTicks);
	}
}
