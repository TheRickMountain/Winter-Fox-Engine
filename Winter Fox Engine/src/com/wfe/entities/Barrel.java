package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Barrel extends Entity {

	public Barrel(Transformation transform) {
		super(ResourceManager.getMesh("barrel"),
				new Material(ResourceManager.getTexture("barrel")), transform);	
		setId(3);
		transform.setScale(0.4f);
		setWalkable(false);
		addComponent(new ColliderComponent(0.75f, 1, 0.75f));
	}
	
}
