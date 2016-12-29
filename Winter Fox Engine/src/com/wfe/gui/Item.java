package com.wfe.gui;

import com.wfe.textures.Texture;

public class Item {
	
	public final Texture icon;
	public final String name;
	public final int ID;
	
	public Item(int ID, Texture icon, String name) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
	}
	
}
