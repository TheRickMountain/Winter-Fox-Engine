package com.wfe.gui;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIManager  {
	
	private static GUITexture craftingButton;
	private static GUITexture inventoryButton;
	private static GUITexture equipmentButton;
	
	public static GUIFrame mainFrame;
	public static GUIFrame inspectFrame;
	
	private static Color background = new Color(0.1f, 0.1f, 0.1f, 0.9f);
	
	public static GUITexture mainF;
	public static GUITexture inspectF;
	
	public static Inventory inventory;
	public static Crafting crafting;
	
	private static boolean open = false;
	private static float offset = 5;
	
	private enum State {
		CRAFTING,
		INVENTORY,
		EQUIPMENT,
	};
	
	private static State state = State.INVENTORY;
	
	public static void init() {
		ItemDatabase.create();
		
		mainFrame = new GUIFrame(new Rect(0, 0, 275, 345));
		inspectFrame = new GUIFrame(new Rect(0, 0, 275, 345));
		
		mainF = new GUITexture(background, mainFrame.rect, false);
		inspectF = new GUITexture(background, inspectFrame.rect, false);
		
		craftingButton = new GUITexture(ResourceManager.getTexture("crafting_ui"), new Rect(0, 0, 50, 50), true);
		inventoryButton = new GUITexture(ResourceManager.getTexture("inventory_ui"), new Rect(0, 0, 50, 50), true);
		equipmentButton = new GUITexture(ResourceManager.getTexture("equipment_ui"), new Rect(0, 0, 50, 50), true);
		
		inventoryButton.setTexture(ResourceManager.getTexture("selected_inventory_ui"));
		
		inventory = new Inventory();
		crafting = new Crafting();
		
		updatePositions();
	}
	
	public static void update() {	
		Mouse.setActiveInGUI(false);
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
			AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
			
			if(open) {
				crafting.updateIngredients();
			}
		}
		
		if(open) {
			if(Mouse.isButtonDown(0)) {
				
				if(craftingButton.rect.isMouseOvered()) {
					state = State.CRAFTING;
					crafting.updateIngredients();
					
					inventoryButton.setTexture(ResourceManager.getTexture("inventory_ui"));
					equipmentButton.setTexture(ResourceManager.getTexture("equipment_ui"));
					
					craftingButton.setTexture(ResourceManager.getTexture("selected_crafting_ui"));
					
				} else if(inventoryButton.rect.isMouseOvered()) {
					state = State.INVENTORY;
					
					craftingButton.setTexture(ResourceManager.getTexture("crafting_ui"));
					equipmentButton.setTexture(ResourceManager.getTexture("equipment_ui"));
					
					inventoryButton.setTexture(ResourceManager.getTexture("selected_inventory_ui"));
				} else if(equipmentButton.rect.isMouseOvered()) {
					state = State.EQUIPMENT;
					
					craftingButton.setTexture(ResourceManager.getTexture("crafting_ui"));
					inventoryButton.setTexture(ResourceManager.getTexture("inventory_ui"));
					
					equipmentButton.setTexture(ResourceManager.getTexture("selected_equipment_ui"));
				}
			}
			
			switch(state) {
			case CRAFTING:
				crafting.update();
				break;
			case INVENTORY:
				inventory.update();
				break;
			case EQUIPMENT:
				break;
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public static void render() {
		if(open) {
		
			GUIRenderer.render(craftingButton);
			GUIRenderer.render(inventoryButton);
			GUIRenderer.render(equipmentButton);
		
			GUIRenderer.render(mainF);
			GUIRenderer.render(inspectF);
			
			switch(state) {
			case CRAFTING:
				crafting.render();
				break;
			case INVENTORY:
				inventory.render();
				break;
			case EQUIPMENT:
				break;
			}
		}
		
		inventory.renderHotbar();
	}

	public static void renderText() {
		if(open) {
			switch(state) {
			case CRAFTING:
				crafting.renderText();
				break;
			case INVENTORY:
				inventory.renderText();
				break;
			case EQUIPMENT:
				break;
			}
		}
		
		inventory.renderHotbarText();
	}

	public static void renderPopUp() {
		if(open) {
			inventory.renderPopUp();
		}
	}

	public static void renderPopUpText() {
		if(open) {
			inventory.renderPopUpText();
		}
	}
	
	private static void updatePositions() {	
		mainFrame.setPosition(Display.getWidth() / 2 - 275 - (offset / 2), Display.getHeight() / 2 - 172.5f);
		inspectFrame.setPosition(Display.getWidth() / 2 + (offset / 2), Display.getHeight() / 2 - 172.5f);
		
		inventoryButton.rect.setPosition(Display.getWidth() / 2, mainFrame.rect.y - (25 + offset));
		craftingButton.rect.setPosition(Display.getWidth() / 2 - 50 - offset, mainFrame.rect.y - (25 + offset));
		equipmentButton.rect.setPosition(Display.getWidth() / 2 + 50 + offset, mainFrame.rect.y - (25 + offset));
		
		inventory.updatePositions();
		crafting.updatePositions();
	}

}
