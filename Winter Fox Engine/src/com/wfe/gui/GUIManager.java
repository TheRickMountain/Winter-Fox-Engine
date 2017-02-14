package com.wfe.gui;

public class GUIManager  {
	
	public static Inventory inventory;
	public static PlayerStats stats;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		stats = new PlayerStats();
	}
	
	public static void update() {	
		inventory.update();
		stats.update();
	}
	
	public static void render() {
		inventory.render();
		stats.render();
	}
	
	public static void renderText() {
		inventory.renderText();
		stats.renderText();
	}
	
	public static void renderPopUp() {
		inventory.renderPopUp();
	}
	
	public static void renderPopUpText() {
		inventory.renderPopUpText();
	}
	
}
