package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.daedalus.ambientevents.gui.Palette;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class WWidget extends GuiScreen {

	public int offsetX = 0;
	public int offsetY = 0;
	protected boolean visible;

	protected WWidget focus;
	protected boolean hasFocus;
	protected boolean mouseOver;

	protected WWidget dragTarget;

	protected ArrayList<WWidget> subWidgets = new ArrayList<WWidget>();
	protected WWidget parent;
	public Palette palette;

	public WWidget(WWidget parentIn) {
		if (parentIn != null) {
			this.setParent(parentIn);
		}
	}

	public void move(int x, int y) {
		this.offsetX = x;
		this.offsetY = y;
	}

	public void setSize(int widthIn, int heightIn) {
		this.width = widthIn;
		this.height = heightIn;
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		this.mouseOver = (mouseX >= 0) && (mouseX < this.width) && (mouseY >= 0) && (mouseY < this.height);
		return this.mouseOver;
	}

	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		for (int i = this.subWidgets.size() - 1; i > -1; i--) {

			if (this.subWidgets.get(i).isVisible() && this.subWidgets.get(i).isMouseOver(mouseX - this.subWidgets.get(i).offsetX,
					mouseY - this.subWidgets.get(i).offsetY)) {

				this.subWidgets.get(i).onMouseClick(mouseX - this.subWidgets.get(i).offsetX,
						mouseY - this.subWidgets.get(i).offsetY, mouseButton);

				if (this.focus != null && !this.focus.equals(this.subWidgets.get(i))) {
					this.focus.setUnfocused();
				}
				this.focus = this.subWidgets.get(i);
				this.focus.setFocused();
				this.dragTarget = this.focus;
				break;
			}
		}
	}

	public void onMouseRelease(int mouseX, int mouseY, int state) {
		if (this.focus != null) {
			this.focus.onMouseRelease(mouseX - this.focus.offsetX, mouseY - this.focus.offsetY, state);
		}
		this.dragTarget = null;
	}

	public void onMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if (this.dragTarget != null) {
			this.dragTarget.onMouseDrag(mouseX - this.dragTarget.offsetX, mouseY - this.dragTarget.offsetY,
					clickedMouseButton, timeSinceLastClick);
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
	
	public boolean isVisible() {
		return this.visible;
	}

	public void onKeyTyped(char typedChar, int keyCode) {
		if (this.focus != null) {
			this.focus.onKeyTyped(typedChar, keyCode);
		}
	}

	public void addWidget(WWidget widget) {
		this.subWidgets.add(widget);
	}
	
	public void removeWidget(WWidget widget) {
		this.subWidgets.remove(widget);
	}

	public void show() {
		this.visible = true;
		for (WWidget subwidget : this.subWidgets) {
			subwidget.show();
		}
	}
	
	public void hide() {
		this.visible = false;
		for (WWidget subwidget : this.subWidgets) {
			subwidget.hide();
		}
	}
	
	public void setParent(WWidget parentIn) {
		if (this.parent != null) {
			this.parent.removeWidget(this);
		}
		this.parent = parentIn;
		this.parent.addWidget(this);
		this.mc = this.parent.mc;
		this.palette = this.parent.palette;
		this.fontRendererObj = this.parent.fontRendererObj;
	}

	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {

			for (WWidget subwidget : this.subWidgets) {
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(subwidget.offsetX, subwidget.offsetY, 0);
					subwidget.draw(mouseX - subwidget.offsetX, mouseY - subwidget.offsetY, partialTicks);
				}
				GlStateManager.popMatrix();
			}
		}
	}

	public static int mixColors(int color1, int color2) {
		int out = 0;
		for (int i = 0; i < 32; i += 8) {
			int color1A = color1 & (0b11111111 << i);
			int color2A = color2 & (0b11111111 << i);
			out ^= ((color1A + color2A) >> 1) & (0b11111111 << i);
		}
		return out;
	}

	public static float getAlpha(int color) {
		return ((color & 0xff000000) >> 24) / 255.0f;
	}

	public static float getRed(int color) {
		return ((color & 0x00ff0000) >> 16) / 255.0f;
	}

	public static float getGreen(int color) {
		return ((color & 0x0000ff00) >> 8) / 255.0f;
	}

	public static float getBlue(int color) {
		return (color & 0x000000ff) / 255.0f;
	}

	public static Number constrain(Number input, Number low, Number high) {
		if (input.doubleValue() > high.doubleValue()) {
			return high;
		} else if (input.doubleValue() < low.doubleValue()) {
			return low;
		} else {
			return input;
		}
	}
	
	public static JSONObject copyJSONObject(JSONObject json) {
		JSONObject result = new JSONObject();
		
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			Object object = json.get(key);
			if (object instanceof Integer) {
				result.put(key, new Integer((int)object));
			} else if (object instanceof Double) {
				result.put(key, new Double((double)object));
			} else if (object instanceof String) {
				result.put(key, new String((String)object));
			} else if (object instanceof JSONObject) {
				result.put(key, copyJSONObject((JSONObject)object));
			} else if (object instanceof JSONArray) {
				result.put(key, copyJSONArray((JSONArray)object));
			}
		}
		
		return result;
	}
	
	public static JSONArray copyJSONArray(JSONArray array) {
		JSONArray result = new JSONArray();
		
		for (int i = 0; i < array.length(); i++) {
			Object object = array.get(i);
			if (object instanceof Integer) {
				result.put(new Integer((int)object));
			} else if (object instanceof Double) {
				result.put(new Double((double)object));
			} else if (object instanceof String) {
				result.put(new String((String)object));
			} else if (object instanceof JSONObject) {
				result.put(copyJSONObject((JSONObject)object));
			} else if (object instanceof JSONArray) {
				result.put(copyJSONArray((JSONArray)object));
			}
		}
		
		return result;
	}
}
