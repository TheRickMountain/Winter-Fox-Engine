package com.wfe.gui;

import com.wfe.blueprints.Blueprint;
import com.wfe.textures.Texture;

public class Item {
	
	public final int ID;
	public final Texture icon;
	public final String name;
	public final ItemType type;
	public int starvation;
	public Blueprint entityBlueprint;
	public int stack;
	
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
