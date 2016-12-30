package com.wfe.gui;

import com.wfe.textures.Texture;

public class Item {
	
	public final int ID;
	public final Texture icon;
	public final String name;
	public final ItemType type;
	
	public Item(int ID, Texture icon, String name, ItemType type) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
	}
	
}
