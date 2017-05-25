package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;

import com.daedalus.ambientevents.actions.PlaySoundAction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class WVanillaButton extends WAbstractButton {

	protected Consumer<Integer> callback;
	protected GuiButton button;
	protected int buttonID;
	protected String buttonText;
	
	public WVanillaButton(WWidget parentIn, int ID, String text) {
		this(parentIn, ID, 0, 0, 0, 0, text);
	}

	public WVanillaButton(WWidget parentIn, int ID, int x, int y, int widthIn, int heightIn, String text) {
		super(parentIn);
		this.buttonID = ID;
		this.buttonText = text;
		this.initGui();
		this.setSize(widthIn, heightIn);
		this.move(x, y);
	}
	
	@Override
	public void initGui() {
		this.button = new GuiButton(buttonID, 0, 0, 0, 0, buttonText);
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		this.button.width = widthIn;
		this.button.height = heightIn;
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			super.draw(mouseX, mouseY, partialTicks);
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(this.offsetX, this.offsetY, 0);
				this.button.drawButton(mc, mouseX, mouseY);
			}
			GlStateManager.popMatrix();
		}
	}
}
