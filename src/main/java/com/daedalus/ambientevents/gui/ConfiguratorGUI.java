package com.daedalus.ambientevents.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.daedalus.ambientevents.gui.widgets.WListElement;
import com.daedalus.ambientevents.gui.widgets.WListView;
import com.daedalus.ambientevents.gui.widgets.WPushButton;
import com.daedalus.ambientevents.gui.widgets.WVanillaButton;
import com.daedalus.ambientevents.gui.widgets.WVanillaTextField;
import com.daedalus.ambientevents.gui.widgets.WWidget;
import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class ConfiguratorGUI extends WWidget {

	protected JSONObject eventsJSON;

	protected WPushButton exit;
	
	protected boolean firstStart = true;

	public ConfiguratorGUI() {
		super(null);
		this.eventsJSON = ClientEventHandler.eventsJSON;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.draw(mouseX, mouseY, partialTicks);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();

		if (this.firstStart) {
			this.palette = new Palette();
			this.exit = new WPushButton(this, "Exit");
		}
		
		// Start Widget Testing Code
		

		
		// End Widget Testing Code
		
		this.exit.setSize(50, 20);
		this.exit.move(this.width / 2 - 25, this.height - 20);
		this.exit.setOnClickAction(this::exit);

		this.show();
		
		this.firstStart = false;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.onMouseClick(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.onMouseRelease(mouseX, mouseY, state);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		this.onMouseDrag(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		this.onKeyTyped(typedChar, keyCode);
	}

	public void exit(int i) {
		WVanillaTextField.nextID = 0;
		this.mc.displayGuiScreen(null);
	}
}
