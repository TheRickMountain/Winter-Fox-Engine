package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Well extends Entity {

	public Well() {
		super(ResourceManager.getMesh("well"),
				new Material(ResourceManager.getTexture("well")), new Transformation());	
		transform.setScale(0.75f);
		setTag("well");
		setWalkable(false);
		addComponent(new ColliderComponent(1f, 1f, 1f));
	}
	
}
