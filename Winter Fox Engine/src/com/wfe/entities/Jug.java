package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Jug extends Entity {

	public Jug() {
		super(ResourceManager.getMesh("jug"),
				new Material(ResourceManager.getTexture("jug")), new Transformation());	
		transform.setScale(1.3f);
		setTag("jug");
		setWalkable(false);
		addComponent(new ColliderComponent(0.5f, 1, 0.5f));
	}
	
}
