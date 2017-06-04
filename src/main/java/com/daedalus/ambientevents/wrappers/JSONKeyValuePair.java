package com.daedalus.ambientevents.wrappers;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONKeyValuePair {

	protected String key;
	protected JSONObject objectValue;
	protected int intValue;
	protected double doubleValue;
	protected String stringValue;
	protected JSONArray arrayValue;
	
	protected boolean containsJSONObject;
	protected boolean containsInteger;
	protected boolean containsDouble;
	protected boolean containsString;
	protected boolean containsArray;
	
	
	public JSONKeyValuePair(String keyIn, JSONObject valueIn) {
		this.key = keyIn;
		this.clearValues();
		this.objectValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public JSONKeyValuePair(String keyIn, JSONArray valueIn) {
		this.key = keyIn;
		this.clearValues();
		this.arrayValue = valueIn;
		this.containsArray = true;
	}
	
	public JSONKeyValuePair(String keyIn, int valueIn) {
		this.key = keyIn;
		this.clearValues();
		this.intValue = valueIn;
		this.containsInteger = true;
	}
	
	public JSONKeyValuePair(String keyIn, double valueIn) {
		this.key = keyIn;
		this.clearValues();
		this.doubleValue = valueIn;
		this.containsDouble = true;
	}
	
	public JSONKeyValuePair(String keyIn, String valueIn) {
		this.key = keyIn;
		this.clearValues();
		this.stringValue = valueIn;
		this.containsDouble = true;
	}
	
	public void put(JSONObject valueIn) {
		this.clearValues();
		this.objectValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public void put(JSONArray valueIn) {
		this.clearValues();
		this.arrayValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public void put(String valueIn) {
		this.clearValues();
		this.stringValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public void put(int valueIn) {
		this.clearValues();
		this.intValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public void put(double valueIn) {
		this.clearValues();
		this.doubleValue = valueIn;
		this.containsJSONObject = true;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String newKey) {
		this.key = newKey;
	}
	
	public JSONObject getJSONObject() {
		return this.objectValue;
	}
	
	public JSONArray getJSONArray() {
		return this.arrayValue;
	}
	
	public int getInt() {
		return this.intValue;
	}
	
	public double getDouble() {
		return this.doubleValue;
	}
	
	public String getString() {
		return this.stringValue;
	}
	
	public boolean isJSONObject() {
		return this.containsJSONObject;
	}
	
	public boolean isJSONArray() {
		return this.containsArray;
	}
	
	public boolean isInt() {
		return this.containsInteger;
	}
	
	public boolean isDouble() {
		return this.containsDouble;
	}
	
	public boolean isString() {
		return this.containsString;
	}
	
	public boolean isNull() {
		return this.containsArray && this.containsDouble && this.containsInteger && this.containsJSONObject && this.containsString;
	}
	
	protected void clearValues() {
		this.objectValue = null;
		this.intValue = 0;
		this.doubleValue = 0;
		this.stringValue = null;
		this.arrayValue = null;
		
		this.containsArray = false;
		this.containsDouble = false;
		this.containsInteger = false;
		this.containsJSONObject = false;
		this.containsString = false;
	}
}
