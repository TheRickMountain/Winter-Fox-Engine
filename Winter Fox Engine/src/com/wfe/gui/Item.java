package com.wfe.gui;

import com.wfe.ecs.Entity;
import com.wfe.textures.Texture;

public class Item {

	public static final int NULL = 0;
	public static final int APPLE = 1;
	public static final int FLINT = 2;
	public static final int AXE = 3;
	public static final int STICK = 4;
	public static final int MUSHROOM = 5;
	public static final int LOG = 6;
	public static final int PICKAXE = 7;
	public static final int HOE = 8;
	public static final int FIBER = 9;
	public static final int WALL = 10;
	public static final int CROSS_WALL = 11;
	public static final int WINDOW_WALL = 12;
	public static final int DOOR_WALL = 13;
	public static final int ROPE = 14;
	public static final int WELL = 15;
	public static final int WATERSKIN = 16;
	public static final int LEATHER = 17;
	public static final int LAVENDER = 18;
	public static final int HONEY = 19;
	public static final int SHARP_FLINT = 20;
	public static final int CLUB = 21;
	public static final int WHEAT = 22;
	public static final int WHEAT_SEEDS = 23;
	public static final int CLAY = 24;
	public static final int JUG = 25;
	public static final int BASKET = 26;
	
	public final int id;
	public final String name;
	public final String description;
	public final Texture icon;
	public final Entity entity;
	public final ItemType type;
	public final int stackSize;
	public final int health;
	public final int hunger;
	public final int thirst;
	public final int volume;
	public final int[] ingredients;
	private boolean isHasCraft;
	
	public Item(int id, String name, String description, Texture icon, Entity entity, 
			ItemType type, int health, int hunger, int thirst, int volume, int stackSize, int[] ingredients) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.entity = entity;
		this.type = type;
		this.health = health;
		this.hunger = hunger;
		this.thirst = thirst;
		this.volume = volume;
		this.stackSize = stackSize;
		this.ingredients = ingredients;
		if(this.ingredients != null) {
			isHasCraft = true;
		}
	}
	
	public boolean isHasCraft() {
		return isHasCraft;
	}
	
}
