package com.wfe.gui;

import com.wfe.ecs.Entity;
import com.wfe.textures.Texture;

public class ItemBuilder {

	private final int id;
	private final String name;
	private final Texture icon;
	private final ItemType type;
	
	private String description;
	private Entity entity;
	private int stackSize;
	private int health;
	private int hunger;
	private int thirst;
	private int volume;
	private int[] ingredients;
	
	public ItemBuilder(int id, String name, Texture icon, ItemType type) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.type = type;
		
		description = "";
		stackSize = 40;
	}
	
	public Item create() {
		return new Item(id, name, description, icon, entity, type, health, hunger, thirst, volume, stackSize, ingredients);
	}

	public ItemBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public ItemBuilder setEntity(Entity entity) {
		this.entity = entity;
		return this;
	}

	public ItemBuilder setStackSize(int stackSize) {
		this.stackSize = stackSize;
		return this;
	}
	
	public ItemBuilder setHealth(int health) {
		this.health = health;
		return this;
	}

	public ItemBuilder setHunger(int hunger) {
		this.hunger = hunger;
		return this;
	}

	public ItemBuilder setThirst(int thirst) {
		this.thirst = thirst;
		return this;
	}

	public ItemBuilder setVolume(int volume) {
		this.volume = volume;
		return this;
	}

	public ItemBuilder setIngredients(int... ingredients) {
		this.ingredients = ingredients;
		return this;
	}
	
}
