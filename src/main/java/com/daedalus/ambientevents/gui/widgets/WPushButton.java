package com.daedalus.ambientevents.gui.widgets;

public class WPushButton extends WAbstractButton {

	protected String label;

	public WPushButton(WWidget parentIn) {
		super(parentIn);
	}

	public WPushButton(WWidget parentIn, String textIn) {
		super(parentIn);
		this.setLabel(textIn);
	}

	public void setLabel(String labelIn) {
		this.label = labelIn;
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {

		int trim = this.isMouseOver(mouseX, mouseY) ? mixColors(this.palette.trim, this.palette.highlight)
				: this.palette.trim;
		int secondary = this.mouseOver ? mixColors(this.palette.secondary, this.palette.highlight)
				: this.palette.secondary;
		int text = this.mouseOver ? mixColors(this.palette.text, this.palette.highlight) : this.palette.text;

		this.drawRect(0, 0, this.width, this.height, trim);
		this.drawRect(1, 1, this.width - 1, this.height - 1, secondary);

		this.fontRendererObj.drawString(this.label,
				this.width / 2 - this.fontRendererObj.getStringWidth(this.label) / 2,
				this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2, text);
	}
}
