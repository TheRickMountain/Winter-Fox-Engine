package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Furnace extends Entity {
	
	public Furnace() {
		super(ResourceManager.getMesh("furnace"),
				new Material(ResourceManager.getTexture("furnace")), new Transformation());	
		setId(15);
		transform.setScale(0.5f);
		setTag("furnace");
		setWalkable(false);
		addComponent(new ColliderComponent(1, 1, 1));
	}

}
