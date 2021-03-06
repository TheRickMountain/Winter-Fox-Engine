package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class WindowWall extends Entity {

	public WindowWall() {
		super(ResourceManager.getMesh("window_wall"),
				new Material(ResourceManager.getTexture("walls")), new Transformation());	
		setId(29);
		setTag("window wall");
		transform.setScale(0.5f);
		setWalkable(false);
		addComponent(new ColliderComponent(1, 1, 1));
	}
	
}
