package com.daedalus.ambientevents.gui;

import java.io.IOException;

import org.json.JSONObject;

import com.daedalus.ambientevents.gui.widgets.WVanillaButton;
import com.daedalus.ambientevents.gui.widgets.WWidget;
import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class ConfiguratorGUI extends WWidget {

	protected JSONObject eventsJSON;

	protected WVanillaButton exit;
	// private GuiButton exit;

	private final int EXIT = 0;

	public ConfiguratorGUI() {
		super(null);
		this.eventsJSON = ClientEventHandler.eventsJSON;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.draw(this.mc, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.exit = new WVanillaButton(this, this.EXIT, this.width / 2 - 100, this.height - 20, 200, 20, "Exit");
		this.exit.setOnClickAction(this::exit);
		this.exit.show();
		this.show();
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

	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		for (WWidget subwidget : this.subWidgets) {
			if (subwidget.isMouseOver(mouseX - subwidget.offsetX, mouseY - subwidget.offsetY)) {
				subwidget.onMouseClick(mouseX - subwidget.offsetX, mouseY - subwidget.offsetY, mouseButton);
				this.focus = subwidget;
				break;
			}
		}
	}

	public void exit(int i) {
		this.mc.displayGuiScreen(null);
	}

}
