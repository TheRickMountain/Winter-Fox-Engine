package com.wfe.gui;

import com.wfe.input.Mouse;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	
	private GUIManager() {
		ItemDatabase.create();
		
		inventory = new Inventory();
	}
	
	public void update() {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
	}
	
	public void render() {
		inventory.render();
	}
	
	public static GUIManager create() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
