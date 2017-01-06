package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.blueprints.BushBlueprint;
import com.wfe.blueprints.CrossWallBlueprint;
import com.wfe.blueprints.DoorWallBlueprint;
import com.wfe.blueprints.WallBlueprint;
import com.wfe.blueprints.WindowWallBlueprint;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;

public class ItemDatabase {

	private static ItemDatabase itemDatabase;
	
	public static List<Item> items = new ArrayList<Item>();
	
	public static final int BANANA = 0;
	public static final int COOKIE = 1;
	public static final int APPLE = 2;
	public static final int WALL = 3;
	public static final int AXE = 4;
	public static final int SHROOM = 5;
	public static final int BREAD = 6;
	public static final int CROSS_WALL = 7;
	public static final int WINDOW_WALL = 8;
	public static final int DOOR_WALL = 9;
	public static final int BUSH = 10;
	public static final int AMANITA = 11;
	
	private ItemDatabase() {
		items.add(new Item(BANANA, ResourceManager.getTexture("banana_ui"), "Banana", ItemType.FOOD, 15));
		items.add(new Item(COOKIE, ResourceManager.getTexture("cookie_ui"), "Cookie", ItemType.FOOD, 4));
		items.add(new Item(APPLE, ResourceManager.getTexture("apple_ui"), "Apple", ItemType.FOOD, 6));
		items.add(new Item(WALL, ResourceManager.getTexture("wall_ui"), "Wall", ItemType.BUILDING, 0,
				new WallBlueprint(new Transformation(0, 0, 0))));
		items.add(new Item(AXE, ResourceManager.getTexture("axe_ui"), "Axe", ItemType.WEAPON, 0));
		items.add(new Item(SHROOM, ResourceManager.getTexture("shroom_ui"), "Shroom", ItemType.FOOD, 5));
		items.add(new Item(BREAD, ResourceManager.getTexture("bread_ui"), "Bread", ItemType.FOOD, 10));
		items.add(new Item(CROSS_WALL, ResourceManager.getTexture("cross_wall_ui"), "Cross Wall", ItemType.BUILDING, 0,
				new CrossWallBlueprint(new Transformation(0, 0, 0))));
		items.add(new Item(WINDOW_WALL, ResourceManager.getTexture("window_wall_ui"), "Window Wall", ItemType.BUILDING, 0,
				new WindowWallBlueprint(new Transformation(0, 0, 0))));
		items.add(new Item(DOOR_WALL, ResourceManager.getTexture("door_wall_ui"), "Door Wall", ItemType.BUILDING, 0,
				new DoorWallBlueprint(new Transformation(0, 0, 0))));
		items.add(new Item(BUSH, ResourceManager.getTexture("bush_ui"), "Bush", ItemType.BUILDING, 0,
				new BushBlueprint(new Transformation(0, 0, 0))));
		items.add(new Item(AMANITA, ResourceManager.getTexture("amanita_ui"), "Amanita", ItemType.FOOD, -10));
	}
	
	public static void create() {
		if(itemDatabase == null) {
			itemDatabase = new ItemDatabase();
		}
	}
	
}
