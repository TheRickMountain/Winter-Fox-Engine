package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Campfire extends Entity {

	public Campfire(Transformation transform) {
		super(ResourceManager.getMesh("campfire"),
				new Material(ResourceManager.getTexture("campfire")), transform);	
		setId(6);
		transform.setScale(0.4f);
		setWalkable(false);
		addComponent(new ColliderComponent(0.5f, 0.5f, 0.5f));
	}
	
}
