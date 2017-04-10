package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Wall extends Entity {

	public Wall() {
		super(ResourceManager.getMesh("wall"),
				new Material(ResourceManager.getTexture("walls")), new Transformation());	
		setTag("wall");
		transform.setScale(0.5f);
		addComponent(new ColliderComponent(1, 1, 1, transform));
	}
	
}
