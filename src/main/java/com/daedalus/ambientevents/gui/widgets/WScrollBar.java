package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;

import net.minecraft.client.renderer.GlStateManager;

public class WScrollBar extends WWidget {

	protected int barWidth;
	protected int barHeight;
	protected int barOffsetX;
	protected int barOffsetY;

	public static enum Orientation {
		HORIZONTAL, VERTICAL
	};

	protected Orientation orientation;

	protected int displayRange = 0;
	protected int displayCount = 0;
	protected int location = 0;
	protected float stepSize;
	protected int steps;

	protected int mouseStartX;
	protected int mouseStartY;

	protected Consumer<Integer> callback;

	public WScrollBar(WWidget parentIn, Orientation orientationIn) {
		super(parentIn);
		this.orientation = orientationIn;
	}

	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		if (this.displayCount <= this.displayRange) {
			this.barHeight = heightIn;
			this.barWidth = widthIn;
			this.steps = 0;
			this.stepSize = 1f;
			return;
		} else if (this.orientation == Orientation.HORIZONTAL) {
			this.barHeight = heightIn;
			this.barWidth = widthIn * this.displayRange / this.displayCount;
			this.steps = this.displayCount - this.displayRange;
			this.stepSize = (this.width - this.barWidth) / (float) this.steps;
		} else {
			this.barWidth = widthIn;
			this.barHeight = heightIn * this.displayRange / this.displayCount;
			this.steps = this.displayCount - this.displayRange;
			this.stepSize = (this.height - this.barHeight) / (float) this.steps;
		}
	}

	public void setDisplayRange(int range) {
		this.displayRange = range;
		this.setSize(this.width, this.height);
	}

	public void setDisplayCount(int count) {
		this.displayCount = count;
		this.setSize(this.width, this.height);
	}

	public void setOnScrollAction(Consumer<Integer> onScrollActionIn) {
		this.callback = onScrollActionIn;
	}

	public boolean isMouseOverSlider(int mouseX, int mouseY) {
		this.mouseOver = (mouseX >= this.barOffsetX) && (mouseX <= this.barWidth + this.barOffsetX)
				&& (mouseY >= this.barOffsetY) && (mouseY <= this.barHeight + this.barOffsetY);
		return this.mouseOver;
	}

	public void goTo(int index) {
		if (index == this.location || index != constrain(index, 0, this.steps).intValue()) {
			return;
		}
		this.location = constrain(index, 0, this.steps).intValue();
		if (this.orientation == Orientation.HORIZONTAL) {
			this.barOffsetX = (int) (this.location * this.stepSize);
			this.barOffsetY = 0;
		} else {
			this.barOffsetY = (int) (this.location * this.stepSize);
			this.barOffsetX = 0;
		}

		if (this.callback != null) {
			this.callback.accept(constrain(index, 0, this.displayCount).intValue());
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton != 0 || this.isMouseOverSlider(mouseX, mouseY)) {
			return;
		}

		if (this.orientation == WScrollBar.Orientation.HORIZONTAL) {
			this.goTo((int) ((mouseX - this.barWidth / 2) / this.stepSize));
		} else {
			this.goTo((int) ((mouseY - this.barHeight / 2) / this.stepSize));
		}
	}

	@Override
	public void onMouseDrag(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if (clickedMouseButton != 0) {
			return;
		}

		if (this.orientation == WScrollBar.Orientation.HORIZONTAL) {
			this.goTo((int) ((mouseX - this.barWidth / 2) / this.stepSize));
		} else {
			this.goTo((int) ((mouseY - this.barHeight / 2) / this.stepSize));
		}
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {

		int trim = this.isMouseOverSlider(mouseX, mouseY) ? mixColors(this.palette.trim, this.palette.highlight)
				: this.palette.trim;
		int secondary = this.mouseOver ? mixColors(this.palette.secondary, this.palette.highlight)
				: this.palette.secondary;
		int edging = this.mouseOver ? mixColors(this.palette.edging, this.palette.highlight) : this.palette.edging;

		GlStateManager.pushMatrix();
		{
			this.drawRect(0, 0, this.width, this.height, this.palette.edging);
			GlStateManager.translate(this.barOffsetX, this.barOffsetY, 0);
			this.drawRect(0, 0, this.barWidth, this.barHeight, trim);
			this.drawRect(1, 1, this.barWidth - 1, this.barHeight - 1, secondary);

			if (this.orientation == Orientation.VERTICAL) {
				this.drawHorizontalLine(2, this.barWidth - 3, this.barHeight / 2, edging);
				this.drawHorizontalLine(2, this.barWidth - 3, this.barHeight / 2 + 2, edging);
				this.drawHorizontalLine(2, this.barWidth - 3, this.barHeight / 2 - 2, edging);
			} else {
				this.drawVerticalLine(this.barWidth / 2, 1, this.barHeight - 2, edging);
				this.drawVerticalLine(this.barWidth / 2 + 2, 1, this.barHeight - 2, edging);
				this.drawVerticalLine(this.barWidth / 2 - 2, 1, this.barHeight - 2, edging);
			}
		}
		GlStateManager.popMatrix();
	}
}
