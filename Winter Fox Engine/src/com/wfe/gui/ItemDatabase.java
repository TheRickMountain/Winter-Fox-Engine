package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;

public class ItemDatabase {
	
	private static List<Item> database = new ArrayList<Item>();
	
	private static ItemDatabase instance;
	
	private ItemDatabase() {
		addItem(new Item(Item.APPLE, "Apple", ResourceManager.getTexture("apple_ui"), 40));
		addItem(new Item(Item.FLINT, "Flint", ResourceManager.getTexture("flint_ui"), 40));
		addItem(new Item(Item.AXE, 	 "Axe",   ResourceManager.getTexture("axe_ui"),    1));
	}
	
	public static void create() {
		if(instance == null)
			instance = new ItemDatabase();
	}
	
	private void addItem(Item item) {
		database.add(item);
	}
	
	public static Item getItem(int id) {
		for(Item item : database) {
			if(item.id == id)
				return item;
		}
		System.err.println("There is no such item!");
		return null;
	}


}
