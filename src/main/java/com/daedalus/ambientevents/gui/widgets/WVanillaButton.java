package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class WVanillaButton extends WWidget {

	protected Consumer<Integer> callback;
	protected GuiButton button;
	protected int buttonID;

	public WVanillaButton(WWidget parentIn, int ID, int x, int y, int widthIn, int heightIn, String text) {
		super(parentIn);
		this.button = new GuiButton(ID, 0, 0, 0, 0, text);
		this.setSize(widthIn, heightIn);
		this.move(x, y);
	}

	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		this.button.width = widthIn;
		this.button.height = heightIn;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			super.draw(mc, mouseX, mouseY);
			GlStateManager.pushMatrix();
			{
				GlStateManager.translate(this.offsetX, this.offsetY, 0);
				this.button.drawButton(mc, mouseX, mouseY);
			}
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		this.callback.accept(mouseButton);
		super.onMouseClick(mouseX, mouseY, mouseButton);
	}

	public void setOnClickAction(Consumer<Integer> onClick) {
		this.callback = onClick;
	}
}
