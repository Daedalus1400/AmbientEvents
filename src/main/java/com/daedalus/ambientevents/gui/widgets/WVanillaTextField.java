package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import net.minecraft.client.gui.GuiTextField;

public class WVanillaTextField extends WWidget {
	
	protected GuiTextField textField;
	protected Consumer<String> callback;
	protected Pattern pattern = Pattern.compile(".*");
	protected Matcher match;
	
	protected String lastGoodContent;
	
	public static int nextID = 0;
	
	public WVanillaTextField(WWidget parentIn) {
		this(parentIn, 0, 0, 0, 0);
	}

	public WVanillaTextField(WWidget parentIn, int x, int y, int widthIn, int heightIn) {
		super(parentIn);
		this.textField = new GuiTextField(nextID++, this.fontRendererObj, 0, 0, 0, 0);
		this.setSize(widthIn, heightIn);
		this.move(x, y);
	}
	
	@Override
	public void setSize(int widthIn, int heightIn) {
		super.setSize(widthIn, heightIn);
		this.textField.height = heightIn;
		this.textField.width = widthIn;
	}
	
	@Override
	public void setFocused() {
		super.setFocused();
		this.textField.setFocused(true);
	}
	
	@Override
	public void setUnfocused() {
		super.setUnfocused();
		this.textField.setFocused(false);
	}
	
	@Override 
	public void onKeyTyped(char typedChar, int keyCode) {
		this.textField.textboxKeyTyped(typedChar, keyCode);
		this.checkText();
		if (this.callback != null) {
			this.callback.accept(this.getText());
		}
	}
	
	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		this.textField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.textField.drawTextBox();
	}
	
	protected void checkText() {
		this.match = pattern.matcher(this.textField.getText());
		if (match.matches()) {
			this.lastGoodContent = this.textField.getText();
			return;
		} else {
			this.textField.setText(lastGoodContent);
		}
	}
	
	public void setValidRegex(String regexIn) {
		this.pattern = Pattern.compile(regexIn);
	}
	
	public void setOnTextChangedAction(Consumer<String> onTextChangedAction) {
		this.callback = onTextChangedAction;
	}
	
	public String getText() {
		return this.textField.getText();
	}
}
