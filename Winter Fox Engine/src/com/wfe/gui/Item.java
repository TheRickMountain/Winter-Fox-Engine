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
