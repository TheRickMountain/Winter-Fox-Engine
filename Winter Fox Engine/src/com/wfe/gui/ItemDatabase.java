package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.blueprints.BarrelBlueprint;
import com.wfe.blueprints.BushBlueprint;
import com.wfe.blueprints.CrossWallBlueprint;
import com.wfe.blueprints.DoorWallBlueprint;
import com.wfe.blueprints.FurnaceBlueprint;
import com.wfe.blueprints.WallBlueprint;
import com.wfe.blueprints.WindowWallBlueprint;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;

public class ItemDatabase {

	private static ItemDatabase itemDatabase;
	
	public static List<Item> items = new ArrayList<Item>();
	
	private ItemDatabase() {
		items.add(new Item(Item.BANANA, ResourceManager.getTexture("banana_ui"), "Banana", ItemType.FOOD, 15, 20));
		
		items.add(new Item(Item.COOKIE, ResourceManager.getTexture("cookie_ui"), "Cookie", ItemType.FOOD, 4, 20));
		
		items.add(new Item(Item.APPLE, ResourceManager.getTexture("apple_ui"), "Apple", ItemType.FOOD, 6, 20));
		
		items.add(new Item(Item.WALL, ResourceManager.getTexture("wall_ui"), "Wall", ItemType.BUILDING, 0,
				new WallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 5}));
		
		items.add(new Item(Item.AXE, ResourceManager.getTexture("axe_ui"), "Axe", ItemType.WEAPON, 0, 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1, Item.FLINT, 1}));
		
		items.add(new Item(Item.SHROOM, ResourceManager.getTexture("shroom_ui"), "Shroom", ItemType.FOOD, 5, 20));
		
		items.add(new Item(Item.BREAD, ResourceManager.getTexture("bread_ui"), "Bread", ItemType.FOOD, 10, 20));
		
		items.add(new Item(Item.CROSS_WALL, ResourceManager.getTexture("cross_wall_ui"), "Cross Wall", ItemType.BUILDING, 0,
				new CrossWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 5}));
		
		items.add(new Item(Item.WINDOW_WALL, ResourceManager.getTexture("window_wall_ui"), "Window Wall", ItemType.BUILDING, 0,
				new WindowWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 4}));
		
		items.add(new Item(Item.DOOR_WALL, ResourceManager.getTexture("door_wall_ui"), "Door Wall", ItemType.BUILDING, 0,
				new DoorWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 2}));
		
		items.add(new Item(Item.BUSH, ResourceManager.getTexture("bush_ui"), "Bush", ItemType.BUILDING, 0,
				new BushBlueprint(new Transformation()), 10));
		
		items.add(new Item(Item.AMANITA, ResourceManager.getTexture("amanita_ui"), "Amanita", ItemType.FOOD, -10, 20));
		
		items.add(new Item(Item.HOE, ResourceManager.getTexture("hoe_ui"), "Hoe", ItemType.TOOL, 0, 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1, Item.FLINT, 1}));
		
		items.add(new Item(Item.FIBER, ResourceManager.getTexture("fiber_ui"), "Fiber", ItemType.ITEM, 0, 40));
		
		items.add(new Item(Item.FLINT, ResourceManager.getTexture("flint_ui"), "Flint", ItemType.ITEM, 0, 40));
		
		items.add(new Item(Item.STICK, ResourceManager.getTexture("stick_ui"), "Stick", ItemType.ITEM, 0, 20));
		
		items.add(new Item(Item.ROPE, ResourceManager.getTexture("rope_ui"), "Rope", ItemType.ITEM, 0, 10,
				new int[] {Item.FIBER, 3}));
		
		items.add(new Item(Item.BOW, ResourceManager.getTexture("bow_ui"), "Bow", ItemType.WEAPON, 0, 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1}));
		
		items.add(new Item(Item.LOG, ResourceManager.getTexture("log_ui"), "Log", ItemType.ITEM, 0, 40));
		
		items.add(new Item(Item.WHEAT_SEED, ResourceManager.getTexture("wheat_seed_ui"), "Wheat seed", 
				ItemType.ITEM, 0, 40, new int[] {Item.WHEAT, 1}));
		
		items.add(new Item(Item.WHEAT, ResourceManager.getTexture("wheat_ui"), "Wheat", ItemType.ITEM, 0, 40));
		
		items.add(new Item(Item.FLOUR, ResourceManager.getTexture("flour_ui"), "Flour", ItemType.ITEM, 0, 40,
				new int[] {Item.WHEAT, 5}));
		
		items.add(new Item(Item.DOUGH, ResourceManager.getTexture("dough_ui"), "Dough", ItemType.ITEM, 0, 20,
				new int[] {Item.FLOUR, 1}));
		
		items.add(new Item(Item.FURNACE, ResourceManager.getTexture("furnace_ui"), "Furnace", ItemType.BUILDING, 0,
				new FurnaceBlueprint(new Transformation()), 5,
				new int[] {Item.FLINT, 10}));
		
		items.add(new Item(Item.BARREL, ResourceManager.getTexture("barrel_ui"), "Barrel", ItemType.BUILDING, 0,
				new BarrelBlueprint(new Transformation()), 5,
				new int[] {Item.LOG, 10}));
	}
	
	public static void create() {
		if(itemDatabase == null) {
			itemDatabase = new ItemDatabase();
		}
	}
	
	public static Item getItem(int id) {
		return items.get(id);
	}
	
}
