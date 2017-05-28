package com.daedalus.ambientevents.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.daedalus.ambientevents.gui.widgets.WListElement;
import com.daedalus.ambientevents.gui.widgets.WListView;
import com.daedalus.ambientevents.gui.widgets.WVanillaButton;
import com.daedalus.ambientevents.gui.widgets.WWidget;
import com.daedalus.ambientevents.handlers.ClientEventHandler;

public class ConfiguratorGUI extends WWidget {

	protected JSONObject eventsJSON;

	protected WVanillaButton exit;
	protected WListView<String> listView;

	private final int EXIT = 0;

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
		this.subWidgets.clear();

		this.palette = new Palette();
		this.palette.primary = 0xffa0a0a0;
		this.palette.secondary = 0xffc0c0c0;
		this.palette.background = 0xff000000;
		this.palette.text = 0xff000000;
		this.palette.edging = 0xff404040;
		this.palette.trim = 0xff808080;
		this.palette.highlight = 0xffb0b0ff;

		this.listView = new WListView<String>(this);
		this.listView.setSize(this.width / 3, this.height / 2);
		this.listView.move(this.width/3, this.height/4);
		
		ArrayList<WListElement<String>> dummyText = new ArrayList<WListElement<String>> ();
		for (int i = 0; i < 30; i++) {
			WListElement<String> dummy = new WListElement<String> ();
			dummy.text = String.format("%d", i);
			dummyText.add(dummy);
		}
		
		this.listView.populate(dummyText);
		
		this.exit = new WVanillaButton(this, this.EXIT, "Exit");
		this.exit.setSize(50, 20);
		this.exit.move(this.width / 2 - 25, this.height - 20);
		this.exit.setOnClickAction(this::exit);

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
		for (int i = this.subWidgets.size() - 1; i > -1; i--) {
			
			if (this.subWidgets.get(i).isMouseOver(mouseX - this.subWidgets.get(i).offsetX,
					mouseY - this.subWidgets.get(i).offsetY)) {
				
				this.subWidgets.get(i).onMouseClick(mouseX - this.subWidgets.get(i).offsetX,
						mouseY - this.subWidgets.get(i).offsetY, mouseButton);
				
				this.focus = this.subWidgets.get(i);
				this.focus.setFocused();
				this.dragTarget = this.focus;
				break;
			}
		}
	}

	public void exit(int i) {
		this.mc.displayGuiScreen(null);
	}

}
