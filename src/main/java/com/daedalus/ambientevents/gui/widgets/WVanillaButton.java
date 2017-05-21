package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class WVanillaButton extends WWidget {

	protected Consumer<Integer> callback;
	protected GuiButton button;
	protected int buttonID;
	
	public WVanillaButton(WWidget parentIn, int ID, int x, int y, int widthIn, int heightIn, String text) {
		super(parentIn);
		button = new GuiButton(ID, 0, 0, 0, 0, text);
		setSize(widthIn, heightIn);
		move(x, y);
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		button.width = widthIn;
		button.height = heightIn;
	}
	
	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY) {
		if (visible) {
			super.draw(mc, mouseX, mouseY);
			GlStateManager.pushMatrix(); {
				GlStateManager.translate(offsetX, offsetY, 0);
				button.drawButton(mc, mouseX, mouseY);
			} GlStateManager.popMatrix();
		}
	}
	
	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		callback.accept(mouseButton);
		super.onMouseClick(mouseX, mouseY, mouseButton);
	}
	
	public void setOnClickAction(Consumer<Integer> onClick) {
		callback = onClick;
	}
}
