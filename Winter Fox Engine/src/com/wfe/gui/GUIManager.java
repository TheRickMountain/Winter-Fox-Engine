package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public StatusBar healthBar;
	public StatusBar hungerBar;
	
	private GUIManager() {
		ItemDatabase.create();
		
		inventory = new Inventory();
		healthBar = new StatusBar(
				ResourceManager.getTexture("health_icon_ui"), new Vector3f(1.0f, 0.2f, 0.2f), 15, 10);
		hungerBar = new StatusBar(
				ResourceManager.getTexture("hunger_icon_ui"), new Vector3f(1.0f, 0.5f, 0.1f), 15, 35);
	}
	
	public void update() {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
		healthBar.update();
		hungerBar.update();
		
		if(Keyboard.isKeyDown(Key.KEY_H)) {
			healthBar.decrease(50);
			hungerBar.decrease(25);
		}
	}
	
	public void render() {
		inventory.render();
		healthBar.render();
		hungerBar.render();
	}
	
	public static GUIManager create() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
