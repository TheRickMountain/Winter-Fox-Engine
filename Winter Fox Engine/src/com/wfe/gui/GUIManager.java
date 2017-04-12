package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIManager  {
	
	public static Inventory inventory;
	public static Crafting crafting;
	public static PlayerStats stats;
	
	public static ProgressBar progressBar;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		crafting = new Crafting();
		stats = new PlayerStats();
		
		progressBar = new ProgressBar(new Rect(0, 0, 385, 10), new Color(255, 140, 0, 255).convert());
		
		updatePositions();
	}
	
	public static void update() {			
		inventory.update();
		crafting.update();
		stats.update();
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public static void render() {
		inventory.render();
		crafting.render();
		stats.render();
		
		progressBar.render();
	}
	
	public static void renderText() {
		inventory.renderText();
		//crafting.renderText();
		stats.renderText();
	}
	
	public static void renderPopUp() {
		inventory.renderPopUp();
	}
	
	public static void renderPopUpText() {
		inventory.renderPopUpText();
	}
	
	private static void updatePositions() {
		progressBar.rect.setPosition(Display.getWidth() / 2 - progressBar.rect.width / 2, 
				inventory.hotbarFrame.rect.y - progressBar.rect.height - 5);
	}
	
}
