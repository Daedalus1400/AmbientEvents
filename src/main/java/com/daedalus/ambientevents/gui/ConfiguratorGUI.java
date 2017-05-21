package com.daedalus.ambientevents.gui;

import java.io.IOException;

import org.json.JSONObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.daedalus.ambientevents.gui.widgets.*;
import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class ConfiguratorGUI extends WWidget {

	protected JSONObject eventsJSON;
	
	protected WVanillaButton exit;
	//private GuiButton exit;
	
	private final int EXIT = 0;
	
	public ConfiguratorGUI() {
		super(null);
		eventsJSON = ClientEventHandler.eventsJSON;
	}
	
	@Override
	 public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		draw(mc, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		exit = new WVanillaButton(this, EXIT, width/2 - 100, height - 20, 200, 20, "Exit");
		exit.setOnClickAction(this::exit);
		exit.show();
		show();
		//buttonList.add(exit);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		onMouseClick(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		onMouseRelease(mouseX, mouseY, state);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}
	
	public void exit(int i) {
		mc.displayGuiScreen(null);
	}
	
}
