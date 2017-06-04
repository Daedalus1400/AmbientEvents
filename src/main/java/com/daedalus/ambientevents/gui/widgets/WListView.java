package com.daedalus.ambientevents.gui.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import net.minecraft.client.renderer.GlStateManager;

public class WListView<T> extends WWidget {

	protected ArrayList<WListElement<T>> elements = new ArrayList<WListElement<T>>();
	protected WScrollBar scrollBar;
	protected int location;
	protected int count;
	protected int displayRange;
	protected int selected = -1;

	protected int scrollBarWidth = 7;
	protected int elementPadding = 2;

	protected Consumer<WListElement<T>> callback;

	public WListView(WWidget parentIn) {
		super(parentIn);
		this.scrollBar = new WScrollBar(this, WScrollBar.Orientation.VERTICAL);
		this.scrollBar.setOnScrollAction(this::scrollTo);
	}

	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		this.scrollBar.move(this.width - this.scrollBarWidth, 0);
		this.displayRange = this.height / this.fontRendererObj.FONT_HEIGHT;
		this.scrollBar.setDisplayRange(this.displayRange);
		this.scrollBar.setSize(this.scrollBarWidth, heightIn);
		if (this.selected > -1) {
			this.scrollBar.goTo(this.selected + this.location);
		}
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.drawRect(0, 0, this.width, this.height, this.palette.primary);
			super.draw(mouseX, mouseY, partialTicks);
			if (this.count < this.location + this.displayRange) {
				this.scrollBar.goTo(this.count - this.displayRange);
			}
			int drawnElements = this.displayRange > this.count ? this.count : this.displayRange;
			int elementUnderMouse = this.getElementUnderMouse(mouseX, mouseY);
			for (int i = 0; i < drawnElements; i++) {
				int textColor;
				if (i == elementUnderMouse) {
					textColor = mixColors(this.palette.text, this.palette.highlight);
				} else {
					textColor = this.palette.text;
				}
				GlStateManager.pushMatrix();
				{
					GlStateManager.translate(0, i * this.fontRendererObj.FONT_HEIGHT, 0);
					if (i == this.selected) {
						this.drawRect(0, 1, this.width - this.scrollBarWidth, this.fontRendererObj.FONT_HEIGHT + 1,
								this.palette.highlight);
					}
					this.fontRendererObj.drawString(this.elements.get(i + this.location).text, this.elementPadding,
							this.elementPadding, textColor);
				}
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		super.onMouseClick(mouseX, mouseY, mouseButton);
		if (mouseButton != 0) {
			return;
		}

		int localSelected = this.getElementUnderMouse(mouseX, mouseY);

		if (localSelected < 0 || localSelected >= this.count) {
			return;
		}

		this.selected = localSelected;

		int elementID = this.selected + this.location;
		if (elementID >= this.elements.size()) {
			return;
		}

		this.select(this.elements.get(elementID));
	}
	
	public boolean contains(WListElement<T> element) {
		return this.elements.contains(element);
	}
	
	public boolean contains(String elementName) {
		for (WListElement<T> element : elements) {
			if (element.text.equals(elementName)) {
				return true;
			}
		}
		return false;
	}
	
	public int getIndex(WListElement<T> element) {
		return this.elements.indexOf(element);
	}
	
	public int getIndex(String elementName) {
		for (int i = 0; i < this.elements.size(); i++) {
			if (this.elements.get(i).text.equals(elementName)) {
				return i;
			}
		}
		return -1;
	}
	
	public void select(int index) {
		if (index < this.location || index > this.location + this.displayRange) {
			this.scrollBar.goTo(index);
		}
		
		if (index > this.count || index < 0) {
			this.selected = -1;
			return;
		}
		
		this.selected = index - this.location;
		
		if (this.callback != null) {
			this.callback.accept(this.elements.get(index));
		}
	}
	
	public void select(WListElement<T> element) {
		int index = this.elements.indexOf(element);
		
		this.select(index);
	}
	
	public void deselect() {
		this.select(-1);
	}

	public int getElementUnderMouse(int mouseX, int mouseY) {
		if (mouseX < 1 || mouseX > this.width - this.scrollBarWidth || mouseY < 1 || mouseY > this.height) {
			return -1;
		}
		for (int i = 0; i < this.displayRange; i++) {
			if (mouseY >= i * this.fontRendererObj.FONT_HEIGHT + 2
					&& mouseY <= (i + 1) * this.fontRendererObj.FONT_HEIGHT) {
				return i;
			}
		}
		return -1;
	}
	
	public void fitToListSize() {
		this.setSize(this.width, this.elements.size() * this.fontRendererObj.FONT_HEIGHT);
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
	
	public int getSize() {
		return this.elements.size();
	}
	
	public void add(WListElement<T> element, int index) {
		this.elements.add(index, element);
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
	
	public WListElement<T> get(int index) {
		return this.elements.get(index);
	}
	
	public void remove(WListElement<T> element) {
		this.elements.remove(element);
		this.recount();
	}
	
	public void clear() {
		this.elements.clear();
		this.deselect();
		this.recount();
	}

	protected void recount() {
		this.count = this.elements.size();
		this.scrollBar.setDisplayCount(this.count);
	}

	public void scrollTo(int index) {
		index = constrain(index, 0, this.count < this.displayRange? 0 : this.count - this.displayRange).intValue();
		this.selected += this.location - index;
		this.location = index;
	}

	public void setOnElementSelectedAction(Consumer<WListElement<T>> onElementSelectedAction) {
		this.callback = onElementSelectedAction;
	}
}
