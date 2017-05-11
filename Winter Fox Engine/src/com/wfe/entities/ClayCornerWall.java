package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class ClayCornerWall extends Entity {

	public ClayCornerWall() {
		super(ResourceManager.getMesh("clay_corner_wall"),
				new Material(ResourceManager.getTexture("clay_wall")), new Transformation());	
		setId(8);
		setTag("corner_clay_wall");
		transform.setScale(0.5f);
		setWalkable(false);
		addComponent(new ColliderComponent(1, 1, 1));
	}
	
}
