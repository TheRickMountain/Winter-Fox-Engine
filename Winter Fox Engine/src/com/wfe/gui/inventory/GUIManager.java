package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.gui.GUIText;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.PlayerStatus;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public Equipment equipment;
	public PlayerStatus status;
	
	public static Item draggedItem;
	private GUIText draggedItemAmountText;
	private int draggedItemAmount;
	
	private GUIManager() {	
		ItemDatabase.create();
		
		inventory = new Inventory();
		equipment = new Equipment();
		status = new PlayerStatus();
		
		draggedItemAmountText = new GUIText("", 1.3f, FontRenderer.font, 0, 0, 1f, false);
		draggedItemAmountText.setColor(1.0f, 1.0f, 1.0f);
		
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.BANANA), 120);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.COOKIE), 2);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.SHROOM), 198);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.AXE), 4);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.WALL), 204);
		/*inventory.addItem(ItemDatabase.items.get(ItemDatabase.CROSS_WALL), 10);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.DOOR_WALL), 10);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.WINDOW_WALL), 10);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.APPLE), 4);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.BUSH), 15);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.HOE), 1);
		inventory.addItem(ItemDatabase.items.get(ItemDatabase.FIBER), 5);*/
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
	
	public void renderText() {
		inventory.renderText();
		
		if(draggedItem != null) {
			if(draggedItemAmount > 1){
				draggedItemAmountText.setPosition(
						1.0f / Display.getWidth() * (Mouse.getX() - 25), 
						1.0f / Display.getHeight() * (Mouse.getY() - 25));
				FontRenderer.render(draggedItemAmountText);
			}
		}
	}
	
	public static GUIManager getGUI() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
	public void setDraggedItemAmount(int amount) {
		this.draggedItemAmount = amount;
		draggedItemAmountText.setText(String.valueOf(draggedItemAmount));
	}
	
	public int getDraggedItemAmount() {
		return draggedItemAmount;
	}
	
}
