package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;

public class Shroom extends Entity {

	public Shroom(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("shroom"), 
				new Material(ResourceManager.getTexture("shroom")), transform);
		addComponent(new GatherableComponent(Item.SHROOM, player, getTransform(),
				new BoundingBox(0.5f, 0.5f, 0.5f, getTransform()), 0.5f));
		transform.setScale(0.5f);
	}

}
