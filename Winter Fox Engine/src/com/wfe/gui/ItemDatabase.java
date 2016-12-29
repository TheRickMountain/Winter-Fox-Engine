package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;

public class ItemDatabase {

	private static ItemDatabase itemDatabase;
	
	public static List<Item> items = new ArrayList<Item>();
	
	public static final int BANANA = 0;
	public static final int COOKIE = 1;
	
	private ItemDatabase() {
		items.add(new Item(BANANA, ResourceManager.getTexture("banana_ui"), "Banana"));
		items.add(new Item(COOKIE, ResourceManager.getTexture("cookie_ui"), "Cookie"));
	}
	
	public static void create() {
		if(itemDatabase == null) {
			itemDatabase = new ItemDatabase();
		}
	}
	
}
