package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.blueprints.AxeBlueprint;
import com.wfe.blueprints.CrossWallBlueprint;
import com.wfe.blueprints.DoorWallBlueprint;
import com.wfe.blueprints.HoeBlueprint;
import com.wfe.blueprints.PickaxeBlueprint;
import com.wfe.blueprints.WallBlueprint;
import com.wfe.blueprints.WindowWallBlueprint;
import com.wfe.core.ResourceManager;

public class ItemDatabase {
	
	private static List<Item> database = new ArrayList<Item>();
	
	private static ItemDatabase instance;
	
	private ItemDatabase() {
		addItem(new Item(Item.APPLE, "Apple", "Sweet apple is so sweet", ResourceManager.getTexture("apple_ui"),
				null, ItemType.CONSUMABLE, 40, null));
		
		addItem(new Item(Item.FLINT, "Flint", "Common useful resource", ResourceManager.getTexture("flint_ui"), 
				null, ItemType.RESOURCE, 40, null));
		
		addItem(new Item(Item.AXE, "Axe" , "Can chop trees and protect from enemies", ResourceManager.getTexture("axe_ui"),
				new AxeBlueprint(), ItemType.TOOL, 1, 
				new int[] {Item.FLINT, 1, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.STICK, "Stick", "Common useful resource", ResourceManager.getTexture("stick_ui"),
				null, ItemType.RESOURCE, 40, null));
		
		addItem(new Item(Item.MUSHROOM, "Mushroom", "Common useful resource", ResourceManager.getTexture("mushroom_ui"),
				null, ItemType.RESOURCE, 40, null));
		
		addItem(new Item(Item.LOG, "Log", "Common useful resource", ResourceManager.getTexture("log_ui"),
				null, ItemType.RESOURCE, 40, null));
		
		addItem(new Item(Item.PICKAXE, "Pickaxe", "Common useful resource", ResourceManager.getTexture("pickaxe_ui"),
				new PickaxeBlueprint(), ItemType.TOOL, 1, 
				new int[] {Item.FLINT, 1, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.HOE, "Hoe", "Common useful resource", ResourceManager.getTexture("hoe_ui"),
				new HoeBlueprint(), ItemType.TOOL, 1, 
				new int[] {Item.FLINT, 1, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.FIBER, "Fiber", "Common useful resource", ResourceManager.getTexture("fiber_ui"), 
				null, ItemType.RESOURCE, 40, null));
		
		addItem(new Item(Item.WALL, "Wall", "Common useful resource", ResourceManager.getTexture("wall_ui"), 
				new WallBlueprint(), ItemType.BUILDING, 40, new int[]{Item.LOG, 5}));
		
		addItem(new Item(Item.CROSS_WALL, "Cross wall", "Common useful resource", ResourceManager.getTexture("cross_wall_ui"), 
				new CrossWallBlueprint(), ItemType.BUILDING, 40, new int[] {Item.LOG, 5}));
		
		addItem(new Item(Item.WINDOW_WALL, "Window wall", "Common useful resource", ResourceManager.getTexture("window_wall_ui"), 
				new WindowWallBlueprint(), ItemType.BUILDING, 40, new int[] {Item.LOG, 4}));
		
		addItem(new Item(Item.DOOR_WALL, "Door wall", "Common useful resource", ResourceManager.getTexture("door_wall_ui"), 
				new DoorWallBlueprint(), ItemType.BUILDING, 40, new int[] {Item.LOG, 2}));
		
		addItem(new Item(Item.ROPE, "Rope", "Common useful resource", ResourceManager.getTexture("rope_ui"), 
				null, ItemType.RESOURCE, 40, new int[] {Item.FIBER, 5}));
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

	protected static List<Item> getDatabase() {
		return database;
	}
	
}
