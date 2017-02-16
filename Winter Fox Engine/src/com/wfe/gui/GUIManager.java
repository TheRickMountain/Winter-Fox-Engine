package com.wfe.gui;

public class GUIManager  {
	
	public static Inventory inventory;
	public static Crafting crafting;
	public static PlayerStats stats;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		crafting = new Crafting();
		stats = new PlayerStats();
	}
	
	public static void update() {	
		inventory.update();
		crafting.update();
		stats.update();
	}
	
	public static void render() {
		inventory.render();
		crafting.render();
		stats.render();
	}
	
	public static void renderText() {
		inventory.renderText();
		crafting.renderText();
		stats.renderText();
	}
	
	public static void renderPopUp() {
		inventory.renderPopUp();
	}
	
	public static void renderPopUpText() {
		inventory.renderPopUpText();
	}
	
}
