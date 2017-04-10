package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.entities.Axe;
import com.wfe.entities.Club;
import com.wfe.entities.CrossWall;
import com.wfe.entities.DoorWall;
import com.wfe.entities.Hoe;
import com.wfe.entities.Pickaxe;
import com.wfe.entities.Wall;
import com.wfe.entities.Well;
import com.wfe.entities.WindowWall;

public class ItemDatabase {
	
	private static List<Item> database = new ArrayList<Item>();
	
	private static ItemDatabase instance;
	
	private ItemDatabase() {
		addItem(new Item(Item.NULL, "", "", null, null, ItemType.NULL, 0, 0, 0, 0, null));
		
		addItem(new Item(Item.APPLE, "Apple", "Sweet apple is so sweet", ResourceManager.getTexture("apple_ui"),
				null, ItemType.CONSUMABLE, 10, 5, 0, 40, null));
		
		addItem(new Item(Item.FLINT, "Flint", "Common useful resource", ResourceManager.getTexture("flint_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.AXE, "Axe" , "Can chop trees and protect from enemies", ResourceManager.getTexture("axe_ui"),
				new Axe(), ItemType.TOOL, 0, 0, 0, 1, 
				new int[] {Item.SHARP_FLINT, 1, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.STICK, "Stick", "Common useful resource", ResourceManager.getTexture("stick_ui"),
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.MUSHROOM, "Mushroom", "Common useful resource", ResourceManager.getTexture("mushroom_ui"),
				null, ItemType.CONSUMABLE, 5, 0, 0, 40, null));
		
		addItem(new Item(Item.LOG, "Log", "Common useful resource", ResourceManager.getTexture("log_ui"),
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.PICKAXE, "Pickaxe", "Common useful resource", ResourceManager.getTexture("pickaxe_ui"),
				new Pickaxe(), ItemType.TOOL, 0, 0, 0, 1, 
				new int[] {Item.SHARP_FLINT, 2, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.HOE, "Hoe", "Common useful resource", ResourceManager.getTexture("hoe_ui"),
				new Hoe(), ItemType.TOOL, 0, 0, 0, 1, 
				new int[] {Item.SHARP_FLINT, 1, Item.STICK, 1, Item.ROPE, 1}));
		
		addItem(new Item(Item.FIBER, "Fiber", "Common useful resource", ResourceManager.getTexture("fiber_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.WALL, "Wall", "Common useful resource", ResourceManager.getTexture("wall_ui"), 
				new Wall(), ItemType.BUILDING, 0, 0, 0, 40, new int[]{Item.LOG, 5}));
		
		addItem(new Item(Item.CROSS_WALL, "Cross wall", "Common useful resource", ResourceManager.getTexture("cross_wall_ui"), 
				new CrossWall(), ItemType.BUILDING, 0, 0, 0, 40, new int[] {Item.LOG, 5}));
		
		addItem(new Item(Item.WINDOW_WALL, "Window wall", "Common useful resource", ResourceManager.getTexture("window_wall_ui"), 
				new WindowWall(), ItemType.BUILDING, 0, 0, 0, 40, new int[] {Item.LOG, 4}));
		
		addItem(new Item(Item.DOOR_WALL, "Door wall", "Common useful resource", ResourceManager.getTexture("door_wall_ui"), 
				new DoorWall(), ItemType.BUILDING, 0, 0, 0, 40, new int[] {Item.LOG, 2}));
		
		addItem(new Item(Item.ROPE, "Rope", "Common useful resource", ResourceManager.getTexture("rope_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, new int[] {Item.FIBER, 5}));
		
		addItem(new Item(Item.WELL, "Well", "Common useful resource", ResourceManager.getTexture("well_ui"), 
				new Well(), ItemType.BUILDING, 0, 0, 0, 1, new int[] {Item.FLINT, 15}));
		
		addItem(new Item(Item.WATERSKIN, "Waterskin", "Common useful resource", ResourceManager.getTexture("waterskin_ui"), 
				null, ItemType.WATER_STORAGE, 0, 20, 100, 1, new int[] {Item.LEATHER, 1, Item.ROPE, 2}));
		
		addItem(new Item(Item.LEATHER, "Leather", "Common useful resource", ResourceManager.getTexture("leather_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.LAVENDER, "Lavender", "Common useful resource", ResourceManager.getTexture("lavender_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.HONEY, "Honey", "Common useful resource", ResourceManager.getTexture("honey_ui"), 
				null, ItemType.CONSUMABLE, 20, -5, 0, 40, null));
		
		addItem(new Item(Item.SHARP_FLINT, "Sharp flint", "Common useful resource", ResourceManager.getTexture("sharp_flint_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 1, new int[] {Item.FLINT, 2}));
		
		addItem(new Item(Item.CLUB, "Club", "Common useful resource", ResourceManager.getTexture("club_ui"), 
				new Club(), ItemType.WEAPON, 0, 0, 0, 1, new int[] {Item.LOG, 1}));
		
		addItem(new Item(Item.WHEAT, "Wheat", "Common useful resource", ResourceManager.getTexture("wheat_ui"), 
				null, ItemType.RESOURCE, 0, 0, 0, 40, null));
		
		addItem(new Item(Item.WHEAT_SEEDS, "Wheat seeds", "Common useful resource", 
				ResourceManager.getTexture("wheat_seeds_ui"), null, ItemType.RESOURCE, 0, 0, 0, 40, 
				new int[]{Item.WHEAT, 1}));
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
