package com.daedalus.ambientevents.gui.widgets;

import com.daedalus.ambientevents.actions.PlaySoundAction;

import net.minecraft.util.SoundCategory;

public class WTabLabel extends WAbstractButton {

	protected String label;
	protected int padding = 2;
	protected int ID;
	
	public int border = 2;
	protected boolean selected = false;
	
	public WTabLabel(WWidget parentIn, int IDIn) {
		this(parentIn, IDIn, "");
	}
	
	public WTabLabel(WWidget parentIn, int IDIn, String labelIn) {
		super(parentIn);
		this.ID = IDIn;
		this.setLabel(labelIn);
	}
	
	public void setLabel(String labelIn) {
		this.label = labelIn;
		this.width = this.fontRendererObj.getStringWidth(label) + this.padding * 2 + this.border * 2;
		this.height = this.fontRendererObj.FONT_HEIGHT + this.padding + this.border * 2;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.drawRect(0, 0, this.width, this.height, this.palette.edging);
			this.drawRect(this.border, this.border, this.width - this.border, this.height - (this.selected? 0 : this.border), this.selected? this.palette.primary : this.palette.secondary);
			this.fontRendererObj.drawString(this.label, this.padding + this.border, this.padding + this.border, this.palette.text);
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (this.selected) {
			return;
		}
		this.mc.world.playSound(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ,
				PlaySoundAction.registry.get("ui.button.click"), SoundCategory.MASTER, 0.3f, 1.0f, true);
		this.select();
	}
	
	public void select() {
		this.selected = true;
		if (this.callback != null) {
			this.callback.accept(this.ID);
		}
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
}
