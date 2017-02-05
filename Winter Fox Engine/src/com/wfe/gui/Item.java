package com.wfe.gui;

import com.wfe.blueprints.Blueprint;
import com.wfe.textures.Texture;

public class Item {

	public static final int APPLE = 0;
	public static final int FLINT = 1;
	public static final int AXE = 2;
	public static final int STICK = 3;
	public static final int MUSHROOM = 4;
	public static final int LOG = 5;
	public static final int PICKAXE = 6;
	public static final int HOE = 7;
	public static final int FIBER = 8;
	public static final int WALL = 9;
	public static final int CROSS_WALL = 10;
	public static final int WINDOW_WALL = 11;
	public static final int DOOR_WALL = 12;
	
	public final int id;
	public final String name;
	public final String description;
	public final Texture icon;
	public final Blueprint blueprint;
	public final ItemType type;
	public final int stackSize;
	
	public Item(int id, String name, String description, Texture icon, Blueprint blueprint, 
			ItemType type, int stackSize) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.blueprint = blueprint;
		this.type = type;
		this.stackSize = stackSize;
	}
	
}
