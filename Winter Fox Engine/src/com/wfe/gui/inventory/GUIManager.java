package com.wfe.gui.inventory;

import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.PlayerStatus;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public Equipment equipment;
	public PlayerStatus status;
	
	public static Item draggedItem;
	
	private GUIManager() {
		ItemDatabase.create();
		
		inventory = new Inventory();
		equipment = new Equipment();
		status = new PlayerStatus();
	}
	
	public void update(float dt) {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
		equipment.update();
		status.update(dt);
	}
	
	public void render() {
		inventory.render();
		equipment.render();
		status.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX() - 25, Mouse.getY() - 25, 0, 50, 50, false);
		}
	}
	
	public static GUIManager getGUI() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
