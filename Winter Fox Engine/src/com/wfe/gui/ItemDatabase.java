package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;

public class ItemDatabase {

	private static ItemDatabase itemDatabase;
	
	public static List<Item> items = new ArrayList<Item>();
	
	public static final int BANANA = 0;
	public static final int COOKIE = 1;
	public static final int APPLE = 2;
	public static final int LOG_WALL = 3;
	public static final int AXE = 4;
	public static final int SHROOM = 5;
	public static final int BREAD = 6;
	
	private ItemDatabase() {
		items.add(new Item(BANANA, ResourceManager.getTexture("banana_ui"), "Banana"));
		items.add(new Item(COOKIE, ResourceManager.getTexture("cookie_ui"), "Cookie"));
		items.add(new Item(APPLE, ResourceManager.getTexture("apple_ui"), "Apple"));
		items.add(new Item(LOG_WALL, ResourceManager.getTexture("log_wall_ui"), "Log Wal"));
		items.add(new Item(AXE, ResourceManager.getTexture("axe_ui"), "Axe"));
		items.add(new Item(SHROOM, ResourceManager.getTexture("shroom_ui"), "Shroom"));
		items.add(new Item(BREAD, ResourceManager.getTexture("bread_ui"), "Bread"));
	}
	
	public static void create() {
		if(itemDatabase == null) {
			itemDatabase = new ItemDatabase();
		}
	}
	
}
