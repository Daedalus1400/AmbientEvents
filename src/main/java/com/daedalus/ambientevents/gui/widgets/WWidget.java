package com.daedalus.ambientevents.gui.widgets;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class WWidget extends GuiScreen {

	public int offsetX = 0;
	public int offsetY = 0;
	protected int widgetWidth;
	protected int widgetHeight;
	protected boolean visible;
	
	protected ArrayList<WWidget> subWidgets = new ArrayList<WWidget>();
	protected WWidget parent;
	
	public WWidget(WWidget parent) {
		if (parent != null) {
			parent.addWidget(this);
		}
	}
	
	public void move(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	
	public void setSize(int widthIn, int heightIn) {
		widgetWidth = widthIn;
		widgetHeight = heightIn;
	}
	
	public boolean isMouseOver(int mouseX, int mouseY) {
		return (mouseX >= 0) && (mouseX <= widgetWidth) && (mouseY >= 0) && (mouseY<= widgetHeight);
	}
	
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		for (WWidget subwidget : subWidgets) {
			if (subwidget.isMouseOver(mouseX - subwidget.offsetX, mouseY - subwidget.offsetY)) {
				subwidget.onMouseClick(mouseX - subwidget.offsetX, mouseY - subwidget.offsetY, mouseButton);
			}
		}
	}
	
	public void onMouseRelease(int mouseX, int mouseY, int state) {
		
	}
	
	public void addWidget(WWidget widget) {
		subWidgets.add(widget);
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}
	
	public void draw(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			GlStateManager.pushMatrix(); {
				GlStateManager.translate(offsetX, offsetY, 0);
				for (WWidget subwidget : subWidgets) {
					subwidget.draw(mc, mouseX - subwidget.offsetX, mouseY - subwidget.offsetY);
				}
			} GlStateManager.popMatrix();
		}
	}
}
