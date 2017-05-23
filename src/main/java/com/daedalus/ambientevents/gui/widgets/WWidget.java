package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class WWidget extends GuiScreen {

	public int offsetX = 0;
	public int offsetY = 0;
	protected int widgetWidth;
	protected int widgetHeight;
	protected boolean visible;

	protected WWidget focus;
	protected boolean hasFocus;

	protected ArrayList<WWidget> subWidgets = new ArrayList<WWidget>();
	protected WWidget parent;

	public WWidget(WWidget parent) {
		if (parent != null) {
			parent.addWidget(this);
		}
	}

	public void move(int x, int y) {
		this.offsetX = x;
		this.offsetY = y;
	}

	public void setSize(int widthIn, int heightIn) {
		this.widgetWidth = widthIn;
		this.widgetHeight = heightIn;
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return (mouseX >= 0) && (mouseX <= this.widgetWidth) && (mouseY >= 0) && (mouseY <= this.widgetHeight);
	}

	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		for (int i = this.subWidgets.size() - 1; i < -1; i--) {
			if (this.subWidgets.get(i).isMouseOver(mouseX - this.subWidgets.get(i).offsetX,
					mouseY - this.subWidgets.get(i).offsetY)) {
				this.subWidgets.get(i).onMouseClick(mouseX - this.subWidgets.get(i).offsetX,
						mouseY - this.subWidgets.get(i).offsetY, mouseButton);
				if (this.focus != null) {
					this.focus.setUnfocused();
				}
				this.focus = this.subWidgets.get(i);
				this.focus.setFocused();
				break;
			}
		}
	}

	public void setFocused() {
		this.hasFocus = true;
	}

	public void setUnfocused() {
		this.hasFocus = false;
		if (this.focus != null) {
			this.focus.setUnfocused();
		}
	}

	public boolean isFocused() {
		return this.hasFocus;
	}

	public void onMouseRelease(int mouseX, int mouseY, int state) {
		if (this.focus != null) {
			this.focus.onMouseRelease(mouseX, mouseY, state);
		}
	}

	public void onMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if (this.focus != null) {
			this.focus.onMouseDrag(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		}
	}

	public void onKeyTyped(char typedChar, int keyCode) {
		if (this.focus != null) {
			this.focus.onKeyTyped(typedChar, keyCode);
		}
	}

	public void addWidget(WWidget widget) {
		this.subWidgets.add(widget);
	}

	public void show() {
		this.visible = true;
	}

	public void hide() {
		this.visible = false;
	}

	public void draw(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(this.offsetX, this.offsetY, 0);
				for (WWidget subwidget : this.subWidgets) {
					subwidget.draw(mc, mouseX - subwidget.offsetX, mouseY - subwidget.offsetY);
				}
			}
			GlStateManager.popMatrix();
		}
	}
}
