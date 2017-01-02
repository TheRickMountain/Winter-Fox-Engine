package com.wfe.gui;

import com.wfe.input.Mouse;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public PlayerStatus status;
	
	private GUIManager() {
		ItemDatabase.create();
		
		inventory = new Inventory();
		status = new PlayerStatus();
	}
	
	public void update(float dt) {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
		status.update(dt);
	}
	
	public void render() {
		inventory.render();
		status.render();
	}
	
	public static GUIManager getGUI() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
