package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import org.apache.logging.log4j.Level;

import com.daedalus.ambientevents.AmbientEvents;

import net.minecraft.client.renderer.GlStateManager;

public class WListView <T> extends WWidget {

	protected ArrayList<WListElement<T>> elements = new ArrayList<WListElement<T>>();
	protected WScrollBar scrollBar;
	protected int location;
	protected int count;
	protected int displayRange;
	protected int elementHeight;
	
	protected int scrollBarWidth = 7;
	protected int elementPadding = 2;
	
	protected Consumer<T> callback;
	
	public WListView(WWidget parentIn) {
		super(parentIn);
		this.scrollBar = new WScrollBar(this, WScrollBar.Orientation.VERTICAL);
		this.scrollBar.setOnScrollAction(this::scrollTo);
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		this.scrollBar.setSize(this.scrollBarWidth, heightIn);
		this.scrollBar.move(this.width - this.scrollBarWidth, 0);
		this.displayRange = this.height / this.fontRendererObj.FONT_HEIGHT;
		this.scrollBar.setDisplayRange(this.displayRange);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.drawRect(0, 0, this.width, this.height, this.palette.primary);
		super.draw(mouseX, mouseY, partialTicks);
		int drawnElements = this.displayRange > this.elements.size() ? this.elements.size() : this.displayRange;
		for (int i = 0; i < drawnElements; i++) {
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(this.elementPadding, i * this.fontRendererObj.FONT_HEIGHT + this.elementPadding, 0);
				this.fontRendererObj.drawString(this.elements.get(i + this.location).text, 0, 0, this.palette.text);
			}
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		super.onMouseClick(mouseX, mouseY, mouseButton);
		if (mouseButton != 0) {
			return;
		}
		
		if (callback != null) {
			int elementID = this.getElementUnderMouse(mouseX, mouseY) + location;
			if (elementID < 0 || elementID >= this.elements.size()) {
				return;
			}
			callback.accept(this.elements.get(elementID).item);
		}
	}
	
	public int getElementUnderMouse(int mouseX, int mouseY) {
		if (mouseX < 1 || mouseX > this.width - this.scrollBarWidth || mouseY < 1 || mouseY > this.height) {
			return -1;
		}
		for (int i = 0; i < this.displayRange; i++) {
			if (mouseY > i * this.fontRendererObj.FONT_HEIGHT + 2 && mouseY < (i + 1) * this.fontRendererObj.FONT_HEIGHT) {
				return i;
			}
		}
		return -1;
	}
	
	public void populate(ArrayList<WListElement<T>> elementsIn) {
		this.elements = elementsIn;
		this.recount();
	}
	
	public void populate(Iterator<WListElement<T>> elementsIn) {
		this.elements.clear();
		while (elementsIn.hasNext()) {
			this.elements.add(elementsIn.next());
		}
		this.recount();
	}
	
	public void add(WListElement<T> elementIn) {
		this.elements.add(elementIn);
		this.recount();
	}
	
	public void add(ArrayList<WListElement<T>> elementsIn) {
		this.elements.addAll(elementsIn);
		this.recount();
	}
	
	public void add(Iterator<WListElement<T>> elementsIn) {
		while (elementsIn.hasNext()) {
			this.elements.add(elementsIn.next());
		}
		this.recount();
	}
	
	protected void recount() {
		this.count = this.elements.size();
		this.scrollBar.setDisplayCount(this.count);
	}
	
	protected void scrollTo(int index) {
		this.location = index;
	}

}
