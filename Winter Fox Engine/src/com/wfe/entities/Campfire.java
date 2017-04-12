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
		transform.setScale(0.4f);
		addComponent(new ColliderComponent(0.5f, 0.5f, 0.5f));
	}
	
}
