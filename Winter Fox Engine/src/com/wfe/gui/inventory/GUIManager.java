package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.gui.GUIText;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;

public class GUIManager {

	private static GUIManager guiManager;
	
	public Inventory inventory;
	public Equipment equipment;
	public Crafting crafting;
	public PlayerStatus status;
	
	public static Item draggedItem;
	private GUIText draggedItemAmountText;
	private int draggedItemAmount;
	
	private GUIManager() {	
		ItemDatabase.create();
		
		inventory = new Inventory();
		equipment = new Equipment();
		crafting = new Crafting();
		status = new PlayerStatus();
		
		draggedItemAmountText = new GUIText("", 1.3f, FontRenderer.font, 0, 0, 1f, false);
		draggedItemAmountText.setColor(1.0f, 1.0f, 1.0f);
		
		inventory.addItem(ItemDatabase.items.get(Item.BANANA), 15);
		//inventory.addItem(ItemDatabase.items.get(Item.COOKIE), 25);
		//inventory.addItem(ItemDatabase.items.get(Item.SHROOM), 68);
		inventory.addItem(ItemDatabase.items.get(Item.AXE), 3);
		inventory.addItem(ItemDatabase.items.get(Item.ROPE), 2);
		//inventory.addItem(ItemDatabase.items.get(Item.WALL), 40);
		//inventory.addItem(ItemDatabase.items.get(Item.CROSS_WALL), 10);
		//inventory.addItem(ItemDatabase.items.get(Item.DOOR_WALL), 10);
		//inventory.addItem(ItemDatabase.items.get(Item.WINDOW_WALL), 10);
		//inventory.addItem(ItemDatabase.items.get(Item.APPLE), 4);
		//inventory.addItem(ItemDatabase.items.get(Item.BUSH), 15);
		//inventory.addItem(ItemDatabase.items.get(Item.HOE), 1);
		/*inventory.addItem(ItemDatabase.items.get(Item.FIBER), 3);
		inventory.addItem(ItemDatabase.items.get(Item.STICK), 1);
		inventory.addItem(ItemDatabase.items.get(Item.FLINT), 1);*/
	}
	
	public void update(float dt) {
		Mouse.setActiveInGUI(false);
		
		inventory.update();
		equipment.update();
		crafting.update();
		status.update(dt);
	}
	
	public void render() {
		inventory.render();
		equipment.render();
		crafting.render();
		status.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX() - 25, Mouse.getY() - 25, 0, 55, 55, false);
		}
	}
	
	public void renderText() {
		inventory.renderText();
		crafting.renderText();
		
		if(draggedItem != null) {
			if(draggedItemAmount > 1){
				draggedItemAmountText.setPosition(
						1.0f / Display.getWidth() * (Mouse.getX() - 25), 
						1.0f / Display.getHeight() * (Mouse.getY() - 35));
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
