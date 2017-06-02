package com.daedalus.ambientevents.gui.widgets;

public class WListElement<T> {

	public String text;
	public T item;
	
	public WListElement (String textIn, T itemIn) {
		this.text = textIn;
		this.item = itemIn;
	}
}
