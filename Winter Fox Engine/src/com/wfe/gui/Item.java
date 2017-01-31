package com.wfe.gui;

import com.wfe.textures.Texture;

public class Item {

	public static final int APPLE = 0;
	public static final int FLINT = 1;
	public static final int AXE = 2;
	
	public final int id;
	public final String name;
	public final Texture icon;
	public final ItemType type;
	public final int stackSize;
	
	public Item(int id, String name, Texture icon, ItemType type, int stackSize) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.stackSize = stackSize;
	}
	
}
