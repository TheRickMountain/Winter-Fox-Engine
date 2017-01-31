package com.wfe.gui;

import com.wfe.input.Key;
import com.wfe.input.Keyboard;

public class GUIManager  {
	
	public static Inventory inventory;
	
	private static boolean open = false;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
	}
	
	public static void update() {
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
		}
		
		inventory.update();
	}
	
	public static void render() {
		if(open) {
			inventory.render();
		}
		inventory.quickInventory.render();
	}

	public static void renderText() {
		if(open) {
			inventory.renderText();
		}
		inventory.quickInventory.renderText();
	}

	public static void renderPopUp() {
		
	}

	public static void renderPopUpText() {
		
	}
	
	

}
