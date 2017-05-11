package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class Mushroom extends Entity {

	public Mushroom(Transformation transform) {
		super(ResourceManager.getMesh("mushroom"), 
				new Material(ResourceManager.getTexture("mushroom")), transform);
		setId(20);
		setTag("consumable");
		transform.setScale(0.5f);
		addComponent(new GatherableComponent(ItemDatabase.getItem(Item.MUSHROOM), 1,
				ResourceManager.getSound("taking")));
	}

}
