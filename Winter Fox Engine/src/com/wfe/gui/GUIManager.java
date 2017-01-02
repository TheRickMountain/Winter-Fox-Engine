package com.wfe.gui;

import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public PlayerStats playerStats;
	
	private GUIManager() {
		ItemDatabase.create();
		
		inventory = new Inventory();
		playerStats = new PlayerStats();
	}
	
	public void update() {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
		playerStats.update();
		
		if(Keyboard.isKeyDown(Key.KEY_H)) {
			playerStats.decrease(50);
		}
	}
	
	public void render() {
		inventory.render();
		playerStats.render();
	}
	
	public static GUIManager create() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
