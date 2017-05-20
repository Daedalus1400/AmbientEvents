package com.daedalus.ambientevents.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class ConfiguratorGUI extends GuiScreen {

	private GuiButton exit;
	
	private final int EXIT = 0;
	
	@Override
	 public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		exit.drawButton(mc, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		exit = new GuiButton(EXIT, width/2 - 100, height - 20, 200, 20, "Exit");
		buttonList.add(exit);
	}
	
	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch(button.id) {
		case EXIT:
			mc.displayGuiScreen(null);
			break;
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}
}
