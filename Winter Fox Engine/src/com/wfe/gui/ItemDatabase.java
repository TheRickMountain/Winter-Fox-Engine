package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.blueprints.AxeBlueprint;
import com.wfe.blueprints.BarrelBlueprint;
import com.wfe.blueprints.BushBlueprint;
import com.wfe.blueprints.CampfireBlueprint;
import com.wfe.blueprints.CrossWallBlueprint;
import com.wfe.blueprints.DoorWallBlueprint;
import com.wfe.blueprints.FurnaceBlueprint;
import com.wfe.blueprints.HelmetBlueprint;
import com.wfe.blueprints.WallBlueprint;
import com.wfe.blueprints.WindowWallBlueprint;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;

public class ItemDatabase {

	private static ItemDatabase itemDatabase;
	
	public static List<Item> items = new ArrayList<Item>();
	
	private ItemDatabase() {
		items.add(new Item(Item.APPLE, ResourceManager.getTexture("apple_ui"), "Apple",
				"Sow sweet and tasty apple", ItemType.CONSUMABLE, 6, 20));
		
		items.add(new Item(Item.WALL, ResourceManager.getTexture("wall_ui"), "Wall",
				"Build your own house", ItemType.PLACEABLE, 0,
				new WallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 5}));
		
		items.add(new Item(Item.AXE, ResourceManager.getTexture("axe_ui"), "Axe",
				"With an axe, you can chop trees and protect yourself from wild animals", ItemType.TOOL, 0, 
				new AxeBlueprint(new Transformation()), 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1, Item.FLINT, 1}));
		
		items.add(new Item(Item.SHROOM, ResourceManager.getTexture("shroom_ui"), "Shroom",
				"Edible and delicious mushrooms", ItemType.CONSUMABLE, 5, 20));
		
		items.add(new Item(Item.BREAD, ResourceManager.getTexture("bread_ui"), "Bread",
				"What can be better than fresh bread", ItemType.CONSUMABLE, 10, 20));
		
		items.add(new Item(Item.CROSS_WALL, ResourceManager.getTexture("cross_wall_ui"), "Cross Wall",
				"Build your own house", ItemType.PLACEABLE, 0,
				new CrossWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 5}));
		
		items.add(new Item(Item.WINDOW_WALL, ResourceManager.getTexture("window_wall_ui"), "Window Wall",
				"Build your own house", ItemType.PLACEABLE, 0,
				new WindowWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 4}));
		
		items.add(new Item(Item.DOOR_WALL, ResourceManager.getTexture("door_wall_ui"), "Door Wall",
				"Build your own house", ItemType.PLACEABLE, 0,
				new DoorWallBlueprint(new Transformation()), 20,
				new int[] {Item.LOG, 2}));
		
		items.add(new Item(Item.BUSH, ResourceManager.getTexture("bush_ui"), "Bush",
				"One will reap what he'll sow", ItemType.PLACEABLE, 0,
				new BushBlueprint(new Transformation()), 10));
		
		items.add(new Item(Item.AMANITA, ResourceManager.getTexture("amanita_ui"), "Amanita",
				"One will reap what he'll sow", ItemType.CONSUMABLE, -10, 20));
		
		items.add(new Item(Item.HOE, ResourceManager.getTexture("hoe_ui"), "Hoe",
				"Lets you plough the earth for seed planting", ItemType.TOOL, 0, 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1, Item.FLINT, 1}));
		
		items.add(new Item(Item.FIBER, ResourceManager.getTexture("fiber_ui"), "Fiber",
				"Very strong fiber", ItemType.RESOURCE, 0, 40));
		
		items.add(new Item(Item.FLINT, ResourceManager.getTexture("flint_ui"), "Flint",
				"Flint ... Flint everywhere", ItemType.RESOURCE, 0, 40));
		
		items.add(new Item(Item.STICK, ResourceManager.getTexture("stick_ui"), "Stick",
				"Can be used as fuel", ItemType.RESOURCE, 0, 20));
		
		items.add(new Item(Item.ROPE, ResourceManager.getTexture("rope_ui"), "Rope",
				"How many knots do you know?", ItemType.RESOURCE, 0, 10,
				new int[] {Item.FIBER, 3}));
		
		items.add(new Item(Item.BOW, ResourceManager.getTexture("bow_ui"), "Bow",
				"A great weapon to attack a distance", ItemType.WEAPON, 0, 1,
				new int[] {Item.ROPE, 1, Item.STICK, 1}));
		
		items.add(new Item(Item.LOG, ResourceManager.getTexture("log_ui"), "Log",
				"Universal thing, you want to burn!", ItemType.RESOURCE, 0, 40));
		
		items.add(new Item(Item.WHEAT_SEED, ResourceManager.getTexture("wheat_seed_ui"), "Wheat seed",
				"One will reap what he'll sow", 
				ItemType.SEED, 0, 40, new int[] {Item.WHEAT, 1}));
		
		items.add(new Item(Item.WHEAT, ResourceManager.getTexture("wheat_ui"), "Wheat",
				"Test", ItemType.RESOURCE, 0, 40));
		
		items.add(new Item(Item.FLOUR, ResourceManager.getTexture("flour_ui"), "Flour",
				"Test", ItemType.RESOURCE, 0, 40,
				new int[] {Item.WHEAT, 5}));
		
		items.add(new Item(Item.DOUGH, ResourceManager.getTexture("dough_ui"), "Dough",
				"Test", ItemType.RESOURCE, 0, 20,
				new int[] {Item.FLOUR, 1}));
		
		items.add(new Item(Item.FURNACE, ResourceManager.getTexture("furnace_ui"), "Furnace",
				"Test", ItemType.PLACEABLE, 0,
				new FurnaceBlueprint(new Transformation()), 1,
				new int[] {Item.FLINT, 10}));
		
		items.add(new Item(Item.BARREL, ResourceManager.getTexture("barrel_ui"), "Barrel",
				"Test", ItemType.PLACEABLE, 0,
				new BarrelBlueprint(new Transformation()), 1,
				new int[] {Item.LOG, 10}));
		
		items.add(new Item(Item.CAMPFIRE, ResourceManager.getTexture("campfire_ui"), "Campfire",
				"Test", ItemType.PLACEABLE, 0,
				new CampfireBlueprint(new Transformation()), 1,
				new int[] {Item.STICK, 3, Item.FIBER, 5, Item.FLINT, 5}));
		
		items.add(new Item(Item.HELMET, ResourceManager.getTexture("helmet_ui"), "Helmet",
				"With an axe, you can chop trees and protect yourself from wild animals", ItemType.HELMET, 0, 
				new HelmetBlueprint(new Transformation()), 1));
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
