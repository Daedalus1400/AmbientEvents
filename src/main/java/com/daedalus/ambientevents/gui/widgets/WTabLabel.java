package com.daedalus.ambientevents.gui.widgets;

import com.daedalus.ambientevents.actions.PlaySoundAction;

import net.minecraft.util.SoundCategory;

public class WTabLabel extends WAbstractButton {

	protected String label;
	protected int padding = 2;
	protected int ID;
	
	public int border = 2;
	
	public WTabLabel(WWidget parentIn, int IDIn) {
		this(parentIn, IDIn, "");
	}
	
	public WTabLabel(WWidget parentIn, int IDIn, String labelIn) {
		super(parentIn);
		this.ID = IDIn;
		this.label = labelIn;
	}
	
	public void setLabel(String labelIn) {
		this.label = labelIn;
		this.width = this.fontRendererObj.getStringWidth(label) + this.padding * 2 + 4;
		this.height = this.fontRendererObj.FONT_HEIGHT + this.padding + 4;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.drawRect(0, 0, this.width, this.height, this.palette.edging);
			this.drawRect(this.border, this.border, this.width - this.border, this.height - (this.hasFocus? 0 : this.border), this.palette.primary);
			this.fontRendererObj.drawString(this.label, this.padding + this.border, this.padding + this.border, this.palette.text);
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		this.mc.world.playSound(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ,
				PlaySoundAction.registry.get("ui.button.click"), SoundCategory.MASTER, 0.3f, 1.0f, true);
		if (this.callback != null) {
			this.callback.accept(this.ID);
		}
	}
}
