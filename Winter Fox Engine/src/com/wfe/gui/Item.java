package com.wfe.gui;

import com.wfe.blueprints.Blueprint;
import com.wfe.textures.Texture;

public class Item {
	
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
	public static final int HOE = 12;
	public static final int FIBER = 13;
	public static final int FLINT = 14;
	public static final int STICK = 15;
	public static final int ROPE = 16;
	public static final int BOW = 17;
	public static final int WHEAT = 18;
	
	public final int ID;
	public final Texture icon;
	public final String name;
	public final ItemType type;
	public int starvation;
	public Blueprint entityBlueprint;
	public int stack;
	public int[] ingredients;
	
	public Item(int ID, Texture icon, String name, ItemType type, int starvation, int stack, int[] ingredients) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = null;
		this.stack = stack;
		this.ingredients = ingredients;
	}
	
	public Item(int ID, Texture icon, String name, ItemType type, int starvation, Blueprint entity, int stack, int[] ingredients) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = entity;
		this.stack = stack;
		this.ingredients = ingredients;
	}
	
	public Item(int ID, Texture icon, String name, ItemType type, int starvation, int stack) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = null;
		this.stack = stack;
	}
	
	public Item(int ID, Texture icon, String name, ItemType type, int starvation, Blueprint entity, int stack) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.starvation = starvation;
		this.entityBlueprint = entity;
		this.stack = stack;
	}
	
}
