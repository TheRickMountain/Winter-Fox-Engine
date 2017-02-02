package com.wfe.gui;

import com.wfe.input.Mouse;

public class GUIManager  {
	
	public static Inventory inventory;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
	}
	
	public static void update() {	
		Mouse.setActiveInGUI(false);
		
		inventory.update();
	}
	
	public static void render() {
		inventory.render();
	}

	public static void renderText() {
		inventory.renderText();
	}

	public static void renderPopUp() {
		
	}

	public static void renderPopUpText() {
		
	}

}
