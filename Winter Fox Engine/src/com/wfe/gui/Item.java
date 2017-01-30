package com.wfe.gui;

import com.wfe.blueprints.Blueprint;
import com.wfe.textures.Texture;

public class Item {
	
	public static final int APPLE = 0;
	public static final int WALL = 1;
	public static final int AXE = 2;
	public static final int SHROOM = 3;
	public static final int BREAD = 4;
	public static final int CROSS_WALL = 5;
	public static final int WINDOW_WALL = 6;
	public static final int DOOR_WALL = 7;
	public static final int BUSH = 8;
	public static final int AMANITA = 9;
	public static final int HOE = 10;
	public static final int FIBER = 11;
	public static final int FLINT = 12;
	public static final int STICK = 13;
	public static final int ROPE = 14;
	public static final int BOW = 15;
	public static final int LOG = 16;
	public static final int WHEAT_SEED = 17;
	public static final int WHEAT = 18;
	public static final int FLOUR = 19;
	public static final int DOUGH = 20;
	public static final int FURNACE = 21;
	public static final int BARREL = 22;
	public static final int CAMPFIRE = 23;
	public static final int HELMET = 24;
	
	public final int id;
	public final Texture icon;
	public final String title;
	public final String description;
	public final ItemType type;
	public int starvation;
	public Blueprint entityBlueprint;
	public int stack;
	public int[] ingredients;
	
	public Item(int ID, Texture icon, String name, String desc, ItemType type, int starvation, int stack, int[] ingredients) {
		this.id = ID;
		this.title = name;
		this.description = desc;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = null;
		this.stack = stack;
		this.ingredients = ingredients;
	}
	
	public Item(int ID, Texture icon, String name, String desc, ItemType type, int starvation, Blueprint entity, int stack, int[] ingredients) {
		this.id = ID;
		this.title = name;
		this.description = desc;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = entity;
		this.stack = stack;
		this.ingredients = ingredients;
	}
	
	public Item(int ID, Texture icon, String name, String desc, ItemType type, int starvation, int stack) {
		this.id = ID;
		this.title = name;
		this.description = desc;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = null;
		this.stack = stack;
	}
	
	public Item(int ID, Texture icon, String name, String desc, ItemType type, int starvation, Blueprint entity, int stack) {
		this.id = ID;
		this.title = name;
		this.description = desc;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = entity;
		this.stack = stack;
	}
	
}
