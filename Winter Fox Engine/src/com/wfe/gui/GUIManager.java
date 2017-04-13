package com.wfe.gui;

import com.wfe.audio.AudioMaster;
import com.wfe.components.ChestComponent;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIManager  {
	
	public enum GUIState {
		CRAFTING,
		CHEST,
		GAME
	}
	
	public static Inventory inventory;
	public static Crafting crafting;
	public static Chest chest;
	public static PlayerStats stats;
	
	protected static Item draggedItem;
	protected static GUIText draggedItemText;
	protected static int draggedItemCount;
	
	public static boolean showProgressBar;
	public static ProgressBar progressBar;
	
	public static GUIState state = GUIState.GAME;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		crafting = new Crafting();
		chest = new Chest();
		stats = new PlayerStats();
		
		draggedItem = ItemDatabase.getItem(Item.NULL);
		draggedItemText = new GUIText("", FontRenderer.ARIAL);
		draggedItemText.setScale(0.8f);
		draggedItemCount = 0;
		
		progressBar = new ProgressBar(new Rect(0, 0, 385, 10), new Color(255, 140, 0, 255).convert());
		
		updatePositions();
	}
	
	public static void update() {	
		if(Keyboard.isKeyDown(Key.KEY_F)) {
			inventory.addItem(ItemDatabase.getItem(Item.BASKET), 1);
			inventory.addItem(ItemDatabase.getItem(Item.PICKAXE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.AXE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.CLUB), 1);
			inventory.addItem(ItemDatabase.getItem(Item.HOE), 1);
		}
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			switch(state) {
			case GAME:
				state = GUIState.CRAFTING;
				crafting.updateRecipes();
				Mouse.setActiveInGUI(true);
				Display.setCursor(Display.defaultCursor);
				break;
			case CRAFTING:
			case CHEST:
				state = GUIState.GAME;
				Mouse.setActiveInGUI(false);
				break;
			}
			
			AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
		}
		
		inventory.updateHotbar();
		
		switch (state) {
		case CRAFTING:
			crafting.update();
			inventory.update();
			break;
		case CHEST:
			chest.update();
			inventory.update();
			break;
		}
		
		stats.update();
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public static void render() {
		inventory.renderHotbar();
		
		switch (state) {
		case CRAFTING:
			inventory.render();
			crafting.render();
			break;
		case CHEST:
			inventory.render();
			chest.render();
			break;
		}
		
		stats.render();
		
		if(showProgressBar)
			progressBar.render();
	}
	
	public static void renderText() {
		inventory.renderHotbarText();
		
		switch (state) {
		case CRAFTING:
			inventory.renderText();
			break;
		case CHEST:
			inventory.renderText();
			chest.renderText();
			break;
		}
		
		stats.renderText();
	}
	
	public static void renderPopUp() {
		if(draggedItem.id != Item.NULL) {
			GUIRenderer.render(draggedItem.icon, Color.WHITE, 
					Mouse.getX() - Slot.SIZE / 2, Mouse.getY() - Slot.SIZE / 2, 
					0, Slot.SIZE, Slot.SIZE, false);
		}
	}
	
	public static void renderPopUpText() {
		if(draggedItem.id != Item.NULL) {
			draggedItemText.setPosition(
					Mouse.getX() - Slot.SIZE / 2 + 4f, 
					Mouse.getY() - Slot.SIZE / 2 + 3f);
			FontRenderer.render(draggedItemText);
		}
	}
	
	private static void updatePositions() {
		inventory.updatePositions();
		crafting.updatePositions();
		chest.updatePositions();
		
		progressBar.rect.setPosition(Display.getWidth() / 2 - progressBar.rect.width / 2, 
				inventory.hotbarFrame.rect.y - progressBar.rect.height - 5);
	}
	
	protected static void setDraggedItem(Item item, int count) {
		draggedItem = item;
		draggedItemCount = count;
		if(count > 1)
			draggedItemText.setText("" + count);
		else
			draggedItemText.setText("");
	}
	
	public static void openChest(ChestComponent chestComponent) {
		chest.open(chestComponent);
		state = GUIState.CHEST;
	}
	
}
