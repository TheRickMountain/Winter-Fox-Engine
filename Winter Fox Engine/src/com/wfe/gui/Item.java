package com.wfe.gui;

import com.wfe.blueprints.Blueprint;
import com.wfe.textures.Texture;

public class Item {
	
	public final int ID;
	public final Texture icon;
	public final String name;
	public final ItemType type;
	public Blueprint entityBlueprint;
	
	public Item(int ID, Texture icon, String name, ItemType type) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.entityBlueprint = null;
	}
	
	public Item(int ID, Texture icon, String name, ItemType type, Blueprint entity) {
		this.ID = ID;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.entityBlueprint = entity;
	}
	
}
