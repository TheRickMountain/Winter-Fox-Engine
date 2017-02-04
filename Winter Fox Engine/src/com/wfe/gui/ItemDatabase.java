package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.blueprints.AxeBlueprint;
import com.wfe.core.ResourceManager;

public class ItemDatabase {
	
	private static List<Item> database = new ArrayList<Item>();
	
	private static ItemDatabase instance;
	
	private ItemDatabase() {
		addItem(new Item(Item.APPLE, "Apple", "Sweet apple is so sweet", ResourceManager.getTexture("apple_ui"),
				null, ItemType.CONSUMABLE, 40));
		
		addItem(new Item(Item.FLINT, "Flint", "Common useful resource", ResourceManager.getTexture("flint_ui"), 
				null, ItemType.RESOURCE, 40));
		
		addItem(new Item(Item.AXE, "Axe" , "Can chop trees and protect from enemies", ResourceManager.getTexture("axe_ui"),
				new AxeBlueprint(), ItemType.TOOL, 1));
		
		addItem(new Item(Item.STICK, "Stick", "Common useful resource", ResourceManager.getTexture("stick_ui"),
				null, ItemType.RESOURCE, 40));
		
		addItem(new Item(Item.MUSHROOM, "Mushroom", "Common useful resource", ResourceManager.getTexture("mushroom_ui"),
				null, ItemType.RESOURCE, 40));
		
		addItem(new Item(Item.LOG, "Log", "Common useful resource", ResourceManager.getTexture("log_ui"),
				null, ItemType.RESOURCE, 40));
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
