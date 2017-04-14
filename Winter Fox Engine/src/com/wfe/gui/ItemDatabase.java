package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.ResourceManager;
import com.wfe.entities.Axe;
import com.wfe.entities.Basket;
import com.wfe.entities.Club;
import com.wfe.entities.CrossWall;
import com.wfe.entities.DoorWall;
import com.wfe.entities.Hoe;
import com.wfe.entities.Jug;
import com.wfe.entities.Pickaxe;
import com.wfe.entities.Wall;
import com.wfe.entities.Well;
import com.wfe.entities.WindowWall;

public class ItemDatabase {
	
	private static List<Item> database = new ArrayList<Item>();
	
	private static ItemDatabase instance;
	
	private ItemDatabase() {
		addItem(new ItemBuilder(Item.NULL, "", null, ItemType.NULL).create());
		
		addItem(new ItemBuilder(Item.APPLE, "Apple", ResourceManager.getTexture("apple_ui"), ItemType.CONSUMABLE)
				.setDescription("Sweet apple is so sweet")
				.setHunger(10)
				.setThirst(5)
				.create());
		
		addItem(new ItemBuilder(Item.FLINT, "Flint", ResourceManager.getTexture("flint_ui"), ItemType.RESOURCE)
				.setDescription("Strong and useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.AXE, "Axe", ResourceManager.getTexture("axe_ui"), ItemType.TOOL)
				.setDescription("Can chop trees and protect from enemies")
				.setStackSize(1)
				.setEntity(new Axe())
				.setIngredients(Item.SHARP_FLINT, 1, Item.STICK, 1, Item.ROPE, 1)
				.create());
		
		addItem(new ItemBuilder(Item.STICK, "Stick", ResourceManager.getTexture("stick_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());

		addItem(new ItemBuilder(Item.MUSHROOM, "Mushroom", ResourceManager.getTexture("mushroom_ui"), ItemType.CONSUMABLE)
				.setDescription("Common useful resource")
				.setHunger(5)
				.create());
		
		addItem(new ItemBuilder(Item.LOG, "Log", ResourceManager.getTexture("log_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.PICKAXE, "Pickaxe", ResourceManager.getTexture("pickaxe_ui"), ItemType.TOOL)
				.setDescription("Common useful resource")
				.setEntity(new Pickaxe())
				.setStackSize(1)
				.setIngredients(Item.SHARP_FLINT, 2, Item.STICK, 1, Item.ROPE, 1)
				.create());
		
		addItem(new ItemBuilder(Item.HOE, "Hoe", ResourceManager.getTexture("hoe_ui"), ItemType.TOOL)
				.setDescription("Common useful resource")
				.setEntity(new Hoe())
				.setStackSize(1)
				.setIngredients(Item.SHARP_FLINT, 1, Item.STICK, 1, Item.ROPE, 1)
				.create());
		
		addItem(new ItemBuilder(Item.FIBER, "Fiber", ResourceManager.getTexture("fiber_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());
	
		addItem(new ItemBuilder(Item.WALL, "Wall", ResourceManager.getTexture("wall_ui"), ItemType.BUILDING)
				.setDescription("Common useful resource")
				.setEntity(new Wall())
				.setIngredients(Item.LOG, 5)
				.create());
		
		addItem(new ItemBuilder(Item.CROSS_WALL, "Cross wall", ResourceManager.getTexture("cross_wall_ui"), 
				ItemType.BUILDING)
				.setDescription("Common useful resource") 
				.setEntity(new CrossWall())
				.setIngredients(Item.LOG, 5)
				.create());
		
		addItem(new ItemBuilder(Item.WINDOW_WALL, "Window wall", ResourceManager.getTexture("window_wall_ui"), 
				ItemType.BUILDING)
				.setDescription("Common useful resource")
				.setEntity(new WindowWall())
				.setIngredients(Item.LOG, 4)
				.create());
		
		addItem(new ItemBuilder(Item.DOOR_WALL, "Door wall", ResourceManager.getTexture("door_wall_ui"), ItemType.BUILDING)
				.setDescription("Common useful resource")
				.setEntity(new DoorWall())
				.setIngredients(Item.LOG, 2)
				.create());
		
		addItem(new ItemBuilder(Item.ROPE, "Rope", ResourceManager.getTexture("rope_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.setIngredients(Item.FIBER, 3)
				.create());
		
		addItem(new ItemBuilder(Item.WELL, "Well", ResourceManager.getTexture("well_ui"), ItemType.BUILDING)
				.setDescription("Common useful resource")
				.setStackSize(1)
				.setEntity(new Well())
				.setIngredients(Item.FLINT, 15)
				.create());
		
		addItem(new ItemBuilder(Item.WATERSKIN, "Waterskin", ResourceManager.getTexture("waterskin_ui"), ItemType.TANK)
				.setDescription("Common useful resource")
				.setThirst(20)
				.setVolume(100)
				.setStackSize(1)
				.setIngredients(Item.LEATHER, 1, Item.ROPE, 2)
				.create());
		
		addItem(new ItemBuilder(Item.LEATHER, "Leather", ResourceManager.getTexture("leather_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.LAVENDER, "Lavender", ResourceManager.getTexture("lavender_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.HONEY, "Honey", ResourceManager.getTexture("honey_ui"), ItemType.CONSUMABLE)
				.setDescription("Common useful resource")
				.setHunger(20)
				.setThirst(-5)
				.create());
		
		addItem(new ItemBuilder(Item.SHARP_FLINT, "Sharp flint", ResourceManager.getTexture("sharp_flint_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.setIngredients(Item.FLINT, 2)
				.create());
		
		addItem(new ItemBuilder(Item.CLUB, "Club", ResourceManager.getTexture("club_ui"), ItemType.WEAPON)
				.setDescription("Common useful resource")
				.setStackSize(1)
				.setEntity(new Club())
				.setIngredients(Item.LOG, 1)
				.create());
		
		addItem(new ItemBuilder(Item.WHEAT, "Wheat", ResourceManager.getTexture("wheat_ui"), ItemType.RESOURCE)
				.setDescription("Common useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.WHEAT_SEEDS, "Wheat seeds", ResourceManager.getTexture("wheat_seeds_ui"), ItemType.CONSUMABLE)
				.setDescription("Common useful resource")
				.setHunger(4)
				.setIngredients(Item.WHEAT, 1)
				.create());
		
		addItem(new ItemBuilder(Item.CLAY, "Clay", ResourceManager.getTexture("clay_ui"), ItemType.RESOURCE)
				.setDescription("Strong and useful resource")
				.create());
		
		addItem(new ItemBuilder(Item.JUG, "Jug", ResourceManager.getTexture("jug_ui"), ItemType.BUILDING)
				.setEntity(new Jug())
				.setDescription("Tank for liquidq keeping")
				.setStackSize(1)
				.setIngredients(Item.CLAY, 5)
				.create());
		
		addItem(new ItemBuilder(Item.BASKET, "Basket", ResourceManager.getTexture("basket_ui"), ItemType.BUILDING)
				.setEntity(new Basket())
				.setDescription("Storage")
				.setStackSize(1)
				.setIngredients(Item.ROPE, 5)
				.create());
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
